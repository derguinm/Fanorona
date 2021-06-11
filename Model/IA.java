package Model;

import java.awt.*;
import java.util.*;
import java.lang.Thread;
import Structures.*;
import View.GameBoard;
import Control.*;

class triple {
    int valeur ;
    Point p1;
    Point p2;

    public triple(int valeur) {
        this.valeur = valeur;
    }

    triple(int valeur, Point p1, Point p2 ){
        this.valeur = valeur ;
        this.p1 = new Point(p1.x,p1.y) ;
        this.p2 = new Point(p2.x,p2.y) ;
    }
    public String toString(){
        return "valeur d'evaluation :"+valeur+",(p1,p2) : ("+p1+","+p2+")";
    }
}

public class IA {
	Coup cp;
	Jeu J;
	Plateau PL;

	public IA(Jeu j, Coup cp) {
		J = j;
		this.cp = cp;
		PL = J.getPlateau();
		cp = new Coup();
	}

	int cmpPions(int joueur) {
		int l = PL.lignes();
		int c = PL.colonnes();
		int cmp = 0;

		for (int i = 0; i < l; i++) {
			for (int j = 0; j < c; j++) {
				if (PL.aPionX(joueur, i, j))
					cmp++;
			}
		}
		return cmp;
	}

	int evaluation(int joueur) {
		if (joueur == 2)
			return cmpPions(1) - cmpPions(2);
		else
			return cmpPions(2) - cmpPions(1);
	}
	public triple minMax(int horizon, boolean maxJoueur, int joueur,Point p_int,Point dp) {
		cp.videList();
		boolean mangeur=true;
		Point p, p_m = null, d, pp, p_p = null ;
		int etape = 0 ;

		if( horizon == 0 || J.FinJeu() ){
			//System.err.println("attention au plateau") ;
			//J.affiche();
			return new triple( evaluation(joueur) )  ;
		}

		SequenceListe<Point> pionsM = J.joueurDoitManger(cp) ;
		if(pionsM.estVide()){
			pionsM = pionsNonMongeur();
			mangeur = false;
			//System.err.println("pions bouger par l'IA à l'horizon "+horizon+" est "+pionsM);
		}
		Iterateur<Point> it = pionsM.iterateur();

		if( maxJoueur ) {
			//System.err.println(" if( maxJoueur ) ");

			double maxEval = Double.NEGATIVE_INFINITY;
			while( it.aProchain() ) {
				p = it.prochain();
				SequenceListe<Point> pionsD = J.directionMangeur(p.x, p.y, cp,mangeur) ;
				Iterateur<Point> itd = pionsD.iterateur();
				
				if(!mangeur) System.err.println("pions bouger par l'IA à l'horizon "+horizon+" est "+p+" et sa liste de D :" + pionsD);

				while( itd.aProchain() ) {
					d = itd.prochain();

					pp = new Point(p.x + d.x, p.y + d.y ) ;
					etape = J.jouercoup(p, 0, cp);
					etape = J.jouercoup(pp, 1, cp);
					if(etape == 2)
						J.jouercoup(new Point(p.x + 2*d.x, p.y + 2*d.y ), etape , cp);
					J.ajouterConfiguratation(cp);
					J.changerJoueur();
					cp.videList();
					triple trp = minMax( horizon -1 , false, J.joueur(),p,pp) ;
					int eval = trp.valeur ;
					if((int)maxEval < eval) {
						maxEval = eval ;
						p_m = p ;
						p_p = pp ;
					}
					J.annulerCoup3();
					//System.out.println("j'annule");
					//J.affiche();
				}
				//System.out.println("coup : "+p_m+" "+p_p);

			}
			return new triple( (int)maxEval, p_m, p_p );
		}
		else {
			//System.err.println(" if( minJoueur ) ");

			double minEval = Double.POSITIVE_INFINITY;
			while( it.aProchain() ) {
				
				p = it.prochain();
				//System.err.println("pion bouger par l'IA :"+p);
				SequenceListe<Point> pionsD = J.directionMangeur(p.x, p.y, cp,mangeur) ;
				Iterateur<Point> itd = pionsD.iterateur();
				//System.err.println("pion bouger par l'IA à l'horizon "+horizon+" est "+p+" et sa liste de D :" + pionsD);


				while( itd.aProchain() ) {
					d = itd.prochain();
					
					if(!mangeur) System.err.println("pions bouger par l'IA à l'horizon "+horizon+" est "+p+" et sa liste de D :" + pionsD);
					
					pp = new Point(p.x + d.x, p.y + d.y ) ;
					etape = J.jouercoup(p, 0, cp);
					etape = J.jouercoup(pp, 1, cp);
					if(etape == 2)
						J.jouercoup(new Point(p.x + 2*d.x, p.y + 2*d.y ), etape , cp);
					J.ajouterConfiguratation(cp);
					J.changerJoueur();
					cp.videList();
					triple trp = minMax( horizon -1 , true, J.joueur(),p,pp);
					int eval = trp.valeur ;
					if((int) minEval > eval) {
						minEval = eval ;
						p_m = p ;
						p_p = pp ;
					}
					J.annulerCoup3();
					//System.out.println("j'annulle ");
					//J.affiche();

				}
			}
			return new triple( (int)minEval, p_m, p_p ) ;
		}

	}
/*incomplet*/
public  SequenceListe<Point> min_max_globale(int niveau,BoardPanel dp) {
	int etape = 0 ;
	SequenceListe<Point> lcl =new SequenceListe<Point>() ;
	//System.out.println(" min_max_globale ");

	triple trp = minMax(niveau , true, J.joueur(),null,null) ;
	//J.affiche();
	Point p = trp.p1 ;
	Point p2 = trp.p2 ;
	dp.highlightEaters(1);
	pause_paint(dp);
	dp.clickHandler(dp.getPointByIndex(p));
	pause_paint(dp);
	dp.dragHandler(dp.getPointByIndex(p2));
	pause_paint(dp);
	dp.releasedHandler(dp.getPointByIndex(p2));
	pause_paint(dp);
	Point d = J.direction(p, p2);
	if(dp.getStep() == 2) {
		dp.clickHandler(dp.getPointByIndex(new Point(p2.x+d.x,p2.y+d.y)));
		pause_paint(dp);
	}
	
	return lcl;
}


