import com.sun.tools.javac.comp.Todo;
import sun.applet.Main;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by ComingWind on 2018/11/19.
 */
public class TestUI {

    private String saveSpace;

    public TestUI(String saveSpace){
        this.saveSpace = saveSpace;
        initGUI();
    }

    public void initGUI(){
        System.out.println("TestUI: " + saveSpace);
        JFrame jf = new JFrame("Test Mode");
        jf.setSize(MainInterFace.width, MainInterFace.height);
        jf.setLocationRelativeTo(null);

        jf.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowClosed(e);
                MainInterFace mi = new MainInterFace(saveSpace);
            }
        });
        JPanel jp = new JPanel(new BorderLayout());
        JSplitPane jSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, true);
        jSplitPane.setDividerLocation(MainInterFace.width/2);
        jp.add(jSplitPane, BorderLayout.CENTER);
        jf.setContentPane(jp);
        loadUI(jSplitPane, jp, jf);
        jf.setVisible(true);
    }

    public void loadUI(JSplitPane jp, JPanel jPanel, JFrame jf){
        JTextArea enterWords = new JTextArea("Please enter here...", 20, 40);
        enterWords.setFont(new Font(null, Font.PLAIN, 20));
        enterWords.setLineWrap(true);
        enterWords.setWrapStyleWord(true);
        jp.setLeftComponent(enterWords);
        Words words = new Words(saveSpace);
        String[] w = words.generateWords();
        String wordsList = "<html><body><p align=\"center\">";
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

        JButton back = new JButton("Back to main");
        Box box = Box.createHorizontalBox();
        box.add(Box.createHorizontalStrut(850));
        box.add(back);
        jPanel.add(box, BorderLayout.SOUTH);

        back.addActionListener(new backButtonActionListener(jf));
    }

    private class backButtonActionListener implements ActionListener {
        private JFrame jf;

        public backButtonActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            MainInterFace mi = new MainInterFace(saveSpace);
            this.jf.dispose();
        }
    }
}
