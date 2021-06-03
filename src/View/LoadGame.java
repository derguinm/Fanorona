package View;


import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.*;

public class LoadGame extends javax.swing.JPanel {
    JFrame frame;
    JPanel menu, GameBoard;

    public LoadGame(JFrame frame, JPanel menu, JPanel GameBoard) {
        this.frame = frame;
        this.GameBoard = GameBoard;
        this.menu = menu;
        initComponents();
    }

    public void updateLoadGame(JPanel menu, JPanel GamaBoard) {
        this.menu = menu;
        this.GameBoard = GameBoard;
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

        setLayout(null);

        jList1.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Save 1 - 22/05/2021 20:05:00", "Save 2 - 23/05/2021 20:05:00",
                    "Save 3 - 23/05/2021 20:05:00" };

            public int getSize() {
                return strings.length;
            }

            public String getElementAt(int i) {
                return strings[i];
            }
        });
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

        // BACK Butoon
        jLabel2.setIcon(new javax.swing.ImageIcon("src/View/ressources/Buttons_images/back.png")); // NOI18N
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel2);
        jLabel2.setBounds(60, 580, 275, 90);
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
        jLabel3.setIcon(new javax.swing.ImageIcon("src/View/ressources/Buttons_images/ok.png")); // NOI18N
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        add(jLabel3);
        jLabel3.setBounds(460, 580, 275, 90);

        jLabel4.setIcon(new javax.swing.ImageIcon("src/View/ressources/Labels_images/bg.jpg")); // NOI18N
        add(jLabel4);
        jLabel4.setBounds(0, 0, 800, 720);
    }// </editor-fold>

    // Variables declaration - do not modify
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
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
