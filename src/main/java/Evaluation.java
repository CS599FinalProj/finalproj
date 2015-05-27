import edu.stanford.nlp.ling.CoreAnnotations;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.sentiment.SentimentCoreAnnotations;
import edu.stanford.nlp.util.CoreMap;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

/**
 * Created by ZhengLu on 5/26/15.
 */
public class Evaluation {
    public int numberOfPositive = 0;
    public int numberOfNegative = 0;
    public double positiveProportion = 0.0;

    public Evaluation(String path) throws IOException {

        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, parse, sentiment");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        File testFile = new File(path);
        FileReader testFr = new FileReader(testFile);
        BufferedReader testBr = new BufferedReader(testFr);
        String testLine;
        String category;
        while((testLine = testBr.readLine()) != null){
            category = eval(testLine, pipeline);
            if(category.equals("Positive")){
                numberOfPositive++;
            }else if(category.equals("Negative")){
                numberOfNegative++;
            }
        }
        positiveProportion = (double)numberOfPositive / (numberOfPositive + numberOfNegative);
        testBr.close();
        testFr.close();

    }


    private static String eval(String document, StanfordCoreNLP pipeline){
        String result = null;

        Annotation annotation = pipeline.process(document);

        List<CoreMap> sentences = annotation.get(CoreAnnotations.SentencesAnnotation.class);

        for (CoreMap sentence : sentences) {
            result = sentence.get(SentimentCoreAnnotations.SentimentClass.class);
        }
        return result;
    }
}
