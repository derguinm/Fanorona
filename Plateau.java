
public class Plateau {

	static final int VIDE = 0;
	static final int PION1 = 1;
	static final int PION2 = 2;
	int l, c ;
	int[][] cases;
	
	Plateau( ){
		l = 5 ;
		c = 9 ;
		cases = new int [l][c] ;
	}

	public void iniPlateau () {
		for(int i = 0; i< l; i++) {
			for(int j = 0; j< c; j++) {
				if(i < 2)
					ajoutePion1(i,j) ;
				else if (i > 2)
					ajoutePion2(i,j) ;
			}
		}
		int j = 0, pion = PION1 ;
		while(j < c) {
			if(j != 4) {
				if(pion == PION1 ) {
					ajoutePion1(2,j) ;
					pion = PION2 ;
				}
				else {
					ajoutePion2(2,j) ;
					pion = PION1 ;
				}
				j++ ;
			}
			else
				j++;	
		}
			
	}
	
	public void videCase(int i, int j) {
		cases[i][j] = VIDE;
	}

	public void ajoute(int contenu, int i, int j) {
		cases[i][j] = contenu;
	}

	public void ajoutePion1(int i, int j) {
		ajoute(PION1, i, j);
	}

	public void ajoutePion2(int i, int j) {
		ajoute(PION2, i, j);
	}

	public int lignes() {
		return l;
	}

	public int colonnes() {
		return c;
	}
	
	public boolean estVide(int l, int c) {
		return (cases[l][c] == VIDE) ;
	}

	public boolean aPion1(int l, int c) {
		return (cases[l][c] == PION1) ;
	}

	public boolean aPion2(int l, int c) {
		return (cases[l][c] == PION2) ;
	}

	public boolean aPionX(int x,int l, int c) {
		return (cases[l][c] == x) ;
	}
	
	public boolean aPionAdversaire(int x,int l, int c) {
		return (!( cases[l][c] == x ) && !estVide(l,c)) ;
	}

	public void affiche(){
		for(int i = 0; i< l; i++) {
			for(int j = 0; j< c; j++) {
				if(aPion1(i,j))
					System.out.print("|+");
				else if (aPion2(i,j))
					System.out.print("|*");
				else
					System.out.print("| ");
			}
			System.out.println("|");
		}
		System.out.println();
	}
}
