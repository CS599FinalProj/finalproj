/**
 * Created by ZhengLu on 5/8/15.
 */


import java.io.*;
import java.util.HashSet;

public class StopWords
{
    private HashSet<String> words;

    public StopWords() throws IOException {
        words = new HashSet<String>();
        File file = new File("src/main/stopwords_en.txt");
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);
        String line;
        while((line = bufferedReader.readLine()) != null){
            words.add(line);
        }
        fileReader.close();
        bufferedReader.close();
    }

    public boolean isStopWord(String a) {

        return words.contains(a);
    }
}
