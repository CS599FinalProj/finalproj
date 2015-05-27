import java.io.*;

/**
 * Created by ZhengLu JeffWang on 5/20/15.
 */
public class nbGenerator {

    NaiveBayesClassifier nbc = new NaiveBayesClassifier();
    public int numberOfPositive = 0;
    public int numberOfNegative = 0;
    public double positiveProportion = 0.0;

    public nbGenerator(String path) throws IOException {



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


        File testFile = new File(path);
        FileReader testFr = new FileReader(testFile);
        BufferedReader testBr = new BufferedReader(testFr);
        String testLine;
        while((testLine = testBr.readLine()) != null){
            String category = nbc.testData(testLine);
            System.out.println(category);
            if(category.equals("positive")){
                numberOfPositive++;
            }else{
                numberOfNegative++;
            }
        }
        positiveProportion = (double)numberOfPositive / (numberOfPositive + numberOfNegative);
        testBr.close();
        testFr.close();
    }
}
