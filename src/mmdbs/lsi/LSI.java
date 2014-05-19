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
        Indexer indexer = MMDBSFactory.createIndexer(dataDir, indexDir);
        indexer.index();
        
        IndexReaderInterface idxReader = MMDBSFactory.createIndexReader(indexDir);
        int arr[][] = idxReader.getTermDocumentMatrix();
        
        System.out.println(arr.length);
        
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                System.out.print(arr[i][j] + " ");
            }
            System.out.println();
        }
    }
    
}
