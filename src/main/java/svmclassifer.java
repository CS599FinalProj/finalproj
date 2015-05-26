import libsvm.*;
import sun.rmi.server.InactiveGroupException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.DoubleSummaryStatistics;
import java.util.Hashtable;

public class svmclassifer
{


    svm_parameter _param = new svm_parameter();
    svm_problem _prob = new svm_problem();
    String _model_file;

    String path;

    public svmclassifer(String path)
    {
        this.path = path;
    }

    public void run() throws IOException {
        setParameter();
        trainingData();
        testingData();
    }

    public void trainingData() throws IOException {
        needtodeleted svmGenerate = new needtodeleted();
        Hashtable<String, Integer> data = svmGenerate.getSVMTrainingData();
        ArrayList<Double> classes = svmGenerate.getClasses();
        ArrayList<ArrayList<svm_node>> parameter = svmGenerate.getTrainingParamater(data);
        Object[] objects = classes.toArray();
        Double[] labels = Arrays.copyOf(objects, objects.length, Double[].class);

        _param.gamma = 1.0/data.size();
        _prob.l = classes.size();
        _prob.y = toPrimitive(labels);
        _prob.x = changeTo2DArray(parameter);

        try
        {
            _model_file = "out.txt";
            svm_model model = svm.svm_train(_prob, _param);
            svm.svm_save_model(_model_file, model);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public double[] toPrimitive(Double[] DoubleArray) {

        double[] result = new double[DoubleArray.length];
        for (int i = 0; i < DoubleArray.length; i++) {
            result[i] = DoubleArray[i].intValue();
        }
        return result;
    }

    public svm_node[][] changeTo2DArray(ArrayList<ArrayList<svm_node>> parameter)
    {
        int row = parameter.size();
        int column = Integer.MIN_VALUE;
        for(ArrayList<svm_node> nodes : parameter){
            if(nodes.size() > column)
                column = nodes.size();
        }
        svm_node[][] result = new svm_node[row][column];
        for(int i=0; i<row; i++)
        {
            Object[] objects = parameter.get(i).toArray();
            result[i] = Arrays.copyOf(objects, objects.length, svm_node[].class);

        }

        return result;
    }

    public void testingData() throws IOException {
        needtodeleted svmGenerate = new needtodeleted();
        Hashtable<String, Integer> data = svmGenerate.getSVMTrainingData();
        svm_node[][] result = changeTo2DArray(svmGenerate.getTestingParamater(data, path));
        svm_model model;
        try
        {
            model = svm.svm_load_model("out.txt");

            for(int i=0;i<result.length;i++){
                double v;
                v = svm.svm_predict(model, result[i]);
                if(v==1.0)
                    System.out.println("positive");
                else
                    System.out.println("negative");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void setParameter()
    {
        _param.svm_type = svm_parameter.C_SVC;
        _param.kernel_type = svm_parameter.LINEAR;
        _param.degree = 3;
        _param.gamma = 0;		// 1/num_features
        _param.coef0 = 0;
        _param.nu = 0.5;
        _param.cache_size = 100;
        _param.C = 1;
        _param.eps = 1e-3;
        _param.p = 0.1;
        _param.shrinking = 1;
        _param.probability = 0;
        _param.nr_weight = 0;
        _param.weight_label = new int[0];
        _param.weight = new double[0];
    }

    public void readData()
    {

    }
}
