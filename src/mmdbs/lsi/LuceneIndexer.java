package mmdbs.lsi;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.KeywordAnalyzer;
import org.apache.lucene.analysis.PerFieldAnalyzerWrapper;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class LuceneIndexer implements Indexer {

    protected String _dataDir;
    protected String _indexDir;

    LuceneIndexer(String dataDir, String indexDir) {
        this._dataDir = dataDir.trim();
        this._indexDir = indexDir.trim();
    }

    @Override
    public void index() throws Exception {
        File dataDir = new File(this._dataDir);
        if (!dataDir.isDirectory()) {
            throw new Exception("data dir is vot validate");
        }

        System.out.println("Start Lucene Indexing:");
        IndexWriter writer = new IndexWriter(
                FSDirectory.open(new File(this._indexDir)),
                new StandardAnalyzer(Version.LUCENE_30),
                true, IndexWriter.MaxFieldLength.LIMITED);

        for (File file : dataDir.listFiles()) {
            indexDocs(writer, file);
        }
        writer.optimize();
        writer.close();
    }

    public static void indexDocs(IndexWriter writer, File file)
            throws IOException {
        if (file.canRead()) {
            if (file.isDirectory()) {
                String[] files = file.list();
                if (files != null) {
                    for (int i = 0; i < files.length; i++) {
                        indexDocs(writer, new File(file, files[i]));
                    }
                }
            } else {
                System.out.println("index " + file);
                Document doc = new Document();
                doc.add(new Field("contents", new FileReader(file)));
                writer.addDocument(doc);
            }
        }
    }
}
