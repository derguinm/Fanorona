
import Structures.Iterateur;
import Structures.SequenceListe;

import javax.swing.*;
import java.awt.*;

public class Jeu {
	Plateau p ;
	int joueur;
	int TC;

	Jeu(){
		p = new Plateau() ;
		p.iniPlateau();
		joueur=1;
	}
	
	public int joueur() {
		return joueur ;
	}
	
	void jouer(Point p1, Point d) {
		if(!MangerDirection(joueur,p1.x,p1.y,d,0) )
			MangerDirection(joueur,p1.x,p1.y,d,1) ;
		/*
		SequenceListe<Point> listmangeur=null;
		listmangeur=joueurDoitManger(joueur);
		if(!listmangeur.estVide())
		{
			Iterateur<Point> it=listmangeur.iterateur();
			while(it.aProchain())
			{
				Point p=it.prochain();
				if((p.x==p1.x)&&(p.y==p1.y))
				{
					if( MangerDirection(joueur,p1.x,p1.y,d,1) ) {
						break;
					}
					
				}
			}
		}
		else
			deplacer(p1,d,1);//p1 et d doivent être valide
		*/

	}
	public void deplacer(Point p,Point d,int type){
		if(joueur==1){
			this.p.ajoutePion1(p.x+d.x,p.y+d.y);
			this.p.videCase(p.x,p.y);
			int i=2;
			if(type==0)//par approche
			{
				while(interieure(p.x+d.x*i,p.y+d.y*i) && this.p.aPion2(p.x+d.x*i,p.y+d.y*i))
				{
					this.p.videCase(p.x+d.x*i,p.y+d.y*i);
					i++;
				}
			}
			else// par eloignement
			{
				while(interieure(p.x - d.x*(i-1), p.y - d.y*(i-1)) && this.p.aPion2(p.x - d.x*(i-1), p.y - d.y*(i-1)) )
				{
					this.p.videCase(p.x - d.x*(i-1), p.y - d.y*(i-1));
					i++;
				}
			}
		}
		else
		{
			this.p.ajoutePion2(p.x+d.x,p.y+d.y);
			this.p.videCase(p.x,p.y);
			int i=2;
			if(type==0)//par approche
			{
				while(interieure(p.x+d.x*i,p.y+d.y*i) && this.p.aPion1(p.x+d.x*i,p.y+d.y*i))
				{
					this.p.videCase(p.x+d.x*i,p.y+d.y*i);
					i++;
				}
			}
			else// par eloignement
			{
				while(interieure(p.x - d.x*(i-1), p.y - d.y*(i-1)) && this.p.aPion1(p.x - d.x*(i-1), p.y - d.y*(i-1)) )
				{
					this.p.videCase(p.x - d.x*(i-1), p.y - d.y*(i-1));
					i++;
				}
			}
		}
	}
	//la fonction retourne la liste des pions mangueur du joueur
	SequenceListe<Point>joueurDoitManger(int joueur){
		SequenceListe<Point> LPionAManger=new SequenceListe<>();
		for (int i = 0; i < p.lignes(); i++) {
			for (int j = 0; j < p.colonnes(); j++) {
				if(p.aPionX(joueur,i,j))
					if(pionMangeur(joueur,i,j))
						LPionAManger.insereTete(new Point(i,j));

			}
		}
		return LPionAManger;
	}

	// vérifie si le pion la position (l,c) est un pion mangeur;
	boolean pionMangeur(int pion, int l, int c){
		//vérifie au niveau de la verticale et l'horizentale
		SequenceListe<SequenceListe<Point>> listpionmange=new SequenceListe<>();

		SequenceListe<SequenceListe<Point>> lma=pionMangeurApproche(pion,l,c);
		SequenceListe<SequenceListe<Point>> lme=pionMangeurEloignement(pion,l,c);
		
		if(lma.estVide() && lme.estVide())
			return false;


		return true;
	}

	
	boolean MangerDirection(int pion, int l, int c, Point d, int type){
		Point prochainP ;
		if(type == 0) { //par aproche
			prochainP = new Point(l + 2*d.x, c + 2*d.y) ;
		}
		else // par eloignement 
			prochainP = new Point(l - d.x, c - d.y) ;
		
		if(interieure(prochainP.x, prochainP.y) && p.estVide(l + d.x, c + d.y) && p.aPionAdversaire(pion, prochainP.x, prochainP.y) ) {
			if( ( Math.abs(d.x) + Math.abs(d.y) ) == 1) {
				deplacer(new Point(l,c), d, type);
				return true;
			}
			else {
				if(l%2 == c%2) {
					deplacer(new Point(l,c), d, type);
					return true;
				}
			}
		}
		

		return false ;
	}

