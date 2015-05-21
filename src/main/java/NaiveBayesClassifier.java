import edu.stanford.nlp.ling.CoreLabel;

import java.io.IOException;
import java.util.*;

public class NaiveBayesClassifier implements NaiveBayesInterface {
    // TODO: students could define any private data structure to be used,
    // or use the recommended data structure like followings (you need to
    // uncomment them out).

    /**
     * docfrequency maps each unique string to its document frequency
     * docfrequency is the number of documents it has appeared in
     */
    private ArrayList<String> docFrequency;

    private nlpProcessing nlp;

    /**
     * data maps each string to a category, eg: "AskReddit" string
     * will be mapped to AskReddit category's documents info
     */
    private TreeMap<String, Category> data;

    /**
     * total number of documents added to the classifier
     */
    private int totalDocumentCount;


    public NaiveBayesClassifier() {
        nlp = new nlpProcessing();
        docFrequency = new ArrayList<String>();
        data = new TreeMap<String, Category>();
        totalDocumentCount = 0;
    }

    /**
     * given an input training doc, this method converts string document
     * to lowercase, removes punctuations, stems the words and iterates
     * through each individual string of the doc (delimited by whitespace)
     * and updates the fields of the classifier accordingly
     * @param document reddit post title
     * @param category subreddit name
     */
    public void addTrainingDocument(List<CoreLabel> document, String category) throws IOException {
        //increment the total document count
        totalDocumentCount++;

        // use clean to remove punctuation, switch to lowercase, stem,
        // and split the document on whitespace
        Object[] objectses = nlp.clean(document).toArray();
        String[] documents = Arrays.copyOf(objectses, objectses.length, String[].class);

        // TODO: students need to implement this method
        for(String str : documents){
            if(!docFrequency.contains(str)){
                docFrequency.add(str);
            }
        }

        // TODO: Students need to update statistical information like
        // term frequency of an input training doc and document frequency etc.

        if(data.containsKey(category)){
            Category newUpdate = data.get(category);
            newUpdate.documentsInCategory++;
            newUpdate.categoryTF = updateFrequency(documents, data.get(category).categoryTF);
            data.put(category, newUpdate);
        }else {
            Category tag = new Category();
            TreeMap<String, Integer> docTermFrequency = new TreeMap<String, Integer>();
            tag.documentsInCategory = 1;
            tag.categoryTF = updateFrequency(documents, docTermFrequency);
            data.put(category, tag);
        }

    }



    /**
     * converts input string to lowercase, removes all non-word characters
     * splits the input string on whitespace, stems each string, and gets
     * rid of stop words returns the array of "cleaned" strings
     * @param document reddit post title
     * @return "cleaned" reddit post title
     */


    /**
     * @param document reddit post title for testing
     * @return subreddit document most likely belongs to
     */
    public String testData(List<CoreLabel> document) throws IOException {
        // TODO: students need to implement this method
        Object[] objectses = nlp.clean(document).toArray();
        String[] documents = Arrays.copyOf(objectses, objectses.length, String[].class);

        TreeMap<String, Double> results = new TreeMap<String, Double>(); //TreeMap results store String category and Double probability
        double probability; //Prior Probability
        double totalSize;  //totalSize means all words + unique words

        double value = (double)Integer.MIN_VALUE;
        String category = null; // return value


        // TODO: students need to use the statistical information you have
        // collected during adding the training documents to predict the
        // category of each input testing document.

        //entry means <String, Category>
        for(Map.Entry<String, Category> entry : data.entrySet()){
            Category thisTag = entry.getValue();

            //Prior Probability
            probability = Math.log10( (double) thisTag.documentsInCategory / (double) totalDocumentCount );

            //totalSize means all words in specific category + unique words
            totalSize = 0.0;

            //Calculate total size(all words + unique words)
            for(Map.Entry<String, Integer> termEntry : thisTag.categoryTF.entrySet()){
                totalSize += (double)termEntry.getValue(); // means all words in specific category
            }
            totalSize += docFrequency.size(); // update total size by adding all unique words


            for(String feature : documents){
                if(thisTag.categoryTF.containsKey(feature)){
                    probability += Math.log10( ( (double)(thisTag.categoryTF.get(feature)) + 1.0) / totalSize );
                }else{
                    probability += Math.log10( 1.0 / totalSize );
                }
            }
            results.put(entry.getKey(), probability);
        }

        for(Map.Entry<String, Double> entry : results.entrySet()){
            if(entry.getValue() > value){
                value = entry.getValue();
                category = entry.getKey();
            }
        }

        // TODO: students need to return the name of the category as output.
        return category;
    }

    //public TreeMap<String, Integer> getDocFrequency() {
        //return docFrequency;
    //}

    //public TreeMap<String, Category> getData() {
    //    return data;
    //}

    public int getTotalDocumentCount() {
        return totalDocumentCount;
    }

    private TreeMap<String, Integer> updateFrequency(String[] documents, TreeMap<String, Integer> treeMap){
        for(String str : documents){
            if(treeMap.containsKey(str)){
                int updateCount = treeMap.get(str) + 1;
                treeMap.put(str, updateCount);
            }else{
                treeMap.put(str, 1);
            }
        }
        return treeMap;
    }


}
