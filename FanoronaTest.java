import java.util.Scanner ;

import Structures.SequenceListe;

import java.awt.*  ;

public class FanoronaTest {
	public static void main(String[] args) {
		int x1, x2, y1, y2, d=0 ;
		//Jeu j = new Jeu() ; 
		Coup c = new Coup() ;
		c.j.getPlateau().iniPlateau();
		c.j.affiche();
		
		Scanner sc = new Scanner(System.in);
		Point p1 ;
		//SequenceListe<Point> s = new SequenceListe<>() ;
		int etape = 0, k = 0 ;
		
		while (!c.j.FinJeu()) {
			k = 0;
			while(d == 0){
				System.out.println(" enter x y");
				x1 = sc.nextInt();
				y1 = sc.nextInt();
				
				/*
				System.out.println(" enter x2 y2");
				x2 = sc.nextInt();
				y2 = sc.nextInt();
				*/
				//j.jouer(new Point(x1,y1), new Point(x2,y2));
				
				p1 = new Point(x1,y1) ;
				System.out.println(" etape avant est : " + etape);
				etape = c.coups( p1,  etape ) ;
				System.out.println(" etape apres est : " + etape);
				System.out.println(" ma liste est : " + c.s);
				
				if(k == 1) {
					c.j.affiche();
					
					if(etape == 1) {
						System.out.println(" enter d: ");
						d = sc.nextInt();
					}
					
					k = 0 ;
				}
				k ++;
				
			}
			c.videList();
			//s = new SequenceListe<>() ;
			etape = 0 ;
			d = 0 ;
			c.j.changerJoueur();
		}
		sc.close();
		
		
		
		
	}

}
