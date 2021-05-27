import java.awt.Point;

import Structures.Iterateur;
import Structures.SequenceListe;

public class Historique  {
	SequenceListe<Plateau> tete ;
	SequenceListe<Plateau> queue ;
	
	Historique(){
		tete = new SequenceListe<Plateau>() ;
		queue = new SequenceListe<Plateau>() ;
	}
	
	boolean aProchain () {
		return !queue.estVide() ;
	}
	boolean aPrecedent () {
		return !tete.estVide() ;
	
	}
	
	Plateau prochain() {
			return queue.extraitTete() ;
	}
	
	Plateau precedent( ) {
		return tete.extraitTete() ;
	 }
	 
	void ajouterConfiguration( Plateau c ) throws CloneNotSupportedException {
		tete.insereTete( (Plateau) c.clone() );
		queue = new SequenceListe<Plateau>() ;
	}
	Plateau retablirCoup(){
		if(!queue.estVide()){
			Plateau courant=queue.extraitTete();
			tete.insereTete(courant);
			return courant;
		}
			return null;
	}

	Plateau annulerCoup() {
		if(!tete.estVide()){
			Plateau movannulee=tete.extraitTete();
			queue.insereTete(movannulee);
			return tete.sommet();
		}
			return null;
	}
	
}
