package Model;

import java.awt.Point;

import Structures.Iterateur;
import Structures.SequenceListe;

public class Coup implements Cloneable {
	SequenceListe<Point> s;
	SequenceListe<SequenceListe<Point>> pionspris;
	Point direction_prec;

	public Coup() {
		s = new SequenceListe<>();
		pionspris = new SequenceListe<SequenceListe<Point>>();
		direction_prec=new Point();
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
