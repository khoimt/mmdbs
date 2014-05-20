package mmdbs.lsi;

import java.util.ArrayList;

public interface IndexReaderInterface {
    public String[] getTerms() throws Exception;
    public double[][] getTermDocumentMatrix() throws Exception;
}