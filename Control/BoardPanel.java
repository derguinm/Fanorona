/*this the actual board panel which contains pawns ... */
package Control;

import javax.swing.*;
import javax.imageio.ImageIO;
import java.util.Properties;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import Model.*;
import Structures.*;
import View.GameBoard;

public class BoardPanel extends JPanel {
    Jeu game;
    JPanel gb;
    int c, l, step, oneMove, gameOver;

    boolean estCliquer;
    Pawn[][] pawns;
    Point[][] centerPoints;
    Image img;
    Point pawnPressedPt, direction;
    Pawn pawnPressed, sp;
    Coup strock;
    Plateau board;
    SequenceListe<Point> eligiblePoints;
    Properties prop;
    IA ia;
    public int mode, iaStarts, p1, p2, init, difficulty ;

    public BoardPanel(Jeu game, Coup strock, GameBoard gb) {
        setLocation(80, 140);
        setSize(640, 350);
        this.gb = gb;
        this.direction = new Point(0, 0);
        this.game = game;
        this.board = game.getPlateau();
        this.c = board.colonnes();
        this.l = board.lignes();
        this.centerPoints = new Point[l][c];
        this.step = 0;
        this.strock = strock;
        this.eligiblePoints = new SequenceListe<Point>();
        this.oneMove = 0;
        this.estCliquer = true;
        this.prop = new Properties();
        this.ia = new IA(game, strock);
        /* getting settings from the config file */
        try {
            InputStream stream = new FileInputStream("default.cfg");
            prop.load(stream);
            init = Integer.parseInt(prop.getProperty("Initiator"));
            p1 = Integer.parseInt(prop.getProperty("Player1"));
            p2 = Integer.parseInt(prop.getProperty("Player2"));
            difficulty = Integer.parseInt(prop.getProperty("Difficulty"));
            game.setJoueur(init + 1);

            if (p1 == 0 && p2 == 0)
                mode = 0;
            else if (p1 == 1 && p2 == 1) {
                mode = 1;
                iaStarts = 1;
            } else if ((p1 + p2) == 1) {
                mode = 2;
                if (p1 == 1 && init == 0 || p2 == 1 && init == 1)
                    iaStarts = 1;
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            InputStream stream = new FileInputStream("View/ressources/board_bg_n.png");
            img = ImageIO.read(stream);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        pawns = new Pawn[l][c];
        initPawns();
        /* creatring mouse listners */
        this.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e))
                    clickHandler(p);
            }

