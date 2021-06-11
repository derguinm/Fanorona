package View;

import Control.*;
import Model.*;
import IO.* ;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Currency;
import java.util.ArrayList;	
import java.time.format.DateTimeFormatter;	
import java.time.LocalDateTime;
import static javax.swing.JOptionPane.showMessageDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class GameBoard extends javax.swing.JPanel {

    /**
     * Creates new form game_board
     */
    Jeu game;
    Coup stroke;
    JFrame frame;
    JPanel menu;	
    SaveLoad SL ;
    AudioInputStream audioInputStream;
    Clip bgmusic;
    boolean on;
    FloatControl gainControl;

    public GameBoard(Jeu game, Coup strock) {
        this.game = game;
        this.stroke = strock;
        try{
            InputStream audioSrc = getClass().getResourceAsStream("/View/ressources/bgmusic.wav");
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            audioInputStream = AudioSystem.getAudioInputStream(bufferedIn);
            bgmusic = AudioSystem.getClip();
            bgmusic.open(audioInputStream);
            gainControl= (FloatControl) bgmusic.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-20.0f);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        on=false;
        initComponents();
    }

    public void updategameboard(JFrame frame, JPanel menu) {
        this.frame = frame;
        this.menu = menu;
    }
    
	public void updateJCS( ) {		
	try{	
		reset_board(game, stroke);	
	}	
	catch(Exception ex){	
	System.out.println(ex);	
	}
    }

    private void initComponents() {

        
        jLabel1 = new javax.swing.JLabel();
        rules_panel = new javax.swing.JPanel();
        rules_text = new javax.swing.JLabel();
        rules_title = new javax.swing.JLabel();
        rules_back = new javax.swing.JLabel();
        rules_bg = new javax.swing.JLabel();
        menu_panel = new javax.swing.JPanel();
        menu_cancel = new javax.swing.JLabel();
        menu_save = new javax.swing.JLabel();
        menu_restart = new javax.swing.JLabel();
        menu_home = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        current_player = new javax.swing.JLabel();
        rules = new javax.swing.JLabel();
        cancel = new javax.swing.JLabel();
        redo = new javax.swing.JLabel();
        surrender = new javax.swing.JLabel();
        pause_menu = new javax.swing.JLabel();
        curren_player = new javax.swing.JLabel();
        sound = new javax.swing.JLabel();
        end_turn = new javax.swing.JLabel();
        game_board = new javax.swing.JLabel();

        bp = new BoardPanel(game, stroke, this);
        // this.addMouseListener(new GameBoardListener(this, game, stroke));

        jLabel1.setText("jLabel1");

        setLayout(null);

        rules_panel.setLayout(null);
        rules_panel.setVisible(false);

        rules_text.setFont(new java.awt.Font("Dialog", 1, 11)); // NOI18N
        rules_text.setText(
                "<HTML>1. In turns, each player moves 1 BEAD along the marked lines to an adjacent empty point, or they can capture an opponent’s BEAD<BR><BR> 2. A player can capture either by Approach or by Withdrawal.<BR> <BR> 3. During an Approach capture, a player moves their BEAD to a point adjacent to their opponent’s BEAD. The BEAD is only captured if the BEAD is on the continuum of the capturing BEADs movement.<BR> <BR> 4. During a Withdrawal capture, a player will move their BEAD from a point adjacent to their opponent’s BEAD. The BEAD is only captured if the BEAD is on a continuum of the capturing BEADs movement.<BR> <BR> 5. When a player’s BEAD is captured, all their BEADS in an uninterrupted line beyond the capturing BEAD are also captured.<BR> <BR> 6. A player cannot make an Approach and Withdrawal capture at the same time – the player must choose one.<BR> <BR> 7. A player can make multiple captures during their turn, but their BEAD cannot land on the same intersection more than once each turn and must alternate between an Approach and a Withdrawal capture.<BR> <BR> 8. Making multiple captures in a single turn is optional.<BR> <BR> 9. On a player’s first move of the game, only one capture is allowed.<BR> <BR> 10. Captured BEADs are removed from the board.<BR> </HTML> ");
        rules_panel.add(rules_text);
        rules_text.setBounds(20, 100, 420, 470);

        rules_title.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/rules_title.png"))); // NOI18N
        rules_panel.add(rules_title);
        rules_title.setBounds(180, 0, 101, 109);
        rules_title.setToolTipText("show Fanorona rules");

        rules_back.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/back_big.png"))); // NOI18N
        rules_back.setText("jLabel4");
        rules_panel.add(rules_back);
        rules_back.setBounds(190, 570, 100, 100);
        rules_back.setToolTipText("back to the board");
        rules_back.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rules_panel.setVisible(false);
                repaint();
            }
        });

        rules_bg.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/board_rules.png"))); // NOI18N
        rules_panel.add(rules_bg);
        rules_bg.setBounds(0, 0, 460, 670);

        add(rules_panel);
        rules_panel.setBounds(170, 10, 460, 670);

        menu_panel.setLayout(null);
        menu_panel.setOpaque(false);

        menu_cancel.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/back_big.png")))); // NOI18N
        menu_cancel.setToolTipText("back to the board");
        menu_cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menu_panel.setVisible(false);
                repaint();
            }

        });
        menu_panel.add(menu_cancel);
        menu_cancel.setBounds(0, 0, 98, 106);

        menu_save.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/save.png")))); // NOI18N
        menu_save.setToolTipText("save game");
        menu_panel.add(menu_save);
        menu_save.setBounds(0, 112, 95, 105);
        menu_save.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int p1 = bp.p1 ;
                int p2 = bp.p2 ;
                int init = bp.init ;
                int niv = bp.difficulty ;
                ArrayList <Object> l = SL.GameToObj(game , stroke ,  p1, p2,init,niv);
		try{	
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");	
			LocalDateTime now = LocalDateTime.now();	
			SL.save(l , "saved/fanorona"+dtf.format(now)+".save") ;	
		}catch (Exception ex ){	
			System.err.println(ex);	
		}
		menu_panel.setVisible(false);
                JOptionPane.showMessageDialog(frame, "Successfully saved", "Backup", JOptionPane.PLAIN_MESSAGE);
            }

        });

        menu_restart.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/restart.png")))); // NOI18N
        menu_restart.setToolTipText("restart game");
        menu_panel.add(menu_restart);
        menu_restart.setBounds(0, 223, 97, 103);

        menu_restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = { "Yes", "No " };
                int a = JOptionPane.showOptionDialog(frame, "Do you really want to restart the game ?", "Restart",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (a == JOptionPane.YES_OPTION) {
                    game = new Jeu();
                    stroke = new Coup();
                    reset_board(game, stroke);
                    class launchSIA implements Runnable{
                        public void run(){
                                try{
                                        Thread.sleep(10);
                                        ((BoardPanel)bp).iaPlays();
                                }
                                catch(Exception e){
                                        System.out.println(e.getMessage());
                                }
                        }
                    }
                    launchSIA lsia = new launchSIA();
                    Thread t = new Thread(lsia);
                    t.start();
                    }
            }

        });

        menu_home.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/rules_home.png")))); // NOI18N
        menu_panel.add(menu_home);
        menu_home.setBounds(0, 332, 103, 110);
        menu_home.setToolTipText("go back home");
        menu_home.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = { "Yes", "No ", "Cancel" };
                int a = JOptionPane.showOptionDialog(frame, "Do you want to save the game before going back home?",
                        "Warning!", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (a == JOptionPane.YES_OPTION) {
                    ArrayList <Object> l = SL.GameToObj(game , stroke , bp.p1 , bp.p2 , bp.init, bp.difficulty );
			try{	
				DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH-mm-ss");	
				LocalDateTime now = LocalDateTime.now();	
				SL.save(l , "saved/fanorona"+dtf.format(now)+".save") ;	
			}	
			catch(Exception x){	
			}
                    game = new Jeu();
                    stroke = new Coup();
                    reset_board(game, stroke);
                    try {
                        frame.getContentPane().removeAll();
                        frame.add(menu);
                        frame.repaint();
                    } catch (Exception ez) {
                        System.out.println(ez.getMessage());
                    }

                } else {
                    if (a == JOptionPane.NO_OPTION) {
                        game = new Jeu();
                        stroke = new Coup();
                        reset_board(game, stroke);
                        frame.getContentPane().removeAll();
                        frame.add(menu);
                        frame.repaint();
                    }
                }
            }

        });
        menu_panel.setVisible(false);

        add(menu_panel);
        menu_panel.setBounds(590, 0, 100, 440);
        bp.setLayout(null);
        bp.setBounds(80, 140, 640, 350);
        add(bp);
        bp.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                menu_panel.setVisible(false);
                repaint();
            }

        });

        jPanel2.setLayout(null);

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/board_bg_n.png"))); // NOI18N
        jPanel2.add(jLabel2);
        jLabel2.setBounds(0, 0, 600, 310);

        add(jPanel2);
        jPanel2.setBounds(100, 160, 600, 310);

        current_player.setFont(new java.awt.Font("Ani", 1, 30)); // NOI18N
        current_player.setEnabled(true);
        add(current_player);
        current_player.setBounds(320, 40, 200, 30);
        current_player.setHorizontalAlignment(SwingConstants.CENTER);
        current_player.setVerticalAlignment(SwingConstants.CENTER);
        joueur_current();

        rules.setIcon(new javax.swing.ImageIcon(getClass().getResource("/View/ressources/rules_title.png"))); // NOI18N
        add(rules);
        rules.setToolTipText("show rules");
        rules.setBounds(0, 0, 100, 100);
        rules.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                rules_panel.setVisible(true);
                repaint();
            }
        });

        cancel.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/cancel_turn.png")))); // NOI18N
        add(cancel);
        cancel.setBounds(230, 570, 50, 50);
        cancel.setToolTipText("undo");
        cancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (!game.h.coupasse.estVide()) {
                    game = bp.Undo();
                    reset_board(game, stroke);
                }

            }
        });

        redo.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/redo.png")))); // NOI18N
        add(redo);
        redo.setBounds(500, 570, 50, 50);
        redo.setToolTipText("redo");
        redo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                game = bp.Redo();
                reset_board(game, stroke);
            }
        });

        surrender.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/surrender.png")))); // NOI18N
        add(surrender);
        surrender.setBounds(600, 570, 50, 50);
        surrender.setToolTipText("surrendder");
        surrender.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Object[] options = { "Yes", "No " };
                int a = JOptionPane.showOptionDialog(frame, "Do you really want to surrender ?", "Surrender",
                        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (a == JOptionPane.YES_OPTION) {
                    game.surrender = true;
                    game.FinJeu();
                    game.changerJoueurcoup(stroke);
                    repaint();
                }
            }
        });

        pause_menu.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/menu.png")))); // NOI18N
        pause_menu.setToolTipText("board menu");
        pause_menu.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (menu_panel.isVisible() == false) {
                    menu_panel.setVisible(true);
                } else {
                    menu_panel.setVisible(false);
                }
                repaint();
            }
        });
        add(pause_menu);
        pause_menu.setBounds(700, 0, 100, 100);

        curren_player.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/current_player.png")))); // NOI18N
        add(curren_player);
        curren_player.setToolTipText("its this player turn");
        curren_player.setBounds(290, 30, 250, 50);

        sound.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/soundoff.png")))); // NOI18N
        add(sound);
        sound.setVisible(true);
        sound.setBounds(120, 570, 50, 50);
        sound.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(on){
                    sound.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/soundoff.png"))));
                    repaint();
                    bgmusic.stop();
                    on=false;
                }else{
                    sound.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/sound.png"))));
                    repaint();
                    bgmusic.loop(-1);
                    on=true;
                }
            }
        });

        end_turn.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/end_turn.png")))); // NOI18N
        add(end_turn);
        end_turn.setBounds(335, 560, 100, 100);
        end_turn.setToolTipText("validate turn");
        end_turn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                switch(bp.mode){
                    case 0: bp.endRound();
                    break;
                    case 2: bp.endRoundIA();
                    break;
                }
                joueur_current();
            }
        });

        game_board.setIcon(new javax.swing.ImageIcon((getClass().getResource("/View/ressources/board_bg.jpg")))); // NOI18N
        add(game_board);
        game_board.setBounds(0, 0, 800, 720);
    }// </editor-fold>

    public void reset_board(Jeu game, Coup stroke) {
        menu_panel.setVisible(false);
        menu_panel.getParent().removeAll();
        this.game = game;
        this.stroke = stroke;
        bp = new BoardPanel(game, stroke, this);
        add(rules_panel);
        add(menu_panel);
        add(bp);
        add(jPanel2);
        add(current_player);
        add(rules);
        add(cancel);
        add(redo);
        add(surrender);
        add(pause_menu);
        add(curren_player);
        add(sound);
        add(end_turn);
        add(game_board);
        joueur_current();
        repaint();
    }

    public void joueur_current() {
        if (game.joueur() == 1) {
            current_player.setForeground(Color.BLACK);
            current_player.setText("Black");
            paintImmediately(0, 0, 720, 800);
        } else {
            current_player.setForeground(Color.YELLOW);
            current_player.setText("Yellow");
            paintImmediately(0, 0, 720, 800);
        }
        repaint();
    }

    public void new_game() {
        game = new Jeu();
        stroke = new Coup();
        reset_board(game, stroke);
    }

    public void sia(){
        if(bp.iaStarts == 1)
            bp.iaPlays();
    }

    // Variables declaration
    private javax.swing.JLabel cancel;
    private javax.swing.JLabel redo;
    private javax.swing.JLabel curren_player;
    private javax.swing.JLabel current_player;
    private javax.swing.JLabel end_turn;
    private javax.swing.JLabel game_board;
    private javax.swing.JLabel sound;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel menu_cancel;
    private javax.swing.JLabel menu_home;
    private javax.swing.JPanel menu_panel;
    private javax.swing.JLabel menu_restart;
    private javax.swing.JLabel menu_save;
    private javax.swing.JLabel pause_menu;
    private javax.swing.JLabel rules;
    private javax.swing.JLabel rules_back;
    private javax.swing.JLabel rules_bg;
    private javax.swing.JPanel rules_panel;
    private javax.swing.JLabel rules_text;
    private javax.swing.JLabel rules_title;
    private javax.swing.JLabel surrender;
    private BoardPanel bp;

    // End of variables declaration
}