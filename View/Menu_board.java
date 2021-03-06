/*this the first panel of the game which shows the menu of the game that give you the possibility to chose from different utilities */
package View;

import java.awt.event.*;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Model.*;

public class Menu_board extends javax.swing.JPanel {
        JFrame frame;
        JPanel settings, rules, loadGame, game_board, menu;
        Jeu game;
        Coup strock;

        /**
         * Creates new form Menu
         */
        public Menu_board(JFrame f, JPanel rules, JPanel game_board, JPanel loadGame, JPanel settings, Jeu game,
                        Coup strock) {
                frame = f;
                this.game = game;
                this.strock = strock;
                this.rules = rules;
                this.loadGame = new LoadGame(frame, this, game_board);
                this.settings = settings;
                this.game_board = game_board;
                initComponents();
                menu = this;
        }

        /**
         * This method is called from within the constructor to initialize the form.
         * WARNING: Do NOT modify this code. The content of this method is always
         * regenerated by the Form Editor.
         */
        @SuppressWarnings("unchecked")
        // <editor-fold defaultstate="collapsed" desc="Generated Code">
        private void initComponents() {
                jLabel1 = new javax.swing.JLabel();
                jLabel2 = new javax.swing.JLabel();
                jLabel3 = new javax.swing.JLabel();
                jLabel4 = new javax.swing.JLabel();
                jLabel5 = new javax.swing.JLabel();
                jLabel7 = new javax.swing.JLabel();
                jLabel6 = new javax.swing.JLabel();

                this.setLayout(null);
                setPreferredSize(new java.awt.Dimension(800, 720));
                /* rules label that takes you the the rules panel */
                jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Buttons_images/rules.png"))); // NOI18N
                jLabel1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jLabel1.setBounds(12, 12, 50, 50);
                add(jLabel1);
                jLabel1.setToolTipText("show game rules");
                jLabel1.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                frame.getContentPane().removeAll();
                                frame.add(rules);
                                frame.setVisible(true);
                                frame.repaint();
                        }

                });
                /* settings label that takes you the the settings panel */
                jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Buttons_images/settings.png"))); // NOI18N
                jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jLabel2.setBounds(738, 12, 50, 50);
                jLabel2.setToolTipText("go to settings");
                add(jLabel2);
                jLabel2.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                frame.getContentPane().removeAll();
                                settings = new settings(frame, menu, game) ;
                                frame.add(settings);
                                frame.setVisible(true);
                                frame.repaint();
                        }

                });

                /* load game label that takes you to the load game panel */
                jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Buttons_images/loadGame.png"))); // NOI18N
                jLabel5.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jLabel5.setBounds(262, 300, 280, 103);
                jLabel5.setToolTipText("go to load game");
                add(jLabel5);
                jLabel5.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                frame.getContentPane().removeAll();
                                frame.add(new LoadGame(frame, menu, game_board));
                                frame.setVisible(true);
                                frame.repaint();
                        }

                });

                /* new game label that let you start a new game */
                jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Buttons_images/newGame.png"))); // NOI18N
                jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jLabel3.setBounds(262, 420, 280, 103);
                jLabel3.setToolTipText("start new game");
                add(jLabel3);
                jLabel3.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                frame.getContentPane().removeAll();
                                ((GameBoard) game_board).new_game();
                                frame.add(game_board);
                                frame.setVisible(true);
                                frame.repaint();
                                class launchSIA implements Runnable {
                                        public void run() {
                                                try {
                                                        Thread.sleep(10);
                                                        ((GameBoard) game_board).sia();
                                                } catch (Exception e) {
                                                        System.out.println(e.getMessage());
                                                }
                                        }
                                }
                                launchSIA lsia = new launchSIA();
                                Thread t = new Thread(lsia);
                                t.start();
                        }

                });

                /* label that allows you to close the game */
                jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Buttons_images/exitGame.png"))); // NOI18N
                jLabel4.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                jLabel4.setBounds(262, 540, 280, 103);
                jLabel4.setToolTipText("exit the game");
                add(jLabel4);
                jLabel4.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseClicked(MouseEvent e) {
                                frame.dispose();
                        }

                });
                /* designe labels background and title */
                jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Labels_images/title.png"))); // NOI18N
                jLabel7.setBounds(226, 130, 341, 140);
                add(jLabel7);

                jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/Labels_images/bg.jpg"))); // NOI18N
                jLabel6.setBounds(0, 0, 800, 720);
                add(jLabel6);

        }

        private javax.swing.JLabel jLabel1;
        private javax.swing.JLabel jLabel2;
        private javax.swing.JLabel jLabel3;
        private javax.swing.JLabel jLabel4;
        private javax.swing.JLabel jLabel5;
        private javax.swing.JLabel jLabel7;
        private javax.swing.JLabel jLabel6;
}
