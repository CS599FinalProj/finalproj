import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CleanXmlAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.process.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhengLu JeffWang on 5/8/15.
 */
public class nlpProcessing {
    public String removeHttp(String str) {
        String a = str;
        if(a.startsWith("http:"))
            a = "";
        return a;
    }

    public String removeRepeatedCharacter(String str) {
        return str.replaceAll("(.)\\1+", "$1");
    }

    public String removePunctuation(String str) {
        return str.replaceAll("[\\p{P}&&[^\u0027]]", "");
    }


//    public static void main(String[] args) throws IOException {
//
//        TokenizerAnnotator token = new TokenizerAnnotator();
//        CleanXmlAnnotator clean = new CleanXmlAnnotator();
//        try {
//            Reader reader = new FileReader("src/main/myfile.txt");
//
//            Tokenizer<CoreLabel> aa = token.getTokenizer(reader);
//            List<CoreLabel> bb = clean.process(aa.tokenize());
//            for(String str : clean(bb)){
//                System.out.println(str);
//            }
//
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//
//    }


    public ArrayList<String> clean(List<CoreLabel> document) throws IOException {
        ArrayList<String> titles = new ArrayList<String>();
        for(CoreLabel token : document){
            String str = token.toString().toLowerCase();
            str = removeHttp(str);
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
