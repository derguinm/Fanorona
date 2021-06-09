
package Model;

import java.awt.Point;
import java.util.ArrayList;
import java.io.Serializable;

import Structures.Iterateur;
import Structures.SequenceListe;

public class Historique implements Serializable{
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
		queue = new SequenceListe<Plateau>();
	}

	Plateau retablirCoup() {

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
