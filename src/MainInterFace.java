/**
 * Created by ComingWind on 2018/11/18.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class MainInterFace {

    public static String workSpace = System.getProperty("user.dir");
    public static String _spliter = File.separator;
    public static String sysType = System.getProperty("os.name").toLowerCase();
    public static String saveSpace = workSpace + _spliter + "save";
    public static int width = 1000;
    public static int height = 750;

    public void setSaveSpace(String saveSpace) {
        this.saveSpace = saveSpace;
    }

    public String getSaveSpace() {
        return saveSpace;
    }

    public MainInterFace(){
        initGUI();
    }

    public MainInterFace(String saveSpace){
        initGUI();
        this.saveSpace = saveSpace;
    }

    public void initGUI(){
        System.out.println("Main: " + saveSpace);
        // Init a JFrame object
        JFrame jf = new JFrame("Type test");
        jf.setSize(width, height);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // Init a JPanel object
        JPanel jp = new JPanel(new BorderLayout());
        //jp.add(BorderLayout.WEST, new Button("DDD"));
        jf.setContentPane(jp);
        addMenuBar(jf);
        manageCenterLayout(jp, jf);
        manageButtomLayout(jp);
        //jf.pack();
        jf.setVisible(true);

        File pksave = new File(saveSpace + _spliter + "pkdata.txt");
        File tksave = new File(saveSpace + _spliter + "tkdata.txt");
        File dir = new File(saveSpace);
        if(!dir.exists()) {
            dir.mkdir();
        }
        if (!pksave.exists()){
            try{
                pksave.createNewFile();
                System.out.println("Physical data file created!");
            }catch (IOException e){
                JOptionPane.showMessageDialog(jf, "Failed to create physical save file!", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
        if (!tksave.exists()){
            try{
                tksave.createNewFile();
                System.out.println("Touch data file created!");
            }catch (IOException e){
                JOptionPane.showMessageDialog(jf, "Failed to create touch save file!", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    public void manageButtomLayout(JPanel jp){
        //JPanel bottomLayout = new JPanel(new BoxLayout());
        Box vbox = Box.createHorizontalBox();
        vbox.add(Box.createHorizontalStrut(930));
        JButton quit = new JButton("Quit");
        vbox.add(quit);
        jp.add(vbox, BorderLayout.SOUTH);

        quit.addActionListener(new exitActionListener());
    }

    public void manageCenterLayout(JPanel jp, JFrame jf){
        JPanel centerLayout = new JPanel(new GridLayout(2, 1));
        JPanel centerTopLayout = new JPanel(new GridLayout(2, 1));

        JLabel welcome = new JLabel();
        welcome.setHorizontalAlignment(SwingConstants.CENTER);
        welcome.setText("Thank you for taking this typing test!");
        welcome.setFont(new Font(null, Font.PLAIN, 30));
        jp.add(welcome, BorderLayout.NORTH);

        JPanel centerTopLayoutTop = new JPanel(new GridLayout(2, 1));
        centerTopLayout.add(centerTopLayoutTop);
        JLabel enterId = new JLabel();
        enterId.setText("Please Enter Your Participant ID Below");
        enterId.setFont(new Font(null, Font.PLAIN, 15));
        enterId.setHorizontalAlignment(SwingConstants.CENTER);
        enterId.setVerticalAlignment(SwingConstants.BOTTOM);
        centerTopLayoutTop.add(enterId);
        JLabel afterEntering = new JLabel();
        afterEntering.setText("After entering, please click start or test");
        afterEntering.setFont(new Font(null, Font.PLAIN, 15));
        afterEntering.setHorizontalAlignment(SwingConstants.CENTER);
        enterId.setVerticalAlignment(SwingConstants.BOTTOM);
        centerTopLayoutTop.add(afterEntering);

        InputVerifier inputVerifier = new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                boolean returnValue = false;
                JTextField jTextField = (JTextField) input;
                try{
                    if(!jTextField.getText().equals("")){
                        returnValue = true;
                    }
                }catch(Exception e){
                    returnValue = false;
                }
                return returnValue;
            }
        };

        JPanel centerTopLayoutBottom = new JPanel();
        Box box = Box.createHorizontalBox();
        centerTopLayout.add(centerTopLayoutBottom);
        centerTopLayoutBottom.add(box);
        final JTextField idEnter = new JTextField(4);
        box.add(idEnter);
        idEnter.setInputVerifier(inputVerifier);

        JPanel centerBottomLayout = new JPanel(new GridLayout(1, 2));
        JButton start = new JButton("Start");
        JButton test = new JButton("Test");
        start.setFont(new Font(null, Font.PLAIN, 30));
        test.setFont(new Font(null, Font.PLAIN, 30));
        centerBottomLayout.add(start);
        centerBottomLayout.add(test);

        centerLayout.add(centerTopLayout);
        centerLayout.add(centerBottomLayout);

        jp.add(BorderLayout.CENTER, centerLayout);

        test.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //System.out.println(enterId.getText());
                if (idEnter.getText().equals("")){
                    JOptionPane.showMessageDialog(jf, "Please enter your participant ID first!", "Forget ID?", JOptionPane.WARNING_MESSAGE);
                }else{
                    BeforeTest bt = new BeforeTest(saveSpace);
                    jf.dispose();
                }
            }
        });

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (idEnter.getText().equals("")){
                    JOptionPane.showMessageDialog(jf, "Please enter your participant ID first!", "Forget ID?", JOptionPane.WARNING_MESSAGE);
                }else{
                    BeforeStart bs = new BeforeStart(idEnter.getText(), saveSpace);
                    jf.dispose();
                }

            }
        });
    }

    public void addMenuBar(JFrame jf){
        JMenuBar jmb = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenu aboutMenu = new JMenu("About");

        jmb.add(fileMenu);
        jmb.add(aboutMenu);

        JMenuItem openPWD = new JMenuItem("Open Workspace");
        JMenuItem setPWD = new JMenuItem("Set Workspace");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        fileMenu.add(openPWD);
        fileMenu.add(setPWD);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        openPWD.addActionListener(new openPWDActionListener());
        setPWD.addActionListener(new setPWDActionListener(jf));
        exitMenuItem.addActionListener(new exitActionListener());

        JMenuItem version = new JMenuItem("Version Info");
        JMenuItem contact = new JMenuItem("Contact");
        aboutMenu.add(version);
        aboutMenu.add(contact);

        version.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jf, "Version 1.0 by Haoyu Wu", "Version Info", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        contact.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(jf, "Email yl18277@bristol.ac.uk", "Contact", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        jf.setJMenuBar(jmb);
    }

    private class openPWDActionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            File dir = new File(saveSpace);
            if(!dir.exists()){
                dir.mkdir();
                openDir();
            }else{
                openDir();
            }
        }
    }

    private class setPWDActionListener implements ActionListener{
        private JFrame jf;

        public setPWDActionListener(JFrame jf){
            this.jf = jf;
        }

        public void actionPerformed(ActionEvent event){
            JFileChooser jfc = new JFileChooser();
            jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            jfc.setCurrentDirectory(new File(saveSpace));
            int res = jfc.showDialog(null, null);
            if (res == JFileChooser.APPROVE_OPTION){
                File f = jfc.getSelectedFile();
                setSaveSpace(f.getAbsolutePath());
                //System.out.println(getSaveSpace());
                File pksave = new File(saveSpace + _spliter + "pkdata.txt");
                File tksave = new File(saveSpace + _spliter + "tkdata.txt");
                if (!pksave.exists()){
                    try{
                        pksave.createNewFile();
                        System.out.println("New Location given! Created pfile!");
                    }catch (IOException e){
                        JOptionPane.showMessageDialog(jf, "Failed to create physical save file!", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
                if (!tksave.exists()){
                    try{
                        tksave.createNewFile();
                        System.out.println("New Location given! Created tfile!");
                    }catch (IOException e){
                        JOptionPane.showMessageDialog(jf, "Failed to create touch save file!", "Unsuccessful", JOptionPane.ERROR_MESSAGE);
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private class exitActionListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
            System.exit(0);
        }
    }

    public void openDir(){
        File dir = new File(saveSpace);
        Runtime runtime = null;
        try{
            runtime = Runtime.getRuntime();
            if(sysType.contains("mac")){
                runtime.exec("open " + saveSpace);
                System.out.println("Open Current Directory");
            }else if(sysType.contains("win")){
                java.awt.Desktop.getDesktop().open(new File(saveSpace));
            }
        }catch (Exception e) {
            System.exit(1);
        }
    }

}
