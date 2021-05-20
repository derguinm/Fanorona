import java.util.Scanner ;
import java.awt.*  ;

public class testFanorona {
	public static void main(String[] args) {
		int x1, x2, y1, y2, d=0 ;
		Jeu j = new Jeu() ; 
		j.getPlateau().iniPlateau();
		j.getPlateau().affiche();
		
		Scanner sc = new Scanner(System.in);
		
		
		while (!j.FinJeu()) {
			
			while(d == 0){
				System.out.println(" enter x y");
				x1 = sc.nextInt();
				y1 = sc.nextInt();
				
				System.out.println(" enter x2 y2");
				x2 = sc.nextInt();
				y2 = sc.nextInt();

				j.coup(new Point(x1,y1), new Point(x2,y2));
				j.getPlateau().affiche();
				d = sc.nextInt();
				
			}
			d = 0 ;
			j.changerJoueur();
		}
		
		
		
		
		
	}

}
