import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.swing.*;

public class gui extends JFrame
{
    private JButton button1;
    private JButton button2;
    private JButton button3;

    private String path;

    public gui()
    {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));


        button1 = new JButton("Choosing Testing Data");
        button2 = new JButton("Naive Bayes");
        button3 = new JButton("SVM");

        button1.addActionListener(new button1());
        button2.addActionListener(new button2());
        button3.addActionListener(new button3());

        add(button1);
        add(button2);
        add(button3);

        setSize(400, 400);
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
            }
        }
    }

    private class button2 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try {
                new nbGenerator(path);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private class button3 implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            svmClassifer svmc = new svmClassifer(path);
            try {
                svmc.run();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static void main(String[] args)
    {
        new gui();
    }
}
