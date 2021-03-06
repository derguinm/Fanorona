
package Model;

import Structures.Iterateur;
import Structures.SequenceListe;

import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.swing.*;
import java.awt.*;

public class Jeu implements Serializable {
	Plateau p;
	int joueur;
	public Historique hi;
	public boolean IA, surrender,debutjeu;
	int initiateur;
	public HistoriqueCoup h;
	Properties prop;

	public Jeu() {
		debutjeu=true;
		this.prop = new Properties();
		try {
            InputStream stream = new FileInputStream("default.cfg");
            prop.load(stream);
            initiateur = Integer.parseInt(prop.getProperty("Initiator"));
		} catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
		joueur=initiateur;

		//System.out.println(joueur());
		surrender = false;
		hi = new Historique();
		p = new Plateau();
		p.iniPlateau();
		h = new HistoriqueCoup(p);


		joueur = 1;
		IA = false;
	}
	
	public void notdebut(){
		debutjeu=false;
	}

	public void changerJoueurcoup(Coup cp){
		changerJoueur();
		cp.setjoueur(joueur);
	}

	public void ajouterConfiguratation(Coup cp) {
		try {
			h.ajouterConfiguration(cp);
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	public void setJoueur(int i) {
		joueur = i;
	}

	public int joueur() {
		return joueur;
	}

	public int initiateur() {
		return initiateur;
	}

	public void setinitiateur(int initiateur) {
		this.initiateur=initiateur;
	}

	public void ajoutConfiguration() {
		try {
			hi.tete.insereTete((Plateau) p.clone());
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
	}

	Point direction(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}

	boolean trouverPoint(Point p1, SequenceListe<Point> s) {
		Iterateur<Point> it = s.iterateur();
		while (it.aProchain()) {
			Point p = it.prochain();
			if ((p.x == p1.x) && (p.y == p1.y))
				return true;
		}
		return false;
	}

	int coups(Point p1, SequenceListe<Point> s, int etape) {
		if ((!s.estVide() && !trouverPoint(p1, s)) || s.estVide()) {
			if (etape == 0) {
				s.insereTete(p1);
				etape++;
			} else if (etape == 1) {
				Iterateur<Point> it = s.iterateur();
				Point p = it.prochain();
				Point d = direction(p, p1);
				if (MangerDirection(joueur, p1.x, p1.y, d, 0) && !MangerDirection(joueur, p1.x, p1.y, d, 1)) {
					deplacer(p1, d, 0);
					s.insereTete(p1);
					etape = 1;
				}

				else if (!MangerDirection(joueur, p1.x, p1.y, d, 0) && MangerDirection(joueur, p1.x, p1.y, d, 1)) {
					deplacer(p1, d, 1);
					s.insereTete(p1);
					etape = 1;
				}

				else if (MangerDirection(joueur, p1.x, p1.y, d, 0) && MangerDirection(joueur, p1.x, p1.y, d, 1)) {
					s.insereTete(p1);
					etape++;
				}

			} else if (etape == 2) {
				Iterateur<Point> it = s.iterateur();
				Point p_1 = it.prochain();
				Point p_2 = it.prochain();

				Point d = direction(p_1, p_2);
				Point d1 = direction(p_1, p1);
				d1 = new Point(d1.x % 2, d1.y % 2);
				if (d.x == d1.x && d.y == d1.y)
					deplacer(p1, d, 0);
				else if (d.x == -d1.x && d.y == -d1.y)
					deplacer(p1, d, 1);
				etape = 1;
			}
		}
		return etape;
	}

	public int jouercoup(Point p1, int etape, Coup c) {
		SequenceListe<Point> s = c.s;// point de passage
		SequenceListe<SequenceListe<Point>> pp = c.pionspris;// liste de pions pris pour chaque mouvement du pion
		Point d = null; // mangeur.
		SequenceListe<Point> res = new SequenceListe<>(); // pions pris
		Point direction_pred = c.getDirection_prec();
		if ((!s.estVide() && !c.trouverPoint(p1)) || s.estVide()) {
			if (etape == 0) {
				c.setDirection_prec(new Point(0, 0));
				s.insereTete(p1);
				etape++;
			} else if (etape == 1) {
				Iterateur<Point> it = s.iterateur();
				Point p = it.prochain();
				d = direction(p, p1);

				if (joueurDoitManger(c).estVide()) {
					deplacer3(p, d, 0);
					s.insereTete(new Point(p.x + d.x, p.y + d.y));
				} else {
					if (direction_pred.x != d.x || direction_pred.y != d.y) {
						if (MangerDirection(joueur(), p.x, p.y, d, 0) && !MangerDirection(joueur(), p.x, p.y, d, 1)) {
							res = deplacer3(p, d, 0);
							s.insereTete(p1);
							etape = 1;
						} else if (!MangerDirection(joueur(), p.x, p.y, d, 0)
								&& MangerDirection(joueur(), p.x, p.y, d, 1)) {
							res = deplacer3(p, d, 1);
							s.insereTete(p1);
							etape = 1;
						} else if (MangerDirection(joueur(), p.x, p.y, d, 0)
								&& MangerDirection(joueur(), p.x, p.y, d, 1)) {
							s.insereTete(p1);
							etape++;
						}
					}
				}

			} else if (etape == 2) {

				Iterateur<Point> it = s.iterateur();
				Point p_2 = it.prochain();
				Point p_1 = it.prochain();

				d = direction(p_1, p_2);
				Point d1 = direction(p_1, p1);

				d1 = new Point(arrondir(d1.x), arrondir(d1.y));

				if (d.x == d1.x && d.y == d1.y)
					res = deplacer3(p_1, d, 0);
				else if (d.x == -d1.x && d.y == -d1.y)
					res = deplacer3(p_1, d, 1);
				etape = 1;
			}

		}
		if (d != null)
			c.setDirection_prec(d);
		if (!res.estVide()) {
			pp.insereTete(res);
		}
		return etape;
	}

	public SequenceListe<Point> deplacer2(Point p, Point d, int type) {
		SequenceListe<Point> res = new SequenceListe<>();
		if (joueur == 1) {
			this.p.ajoutePion1(p.x + d.x, p.y + d.y);
			this.p.videCase(p.x, p.y);
			res.insereTete(p);
			int i = 2;
			if (type == 0)// par approche
			{
				while (interieure(p.x + d.x * i, p.y + d.y * i) && this.p.aPion2(p.x + d.x * i, p.y + d.y * i)) {
					this.p.videCase(p.x + d.x * i, p.y + d.y * i);
					res.insereTete(p);
					i++;
				}
			} else// par eloignement
			{
				while (interieure(p.x - d.x * (i - 1), p.y - d.y * (i - 1))
						&& this.p.aPion2(p.x - d.x * (i - 1), p.y - d.y * (i - 1))) {
					this.p.videCase(p.x - d.x * (i - 1), p.y - d.y * (i - 1));
					res.insereTete(p);
					i++;
				}
			}
		} else {
			this.p.ajoutePion2(p.x + d.x, p.y + d.y);
			this.p.videCase(p.x, p.y);
			int i = 2;
			if (type == 0)// par approche
			{
				while (interieure(p.x + d.x * i, p.y + d.y * i) && this.p.aPion1(p.x + d.x * i, p.y + d.y * i)) {
					this.p.videCase(p.x + d.x * i, p.y + d.y * i);
					i++;
				}
			} else// par eloignement
			{
				while (interieure(p.x - d.x * (i - 1), p.y - d.y * (i - 1))
						&& this.p.aPion1(p.x - d.x * (i - 1), p.y - d.y * (i - 1))) {
					this.p.videCase(p.x - d.x * (i - 1), p.y - d.y * (i - 1));
					i++;
				}
			}
		}
		return null;
	}

	public SequenceListe<Point> deplacer3(Point p, Point d, int type) {

		SequenceListe<Point> res = new SequenceListe<>();
		int joueuradvs;
		if (joueur == 1)
			joueuradvs = 2;
		else
			joueuradvs = 1;

		this.p.ajoutePionX(joueur, p.x + d.x, p.y + d.y);
		this.p.videCase(p.x, p.y);
		int i = 2;
		if (type == 0)// par approche
		{
			while (interieure(p.x + d.x * i, p.y + d.y * i)
					&& this.p.aPionX(joueuradvs, p.x + d.x * i, p.y + d.y * i)) {
				this.p.videCase(p.x + d.x * i, p.y + d.y * i);
				res.insereTete(new Point(p.x + d.x * i, p.y + d.y * i));
				i++;
			}
		} else {// par eloignement
			while (interieure(p.x - d.x * (i - 1), p.y - d.y * (i - 1))
					&& this.p.aPionX(joueuradvs, p.x - d.x * (i - 1), p.y - d.y * (i - 1))) {
				this.p.videCase(p.x - d.x * (i - 1), p.y - d.y * (i - 1));
				res.insereTete(new Point(p.x - d.x * (i - 1), p.y - d.y * (i - 1)));
				i++;
			}
		}
		return res;
	}

	int arrondir(int a) {
		int b = 0;
		if (a == 0)
			b = 0;
		else if (a > 0)
			b = 1;
		else if (a < 0)
			b = -1;
		return b;
	}

	void jouer(Point p1, Point d) {
		if (!MangerDirection(joueur, p1.x, p1.y, d, 0))
			MangerDirection(joueur, p1.x, p1.y, d, 1);
		/*
		 * SequenceListe<Point> listmangeur=null; listmangeur=joueurDoitManger(joueur);
		 * if(!listmangeur.estVide()) { Iterateur<Point> it=listmangeur.iterateur();
		 * while(it.aProchain()) { Point p=it.prochain(); if((p.x==p1.x)&&(p.y==p1.y)) {
		 * if( MangerDirection(joueur,p1.x,p1.y,d,1) ) { break; }
		 * 
		 * } } } else deplacer(p1,d,1);//p1 et d doivent ????????tre valide
		 */

	}

	public void deplacer(Point p, Point d, int type) {

		if (joueur == 1) {
			this.p.ajoutePion1(p.x + d.x, p.y + d.y);
			this.p.videCase(p.x, p.y);
			int i = 2;
			if (type == 0)// par approche
			{
				while (interieure(p.x + d.x * i, p.y + d.y * i) && this.p.aPion2(p.x + d.x * i, p.y + d.y * i)) {
					this.p.videCase(p.x + d.x * i, p.y + d.y * i);

					i++;
				}
			} else// par eloignement
			{
				while (interieure(p.x - d.x * (i - 1), p.y - d.y * (i - 1))
						&& this.p.aPion2(p.x - d.x * (i - 1), p.y - d.y * (i - 1))) {
					this.p.videCase(p.x - d.x * (i - 1), p.y - d.y * (i - 1));
					i++;
				}
			}
		} else {
			this.p.ajoutePion2(p.x + d.x, p.y + d.y);
			this.p.videCase(p.x, p.y);
			int i = 2;
			if (type == 0)// par approche
			{
				while (interieure(p.x + d.x * i, p.y + d.y * i) && this.p.aPion1(p.x + d.x * i, p.y + d.y * i)) {
					this.p.videCase(p.x + d.x * i, p.y + d.y * i);
					i++;
				}
			} else// par eloignement
			{
				while (interieure(p.x - d.x * (i - 1), p.y - d.y * (i - 1))
						&& this.p.aPion1(p.x - d.x * (i - 1), p.y - d.y * (i - 1))) {
					this.p.videCase(p.x - d.x * (i - 1), p.y - d.y * (i - 1));
					i++;
				}
			}
		}
	}

	public SequenceListe<Point> deplacementsPions(Point p) {
		SequenceListe<Point> listeD = new SequenceListe<>();

		for (int k = -1; k < 2; k++) {
			for (int l = -1; l < 2; l++) {

				if (interieure(p.x + k, p.y + l) && !(k == 0 && l == 0) && this.p.estVide(p.x + k, p.y + l)) {
					if ((p.x % 2 == p.y % 2)) {
						listeD.insereTete(new Point(k, l));
					} else {
						if (Math.abs(k) + Math.abs(l) == 1)
							listeD.insereTete(new Point(k, l));
					}

				}

			}
		}
		return listeD;
	}

	public SequenceListe<Point> directionMangeur(int i, int j, Coup c, boolean mangeur) {
		SequenceListe<Point> listeD = new SequenceListe<>();
		if (mangeur) {
			for (int k = -1; k < 2; k++) {
				for (int l = -1; l < 2; l++) {

					if (!(k == 0 && l == 0) && p.aPionX(joueur, i, j)) {// pourquoi p.aPionX
						if (MangerDirection(joueur(), i, j, new Point(k, l), 0)
								|| MangerDirection(joueur(), i, j, new Point(k, l), 1)) {
							Point d = c.getDirection_prec();
							if (!c.trouverPoint(new Point(i + k, j + l)) && (d.x != k || d.y != l)) {
								listeD.insereTete(new Point(k, l));
							}

						}

					}

				}
			}
		} else {
			listeD = deplacementsPions(new Point(i, j));
		}
		return listeD;

	}

	// la fonction retourne la liste des pions mangueur du joueur
	public SequenceListe<Point> joueurDoitManger(Coup c) {
		SequenceListe<Point> LPionAManger = new SequenceListe<>();
		SequenceListe<Point> listeD = new SequenceListe<>();

		if (c.s.estVide()) {
			for (int i = 0; i < p.lignes(); i++) {
				for (int j = 0; j < p.colonnes(); j++) {

					listeD = directionMangeur(i, j, c, true);
					if (!listeD.estVide())
						LPionAManger.insereTete(new Point(i, j));
				}
			}
		} else {
			Point sommet = c.s.sommet();
			listeD = directionMangeur(sommet.x, sommet.y, c, true);
			if (!listeD.estVide())
				LPionAManger.insereTete(sommet);
		}

		return LPionAManger;
	}

	// v????????rifie si le pion la position (l,c) est un pion mangeur;
	boolean pionMangeur(int pion, int l, int c) {
		// v????????rifie au niveau de la verticale et l'horizentale
		SequenceListe<SequenceListe<Point>> listpionmange = new SequenceListe<>();

		SequenceListe<SequenceListe<Point>> lma = pionMangeurApproche(pion, l, c);
		SequenceListe<SequenceListe<Point>> lme = pionMangeurEloignement(pion, l, c);

		if (lma.estVide() && lme.estVide())
			return false;

		return true;
	}

	public boolean MangerDirection(int pion, int l, int c, Point d, int type) {
		Point prochainP;
		if (type == 0) { // par aproche
			prochainP = new Point(l + 2 * d.x, c + 2 * d.y);
		} else // par eloignement
			prochainP = new Point(l - d.x, c - d.y);

		if (interieure(prochainP.x, prochainP.y) && interieure(l + d.x, c + d.y) && p.estVide(l + d.x, c + d.y)
				&& p.aPionAdversaire(pion, prochainP.x, prochainP.y)) {
			if ((Math.abs(d.x) + Math.abs(d.y)) == 1) {
				// deplacer(new Point(l,c), d, type);
				return true;
			} else {
				if (l % 2 == c % 2) {
					// deplacer(new Point(l,c), d, type);
					return true;
				}
			}
		}

		return false;
	}

	SequenceListe<SequenceListe<Point>> pionMangeurApproche(int pionmangeur, int l, int c) {
		int pionamanger;
		if (pionmangeur == 1)
			pionamanger = 2;
		else
			pionamanger = 1;
		SequenceListe<Point> list = new SequenceListe<>();
		SequenceListe<SequenceListe<Point>> tuple = new SequenceListe<>();

		// v????????rifie au niveau de la verticale et l'horizentale
		/*
		 * if((c+2<p.colonnes())&&(p.estVide(l,c+1))&&(p.aPionX(pionamanger,l,c+2)))//
		 * Est { list.insereTete(new Point(0,1)); list.insereTete(new Point(l,c+2));
		 * tuple.insereTete(list);
		 * 
		 * } if((c-2>=0)&&(p.estVide(l,c-1))&&(p.aPionX(pionamanger,l,c-2)))//Ouest {
		 * list=new SequenceListe<>(); list.insereTete(new Point(0,-1));
		 * list.insereTete(new Point(l,c-2)); tuple.insereTete(list); }
		 * if((l+2<p.lignes())&&(p.estVide(l+1,c))&&(p.aPionX(pionamanger,l+2,c)))//Sud
		 * { list=new SequenceListe<>(); list.insereTete(new Point(1,0));
		 * list.insereTete(new Point(l-2,c)); tuple.insereTete(list); }
		 * if((l-2<0)&&(p.estVide(l+1,c))&&(p.aPionX(pionamanger,l-2,c)))//Nord
		 * 
		 * list=new SequenceListe<>(); list.insereTete(new Point(1,0));
		 * list.insereTete(new Point(l-2,c)); tuple.insereTete(list); //v????????rifie au
		 * niveau des deux diagonales if(l%2==c%2)//v????????rifie que le mouvement en
		 * diagonale est possible a partir de la case (l,c) { if( (c+2<p.colonnes()) &&
		 * (l+2<p.lignes()) && (p.estVide(l+1,c+1)) && (p.aPionX(pionamanger,l+2,c+2))
		 * )//SudEst list.insereTete(new Point(l+2,c+2)); else if( (c-2>0) &&
		 * (l+2<p.lignes()) && (p.estVide(l+1,c-1)) && (p.aPionX(pionamanger,l+2,c-2))
		 * )//SudOuest list.insereTete(new Point(l+2,c-2)); else if( (c+2<p.colonnes())
		 * && (l-2>0) && (p.estVide(l-1,c+1)) && (p.aPionX(pionamanger,l-2,c+2))
		 * )//NordEst list.insereTete(new Point(l-2,c+2)); else if( (c-2>0) && (l-2>0)
		 * && (p.estVide(l-1,c-1)) && (p.aPionX(pionamanger,l-2,c-2)) )//NordOuest
		 * list.insereTete(new Point(l-2,c-2)); }
		 */
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if ((i != 0) && (j != 0)) {
					if (Math.abs(i) + Math.abs(j) == 1)// verticale/horizontale
					{
						if (interieure(l + (2 * i), c + (2 * j)) && (p.estVide(l + i, c + j))
								&& (p.aPionX(pionamanger, l + (2 * i), c + (2 * j))))// Nord
						{
							list = new SequenceListe<>();
							list.insereTete(new Point(i, j));
							list.insereTete(new Point(l + (2 * i), c + (2 * j)));
							tuple.insereTete(list);
						}
					} else {
						if (l % 2 == c % 2)// diagonale
						{
							if (interieure(l + (2 * i), c + (2 * j)) && (p.estVide(l + i, c + j))
									&& (p.aPionX(pionamanger, l + (2 * i), c + (2 * j))))// Nord
							{
								list = new SequenceListe<>();
								list.insereTete(new Point(i, j));
								list.insereTete(new Point(l + (2 * i), c + (2 * j)));
								tuple.insereTete(list);
							}
						}
					}
				}

			}
		}
		return tuple;
	}

	public boolean interieure(int x, int y) {
		return (x >= 0 && x < p.lignes()) && (y >= 0 && y < p.colonnes());
	}

	SequenceListe<SequenceListe<Point>> pionMangeurEloignement(int pionmangeur, int l, int c) {
		int pionamanger;
		if (pionmangeur == 1)
			pionamanger = 2;
		else
			pionamanger = 1;

		SequenceListe<Point> list = new SequenceListe<>();
		SequenceListe<SequenceListe<Point>> tuple = new SequenceListe<>();
		/*
		 * if( (l+1!=p.lignes()) && (l!=0) && (c+1!=p.colonnes()) && ( c!=0 ) ){//
		 * v????????rifie si le pion choisis n'est pas dans l'extremit???????? du plateau
		 * //v????????rifie au niveau de la verticale et l'horizentale if(
		 * (p.estVide(l,c+1)) && (p.aPionX(pionamanger,l,c-1)) )//Est
		 * list.insereTete(new Point(l,c-1)); else if( (p.estVide(l,c-1)) &&
		 * (p.aPionX(pionamanger,l,c+1)) )//Ouest list.insereTete(new Point(l,c+1));
		 * else if( (p.estVide(l+1,c)) && (p.aPionX(pionamanger,l-1,c)) )//Sud
		 * list.insereTete(new Point(l-1,c)); else if( (p.estVide(l-1,c)) &&
		 * (p.aPionX(pionamanger,l+1,c)) )//Sud list.insereTete(new Point(l+1,c)); else
		 * if(l%2==c%2){//si le mouvement en diagonale est possible if(
		 * (p.estVide(l-1,c+1)) && (p.aPionX(pionamanger,l+1,c-1)) )//NordEst
		 * list.insereTete(new Point(l+1,c-1)); else if( (p.estVide(l-1,c-1)) &&
		 * (p.aPionX(pionamanger,l+1,c+1)) )//NordOuest list.insereTete(new
		 * Point(l+1,c+1)); else if( (p.estVide(l+1,c+1)) &&
		 * (p.aPionX(pionamanger,l-1,c-1)) )//SudEst list.insereTete(new
		 * Point(l-1,c-1)); else if( (p.estVide(l+1,c-1)) &&
		 * (p.aPionX(pionamanger,l-1,c+1)) )//SudOuest list.insereTete(new
		 * Point(l-1,c+1)); } }
		 */

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if ((i != 0) && (j != 0)) {
					if (Math.abs(i) + Math.abs(j) == 1)// verticale/horizontale
					{
						if (interieure(l - i, c - j) && (p.estVide(l + i, c + j))
								&& (p.aPionX(pionamanger, l - i, c - j)))// Nord
						{
							list = new SequenceListe<>();
							list.insereTete(new Point(i, j));
							list.insereTete(new Point(l - i, c - j));
							tuple.insereTete(list);
						}
					} else {
						if (l % 2 == c % 2)// diagonale
						{
							if (interieure(l - i, c - j) && (p.estVide(l + i, c + j))
									&& (p.aPionX(pionamanger, l - i, c - j)))// Nord
							{
								list = new SequenceListe<>();
								list.insereTete(new Point(i, j));
								list.insereTete(new Point(l - i, c - j));
								tuple.insereTete(list);
							}
						}
					}
				}

			}
		}
		return tuple;
	}

	void priseApproche(int pion) {
	}

	void priseEloingnement(int pion) {
	}

	public Plateau getPlateau() {
		return p;
	}

	public boolean FinJeu() {
		if (surrender) {
			return true;
		}
		int p1 = 0, p2 = 0;
		for (int i = 0; i < p.lignes(); i++) {
			for (int j = 0; j < p.colonnes(); j++) {
				if (p.aPion1(i, j))
					p1++;
				else if (p.aPion2(i, j))
					p2++;
			}

		}
		return (p1 == 0 || p2 == 0);
	}

	public void changerJoueur() {
		if (joueur == 1) {
			joueur = 2;
		} else {
			joueur = 1;
		}
	}

	public boolean diagonale(Point p) {
		if (((p.x % 2 == 0) && (p.y % 2 == 0)) || ((p.x % 2 != 0) && (p.y % 2 != 0)))
			return true;
		else
			return false;
	}

	public void deplacer(Point p1, Point d) {
		if (joueur == 1) {
			p.ajoutePion1(p1.x + d.x, p1.y + d.y);
			p.videCase(p1.x, p1.y);
			int i = 3;
			Point courant = new Point(p1.x + 2 * d.x, p1.y + 2 * d.y);
			while (interieure(courant.x, courant.y) && p.aPion2(courant.x, courant.y)) {
				p.videCase(courant.x, courant.y);
				courant = new Point(courant.x + d.x, courant.y + d.y);
				i++;
			}
		} else {
			p.ajoutePion2(p1.x + d.x, p1.y + d.y);
			p.videCase(p1.x, p1.y);
			int i = 3;
			Point courant = new Point(p1.x + 2 * d.x, p1.y + 2 * d.y);
			while (p.aPion1(courant.x, courant.y)) {
				p.videCase(courant.x, courant.y);
				courant = new Point(p1.x + i * d.x, p1.y + i * d.y);
				i++;
			}
		}
	}

	public void affiche() {
		if (joueur() == 1)
			System.out.println("joueur ::: +");
		else
			System.out.println("joueur ::: *");
		this.p.affiche();
	}

	public void annulerCoup() {

		Plateau pi = hi.annulerCoup();

		if (pi != null) {
			changerJoueur();
			p = pi;
		}
	}

	public void retablirCoup() {

		Plateau pi = hi.retablirCoup();
		if (pi != null) {
			p = pi;
			changerJoueur();
		}
	}

	public void annulerCoup3() {
		h.annulerCoup2();
		changerJoueur();
	}

	public void annulerCoup2() {
		joueur=h.annulerCoup2();
		if(joueur==0){
			joueur=initiateur;
			p.iniPlateau();
		}
	}

	public void retablirCoup2() {
		int i=h.retablirCoup2();
		if(i!=0){
			joueur=i;
			changerJoueur();
		}
		
	}
	/*
	 * public void coup(Point p1, Point d ) { if( ( Math.abs(d.x) + Math.abs(d.y) )
	 * == 1) { if(p.estVide(p1.x + d.x, p1.y + d.y) ) deplacer(p, d, joueur ); }
	 * else { if(diagonale(p1)) deplacer(p1, d, joueur ); } }
	 * 
	 */

	public Coup sommet() {
		return h.sommet();
	}
}
