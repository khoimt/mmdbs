/**
 * @author khoimt
 */
package mmdbs.lsi;

public class LSI {

    public static String dataDir = "./data";
    public static String indexDir = "./index";

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
        if (dataDir.trim().isEmpty() || indexDir.trim().isEmpty()) {
            LSILogger.log("data/index directories are not set");
            return;
        }

        Indexer indexer = MMDBSFactory.createIndexer(dataDir, indexDir);
        indexer.index();
    }
}
