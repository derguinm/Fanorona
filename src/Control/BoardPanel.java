package Control;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;
import Structures.*;

public class BoardPanel extends JPanel{
    Jeu game;
    int c, l, step, oneMove, gameOver;
    boolean estCliquer ;
    Pawn[][] pawns;
    Point[][] centerPoints;
    Image img;
    Point pawnPressedPt;
    Pawn pawnPressed;
    Coup strock;
    Plateau board;
    SequenceListe<Point> eligiblePoints;

    public BoardPanel(Jeu game, Coup strock){
        setLocation(80, 140);
        setSize(640, 350);
        
       
        this.game = game;
        this.board = game.getPlateau();
        this.c = board.colonnes();
        this.l = board.lignes();
        this.centerPoints = new Point[l][c];
        this.step = 0;
        this.strock = strock;
        this.eligiblePoints = new SequenceListe<Point>();
        this.oneMove = 0;
        this.estCliquer = false ;

        try{
            InputStream stream = new FileInputStream("src/View/ressources/board_bg_n.png");
            img = ImageIO.read(stream);
        }
        catch(Exception ex){
            System.out.println(ex.getMessage());
        }
        
        pawns = new Pawn[l][c];
        initPawns();

        this.addMouseListener(new MouseAdapter(){
            public void mouseClicked(MouseEvent e){
                Point p = new Point(e.getX(), e.getY());
                if(SwingUtilities.isLeftMouseButton(e))
                    clickHandler(p);
            }

            public void mousePressed(MouseEvent e){
                Point p = new Point(e.getX(), e.getY());
                if(SwingUtilities.isLeftMouseButton(e))
                    pressHandler(p);
            }

            public void mouseReleased(MouseEvent e){
                Point p = new Point(e.getX(), e.getY());
                if(SwingUtilities.isLeftMouseButton(e))
                    releasedHandler(p);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter(){
            public void mouseDragged(MouseEvent e){
                Point p = new Point(e.getX(), e.getY());
                if(SwingUtilities.isLeftMouseButton(e))
                    dragHandler(p);
            }
        });
    }

    void clickHandler(Point e){
        Pawn p;

        for(int i = 0; i < l; i++){
            for(int j = 0; j < c; j++){
                if(board.estVide(i, j)) continue;
                if((p = pawns[i][j]) == null) return;
                if(p.isMouseOver((int)e.getX(), (int)e.getY())){
                    //System.out.println("Pawn (" + p.i + "," + p.j + ") is clicked");
                    p.setHighlight(true);
                    pawnPressedPt = p.getPawnCenter();
                    pawnPressed = p;
                    if(pawnPressed.IsPlayable())
                        setEligiblePoints();
                }
                else
                    p.setHighlight(false);
            }
        }

        repaint();
    }

    void pressHandler(Point e){
        clickHandler(e);
    }

    void dragHandler(Point e){
        if(pawnPressed != null && pawnPressed.IsPlayable()){
            pawnPressed.setPawnCenter((int)e.getX(), (int)e.getY());
            pawnPressed.wasDragged = true;
        }

        repaint();
    }

    void releasedHandler(Point e){
        int i, j, k, m, flag;
        Iterateur<Point> it;
        Point p;

        if(pawnPressed != null && pawnPressed.wasDragged){
            i = pawnPressed.i;
            j = pawnPressed.j;
            it = eligiblePoints.iterateur();
            flag = 1;
            pawnPressed.setHighlight(false);

            if(!eligiblePoints.estVide()){
                while(it.aProchain()){
                    p = it.prochain();
                    k = (int)p.getX();
                    m = (int)p.getY();

                    if(checkOnSurface(e, centerPoints[k][m])){
                        pawnPressed.setPawnCenter((int)centerPoints[k][m].getX(), (int)centerPoints[k][m].getY());
                        pawnPressed.i = k;
                        pawnPressed.j = m;
                        flag = 1;
                        break;
                    }
                    pawnPressed.setPawnCenter((int)centerPoints[i][j].getX(), (int)centerPoints[i][j].getY());
                    flag = 0;
                }
            }

            if(flag == 1){
                pawns[pawnPressed.i][pawnPressed.j] = pawnPressed;
                step = game.jouercoup(new Point(i, j), step, strock);
                step = game.jouercoup(new Point(pawnPressed.i, pawnPressed.j), step, strock);
                estCliquer = false ;
                pawnPressed.wasDragged = false;
                updatePawns();
                if(oneMove == 0)
                    highlightEaters(0);
            }
        }

        pawnPressed = null;
        pawnPressedPt = null;

        repaint();
        eligiblePoints = new SequenceListe<Point>();
    }

    public void endRound(){
    	
        if (strock.estCoup() && !estCliquer) {
          try {
              game.hi.ajouterConfiguration(game.getPlateau().clone());
              System.out.println("ajout d'une configuration dans l'historique");
          } catch (CloneNotSupportedException ef) {
              ef.printStackTrace();
              System.out.println("exception");
          }
          if (gameOver == 1) return;
          strock.videList();
          step = 0;
          game.changerJoueur();
          updatePawns();
          highlightEaters(1);
          estCliquer = true ;
      }
    }

    boolean is8positions(int i, int j){
        if(i % 2 == j % 2)
            return true;
        return false;
    }

    boolean checkOnSurface(Point p1, Point p2){
        boolean b = false;
        double c1, c2;
        c1 = Math.pow(p1.getX() - p2.getX(), 2);
        c2 = Math.pow(p1.getY() - p2.getY(), 2);

        if(Math.sqrt(c1 + c2) < 20)
            b = true;

        return b;
    }

    boolean isPositionEligible(int currI, int currJ, int i, int j){
        return (game.MangerDirection(game.joueur(), currI, currJ, new Point(i, j), 0) || game.MangerDirection(game.joueur(), currI, currJ, new Point(i, j), 1));
    }

    void setEligiblePoints(){
        int i, j;

        if(pawnPressed != null){
            i = pawnPressed.i;
            j = pawnPressed.j;

            for(int k = -1; k < 2; k++){
                for(int m = -1; m < 2; m++){
                    if(isPositionEligible(i, j, m, k) && !strock.trouverPoint(new Point(i+m,j+k)))
                        eligiblePoints.insereQueue(new Point(i+m, j+k));
                }
            }
            if(eligiblePoints.estVide()){
                for(int k = -1; k < 2; k++){
                    for(int m = -1; m < 2; m++){
                        if(!is8positions(i, j) && (k != 0 && m != 0)) continue;
                        if((i+m) >= 0 && (i+m) < l && (j+k) >= 0 && (j+k) < c && board.estVide((i+m), (j+k)))
                            eligiblePoints.insereQueue(new Point(i+m, j+k));
                    }
                }
            }
        }
    }

    void highlightEaters(int t){
        SequenceListe<Point> seq;
        int i, j, currentPlayer;

        currentPlayer = game.joueur();
        seq = game.joueurDoitManger();
        Iterateur<Point> it = seq.iterateur();
        
        if(!seq.estVide()){
            oneMove = 0;
            while(it.aProchain()){
                Point p = it.prochain();
                i = (int)p.getX();
                j = (int)p.getY();
                if(t == 0 && pawnPressed != null && pawnPressed.i == i && pawnPressed.j == j)
                    pawns[i][j].setPlayable(true);
                else if(t == 1)
                    pawns[i][j].setPlayable(true);
            }
        }
        else if(t == 1){
            for(i = 0; i < l; i++)
                for(j = 0; j < c; j++){
                    if(pawns[i][j] != null && board.aPionX(currentPlayer, i, j))
                        pawns[i][j].setPlayable(true);
                }
            oneMove = 1;
        }

        repaint();
    }

    void initPawns(){
        int pawnSize = 40;
        int hgap = (getWidth() - pawnSize) / (c-1);
        int vgap = (getHeight() - pawnSize) / (l-1);
        Point p = new Point();
        Pawn pawn;
        
        for(int i = 0; i < l; i++){
            for(int j = 0; j < c; j++){
                p.setLocation((int)j*hgap + pawnSize / 2, (int)i*vgap + pawnSize / 2);
                centerPoints[i][j] = new Point(p);
                pawn = pawns[i][j] = new Pawn();

                if(board.estVide(i, j)){
                    pawn = null;
                    continue;
                }
                    
                if(board.aPion1(i, j))
                    pawn.setPawnColor(Color.BLACK);
                    
                else if(board.aPion2(i, j))
                    pawn.setPawnColor(Color.YELLOW);
                
                pawn.i = i;
                pawn.j = j;
                pawn.setPawnDiameter(pawnSize);
                pawn.setPawnCenter((int)p.getX(), (int)p.getY());
            }
        }

        highlightEaters(1);
    }

    void updatePawns(){
        for(int i = 0; i < l; i++){
            for(int j = 0; j < c; j++){
                if(pawns[i][j] != null)
                    pawns[i][j].setPlayable(false);
                if(board.estVide(i, j))
                    pawns[i][j] = null;
            }
        }
    }

    public void paintComponent(Graphics g){
        Graphics2D g2d = (Graphics2D) g;
        Pawn p;
        Iterateur<Point> it;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2d.setColor(Color.BLACK);

        it = eligiblePoints.iterateur();

        for(int i = 0; i < l; i++){
            for(int j = 0; j < c; j++){
                if(!board.estVide(i, j)){
                    p = pawns[i][j];

                    if(p == null) continue;

                    p.drawPawn(g2d);
                    if(p.IsPlayable())
                        p.highlightPawn(g2d, 1);
                    if(p.isHighlithed())
                        p.highlightPawn(g2d, 2);
                }
            }
        }

        while(it.aProchain()){
            Point pt = it.prochain();
            int i, j, x, y;

            i = (int)pt.getX();
            j = (int)pt.getY();
            x = (int)centerPoints[i][j].getX();
            y = (int)centerPoints[i][j].getY();

            g2d.setColor(new Color(255, 50, 50, 80));
            g2d.fillOval(x - 20, y - 20, 40, 40);
        }

        if(game.FinJeu()){
            gameOver = 1;
            g2d.setColor(Color.DARK_GRAY);
            g2d.setFont(new Font("Ani", Font.BOLD, 50));
            g2d.drawString("The winner is: Player" + game.joueur(), (getX() / 2) + 20, getY() / 2);
        }
    }
    
    public void changerjoueur2() {
    	System.out.println(" joueur avant : " + game.joueur());
    	if (game.hi.taille_tete()%2==1 ){
			game.setJoueur( game.initiateur() ) ;
		}
		else {
			
			if( game.initiateur() == 1)
				game.setJoueur(2) ;
			else
				game.setJoueur(1) ;
		}
    	System.out.println(" joueur apres : " + game.joueur());
    }

    public Jeu Undo() {
    	if( !estCliquer ) {
    		endRound();
    	}
    	estCliquer = false ;
    	
        game.annulerCoup();
        //changerjoueur2() ;
        System.out.println(game.joueur());
        return game;
    }

    public Jeu Redo() {
    	
        game.retablirCoup();
        changerjoueur2() ;
        System.out.println(game.joueur());
        return game;
    }
}
