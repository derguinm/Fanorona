package Model;

import java.awt.Point;

import Structures.Iterateur;
import Structures.SequenceListe;
import java.io.Serializable;

public class Coup implements Cloneable, Serializable {
	SequenceListe<Point> s;
	SequenceListe<SequenceListe<Point>> pionspris;
	Point direction_prec;
	int joueur;

	public Coup() {
		s = new SequenceListe<>();
		pionspris = new SequenceListe<SequenceListe<Point>>();
		direction_prec=new Point();
	}

	public void setjoueur(int joueur){
		this.joueur=joueur;
	}

	public int getjoueur(){
		return joueur;
	}

	public void setDirection_prec(Point direction_prec) {
		this.direction_prec = direction_prec;
	}

	public Point getDirection_prec() {
		return direction_prec;
	}

	public SequenceListe listMouv(){
		return s;
	}
	public boolean estCoup(){
		Iterateur<Point> it=s.iterateur();
		int res=0;
		while(it.aProchain()){
			it.prochain();
			res++;
		}
		if(res>1)
			return true;

		return false;
	}
	public Coup clone() throws CloneNotSupportedException {

		return  (Coup)super.clone();

	}

	public void videList() {
		s = new SequenceListe<>();
		pionspris =new SequenceListe<>();
		setDirection_prec(new Point(0,0)) ;
	}

	Point direction(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}

	public boolean trouverPoint(Point p1) {
		Iterateur<Point> it = s.iterateur();
		while (it.aProchain()) {
			Point p = it.prochain();
			if ((p.x == p1.x) && (p.y == p1.y))
				return true;
		}
		return false;
	}



	public void ajouterPoint(Point p){
		s.insereQueue(p);
	}

	public Point position_init(){
		Iterateur<Point> it= s.iterateur();
		Point res=null;
		while(it.aProchain()){
			res=it.prochain();
		}
		return res;
	}
	public Point position_final(){
		return s.sommet();
	}

	public void jouerCoup(Jeu j) {

		Plateau pl = j.getPlateau();
		Coup c = this;
		Point fin=c.position_final();
		Point debut=c.position_init();
		int pionjoueur=pl.cases[debut.x][debut.y];
		//je vide les cases des pions a eliminer
		Iterateur<SequenceListe<Point>> it=c.pionspris.iterateur();
		while(it.aProchain()){
			SequenceListe<Point> liste=it.prochain();
			Iterateur<Point> pionsamang=liste.iterateur();
			while(pionsamang.aProchain()){
				Point courant=pionsamang.prochain();
				pl.ajoute(Plateau.VIDE,courant.x,courant.y);
			}
		}
		//je deplace le pion mangeur
		pl.ajoute(Plateau.VIDE,debut.x,debut.y);
		pl.ajoute(pionjoueur,fin.x,fin.y);
		//ajout du coup a couppasse


	}
	/*
	 * public Coup(SequenceListe<Point> point, SequenceListe<Point> direction, int
	 * type) { this.points = point; this.direction = direction; this.type = type;
	 * 
	 * }
	 * 
	 * public void jouer(Jeu j){ Iterateur<Point> itp=points.iterateur();
	 * Iterateur<Point> itd=direction.iterateur(); Iterateur<Integer>
	 * itt=type.iterateur(); while(itt.aProchain()){ Point point=itp.prochain();
	 * Point direc=itd.prochain(); int typ=itt.prochain();
	 * j.deplacer(point,direc,typ); } }
	 */

}
