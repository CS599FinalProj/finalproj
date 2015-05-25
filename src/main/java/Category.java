import java.util.ArrayList;
import java.util.TreeMap;

public class Category {
    //total number of documents in this category
    public int documentsInCategory;

    //TreeMap for the term frequencies of each string in the category
    public TreeMap<String, Integer> categoryTF;

    public Category()
    {
        documentsInCategory = 0;
        categoryTF = new TreeMap<String, Integer>();
    }
}
