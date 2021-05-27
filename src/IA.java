import java.awt.*;

class couple {
	int valeur ;
	Point p;
}




public class IA {
	
	Coup cp ;
	Jeu j;
	
	IA(Jeu j){
		cp = new Coup() ;
		this.j = j;
	}

	int cmpPions(int joueur) {
		Plateau PL=j.getPlateau();
		int l = PL.lignes() ;
		int c  = PL.colonnes();
		int cmp = 0 ;
 		
		for(int i = 0; i< l; i++) {
			for(int j = 0; j< c ; j++) {
				if( PL.aPionX(joueur, i, j) )
					cmp ++ ;
			}
		}
		return cmp ;
	}
	
	int evaluation(int joueur) {
		if(joueur == 1)
			return cmpPions(1) - cmpPions(2) ;
		else 
			return cmpPions(2) - cmpPions(1) ;
	}
	
	int minMax(int horizon, boolean maxJoueur, int joueur) {
		
		if( horizon == 0 || j.FinJeu() )
			return evaluation(joueur) ;
		else
		
		return 0 ;
	}
	
	
	
	
}
