import org.jfree.ui.RefineryUtilities;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

public class gui extends JFrame
{
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JButton button4;
    private JButton button5;
    private JButton button6;

    private String path = "";
    private String name = "";

    private ArrayList<Double> nbPositive = new ArrayList<Double>();
    private ArrayList<Double> nbNegative = new ArrayList<Double>();
    private ArrayList<String> nbFileName = new ArrayList<String>();
    private ArrayList<Double> svmPositive = new ArrayList<Double>();
    private ArrayList<Double> svmNegative = new ArrayList<Double>();
    private ArrayList<String> svmFileName = new ArrayList<String>();
    private ArrayList<Double> ePositive = new ArrayList<Double>();
    private ArrayList<Double> eNegative = new ArrayList<Double>();
    private ArrayList<String> eFileName = new ArrayList<String>();

    public gui()
    {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 1));


        button1 = new JButton("Choosing Testing Data");
        button2 = new JButton("Naive Bayes");
        button3 = new JButton("SVM");
        button4 = new JButton("Evaluation");
        button5 = new JButton("Clear");
        button6 = new JButton("Exit");

        button1.addActionListener(new button1());
        button2.addActionListener(new button2());
        button3.addActionListener(new button3());
        button4.addActionListener(new button4());
        button5.addActionListener(new button5());
        button6.addActionListener(new button6());

        add(button1);
        add(button2);
        add(button3);
        add(button4);
        add(button5);
        add(button6);

        setSize(300, 500);
        setVisible(true);

    }

    private class button1 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            JFileChooser fileChooser = new JFileChooser();
            int status = fileChooser.showOpenDialog(null);
            if(status == JFileChooser.APPROVE_OPTION)
            {
                File selectedFile = fileChooser.getSelectedFile();
                path = selectedFile.getPath();
                name = fileChooser.getSelectedFile().getName();
                name = name.substring(0, name.lastIndexOf("."));
            }
        }
    }

    private class button2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(path.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please choose a testing data");
            }
            else {
                try {
                    nbGenerator nb = new nbGenerator(path);
                    int total = nb.numberOfPositive + nb.numberOfNegative;
                    System.out.println("Total number is " + total);
                    System.out.println("Positive proportion is " + nb.positiveProportion);
                    System.out.println("Negative proportion is " + (1.0 - nb.positiveProportion));
                    nbPositive.add(nb.numberOfPositive*1.0/total);
                    nbNegative.add(nb.numberOfNegative*1.0/total);
                    nbFileName.add(name);
                    generateChart("Naive Bayes", nbPositive, nbNegative, nbFileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class button3 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(path.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please choose a testing data");
            }
            else
            {
                svmClassifer svmc = new svmClassifer(path);
                try {
                    svmc.run();
                    int total = svmc.numberOfNegative + svmc.numberOfPositive;
                    System.out.println("Total number is " + total);
                    System.out.println("Positive proportion is " + svmc.positiveProportion);
                    System.out.println("Negative proportion is " + (1.0 - svmc.positiveProportion));
                    svmPositive.add(svmc.numberOfPositive * 1.0 / total);
                    svmNegative.add(svmc.numberOfNegative*1.0/total);
                    svmFileName.add(name);
                    generateChart("SVM", svmPositive, svmNegative, svmFileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private class button4 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            if(path.equals(""))
            {
                JOptionPane.showMessageDialog(null, "Please choose a testing data");
            }
            else
            {
                try
                {
                    Evaluation evaluation = new Evaluation(path);
                    int total = evaluation.numberOfNegative + evaluation.numberOfPositive;
                    System.out.println("Total number is " + total);
                    System.out.println("Positive proportion is " + evaluation.positiveProportion);
                    System.out.println("Negative proportion is " + (1.0 - evaluation.positiveProportion));
                    ePositive.add(evaluation.numberOfPositive * 1.0 / total);
                    eNegative.add(evaluation.numberOfNegative * 1.0 / total);
                    eFileName.add(name);
                    generateChart("Evaluation", ePositive, eNegative, eFileName);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

        }
    }

    private class button5 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            nbPositive.clear();
            nbNegative.clear();
            nbFileName.clear();
            svmPositive.clear();
            svmNegative.clear();
            svmFileName.clear();
            ePositive.clear();
            eNegative.clear();
            eFileName.clear();
            path = "";
            name = "";
        }
    }

    private class button6 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    }

    public void generateChart(String str, ArrayList<Double> positive, ArrayList<Double> negative, ArrayList<String> fname)
    {
        Chart chart = new Chart(str, positive, negative, fname);
        chart.pack();
        RefineryUtilities.centerFrameOnScreen(chart);
        chart.setVisible(true);
    }

    public static void main(String[] args)
    {
        new gui();
    }
}
