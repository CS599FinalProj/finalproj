import java.io.*;

/**
 * Created by ZhengLu JeffWang on 5/20/15.
 */
public class nbGenerator {
    public nbGenerator(String path) throws IOException {

        NaiveBayesClassifier nbc = new NaiveBayesClassifier();

        File trainingNegativeFile = new File("src/main/trainingdatanegative.txt");
        FileReader trainingNegativeFr = new FileReader(trainingNegativeFile);
        BufferedReader trainingNegativeBr = new BufferedReader(trainingNegativeFr);
        String trainingNegativeLine;
        while((trainingNegativeLine = trainingNegativeBr.readLine()) != null){
            nbc.addTrainingDocument(trainingNegativeLine, "negative");
        }
        trainingNegativeBr.close();
        trainingNegativeFr.close();

        File trainingPositiveFile = new File("src/main/trainingdatapositive.txt");
        FileReader trainingPositiveFr = new FileReader(trainingPositiveFile);
        BufferedReader trainingPositiveBr = new BufferedReader(trainingPositiveFr);
        String trainingPositiveLine;
        while((trainingPositiveLine = trainingPositiveBr.readLine()) != null){
            nbc.addTrainingDocument(trainingPositiveLine, "positive");
        }
        trainingNegativeBr.close();
        trainingNegativeFr.close();



        File testFile = new File("src/main/testdata.txt");
        FileReader testFr = new FileReader(testFile);
        BufferedReader testBr = new BufferedReader(testFr);
        String testLine;
        while((testLine = testBr.readLine()) != null){
            System.out.println(nbc.testData(testLine));
        }


    }
}
