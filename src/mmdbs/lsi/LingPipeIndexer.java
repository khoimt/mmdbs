//package mmdbs.lsi;
//
//import java.io.File;
//import java.io.IOException;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.document.Document;
//import org.apache.lucene.index.IndexWriter;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//
//public class LingPipeIndexer implements Indexer {
//
//    protected String _dataDir;
//    protected String _indexDir;
//
//    LingPipeIndexer(String dataDir, String indexDir) {
//        this._dataDir = dataDir.trim();
//        this._indexDir = indexDir.trim();
//    }
//
//    @Override
//    public void index() throws Exception {
//        File dataDir = new File(this._dataDir);
//        if (!dataDir.isDirectory()) {
//            throw new Exception("data dir is vot validate");
//        }
//
//        System.out.println("Start Lucene Indexing:");
//        for (File file : dataDir.listFiles()) {
//            IndexWriter writer = new IndexWriter(
//                    FSDirectory.open(new File(this._indexDir)),
//                    new StandardAnalyzer(Version.LUCENE_CURRENT),
//                    true, IndexWriter.MaxFieldLength.LIMITED);
//            indexDocs(writer, file);
//            writer.optimize();
//            writer.close();
//        }
//    }
//
//    public static void indexDocs(IndexWriter writer, File file)
//            throws IOException {
//        if (file.canRead()) {
//            if (file.isDirectory()) {
//                String[] files = file.list();
//                if (files != null) {
//                    for (int i = 0; i < files.length; i++) {
//                        indexDocs(writer, new File(file, files[i]));
//                    }
//                }
//            } else {
//                System.out.println("index " + file);
//                Document doc = new Document();
//                doc.add(null);
//                writer.addDocument(FileDocument.Document(file));
//            }
//        }
//    }
//}
