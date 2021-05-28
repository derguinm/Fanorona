import java.util.Scanner;

import Structures.SequenceListe;

import java.awt.*;

public class FanoronaTest {
	public static void main(String[] args) {
		int x1, x2, y1, y2, d = 0;
		Jeu j = new Jeu() ;
		Coup c = new Coup();
		j.getPlateau().iniPlateau();
		j.affiche();

		Scanner sc = new Scanner(System.in);
		Point p1;
		// SequenceListe<Point> s = new SequenceListe<>() ;
		int etape = 0, k = 0;
		int tuepartie=0;
		while (!j.FinJeu() && tuepartie==0) {
			k = 0;
			Coup coup_courant=new Coup();
			while (d == 0) {
				System.out.println(" enter x y");
				x1 = sc.nextInt();
				y1 = sc.nextInt();

				/*
				 * System.out.println(" enter x2 y2"); x2 = sc.nextInt(); y2 = sc.nextInt();
				 */
				// j.jouer(new Point(x1,y1), new Point(x2,y2));

				p1 = new Point(x1, y1);
				//System.out.println(" etape avant est : " + etape);
				etape = j.jouercoup(p1, etape,c);
				//System.out.println(" etape apres est : " + etape);
				//System.out.println(" ma liste est : " + c.s);

				if (k == 1) {
					j.affiche();

					if (etape == 1) {
						System.out.println(" enter d: ");
						d = sc.nextInt();
					}

					k = 0;
				}
				k++;
				coup_courant.ajouterPoint(p1);
			}
			c.videList();
			// s = new SequenceListe<>() ;


			etape = 0;
			d = 0;

			System.out.println("fin coup");
			try {
				j.hi.ajouterConfiguration(j.getPlateau().clone());
				System.out.println("ajout d'une configuration dans l'historique");
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
				System.out.println("exception");
			}
			j.changerJoueur();
			System.out.println("tuepartie:");
			tuepartie=sc.nextInt();
		}
		sc.close();
		j.annulerCoup();
		j.affiche();
		j.retablirCoup();
		j.affiche();
	}

}
