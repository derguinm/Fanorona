class couple {
	int valeur ;
	Point p
}




public class IA {
	
	Coup cp ;
	Plateau PL ;
	
	IA(){
		cp = new Coup() ;
		Plateau PL = cp.j.getPlateau();
	}

	int cmpPions(int joueur) {
		int l = PL.lignes() ;
		int c  = PL.colonnes();
		int cmp = 0 ;
 		
		for(int i = 0; i< l; i++) {
			for(int j = 0; j< c ; j++) {
				if( cp.j.getPlateau().aPionX(joueur, i, j) )
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
		
		if( horizon == 0 || cp.j.FinJeu() )
			return evaluation(joueur) ;
		else
		
		return 0 ;
	}
	
	
	
	
}
