import com.sun.codemodel.internal.JOp;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Element;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Date;

/**
 * Created by ComingWind on 2018/11/19.
 */
public class PKUI {

    private int mode;
    private String userID;
    private String saveSpace;
    private Date date = new Date();

    private long startTime;
    private long endTime;

    private int wordInputAmount = 0;
    private int keyboardStroke = 0;

    private BufferedWriter bufferedWriter = null;
    private String enteredText;
    private String wordsList;
    private String[] w;

    private int letterEntered = 0;
    private int backSpaceEntered = 0;

    private MainInterFace mi = null;
    private JTextArea enterWords = null;

    private String previous = " ";

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public PKUI(int mode, String userID, String saveSpace){
        this.mode = mode;
        this.userID = userID;
        this.saveSpace = saveSpace;
        setStartTime(date.getTime());
        initUI();
    }

    public PKUI(int mode, String userID, String saveSpace, MainInterFace mi){
        this.mode = mode;
        this.userID = userID;
        this.saveSpace = saveSpace;
        this.mi = mi;
        setStartTime(date.getTime());
        initUI();
    }

    private void initUI(){
        System.out.println("PKUI: " + saveSpace);
        JFrame jf = new JFrame("Type test");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);
        jf.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jf.setResizable(false);

        try{
            if (mode == 0){
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveSpace + File.separator + "pkdata.txt",true)));
                bufferedWriter.write("*****");
                bufferedWriter.newLine();
                bufferedWriter.write("mode: " + 0);
                bufferedWriter.newLine();
            }else{
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveSpace + File.separator + "tkdata.txt", true)));
                bufferedWriter.write("*****");
                bufferedWriter.newLine();
                bufferedWriter.write("mode: " + 1);
                bufferedWriter.newLine();
            }
            bufferedWriter.write("UserID: " + this.userID);
            bufferedWriter.newLine();
            bufferedWriter.write("Start time: " + date.toString());
            bufferedWriter.newLine();
            bufferedWriter.flush();
            bufferedWriter.close();
            System.out.println("Write User Info Data!");
        }catch(IOException e){
            e.printStackTrace();
        }
        JPanel jp = new JPanel(new BorderLayout());
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        jSplitPane.setDividerLocation(MainInterFace.width/2);
        jp.add(jSplitPane, BorderLayout.CENTER);
        jf.setContentPane(jp);

        loadUI(jSplitPane, jp, jf);
        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                if (mi != null){
                    mi.jf.setVisible(true);
                    jf.dispose();
                }else{
                    MainInterFace mi = new MainInterFace(saveSpace);
                }
            }

            @Override
            public void windowOpened(WindowEvent e){
                super.windowOpened(e);
                enterWords.requestFocus();
            }
        });

        jf.setVisible(true);
    }

    public void loadUI(JSplitPane jp, JPanel jPanel, JFrame jf){
        enterWords = new JTextArea("", 20, 40);
        enterWords.setFont(new Font(null, Font.PLAIN, 20));
        enterWords.setLineWrap(true);
        enterWords.setWrapStyleWord(true);
        enterWords.getDocument().addDocumentListener(new enterDocumentListener(enterWords));
        jp.setLeftComponent(enterWords);
        Words words = new Words(saveSpace + File.separator + "words.txt");
        w = words.generateWords();
        wordsList = "<html><body><p align=\"center\">";
        if (mode == 0){
            for (int i = 0; i < words.maxNum; i++){
                //System.out.println(w[i]);
                wordsList += w[i];
                wordsList += "    ";
                if((i+1)%5 == 0){
                    wordsList += "<br>";
                }
            }
        }else{
            for (int i = 0; i < words.maxNum; i++){
                //System.out.println(w[i]);
                wordsList += w[i];
                wordsList += "    ";
                if((i+1)%10 == 0){
                    wordsList += "<br>";
                }
            }
        }
        wordsList += "</p></body></html>";
        JLabel _w = new JLabel();
        _w.setText(wordsList);
        if (mode == 0){
            _w.setVerticalAlignment(SwingConstants.CENTER);
        }else{
            _w.setVerticalAlignment(SwingConstants.NORTH);
        }
        _w.setHorizontalAlignment(SwingConstants.CENTER);
        _w.setFont(new Font(null, Font.PLAIN, 20));
        jp.setRightComponent(_w);

        JButton finish = new JButton("Finish");
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(MainInterFace.width/2));
        box.add(finish);
        jPanel.add(box, BorderLayout.NORTH);

        finish.addActionListener(new finishButtonActionListener(jf, enterWords));
    }

    private class enterDocumentListener implements DocumentListener{

        private JTextArea jTextArea;

        public enterDocumentListener(JTextArea jTextArea){
            this.jTextArea = jTextArea;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if (jTextArea.getText().length() > 1){
                previous = jTextArea.getText().substring(jTextArea.getText().length()-2, jTextArea.getText().length()-1);
                //System.out.println(previous);
            }
            if(jTextArea.getText().substring(jTextArea.getText().length()-1).equals(" ") && !previous.equals(" ")){
                wordInputAmount++;
            }else{
                letterEntered++;
            }
            keyboardStroke++;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if (jTextArea.getText().length() > 1){
                previous = jTextArea.getText().substring(jTextArea.getText().length()-2, jTextArea.getText().length()-1);
            }else{
                previous = " ";
            }
            //System.out.println(previous);
            if(!jTextArea.getText().equals("")){
                if (jTextArea.getText().substring(jTextArea.getText().length()-1).equals(" ") && !previous.equals(" ")){
                    wordInputAmount--;
                }
            }
            backSpaceEntered++;
            keyboardStroke++;
        }

        @Override
        public void changedUpdate(DocumentEvent e) {

        }
    }

    private class finishButtonActionListener implements ActionListener {
        private JFrame jf;
        private JTextArea jt;

        public finishButtonActionListener(JFrame jf, JTextArea jt){
            this.jf = jf;
            this.jt = jt;
        }

        public void actionPerformed(ActionEvent event){
            int conRes = JOptionPane.showConfirmDialog(jf, "Finished?", "Confirm", JOptionPane.YES_NO_OPTION);
            Date d = new Date();
            setEndTime(d.getTime());
            long timeCost = endTime - startTime;
            int timeInSec = (int)timeCost/1000;
            int correct = 0;
            float correctRate = 0;
            float keyboardStrokeRate = 0;
            if (conRes == JOptionPane.YES_OPTION){
                enteredText = jt.getText();
                if (enteredText.length() == 1 && !enteredText.equals(" ")){
                    wordInputAmount++;
                }else if (enteredText.length() >= 1 && !previous.equals(" ")){
                    wordInputAmount++;
                }
                String[] res = enteredText.split(" ");
                int gt = 0;
                for (int i = 0; i < res.length; i++){
                    if (res[i].equals(w[gt])){
                        correct++;
                        gt++;
                    }else if (!res[i].equals("") && !res[i].equals(w[gt])){
                        gt++;
                    }
                }
                correctRate = (float)correct/wordInputAmount;
                keyboardStrokeRate = (float)keyboardStroke/timeInSec;
                try{
                    if (mode == 0){
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveSpace + File.separator + "pkdata.txt",true)));
                        bufferedWriter.write("End Time: " + d.toString());
                        bufferedWriter.newLine();

                        //TODO Add Statistic Data
                    }else {
                        bufferedWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(saveSpace + File.separator + "tkdata.txt",true)));
                        bufferedWriter.write("End Time: " + d.toString());
                        bufferedWriter.newLine();
                        //TODO Add Statistic Data
                    }
                    bufferedWriter.write("Time Consumed: " + timeInSec + "s");
                    bufferedWriter.newLine();
                    bufferedWriter.write("Letters input in total: " + letterEntered);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Backspace input in total: " + backSpaceEntered);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.write("Correct Words Count: " + correct);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Correct Words Ratio: " + correctRate);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Wrong Words Amount: " + (wordInputAmount - correct));
                    bufferedWriter.newLine();
                    bufferedWriter.write("Keyboard Stroke Rate: " + keyboardStrokeRate*60 + "/min");
                    bufferedWriter.newLine();
                    bufferedWriter.write("Correct Word Input Rate: " + ((float)correct/timeInSec)*60 + "/min");
                    bufferedWriter.newLine();
                    bufferedWriter.write("Words Input Amount: " + wordInputAmount);
                    bufferedWriter.newLine();
                    bufferedWriter.write("Words Input Rate (includes wrong words): " + ((float)wordInputAmount/timeInSec)*60 + "/min");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }catch (IOException e){
                    JOptionPane.showMessageDialog(jf, "User Data Open Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                if (mi != null){
                    mi.jf.setVisible(true);
                }else{
                    MainInterFace mi = new MainInterFace(saveSpace);
                }
                this.jf.dispose();
            }
        }
    }
}
