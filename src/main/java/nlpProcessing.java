import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.CleanXmlAnnotator;
import edu.stanford.nlp.pipeline.TokenizerAnnotator;
import edu.stanford.nlp.process.Tokenizer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhengLu on 5/8/15.
 */
public class nlpProcessing {
    public static void main(String[] args) throws IOException {

        String document = "";
        TokenizerAnnotator token = new TokenizerAnnotator();
        CleanXmlAnnotator clean = new CleanXmlAnnotator();
        try {
            Reader reader = new FileReader("src/main/myfile.txt");

            Tokenizer<CoreLabel> aa = token.getTokenizer(reader);
            List<CoreLabel> bb = clean.process(aa.tokenize());
            for(String str : clean(bb)){
                System.out.println(str);
            }
            System.out.println("-------------");
            for(CoreLabel q : bb){
                String str = q.toString();
                System.out.println(str);
            }


//            for(Object a : aa.tokenize().toArray()){
//                System.out.println(a);
//            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }


    static private ArrayList<String> clean(List<CoreLabel> document) throws IOException {
        ArrayList<String> titles = new ArrayList<String>();
        for(CoreLabel token : document){
            String str = token.toString().toLowerCase();
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
