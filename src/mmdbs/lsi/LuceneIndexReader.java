package mmdbs.lsi;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermDocs;
import org.apache.lucene.index.TermEnum;
import org.apache.lucene.store.FSDirectory;

public class LuceneIndexReader implements IndexReaderInterface {

    protected String _indexDir;
    protected IndexReader _reader;

    public LuceneIndexReader(String indexDir) throws IOException {
        this._indexDir = indexDir;
        this._reader = IndexReader.open(FSDirectory.open(new File(this._indexDir)), true);
    }
    
    public String[] getTerms() throws IOException {
        TermEnum terms = this._reader.terms();
        ArrayList tmp = new ArrayList();
        while(terms.next()) {
            tmp.add(terms.term().toString().substring(9));
        }
        
        
        String rs[] = new String[tmp.size()];
        for (int i = 0; i < rs.length; i++) {
            rs[i] = (String) tmp.get(i);
        }
        return rs;
    }

    /**
     *
     * @return 
     * @throws Exception
     */
    public int[][] getTermDocumentMatrix() throws Exception {
        TermEnum terms = this._reader.terms();
        int n = this._reader.numDocs();
        int nt = this.getTerms().length;
        int rs[][] = new int[nt][];
        int i = 0;
        while(terms.next()) {
            Term term = terms.term();
            rs[i] = new int[n];
            Arrays.fill(rs[i], 0);
            
            TermDocs termDocs = this._reader.termDocs(term);
            while(termDocs.next()) {
                int j = termDocs.doc();
                rs[i][j] = termDocs.freq();
            }
            i++;
        }
        return rs;
    }
}
