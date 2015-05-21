import edu.stanford.nlp.ling.CoreLabel;

import java.io.IOException;
import java.util.List;

public interface NaiveBayesInterface {

    //given an input training doc, this method
    //converts string document to lowercase, removes punctuations, stems the words
    //and iterates through each individual string of the doc (delimited by whitespace)
    //and updates the fields of the classifier accordingly
    void addTrainingDocument(List<CoreLabel> doc, String cat) throws IOException;

    //cleans and stems each string, and gets rid of stop words
    //TreeMap<String,Integer> getDocFrequency();
    //TreeMap<String, Category> getData();
    int getTotalDocumentCount();
    String testData(List<CoreLabel> doc) throws IOException;
}
