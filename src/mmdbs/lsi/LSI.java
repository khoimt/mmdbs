/**
 * @author khoimt
 */
package mmdbs.lsi;

import java.io.IOException;

public class LSI {

    public static String dataDir = "./data";
    public static String indexDir = "./index";
    public static IndexReaderInterface idxReader = null;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                javax.swing.UIManager.setLookAndFeel(info.getClassName());
                break;
            }
        }

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                LSIWindow window = new LSIWindow();
                LSILogger.setOutputPanel(window.getConsolePanel());
                window.setVisible(true);
            }
        });

//        Indexer indexer = MMDBSFactory.createIndexer(dataDir, indexDir);
//        indexer.index();
//
//        IndexReaderInterface idxReader = MMDBSFactory.createIndexReader(indexDir);
//        int arr[][] = idxReader.getTermDocumentMatrix();
//
//        System.out.println(arr.length);
//
//        for (int i = 0; i < arr.length; i++) {
//            for (int j = 0; j < arr[i].length; j++) {
//                System.out.print(arr[i][j] + " ");
//            }
//            System.out.println();
//        }
    }

    public static void index(String dataDir, String indexDir) throws Exception {
        if (dataDir.trim().isEmpty()) {
            LSILogger.log("data/index directories are not set");
            return;
        }

        if (indexDir.trim().isEmpty()) {
            indexDir = LSI.indexDir;
        }
        
        LSI.dataDir = dataDir;
        LSI.indexDir = indexDir;

        Indexer indexer = MMDBSFactory.createIndexer(dataDir, indexDir);
        indexer.index();
    }

    public static IndexReaderInterface getIndexReader(String indexDir) throws IOException {
        if (idxReader == null)
            idxReader = MMDBSFactory.createIndexReader(indexDir);
        return idxReader;
    }
    
    public static void printTermDocMatrix(String indexDir) throws IOException, Exception {
        LSI.getIndexReader(indexDir);
        String termArr[] = idxReader.getTerms();
        int[][] freqArr = idxReader.getTermDocumentMatrix();
        
        LSILogger.log("\nTerm-Document Matrix:");
        
        int i = 0;
        for (String term: termArr) {
            LSILogger.print(String.format("%13s:", term));
            for (int j = 0; j < freqArr[i].length; j++) {
                LSILogger.print(String.format("%3d", freqArr[i][j]));
            }
            LSILogger.log("");
            i++;
        }
    }
}
