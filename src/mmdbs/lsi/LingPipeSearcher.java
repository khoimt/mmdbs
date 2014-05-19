package mmdbs.lsi;

import com.aliasi.matrix.SvdMatrix;

import java.util.Arrays;

public class LingPipeSearcher {

    public LingPipeSearcher() {
    }

    public LingPipeSearcher(String query) {
        this.query = query;
    }

    public LingPipeSearcher(double[][] termDocArr, String[] termArr, String query) {
        this.termDocumentArr = termDocArr;
        this.termArr = termArr;
        this.query = query;
    }

    public String query = null;

    public double[][] termDocumentArr
            = new double[][]{
                {1, 0, 0, 1, 0, 0, 0, 0, 0},
                {1, 0, 1, 0, 0, 0, 0, 0, 0},
                {1, 1, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0},
                {0, 1, 1, 2, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 1, 1, 1, 0},
                {0, 0, 0, 0, 0, 0, 1, 1, 1},
                {0, 0, 0, 0, 0, 0, 0, 1, 1}
            };

    public String[] termArr
            = new String[]{
                "human",
                "interface",
                "computer",
                "user",
                "system",
                "response",
                "time",
                "EPS",
                "survey",
                "trees",
                "graph",
                "minors"
            };

    static final int NUM_FACTORS = 2;

    public void run() {
        double featureInit = 0.01;
        double initialLearningRate = 0.005;
        int annealingRate = 1000;
        double regularization = 0.00;
        double minImprovement = 0.0000;
        int minEpochs = 10;
        int maxEpochs = 50000;

//        System.out.println("  Computing SVD");
//        System.out.println("    maxFactors=" + NUM_FACTORS);
//        System.out.println("    featureInit=" + featureInit);
//        System.out.println("    initialLearningRate=" + initialLearningRate);
//        System.out.println("    annealingRate=" + annealingRate);
//        System.out.println("    regularization" + regularization);
//        System.out.println("    minImprovement=" + minImprovement);
//        System.out.println("    minEpochs=" + minEpochs);
//        System.out.println("    maxEpochs=" + maxEpochs);
        SvdMatrix matrix
                = SvdMatrix.svd(this.termDocumentArr,
                        NUM_FACTORS,
                        featureInit,
                        initialLearningRate,
                        annealingRate,
                        regularization,
                        null,
                        minImprovement,
                        minEpochs,
                        maxEpochs);

        double[] scales = matrix.singularValues();
        double[][] termVectors = matrix.leftSingularVectors();
        double[][] docVectors = matrix.rightSingularVectors();

        LSILogger.log("Eigenvalues Matrix");
        for (int k = 0; k < NUM_FACTORS; ++k) {
            LSILogger.print(String.format("%d  %4.2f\n", k, scales[k]));
        }

        LSILogger.log("\nTerm Vectors");
        for (int i = 0; i < termVectors.length; ++i) {
            LSILogger.print("(");
            for (int k = 0; k < NUM_FACTORS; ++k) {
                if (k > 0) {
                    LSILogger.print(", ");
                }
                LSILogger.print(String.format("% 5.2f", termVectors[i][k]));
            }
            LSILogger.log(")  ");
        }

        LSILogger.log("\nDocument Vectors");
        for (int j = 0; j < docVectors.length; ++j) {
            LSILogger.print("(");
            for (int k = 0; k < NUM_FACTORS; ++k) {
                if (k > 0) {
                    LSILogger.print(", ");
                }
                LSILogger.print(String.format("% 5.2f", docVectors[j][k]));
            }
            LSILogger.log(")  ");
        }

        if (this.query != null) {
            this.search(scales, termVectors, docVectors, query);
        }
    }

    public void search(double[] scales,
            double[][] termVectors,
            double[][] docVectors,
            String arg) {
        String[] terms = arg.split(" |,"); // space or comma separated

        double[] queryVector = new double[NUM_FACTORS];
        Arrays.fill(queryVector, 0.0);

        for (String term : terms) {
            addTermVector(term, termVectors, queryVector);
        }

        LSILogger.print("\nQuery=" + Arrays.asList(terms));
        LSILogger.print("Query Vector=(");
        for (int k = 0; k < queryVector.length; ++k) {
            if (k > 0) {
                LSILogger.print(", ");
            }
            LSILogger.print(String.format("% 5.2f", queryVector[k]));
        }
        LSILogger.log(" )");

        LSILogger.log("\nResult and scores:");
        for (int j = 0; j < docVectors.length; ++j) {
            double score = dotProduct(queryVector, docVectors[j], scales);
            score = cosine(queryVector, docVectors[j], scales);
            LSILogger.print(String.format("  %d: % 5.2f \n", j, score));
        }

//        System.out.println("\nTERM SCORES VS. QUERY");
//        for (int i = 0; i < termVectors.length; ++i) {
//            double score = dotProduct(queryVector, termVectors[i], scales);
//            score = cosine(queryVector, termVectors[i], scales);
//            System.out.printf("  %d: % 5.2f  %s\n", i, score, termArr[i]);
//        }
    }

    public void addTermVector(String term, double[][] termVectors, double[] queryVector) {
        for (int i = 0; i < termArr.length; ++i) {
            if (termArr[i].equals(term)) {
                for (int j = 0; j < NUM_FACTORS; ++j) {
                    queryVector[j] += termVectors[i][j];
                }
                return;
            }
        }
    }

    public static double dotProduct(double[] xs, double[] ys, double[] scales) {
        double sum = 0.0;
        for (int k = 0; k < xs.length; ++k) {
            sum += xs[k] * ys[k] * scales[k];
        }
        return sum;
    }

    public static double cosine(double[] xs, double[] ys, double[] scales) {
        double product = 0.0;
        double xsLengthSquared = 0.0;
        double ysLengthSquared = 0.0;
        for (int k = 0; k < xs.length; ++k) {
            double sqrtScale = Math.sqrt(scales[k]);
            double scaledXs = sqrtScale * xs[k];
            double scaledYs = sqrtScale * ys[k];
            xsLengthSquared += scaledXs * scaledXs;
            ysLengthSquared += scaledYs * scaledYs;
            product += scaledXs * scaledYs;
        }
        return product / Math.sqrt(xsLengthSquared * ysLengthSquared);
    }

}
