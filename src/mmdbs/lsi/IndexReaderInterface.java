package mmdbs.lsi;

import java.util.ArrayList;

public interface IndexReaderInterface {
    public String[] getTerms() throws Exception;
    public int[][] getTermDocumentMatrix() throws Exception;
}