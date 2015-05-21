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
        String document = "The disconnect in the US stock market just keeps getting bigger (SPY, SPX) http://t.co/I2nQsYgp5Y http://t.co/QW405Y04Om";
        String text = "I am feeling very bad";
        String document2 = ":-) ";


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
