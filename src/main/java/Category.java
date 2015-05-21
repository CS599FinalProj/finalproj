import java.util.ArrayList;
import java.util.TreeMap;

public class Category {
    //total number of documents in this category
    public int documentsInCategory;

    //TreeMap for the term frequencies of each string in the category
    public TreeMap<String, Integer> categoryTF;

    //each document (within this category's) term frequencies
    public ArrayList<TreeMap<String,Integer>> documentTF;

    //each document's ordered tf-idf vector representation
    //order is with respect to a sorted iteration through the overall
    //classifier's docFrequency treemap
    public ArrayList<ArrayList<Double>> documentVectorModel;

    //centroid vector of the category
    public ArrayList<Double> centroid;

    public Category()
    {
        documentsInCategory = 0;
        categoryTF = new TreeMap<String, Integer>();
        documentTF = new ArrayList<TreeMap<String,Integer>>();
        documentVectorModel = new ArrayList<ArrayList<Double>>();
        centroid = new ArrayList<Double>();
    }
}
