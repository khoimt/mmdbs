/**
 * @author khoimt
 */
package mmdbs.lsi;

import java.io.IOException;

public class MMDBSFactory {

    public static final int LUCENE = 1;
    public static final int LINGPIPE = 2;

    protected static int _class = LUCENE;

    public MMDBSFactory() {

    }

    /**
     * 
     * @param dataDir
     * @param indexDir
     * @return Indexer
     */
    public static Indexer createIndexer(String dataDir, String indexDir) {
        if (_class == LUCENE) {
            return new LuceneIndexer(dataDir, indexDir);
        } else {
            return new LuceneIndexer(dataDir, indexDir);
        }
    }
    
    public static IndexReaderInterface createIndexReader(String indexDir) throws IOException {
        if (_class == LUCENE) {
            return new LuceneIndexReader(indexDir);
        } else {
            return new LuceneIndexReader(indexDir);
        }
    }
}