	SequenceListe<SequenceListe<Point>> pionMangeurApproche(int pionmangeur,int l,int c){
		int pionamanger;
		if(pionmangeur==1)
			pionamanger=2;
		else
			pionamanger=1;
		SequenceListe<Point> list=new SequenceListe<>();
		SequenceListe<SequenceListe<Point>> tuple=new SequenceListe<>();

		//vérifie au niveau de la verticale et l'horizentale
		/*
		if((c+2<p.colonnes())&&(p.estVide(l,c+1))&&(p.aPionX(pionamanger,l,c+2)))//Est
		{
			list.insereTete(new Point(0,1));
			list.insereTete(new Point(l,c+2));
			tuple.insereTete(list);

		}
		 if((c-2>=0)&&(p.estVide(l,c-1))&&(p.aPionX(pionamanger,l,c-2)))//Ouest
		 {
		 	list=new SequenceListe<>();
		 	list.insereTete(new Point(0,-1));
		 	list.insereTete(new Point(l,c-2));
		 	tuple.insereTete(list);
		 }
		if((l+2<p.lignes())&&(p.estVide(l+1,c))&&(p.aPionX(pionamanger,l+2,c)))//Sud
		 {
		 	list=new SequenceListe<>();
		 	list.insereTete(new Point(1,0));
		 	list.insereTete(new Point(l-2,c));
		 	tuple.insereTete(list);
		 }
		if((l-2<0)&&(p.estVide(l+1,c))&&(p.aPionX(pionamanger,l-2,c)))//Nord

			list=new SequenceListe<>();
			list.insereTete(new Point(1,0));
			list.insereTete(new Point(l-2,c));
			tuple.insereTete(list);
			//vérifie au niveau des deux diagonales
		if(l%2==c%2)//vérifie que le mouvement en diagonale est possible a partir de la case (l,c)
		{
			if( (c+2<p.colonnes()) && (l+2<p.lignes()) && (p.estVide(l+1,c+1)) && (p.aPionX(pionamanger,l+2,c+2)) )//SudEst
				list.insereTete(new Point(l+2,c+2));
			else if( (c-2>0) && (l+2<p.lignes()) && (p.estVide(l+1,c-1)) && (p.aPionX(pionamanger,l+2,c-2)) )//SudOuest
				list.insereTete(new Point(l+2,c-2));
			else if( (c+2<p.colonnes()) && (l-2>0) && (p.estVide(l-1,c+1)) && (p.aPionX(pionamanger,l-2,c+2)) )//NordEst
				list.insereTete(new Point(l-2,c+2));
			else if( (c-2>0) && (l-2>0) && (p.estVide(l-1,c-1)) && (p.aPionX(pionamanger,l-2,c-2)) )//NordOuest
				list.insereTete(new Point(l-2,c-2));
		}
		 */
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if((i!=0) && (j!=0))
				{
					if(Math.abs(i)+Math.abs(j)==1)// verticale/horizontale
					{
						if(interieure(l+(2*i),c+(2*j))&&(p.estVide(l+i,c+j))&&(p.aPionX(pionamanger,l+(2*i),c+(2*j))))//Nord
						{
							list=new SequenceListe<>();
							list.insereTete(new Point(i,j));
							list.insereTete(new Point(l+(2*i),c+(2*j)));
							tuple.insereTete(list);
						}
					}
					else
					{
						if(l%2==c%2)//diagonale
						{
							if(interieure(l+(2*i),c+(2*j))&&(p.estVide(l+i,c+j))&&(p.aPionX(pionamanger,l+(2*i),c+(2*j))))//Nord
							{
								list=new SequenceListe<>();
								list.insereTete(new Point(i,j));
								list.insereTete(new Point(l+(2*i),c+(2*j)));
								tuple.insereTete(list);
							}
						}
					}
				}

			}
		}
		return tuple;
	}

	public boolean interieure(int x,int y){
		return (x>=0 && x < p.lignes())&&(y>=0 && y<=p.colonnes());
	}

	SequenceListe<SequenceListe<Point>> pionMangeurEloignement(int pionmangeur,int l,int c){
		int pionamanger;
		if(pionmangeur==1)
			pionamanger=2;
		else
			pionamanger=1;

		SequenceListe<Point> list=new SequenceListe<>();
		SequenceListe<SequenceListe<Point>> tuple=new SequenceListe<>();
		/*
		if( (l+1!=p.lignes()) && (l!=0) &&  (c+1!=p.colonnes()) && ( c!=0 ) ){// vérifie si le pion choisis n'est pas dans l'extremité du plateau
			//vérifie au niveau de la verticale et l'horizentale
			if( (p.estVide(l,c+1)) && (p.aPionX(pionamanger,l,c-1)) )//Est
				list.insereTete(new Point(l,c-1));
			else if( (p.estVide(l,c-1)) && (p.aPionX(pionamanger,l,c+1)) )//Ouest
				list.insereTete(new Point(l,c+1));
			else if( (p.estVide(l+1,c)) && (p.aPionX(pionamanger,l-1,c)) )//Sud
				list.insereTete(new Point(l-1,c));
			else if( (p.estVide(l-1,c)) && (p.aPionX(pionamanger,l+1,c)) )//Sud
				list.insereTete(new Point(l+1,c));
			else if(l%2==c%2){//si le mouvement en diagonale est possible
				if( (p.estVide(l-1,c+1)) && (p.aPionX(pionamanger,l+1,c-1)) )//NordEst
					list.insereTete(new Point(l+1,c-1));
				else if( (p.estVide(l-1,c-1)) && (p.aPionX(pionamanger,l+1,c+1)) )//NordOuest
					list.insereTete(new Point(l+1,c+1));
				else if( (p.estVide(l+1,c+1)) && (p.aPionX(pionamanger,l-1,c-1)) )//SudEst
					list.insereTete(new Point(l-1,c-1));
				else if( (p.estVide(l+1,c-1)) && (p.aPionX(pionamanger,l-1,c+1)) )//SudOuest
					list.insereTete(new Point(l-1,c+1));
			}
		}
		*/

		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				if((i!=0) && (j!=0))
				{
					if(Math.abs(i)+Math.abs(j)==1)// verticale/horizontale
					{
						if(interieure(l-i,c-j)&&(p.estVide(l+i,c+j))&&(p.aPionX(pionamanger,l-i,c-j)))//Nord
						{
							list=new SequenceListe<>();
							list.insereTete(new Point(i,j));
							list.insereTete(new Point(l-i,c-j));
							tuple.insereTete(list);
						}
					}
					else
					{
						if(l%2==c%2)//diagonale
						{
							if(interieure(l-i,c-j)&&(p.estVide(l+i,c+j))&&(p.aPionX(pionamanger,l-i,c-j)))//Nord
							{
								list=new SequenceListe<>();
								list.insereTete(new Point(i,j));
								list.insereTete(new Point(l-i,c-j));
								tuple.insereTete(list);
							}
						}
					}
				}

			}
		}
		return tuple;
	}
	void priseApproche(int pion){}
	void priseEloingnement(int pion){}

	
	public Plateau getPlateau() {
		return p ;
	}
	
	public boolean FinJeu() {
		int p1 = 0, p2 = 0 ;
		for(int i = 0; i< p.lignes(); i++) {
			for(int j = 0; j< p.colonnes(); j++) {
				if(p.aPion1(i,j))
					p1++ ;
				else if (p.aPion2(i,j))
					p2 ++ ;
			}
		
		}
		return ( p1 == 0 || p2 == 0 ) ;
	}
	
	public void changerJoueur() {
		if (joueur == 1) {
			joueur = 2 ;
			TC = 2 ;
		}
		else {
			joueur = 1 ;
			TC = 1 ;
		}
	}
	
	public boolean diagonale(Point p) {
		if(( (p.x % 2 == 0) && (p.y % 2 == 0) ) || ( (p.x % 2 != 0) && (p.y % 2 != 0) ) )
			return true ;
		else	
			return false ;
	}
	

	
	public void deplacer(Point p1, Point d) {
		if(joueur == 1) {
			p.ajoutePion1(p1.x + d.x, p1.y + d.y);
			p.videCase(p1.x , p1.y);
			int i = 3 ;
			Point courant = new Point(p1.x + 2*d.x, p1.y + 2*d.y) ;
			while( interieure(courant.x, courant.y) && p.aPion2(courant.x, courant.y) ) {
				p.videCase(courant.x, courant.y) ;
				courant = new Point(courant.x + d.x, courant.y + d.y) ;
				i ++;
			}
		}
		else {
			p.ajoutePion2(p1.x + d.x, p1.y + d.y);
			p.videCase(p1.x , p1.y);
			int i = 3 ;
			Point courant = new Point(p1.x + 2*d.x, p1.y + 2*d.y) ;
			while(p.aPion1(courant.x, courant.y)) {
				p.videCase(courant.x, courant.y) ;
				courant = new Point(p1.x + i*d.x, p1.y + i*d.y) ;
				i ++;
			}
		}
	}
	/*
	public void coup(Point p1, Point d ) {
		if( ( Math.abs(d.x) + Math.abs(d.y) ) == 1) {
			if(p.estVide(p1.x + d.x, p1.y + d.y) )
				deplacer(p, d, joueur );
		}
		else {
			if(diagonale(p1))
				deplacer(p1, d, joueur );
		}		
	}

	 */
}
