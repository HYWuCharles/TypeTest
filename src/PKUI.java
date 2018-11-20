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

    private void initUI(){
        System.out.println("PKUI: " + saveSpace);
        JFrame jf = new JFrame("Type test");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                MainInterFace mi = new MainInterFace(saveSpace);
            }
        });
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

        jf.setVisible(true);
    }

    public void loadUI(JSplitPane jp, JPanel jPanel, JFrame jf){
        JTextArea enterWords = new JTextArea("", 20, 40);
        enterWords.setFont(new Font(null, Font.PLAIN, 20));
        enterWords.setLineWrap(true);
        enterWords.setWrapStyleWord(true);
        enterWords.getDocument().addDocumentListener(new enterDocumentListener(enterWords));
        jp.setLeftComponent(enterWords);
        Words words = new Words(saveSpace + File.separator + "words.txt");
        w = words.generateWords();
        wordsList = "<html><body><p align=\"center\">";
        for (int i = 0; i < words.maxNum; i++){
            //System.out.println(w[i]);
            wordsList += w[i];
            wordsList += "    ";
            if((i+1)%5 == 0){
                wordsList += "<br>";
            }
        }
        wordsList += "</p></body></html>";
        JLabel _w = new JLabel();
        _w.setText(wordsList);
        _w.setVerticalAlignment(SwingConstants.CENTER);
        _w.setHorizontalAlignment(SwingConstants.CENTER);
        _w.setFont(new Font(null, Font.PLAIN, 20));
        jp.setRightComponent(_w);

        JButton finish = new JButton("Finish");
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(850));
        box.add(finish);
        jPanel.add(box, BorderLayout.SOUTH);

        finish.addActionListener(new finishButtonActionListener(jf, enterWords));
    }

    private class enterDocumentListener implements DocumentListener{

        private JTextArea jTextArea;

        public enterDocumentListener(JTextArea jTextArea){
            this.jTextArea = jTextArea;
        }

        @Override
        public void insertUpdate(DocumentEvent e) {
            if(jTextArea.getText().substring(jTextArea.getText().length()-1).equals(" ")){
                wordInputAmount++;
            }else{
                letterEntered++;
            }
            keyboardStroke++;
        }

        @Override
        public void removeUpdate(DocumentEvent e) {
            if(!jTextArea.getText().equals("")){
                if (jTextArea.getText().substring(jTextArea.getText().length()-1).equals(" ")){
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
                if (!enteredText.equals("")){
                    wordInputAmount++;
                }
                String[] res = enteredText.split(" ");
                for (int i = 0; i < res.length; i++){
                    if (res[i].equals(w[i])){
                        correct++;
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
                    bufferedWriter.write("Words Input Rate (includes wrong words): " + ((float)wordInputAmount/timeInSec)*60 + "/min");
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                    bufferedWriter.close();
                }catch (IOException e){
                    JOptionPane.showMessageDialog(jf, "User Data Open Failed!", "Error", JOptionPane.ERROR_MESSAGE);
                    e.printStackTrace();
                }
                MainInterFace mi = new MainInterFace(saveSpace);
                this.jf.dispose();
            }
        }
    }
}
