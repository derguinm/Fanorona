import java.awt.Point;

import Structures.SequenceListe;

public class Historique  {
	SequenceListe<Coup> tete ;
	SequenceListe<Coup> queue ;
	
	Historique(){
		tete = new SequenceListe<Coup>() ;
		queue = new SequenceListe<Coup>() ; 
	}
	
	boolean aProchain () {
		return !queue.estVide() ;
	}
	boolean aPrecedent () {
		return !tete.estVide() ;
	
	}
	
	Coup prochain() {
			return queue.extraitTete() ;
	}
	
	Coup precedent( ) {
		return tete.extraitTete() ;
	 }
	 
	void ajouterCoup( Coup c ) throws CloneNotSupportedException {
		tete.insereTete( (Coup) c.clone() );
		queue = new SequenceListe<Coup>() ;
	}
	
	void annulerDeplace() {
		Coup 
	}
	
}
