package View;
import java.util.Hashtable;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.File;
import java.io.FileWriter;
import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.event.*;
import Model.*;




/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author bl
 */
public class settings extends javax.swing.JPanel {
    JFrame frame;
    JPanel menu;
    Jeu game;
    Properties prop;
    File cfg;
    InputStream stream;
    FileWriter writer;

    /**
     * Creates new form settings
     */
    public settings(JFrame f, JPanel menu, Jeu game) {
        this.game=game;
        this.prop = new Properties();
        frame = f;
        this.menu = menu;

        try{
           stream = new FileInputStream("default.cfg");
           prop.load(stream);
        }
        catch(Exception ex){
            try{
                System.out.println(ex.getMessage());
                cfg = new File("default.cfg");
                cfg.createNewFile();
                stream = new FileInputStream(cfg);
                prop.load(stream);
                prop.setProperty("Initiator", "0");
                prop.setProperty("Player1", "0");
                prop.setProperty("Player2", "0");
                prop.setProperty("Difficulty", "0");
                writer = new FileWriter(cfg);
                prop.store(writer, "");
                writer.close();
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
            
        }

        initComponents();

        starter.setSelectedIndex(Integer.parseInt(prop.getProperty("Initiator")));
        Player1.setSelectedIndex(Integer.parseInt(prop.getProperty("Player1")));
        Player2.setSelectedIndex(Integer.parseInt(prop.getProperty("Player2")));
        jSlider1.setValue(Integer.parseInt(prop.getProperty("Difficulty")));
    }

    public void updateSettings(JPanel menu) {
        this.menu = menu;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        starter = new javax.swing.JComboBox<>();
        Player1 = new javax.swing.JComboBox<>();
        Player2 = new javax.swing.JComboBox<>();
        starterlabel = new javax.swing.JLabel();
        player2txt = new javax.swing.JLabel();
        player1txt = new javax.swing.JLabel();
        difficulty_txt = new javax.swing.JLabel();
        Apply = new javax.swing.JLabel();
        cancel_par = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jSlider1 = new javax.swing.JSlider();
        background = new javax.swing.JLabel();
        jSlider1.setEnabled(false);
        setPreferredSize(new java.awt.Dimension(800, 720));
        setLayout(null);

        starter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BLACK", "YELLOW" }));
        add(starter);
        starter.setBounds(490, 220, 170, 24);
        starter.setSelectedIndex(1);

        Player1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Human", "Ai" }));
        add(Player1);
        Player1.setBounds(490, 290, 170, 24);
        Player1.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(Player1.getSelectedIndex()==0 && Player2.getSelectedIndex()==0 ){
                    jSlider1.setEnabled(false);
                }else{
                    jSlider1.setEnabled(true);
                }
            }
        });

        Player2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Human", "Ai" }));
        add(Player2);
        Player2.setBounds(490, 360, 170, 24);
        Player2.addActionListener (new ActionListener () {
            public void actionPerformed(ActionEvent e) {
                if(Player1.getSelectedIndex()==0 && Player2.getSelectedIndex()==0 ){
                    jSlider1.setEnabled(false);
                }else{
                    jSlider1.setEnabled(true);
                }
            }
        });

        starterlabel.setIcon(new javax.swing.ImageIcon("View/ressources/starter.png")); // NOI18N
        add(starterlabel);
        starterlabel.setBounds(170, 200, 250, 65);

        player2txt.setIcon(new javax.swing.ImageIcon(("View/ressources/yellow.png"))); // NOI18N
        add(player2txt);
        player2txt.setBounds(170, 340, 250, 60);

        player1txt.setIcon(new javax.swing.ImageIcon(("View/ressources/black.png"))); // NOI18N
        add(player1txt);
        player1txt.setBounds(170, 270, 250, 60);

        difficulty_txt.setIcon(new javax.swing.ImageIcon(("View/ressources/difficulty.png"))); // NOI18N
        add(difficulty_txt);
        difficulty_txt.setBounds(170, 410, 250, 60);

        Apply.setIcon(new javax.swing.ImageIcon(("View/ressources/apply.png"))); // NOI18N
        add(Apply);
        Apply.setBounds(475, 480, 100, 109);
        Apply.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    prop.setProperty("Initiator", String.valueOf(starter.getSelectedIndex()));
                    prop.setProperty("Player1", String.valueOf(Player1.getSelectedIndex()));
                    prop.setProperty("Player2", String.valueOf(Player2.getSelectedIndex()));
                    prop.setProperty("Difficulty", String.valueOf(jSlider1.getValue()));
                    writer = new FileWriter("default.cfg");
                    prop.store(writer, "");
                    writer.close();
                }
                catch(Exception exx){
                    System.out.println(exx.getMessage());
                }        
                Player1.getSelectedIndex();
                Player2.getSelectedIndex();
                jSlider1.getValue();
                frame.getContentPane().removeAll();
                frame.add(menu);
                frame.setVisible(true);
                frame.repaint();
                
            }

        });

        cancel_par.setIcon(new javax.swing.ImageIcon(("View/ressources/cancel.png"))); // NOI18N
        add(cancel_par);
        cancel_par.setBounds(250, 480, 100, 110);
        cancel_par.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getContentPane().removeAll();
                frame.add(menu);
                frame.setVisible(true);
                frame.repaint();
            }

        });

        jLabel1.setIcon(new javax.swing.ImageIcon(("View/ressources/settings_title.png"))); // NOI18N
        add(jLabel1);
        jLabel1.setBounds(350, 20, 99, 103);

        jSlider1.setMajorTickSpacing(50);
        jSlider1.setMinorTickSpacing(50);
        jSlider1.setPaintLabels(true);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        Hashtable position = new Hashtable();
        position.put(0, new JLabel("Easy"));
        position.put(50, new JLabel("medium"));
        position.put(100, new JLabel("hard"));

        // Set the label to be drawn
        jSlider1.setLabelTable(position);
        add(jSlider1);
        jSlider1.setBounds(450, 420, 200, 42);

        background.setIcon(new javax.swing.ImageIcon(("View/ressources/2512129.jpg"))); // NOI18N
        add(background);
        background.setBounds(0, 0, 800, 720);
    }// </editor-fold>                        


    // Variables declaration - do not modify                     
    private javax.swing.JLabel Apply;
    private javax.swing.JComboBox<String> Player1;
    private javax.swing.JComboBox<String> Player2;
    private javax.swing.JLabel background;
    private javax.swing.JLabel cancel_par;
    private javax.swing.JLabel difficulty_txt;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JSlider jSlider1;
    private javax.swing.JLabel player1txt;
    private javax.swing.JLabel player2txt;
    private javax.swing.JComboBox<String> starter;
    private javax.swing.JLabel starterlabel;
    // End of variables declaration                   
}