            public void mousePressed(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e))
                    pressHandler(p);
            }

            public void mouseReleased(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e))
                    releasedHandler(p);
            }
        });

        this.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                Point p = new Point(e.getX(), e.getY());
                if (SwingUtilities.isLeftMouseButton(e))
                    dragHandler(p);
            }
        });
    }

    /* picking game mode with initiator */
    public void iaPlays() {
        if (mode == 1) {
            while (gameOver == 0) {
                ia.IaAleatoire(this);
                endRound();
            }
        } else if (iaStarts == 1) {
            ia.IaAleatoire(this);
            endRound();
        }

    }

    public int getStep() {
        return step;
    }

    public Jeu getGame() {
        return this.game;
    }

    public Point getPointByIndex(Point e) {
        return centerPoints[(int) e.getX()][(int) e.getY()];
    }

    /*
     * handler that highlight where the clicked pawns can go and the choices amons
     * two pawns
     */
    public void clickHandler(Point e) {
        Pawn p;

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                if (board.estVide(i, j))
                    continue;
                if ((p = pawns[i][j]) == null)
                    return;
                if (p.isMouseOver((int) e.getX(), (int) e.getY())) {
                    p.setHighlight(true);
                    pawnPressedPt = p.getPawnCenter();
                    pawnPressed = p;
                    if (pawnPressed.IsPlayable()) {
                        setEligiblePoints();

                    }
                } else {
                    p.setHighlight(false);
                }
            }
        }
        if (step == 2 && pawnPressed != null) {
            int i = pawnPressed.i, j = pawnPressed.j;
            step = game.jouercoup(new Point(i, j), step, strock);
            pawnPressed = null;
            updatePawns();
            highlightEaters(3);

        }
        repaint();
    }

    public void pressHandler(Point e) {
        clickHandler(e);
    }

    public void dragHandler(Point e) {
        if (pawnPressed != null && pawnPressed.IsPlayable()) {
            pawnPressed.setPawnCenter((int) e.getX(), (int) e.getY());
            pawnPressed.wasDragged = true;
        }

        repaint();
    }

    /*
     * drag handler that get the center of the released position and verify if the
     * mouvement if autorised
     */
    public void releasedHandler(Point e) {
        int i, j, k, m, flag;
        Iterateur<Point> it;
        Point p;

        if (pawnPressed != null && pawnPressed.wasDragged) {
            i = pawnPressed.i;
            j = pawnPressed.j;
            it = eligiblePoints.iterateur();
            flag = 1;
            pawnPressed.setHighlight(false);

            if (!eligiblePoints.estVide()) {
                while (it.aProchain()) {
                    p = it.prochain();
                    k = (int) p.getX();
                    m = (int) p.getY();

                    if (checkOnSurface(e, centerPoints[k][m])) {
                        pawnPressed.setPawnCenter((int) centerPoints[k][m].getX(), (int) centerPoints[k][m].getY());
                        pawnPressed.i = k;
                        pawnPressed.j = m;
                        flag = 1;
                        break;
                    }
                    pawnPressed.setPawnCenter((int) centerPoints[i][j].getX(), (int) centerPoints[i][j].getY());
                    flag = 0;
                }
            }

            if (flag == 1) {
                direction = new Point(pawnPressed.i - i, pawnPressed.j - j);
                pawns[pawnPressed.i][pawnPressed.j] = pawnPressed;
                if (step < 2) {
                    step = game.jouercoup(new Point(i, j), step, strock);
                    step = game.jouercoup(new Point(pawnPressed.i, pawnPressed.j), step, strock);
                    estCliquer = false;
                }
                if (step == 2) {
                    sp = pawnPressed;
                    pawns[sp.i + (int) direction.getX()][sp.j + (int) direction.getY()].rainbow = true;
                    pawns[sp.i - 2 * (int) direction.getX()][sp.j - 2 * (int) direction.getY()].rainbow = true;
                }

                pawnPressed.wasDragged = false;
                updatePawns();
                if (oneMove == 0)
                    highlightEaters(0);
            }
        }

        pawnPressed = null;
        pawnPressedPt = null;

        repaint();
        eligiblePoints = new SequenceListe<Point>();
    }

    /* end turn */
    public boolean endRound() {
        boolean b = false;
        if (strock.estCoup() || !estCliquer) {
            try {
                game.h.ajouterConfiguration(strock);

            } catch (CloneNotSupportedException ef) {
                ef.printStackTrace();
            }
            if (gameOver == 1)
                return false;
            if (pawnPressed != null)
                pawnPressed.setHighlight(false);
            eligiblePoints = new SequenceListe<Point>();
            direction = new Point(0, 0);
            strock.videList();
            step = 0;
            game.changerJoueur();
            ((GameBoard) gb).joueur_current();
            updatePawns();
            highlightEaters(1);
            estCliquer = true;
            b = true;
        }
        return b;
    }

    public void endRoundIA() {
        SequenceListe<Point> IAList;
        if (endRound() == true) {
            IAList = ia.IaAleatoire(this);
            estCliquer = false;
            endRound();
        }
    }

    /* verification if we can move the pawn diagonally */
    boolean is8positions(int i, int j) {
        if (i % 2 == j % 2)
            return true;
        return false;
    }

    boolean checkOnSurface(Point p1, Point p2) {
        boolean b = false;
        double c1, c2;
        c1 = Math.pow(p1.getX() - p2.getX(), 2);
        c2 = Math.pow(p1.getY() - p2.getY(), 2);

        if (Math.sqrt(c1 + c2) < 20)
            b = true;

        return b;
    }

    boolean isPositionEligible(int currI, int currJ, int i, int j) {
        return (game.MangerDirection(game.joueur(), currI, currJ, new Point(i, j), 0)
                || game.MangerDirection(game.joueur(), currI, currJ, new Point(i, j), 1));
    }

    void setEligiblePoints() {
        int i, j;

        if (pawnPressed != null) {
            i = pawnPressed.i;
            j = pawnPressed.j;

            for (int k = -1; k < 2; k++) {
                for (int m = -1; m < 2; m++) {
                    if (isPositionEligible(i, j, m, k) && !strock.trouverPoint(new Point(i + m, j + k))
                            && !(direction.getX() == m && direction.getY() == k))
                        eligiblePoints.insereQueue(new Point(i + m, j + k));
                }
            }
            if (eligiblePoints.estVide()) {
                for (int k = -1; k < 2; k++) {
                    for (int m = -1; m < 2; m++) {
                        if (!is8positions(i, j) && (k != 0 && m != 0))
                            continue;
                        if ((i + m) >= 0 && (i + m) < l && (j + k) >= 0 && (j + k) < c
                                && board.estVide((i + m), (j + k)) && !(direction.getX() == m && direction.getX() == k))
                            eligiblePoints.insereQueue(new Point(i + m, j + k));
                    }
                }
            }
        }
    }

    /*
     * highlight the possible eater if none existe highlight non eater with
     * possibility to move
     */
    public void highlightEaters(int t) {
        SequenceListe<Point> seq;
        int i, j, currentPlayer;

        currentPlayer = game.joueur();
        seq = game.joueurDoitManger(strock);
        Iterateur<Point> it = seq.iterateur();

        if (!seq.estVide()) {
            oneMove = 0;
            while (it.aProchain()) {
                Point p = it.prochain();
                i = (int) p.getX();
                j = (int) p.getY();
                if (t == 0 && pawnPressed != null && pawnPressed.i == i && pawnPressed.j == j)
                    pawns[i][j].setPlayable(true);
                else if (t == 1)
                    pawns[i][j].setPlayable(true);
                else if (t == 3 && sp != null && sp.i == i && sp.j == j)
                    pawns[i][j].setPlayable(true);
            }
        } else if (t == 1) {
            for (i = 0; i < l; i++)
                for (j = 0; j < c; j++) {
                    if (pawns[i][j] != null && board.aPionX(currentPlayer, i, j))
                        pawns[i][j].setPlayable(true);
                }
            oneMove = 1;
        }

        repaint();
    }

    void initPawns() {
        int pawnSize = 40;
        int hgap = (getWidth() - pawnSize) / (c - 1);
        int vgap = (getHeight() - pawnSize) / (l - 1);
        Point p = new Point();
        Pawn pawn;

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                p.setLocation((int) j * hgap + pawnSize / 2, (int) i * vgap + pawnSize / 2);
                centerPoints[i][j] = new Point(p);
                pawn = pawns[i][j] = new Pawn();

                if (board.estVide(i, j)) {
                    pawn = null;
                    continue;
                }

                if (board.aPion1(i, j))
                    pawn.setPawnColor(Color.BLACK);

                else if (board.aPion2(i, j))
                    pawn.setPawnColor(Color.YELLOW);

                pawn.i = i;
                pawn.j = j;
                pawn.setPawnDiameter(pawnSize);
                pawn.setPawnCenter((int) p.getX(), (int) p.getY());
            }
        }

        highlightEaters(1);
    }

    void updatePawns() {
        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                if (pawns[i][j] != null)
                    pawns[i][j].setPlayable(false);
                if (board.estVide(i, j))
                    pawns[i][j] = null;
                else if (pawns[i][j] == null)
                    pawns[i][j] = sp;
            }
        }
    }

    public void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        Pawn p;
        Iterateur<Point> it;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.clearRect(0, 0, getWidth(), getHeight());
        g2d.drawImage(img, 0, 0, getWidth(), getHeight(), null);
        g2d.setColor(Color.BLACK);

        it = eligiblePoints.iterateur();

        for (int i = 0; i < l; i++) {
            for (int j = 0; j < c; j++) {
                if (!board.estVide(i, j)) {
                    p = pawns[i][j];

                    if (p == null)
                        continue;

                    p.drawPawn(g2d);
                    if (p.IsPlayable())
                        p.highlightPawn(g2d, 1);
                    if (p.isHighlithed())
                        p.highlightPawn(g2d, 2);
                    if (p.rainbow) {
                        p.highlightPawn(g2d, 3);
                        p.rainbow = false;
                    }
                }
            }
        }

        while (it.aProchain()) {
            Point pt = it.prochain();
            int i, j, x, y;

            i = (int) pt.getX();
            j = (int) pt.getY();
            x = (int) centerPoints[i][j].getX();
            y = (int) centerPoints[i][j].getY();

            g2d.setColor(new Color(255, 50, 50, 80));
            g2d.fillOval(x - 20, y - 20, 40, 40);
        }

        if (game.FinJeu()) {
            gameOver = 1;
            drawWinner(g, game);
            updatePawns();

        }
    }

    public void changerjoueur2() {
        if (game.h.getTaille() % 2 == 0) {
            game.setJoueur((game.initiateur()+1)%2);
        } else {
            game.setJoueur(game.initiateur());
        }
    }

    public void drawWinner(Graphics g, Jeu game) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setFont(new Font("Ani", Font.BOLD, 50));
        if (game.joueur() == 1) {
            g2d.setColor(Color.BLACK);
            g2d.drawString("The winner is: BLACK", getX() / 2, getY() / 2);
        } else {
            g2d.setColor(Color.YELLOW);
            g2d.drawString("The winner is: YELLOW", getX() / 2, getY() / 2);
        }

    }

    public Jeu Undo() {
        if (!estCliquer) {
            endRound();
        }
        estCliquer = false;
        game.affiche();
        game.annulerCoup2();
        game.changerJoueur();

        return game;
    }

    public Jeu Redo() {

        game.retablirCoup2();
        game.changerJoueur();
        return game;
    }
}