package View;

import IO.*;
import Model.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileWriter;

import java.io.File;

public class LoadGame extends javax.swing.JPanel {
    SaveLoad SL;
    JFrame frame;
    JPanel menu, GB;
    Jeu J;
    Coup C;
    Object S;

    public LoadGame(JFrame frame, JPanel menu, JPanel GB) {
        this.frame = frame;
        this.GB = GB;
        this.menu = menu;
        this.J = new Jeu();
        this.C = new Coup();

        initComponents();
    }

    public void updateLoadGame(JPanel menu, JPanel GamaBoard) {
        this.menu = menu;
        this.GB = GB;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setLayout(null);

        reset_list();
        jList1.setOpaque(false);
        jList1.setCellRenderer(new TransparentListCellRenderer());
        jList1.setFont(new java.awt.Font("Ani", 0, 20));

        jScrollPane1.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 0));
        jScrollPane1.setViewportView(jList1);
        jScrollPane1.setOpaque(false);
        jScrollPane1.getViewport().setOpaque(false);
        add(jScrollPane1);

        jScrollPane1.setBounds(52, 151, 690, 400);

        jLabel1.setFont(new java.awt.Font("Ani", 1, 48)); // NOI18N
        jLabel1.setText("LOAD GAME");
        jLabel1.setForeground(java.awt.Color.WHITE);
        add(jLabel1);
        jLabel1.setBounds(246, 30, 308, 81);

        // DELETE Butoon
        jLabel5.setIcon(new javax.swing.ImageIcon("View/ressources/Buttons_images/delete.png")); // NOI18N
        jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel5);
        jLabel5.setBounds(285, 580, 203, 91);
        jLabel5.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!jList1.isSelectionEmpty()) {
                    try {
                        File file = new File("saved/" + (jList1.getSelectedValue()));
                        file.delete();
                        remove(jScrollPane1);
                        remove(jLabel4);
                        reset_list();
                        add(jScrollPane1);
                        add(jLabel4);
                        repaint();

                    } catch (Exception exp) {
                        //
                    }
                }
            }

        });
        // BACK Butoon
        jLabel2.setIcon(new javax.swing.ImageIcon("View/ressources/Buttons_images/back.png")); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel2);
        jLabel2.setBounds(50, 580, 195, 91);
        jLabel2.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.getContentPane().removeAll();
                frame.add(menu);
                frame.setVisible(true);
                frame.repaint();
            }

        });
        // OK Button
        jLabel3.setIcon(new javax.swing.ImageIcon("View/ressources/Buttons_images/ok.png")); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel3);
        jLabel3.setBounds(530, 580, 217, 91);
        jLabel3.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!jList1.isSelectionEmpty()) {
                    try {
                        Object O = SL.load((jList1.getSelectedValue()));
                        J = SL.ObjToJeu(O);
                        J.affiche();
                        C = SL.ObjToCoup(O);
                        int p1 = SL.ObjToP1(O);
                        // Jeu J = (Jeu ) SL.load (
                        // "/home/sultan/Desktop/Fanorona/Fanorona-main/test.ser" ) ;
                        int p2 = SL.ObjToP2(O);
                        int init = SL.ObjToInit(O);
                        int niv = SL.ObjToNiveau(O);
                        File cfg;
                        FileWriter writer;
                        Properties prop = new Properties();
                        ;
                        //
                        FileInputStream stream;
                        File fichier;
                        // = new FileInputStream("default.cfg");
                        try {
                            File file = new File("default.cfg");
                            file.delete();
                            cfg = new File("default.cfg");
                            cfg.createNewFile();
                            stream = new FileInputStream(cfg);
                            prop.load(stream);
                            prop.setProperty("Initiator",  String.valueOf(init));
                            prop.setProperty("Player1", String.valueOf(p1));
                            prop.setProperty("Player2", String.valueOf(p2));
                            prop.setProperty("Difficulty",  String.valueOf(niv));
                            writer = new FileWriter(cfg);
                            prop.store(writer, "");
                            writer.close();
                        } catch (Exception ex) {

                        }
                        GameBoard G = new GameBoard(J, C);
                        G.updategameboard(frame, menu);

                        frame.getContentPane().removeAll();
                        frame.add(G);
                        frame.setVisible(true);
                        frame.repaint();
                    } catch (Exception Ex) {
                        System.out.println(Ex);
                    }
                }
            }

        });

        jLabel4.setIcon(new javax.swing.ImageIcon("View/ressources/Labels_images/bg.jpg")); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(0, 0, 800, 720);
    }// </editor-fold>

    public void reset_list() {
        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            File repertoire = new File("saved/");
            String[] strings = repertoire.list();

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
    }

    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JList<String> jList1;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration
}

class TransparentListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
            boolean cellHasFocus) {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        setForeground(Color.WHITE);
        setOpaque(isSelected);
        setHorizontalAlignment(SwingConstants.CENTER);
        return this;
    }
}
