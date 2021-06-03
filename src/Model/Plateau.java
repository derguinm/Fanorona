package Model;

import java.awt.*;

public class Plateau implements Cloneable {

	static final int VIDE = 0;
	static final int PION1 = 1;
	static final int PION2 = 2;
	int l, c;
	int[][] cases;

	Plateau() {
		l = 5;
		c = 9;
		cases = new int[l][c];
	}
	public Plateau clone() throws CloneNotSupportedException {
		Plateau p=new Plateau();
		p.c=this.c;
		p.l=this.l;
		for (int i = 0; i < 5; i++) {
			for (int j = 0; j < 9; j++) {
				p.cases[i][j]=this.cases[i][j];
			}
		}
		return p;
	}
	public void iniPlateau() {
		cases = new int[l][c];
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < c; j++) {
				if (i < 2)
					ajoutePion1(i, j);
				else if (i > 2)
					ajoutePion2(i, j);
			}
		}
		int j = 0, pion = PION2;
		while (j < c) {
			if (j != (c / 2)) {
				if (pion == PION1) {
					ajoutePion1(2, j);
					pion = PION2;
				} else {
					ajoutePion2(2, j);
					pion = PION1;
				}
				j++;
			} else
				j++;
		}

	}
	public void jouer(Coup c){}

	public void videCase(int i, int j) {
		cases[i][j] = VIDE;
	}

	public void ajoute(int contenu, int i, int j) {
		cases[i][j] = contenu;
	}

	public void ajoutePion1(int i, int j) {
		ajoute(PION1, i, j);
	}

	public void ajoutePion2(int i, int j) {
		ajoute(PION2, i, j);
	}

	public int lignes() {
		return l;
	}

	public int colonnes() {
		return c;
	}

	public boolean estVide(int l, int c) {
		return (cases[l][c] == VIDE);
	}

	public boolean aPion1(int l, int c) {
		return (cases[l][c] == PION1);
	}

	public boolean aPion2(int l, int c) {
		return (cases[l][c] == PION2);
	}

	public boolean aPionX(int x, int l, int c) {
		return (cases[l][c] == x);
	}

	public boolean aPionAdversaire(int x, int l, int c) {
		return (!(cases[l][c] == x) && !estVide(l, c));
	}

	public void affiche() {
		for (int i = 0; i < l; i++) {
			for (int j = 0; j < c; j++) {
				if (aPion1(i, j))
					System.out.print("|+");
				else if (aPion2(i, j))
					System.out.print("|*");
				else
					System.out.print("| ");
			}
			System.out.println("|");
		}
		System.out.println();
	}

	Point direction(Point p1, Point p2) {
		return new Point(p2.x - p1.x, p2.y - p1.y);
	}


	public void ajoutePionX(int joueur,int i, int i1) {
		if(joueur==1){
			ajoutePion1(i,i1);
		}
		else{
			ajoutePion2(i,i1);
		}
	}
}