	public void pause_paint(BoardPanel dp) {
		dp.paintImmediately(0, 0, 640, 350);
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	/*
	 * int minMax(int horizon, boolean maxJoueur, int joueur) {
	 * 
	 * if( horizon == 0 || J.FinJeu() ) return evaluation(joueur) ;
	 * 
	 * if( maxJoueur ) { double maxEval = Double.NEGATIVE_INFINITY;
	 * SequenceListe<Point> pionsM = J.joueurDoitManger(cp) ; Iterateur it =
	 * pionsM.iterateur(); while( it.aProchain() ) { int eval = minMax( horizon -1 ,
	 * false,//( (joueur+1)%3 )+1 ) ; }
	 * 
	 * }
	 * 
	 * 
	 * 
	 * return 0 ; }
	 */

	// ------------------------------------------------------------
	public SequenceListe<Point> pionsNonMongeur() {

		SequenceListe<Point> listeP = new SequenceListe<>();

		for (int i = 0; i < this.PL.lignes(); i++) {
			for (int j = 0; j < this.PL.colonnes(); j++) {
				if (J.interieure(i, j) && PL.aPionX(J.joueur(), i, j) && estDeplacer(new Point(i, j))) {
					listeP.insereTete(new Point(i, j));
				}
			}
		}
		return listeP;
	}

	public Point pionAleatoire(SequenceListe<Point> s) {
		Random rd = new Random();
		double r = rd.nextDouble();
		Iterateur<Point> it = s.iterateur();
		Point p = null;

		if (it.aProchain())
			p = it.prochain();
		while (it.aProchain() && (r < 0.8)) {
			p = it.prochain();
			r = rd.nextDouble();
		}

		return p;
	}

	public boolean estDeplacer(Point p) {

		for (int k = -1; k < 2; k++) {
			for (int l = -1; l < 2; l++) {

				if (J.interieure(p.x + k, p.y + l) && !(k == 0 && l == 0) && PL.estVide(p.x + k, p.y + l)) {
					return true;
				}

			}
		}
		return false;
	}

	public Point directionAleatoire(Point p) {
		Random rd = new Random();
		double r = rd.nextDouble();
		Point pd = null;
		int n = 0;

		for (int k = -1; k < 2; k++) {
			for (int l = -1; l < 2; l++) {

				if (J.interieure(p.x + k, p.y + l) && !(k == 0 && l == 0) && PL.estVide(p.x + k, p.y + l)) {
					if (p.x % 2 == p.y % 2) {
						if (n == 0) {
							pd = new Point(p.x + k, p.y + l);
							n = 1;
						}
						if (r < 0.8) {
							pd = new Point(p.x + k, p.y + l);
							break;
						}
					} else {
						if (Math.abs(k) + Math.abs(l) == 1) {
							if (n == 0) {
								pd = new Point(p.x + k, p.y + l);
								n = 1;
							}
							if (r < 0.8) {
								pd = new Point(p.x + k, p.y + l);
								break;
							}
						}
					}
				}

			}
		}
		return pd;
	}

	public SequenceListe<Point> IaAleatoire(BoardPanel dp) {
		SequenceListe<Point> pionsM;
		SequenceListe<Point> listeD;
		SequenceListe<Point> listeC = new SequenceListe<>();
		Point p, d;
		boolean b = true;
		int etape = 0;

		pionsM = J.joueurDoitManger(cp);
		// J.affiche();

		if (!pionsM.estVide()) {

			p = pionAleatoire(pionsM);

			listeC.insereTete(p);

			while (b) {

				listeD = J.directionMangeur(p.x, p.y, cp, true);

				if (!listeD.estVide()) {
					d = pionAleatoire(listeD);
					d = new Point(p.x + d.x, p.y + d.y);
					dp.highlightEaters(1);
					pause_paint(dp);
					dp.clickHandler(dp.getPointByIndex(p));
					pause_paint(dp);
					// etape = J.jouercoup(p, etape, cp) ;
					// J.affiche();
					// etape = J.jouercoup(p, etape, cp) ;
					dp.dragHandler(dp.getPointByIndex(d));
					pause_paint(dp);
					dp.releasedHandler(dp.getPointByIndex(d));
					pause_paint(dp);
					listeC.insereTete(d);
					Point t = dp.getGame().direction(p, d);
					p = new Point(d);
					if (dp.getStep() == 2) {
						d = new Point(d.x + t.x, d.y + t.y);
						dp.clickHandler(dp.getPointByIndex(d));
						pause_paint(dp);
						// etape = J.jouercoup( new Point(p.x + d.x, p.y + d.y ) , etape, cp) ;
						// J.affiche();
					}
				} else {
					b = false;
				}
			}
		} else {
			boolean x = true;
			while (x) {
				p = pionAleatoire(pionsNonMongeur());
				dp.highlightEaters(1);
				pause_paint(dp);
				// etape = J.jouercoup(p, etape, cp) ;
				dp.clickHandler(dp.getPointByIndex(p));
				pause_paint(dp);
				p = directionAleatoire(p);
				if (p == null) {
					continue;
				}
				x = false;
				dp.dragHandler(dp.getPointByIndex(p));
				pause_paint(dp);
				dp.releasedHandler(dp.getPointByIndex(p));
				pause_paint(dp);
				b = true;
			}

		}
		// J.affiche();

		return listeC;
	}

}
