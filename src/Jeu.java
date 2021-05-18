import java.awt.* ;
import java.math.* ;

public class Jeu {
	Plateau Pl ;
	int joueur ;
	int TC ;
	int l, c ;
	 
	Jeu(){
		Pl = new Plateau() ;
		Pl.iniPlateau();
		l = Pl.lignes();
		c = Pl.colonnes() ;
		joueur = 1 ;
		TC = 1 ;
	}
	
	public Plateau getPlateau() {
		return Pl ;
	}
	
	public boolean FinJeu() {
		int p1 = 0, p2 = 0 ;
		for(int i = 0; i< l; i++) {
			for(int j = 0; j< c; j++) {
				if(Pl.aPion1(i,j))
					p1++ ;
				else if (Pl.aPion2(i,j))
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
	
	public boolean interieure(int x, int y) {
		return ( x>=0 && x <l ) && ( y>=0 && x <c ) ;
	}
	
	public void deplacer(Point p, Point d, int joueur ) {
		if(joueur == 1) {
			Pl.ajoutePion1(p.x + d.x, p.y + d.y);
			Pl.videCase(p.x , p.y);
			int i = 3 ;
			Point courant = new Point(p.x + 2*d.x, p.y + 2*d.y) ;
			while( interieure(courant.x, courant.y) && Pl.aPion2(courant.x, courant.y) ) {
				Pl.videCase(courant.x, courant.y) ;
				courant = new Point(courant.x + d.x, courant.y + d.y) ;
				i ++;
			}
		}
		else {
			Pl.ajoutePion2(p.x + d.x, p.y + d.y);
			Pl.videCase(p.x , p.y);
			int i = 3 ;
			Point courant = new Point(p.x + 2*d.x, p.y + 2*d.y) ;
			while(Pl.aPion1(courant.x, courant.y)) {
				Pl.videCase(courant.x, courant.y) ;
				courant = new Point(p.x + i*d.x, p.y + i*d.y) ;
				i ++;
			}
		}
	}
	
	public void coup(Point p, Point d ) {
		if( ( Math.abs(d.x) + Math.abs(d.y) ) == 1) {
			if(Pl.estVide(p.x + d.x, p.y + d.y) )
				deplacer(p, d, joueur );
		}
		else {
			if(diagonale(p)) 
				deplacer(p, d, joueur );
		}		
	}
	
	
	
}
