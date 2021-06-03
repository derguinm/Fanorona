
package Model;

import java.awt.Point;
import java.util.ArrayList;

import Structures.Iterateur;
import Structures.SequenceListe;

public class Historique {
	SequenceListe<Plateau> tete;
	SequenceListe<Plateau> queue;
	int taille;

	Historique() {
		taille=1;
		tete = new SequenceListe<Plateau>();
		queue = new SequenceListe<Plateau>();
	}

	boolean aProchain() {
		return !queue.estVide();
	}

	boolean aPrecedent() {
		return !tete.estVide();

	}

	Plateau prochain() {
		return queue.extraitTete();
	}

	Plateau precedent() {
		return tete.extraitTete();
	}

	public void ajouterConfiguration(Plateau c) throws CloneNotSupportedException {
		taille++;
		tete.insereTete((Plateau) c.clone());
		System.out.println();
		queue = new SequenceListe<Plateau>();
	}

	Plateau retablirCoup() {
		System.err.println("retablir coup");
		System.err.println(tete);
		System.err.println(queue);
		if (!queue.estVide()) {
			Plateau courant = queue.extraitTete();
			tete.insereTete(courant);
			taille++;
			return courant;
		}
		return null;
	}
	public int taille_tete(){
		return taille;
	}

	Plateau annulerCoup() {
		System.err.println("annulle coup");
		System.out.println("taille :"+taille_tete());
		System.err.println(tete);
		System.err.println(queue);
		Iterateur<Plateau> it = tete.iterateur();
		if (taille > 1 ) {
			taille--;
			Plateau movannulee = tete.extraitTete();
			queue.insereTete(movannulee);
			return tete.sommet();
		}

		return null;
	}

	void afficherEve() {
		Iterateur<Plateau> it = tete.iterateur();
		while (it.aProchain()) {
			it.prochain().affiche();
		}
	}

}
