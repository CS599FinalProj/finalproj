import java.io.*;
import java.util.*;
import libsvm.*;

/**
 * Created by ZhengLu on 5/24/15.
 */
public class svmGenerator {

    private ArrayList<svm_node> sortArrayList(ArrayList<svm_node> nodes){

        ArrayList<svm_node> result = new ArrayList<svm_node>();

        while(!nodes.isEmpty()){
            int min = Integer.MAX_VALUE;
            svm_node minNode = null;
            for(svm_node node : nodes){
                if(node.index < min){
                    min = node.index;
                    minNode = node;
                }
            }
            result.add(minNode);
            nodes.remove(minNode);
        }
        return result;
    }

    public Hashtable<String, Integer> getSVMTrainingData() throws IOException {
        File trainingNegativeFile = new File("src/main/trainingdatanegative.txt");
        FileReader trainingNegativeFr = new FileReader(trainingNegativeFile);
        BufferedReader trainingNegativeBr = new BufferedReader(trainingNegativeFr);

        File trainingPositiveFile = new File("src/main/trainingdatapositive.txt");
        FileReader trainingPositiveFr = new FileReader(trainingPositiveFile);
        BufferedReader trainingPositiveBr = new BufferedReader(trainingPositiveFr);

        String trainingLine;
        String content = "";
        while((trainingLine = trainingNegativeBr.readLine()) != null){
            content += trainingLine + " ";
        }
        while((trainingLine = trainingPositiveBr.readLine()) != null){
            content += trainingLine + " ";
        }

        trainingNegativeBr.close();
        trainingNegativeFr.close();
        trainingPositiveBr.close();
        trainingPositiveFr.close();

        nlpProcessing nlp = new nlpProcessing();
        Set<String> set = new HashSet<String>();
        ArrayList<String> result = nlp.clean(content);
        Hashtable<String, Integer> data = new Hashtable<String, Integer>();
        int i = 1;
        for(String str : result){
            set.add(str);
        }

        for (String aSet : set) {
            data.put(aSet, i);
            i++;
        }
        return data;
    }

    public ArrayList<ArrayList<svm_node>> getTrainingParamater(Hashtable<String, Integer> data) throws IOException {
        svm_node node;
        ArrayList<svm_node> nodes;
        ArrayList<ArrayList<svm_node>> parameter = new ArrayList<ArrayList<svm_node>>();

        File trainingNegativeFile = new File("src/main/trainingdatanegative.txt");
        FileReader trainingNegativeFr = new FileReader(trainingNegativeFile);
        BufferedReader trainingNegativeBr = new BufferedReader(trainingNegativeFr);

        nlpProcessing nlp = new nlpProcessing();
        String trainingNegativeLine;

        while((trainingNegativeLine = trainingNegativeBr.readLine()) != null){
            ArrayList<String> documents = nlp.clean(trainingNegativeLine);
            nodes = new ArrayList<svm_node>();
            for(String document : documents){
                if(data.containsKey(document)){
                    node = new svm_node();
                    node.index = data.get(document);
                    node.value = 1.0;
                    nodes.add(node);
                }
            }
            parameter.add(sortArrayList(nodes));
        }
        trainingNegativeBr.close();
        trainingNegativeFr.close();

        File trainingPositiveFile = new File("src/main/trainingdatapositive.txt");
        FileReader trainingPositiveFr = new FileReader(trainingPositiveFile);
        BufferedReader trainingPositiveBr = new BufferedReader(trainingPositiveFr);
        String trainingPositiveLine;

        while((trainingPositiveLine = trainingPositiveBr.readLine()) != null){
            ArrayList<String> documents = nlp.clean(trainingPositiveLine);
            nodes = new ArrayList<svm_node>();
            for(String document : documents){
                if(data.containsKey(document)){
                    node = new svm_node();
                    node.index = data.get(document);
                    node.value = 1.0;
                    nodes.add(node);
                }
            }
            parameter.add(sortArrayList(nodes));
        }
        trainingPositiveBr.close();
        trainingPositiveFr.close();
        return parameter;
    }

    public ArrayList<Double> getClasses() throws IOException {
        ArrayList<Double> classes = new ArrayList<Double>();
        File trainingNegativeFile = new File("src/main/trainingdatanegative.txt");
        FileReader trainingNegativeFr = new FileReader(trainingNegativeFile);
        BufferedReader trainingNegativeBr = new BufferedReader(trainingNegativeFr);

        while(trainingNegativeBr.readLine() != null){
            classes.add(-1.0);
        }
        trainingNegativeBr.close();
        trainingNegativeFr.close();

        File trainingPositiveFile = new File("src/main/trainingdatapositive.txt");
        FileReader trainingPositiveFr = new FileReader(trainingPositiveFile);
        BufferedReader trainingPositiveBr = new BufferedReader(trainingPositiveFr);
        while (trainingPositiveBr.readLine() != null){
            classes.add(+1.0);
        }
        trainingPositiveBr.close();
        trainingPositiveFr.close();
        return classes;
    }

    public ArrayList<ArrayList<svm_node>> getTestingParamater(Hashtable<String, Integer> data, String path) throws IOException {
        svm_node node;
        ArrayList<svm_node> nodes;
        ArrayList<ArrayList<svm_node>> parameter = new ArrayList<ArrayList<svm_node>>();

        File trainingTestingFile = new File(path);
        FileReader trainingTestingFr = new FileReader(trainingTestingFile);
        BufferedReader trainingTestingBr = new BufferedReader(trainingTestingFr);

        nlpProcessing nlp = new nlpProcessing();
        String testingLine;

        while((testingLine = trainingTestingBr.readLine()) != null){
            ArrayList<String> documents = nlp.clean(testingLine);
            nodes = new ArrayList<svm_node>();
            for(String document : documents){
                if(data.containsKey(document)){
                    node = new svm_node();
                    node.index = data.get(document);
                    node.value = 1.0;
                    nodes.add(node);
                }
            }
            parameter.add(sortArrayList(nodes));
        }
        trainingTestingBr.close();
        trainingTestingFr.close();

        return parameter;
    }

}
