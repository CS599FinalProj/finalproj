/**
 * Created by ZhengLu on 5/8/15.
 */

import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;

import java.util.List;
import java.util.Properties;

public class evaluation {
    public static void main(String[] args) {
        String document = "obviously not siding with Cheney here: http://bit.ly/19j2d";
        String text = "I am feeling very bad";
        String document2 = "Colin Powell rocked yesterday on CBS. Cheney needs to shut the hell up and go home.Powell is a man of Honor and served our country proudly\n";


        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation annotation = pipeline.process(document2);
        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            String sentiment = sentence.get(SentimentCoreAnnotations.ClassName.class);
            System.out.println(sentiment + "\t" + sentence);
        }



        
    }
}
