import java.io.*;
import java.util.ArrayList;

/**
 * Created by ZhengLu JeffWang on 5/8/15.
 */
public class nlpProcessing {

    private String removeHttp(String str) {
        String a = str;
        if(a.startsWith("http:"))
            a = "";
        return a;
    }

    private String removeRepeatedCharacter(String str) {
        return str.replaceAll("(.)\\1+", "$1");
    }

    private String removePunctuation(String str) {
        return str.replaceAll("[\\p{P}&&[^\u0027]]", "");
    }

    private String removeAtUsers(String str){
        String a = str;
        if(a.startsWith("@"))
            a = "";
        return a;
    }

    public ArrayList<String> clean(String document) throws IOException {
        String[] documents = document.split(" ");
        ArrayList<String> titles = new ArrayList<String>();

        for(String token : documents){
            String str = token.toString().toLowerCase();
            str = removeHttp(str);
            str = removeAtUsers(str);
            str = removeRepeatedCharacter(str);
            str = removePunctuation(str);
            if(!str.equals(""))
                titles.add(str);
        }


        StopWords stop = new StopWords();
        ArrayList<String> holder = new ArrayList<String>();

        for (int i = 0; i < titles.size(); i++) {
            if (stop.isStopWord(titles.get(i))) {
                titles.set(i, null);
                continue;
            }
            Stemmer s = new Stemmer();
            char[] word = titles.get(i).toCharArray();
            s.add(word, word.length);
            s.stem();
            holder.add(s.toString());
        }

        holder.remove(null);
        return holder;
    }



}
