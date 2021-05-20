import Structures.Iterateur;
import Structures.IterateurListe;
import Structures.Sequence;
import Structures.SequenceListe;

import java.awt.*;
import java.util.ArrayList;

public class Jeu {
	Plateau p ;
	int joueur;
	int TC;

	Jeu(){
		p = new Plateau() ;
		p.iniPlateau();
		joueur=1;
	}

	void coup(Point p1, Point d) {
		SequenceListe<Point> listmangeur=new SequenceListe<>();
		listmangeur=joueurDoitManger(joueur);
		if(!listmangeur.estVide())
		{
			Iterateur<Point> it=listmangeur.iterateur();
			while(it.aProchain())
			{
				Point p=it.prochain();
				if((p.x==p1.x)&&(p.y==p1.y))
					deplacer(p1,d);
			}
		}
		else
			deplacer(p1,d);//p1 et d doivent être valide

	}
	void deplacer(Point p,Point d){
		if(joueur==1){
			this.p.ajoutePion1(p.x+d.x,p.y+d.y);
			this.p.videCase(p.x,p.y);
			
		}
		else
		{

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
	void coup(int pion){}
}
