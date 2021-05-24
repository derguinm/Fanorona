import java.awt.Point;

import Structures.Iterateur;
import Structures.SequenceListe;


public class Coup {
	Jeu j ; 
    SequenceListe<Point> s ;
    
    Coup(){
    	j = new Jeu() ;
    	s = new SequenceListe<>() ;
    }
    
    void videList() {
    	s = new SequenceListe<>() ;
    }
    
	Point direction(Point p1, Point p2) {
		return new Point(p2.x-p1.x, p2.y-p1.y ) ;
	}
	
	boolean trouverPoint(Point p1) {
		Iterateur<Point> it = s.iterateur() ;
		while(it.aProchain())
		{
			Point p = it.prochain();
			if((p.x == p1.x)&&(p.y == p1.y))
				return true ;
		}
		return false ;
	}
	
	 int arrondir(int a) {
		 int b = 0 ;
		 if(a == 0)
			 b = 0 ;
		 else if( a > 0 )
			 b = 1 ;
		 else if( a < 0 )
			 b = -1 ;
		 return b ;
	 }
    
    int coups(Point p1, int etape ) {
		if( ( !s.estVide() && !trouverPoint(p1) ) || s.estVide() ) {
			if( etape == 0 ) {
				s.insereTete(p1) ;
				etape++ ;
			}
			else if ( etape == 1 ) {
				System.out.println(" enter etape_1 : ");
				Iterateur<Point> it = s.iterateur() ;
				Point p = it.prochain() ;
				Point d = direction(p, p1) ;
				System.out.println(" enter etape_1_d : " + d);
				
				System.out.println(" 0 et 1" + j.MangerDirection(j.joueur(),p.x,p.y,d,0) + " , " + j.MangerDirection(j.joueur(),p.x,p.y,d,1));
				
				if(j.MangerDirection(j.joueur(),p.x,p.y,d,0) && !j.MangerDirection(j.joueur(),p.x,p.y,d,1) ) {
					System.out.println(" enter etape_1_if_1 : " );
					j.deplacer(p, d, 0);
					s.insereTete(p1) ;
					etape = 1 ;
				}
					
				else if( !j.MangerDirection(j.joueur(),p.x,p.y,d,0) && j.MangerDirection(j.joueur(),p.x,p.y,d,1) ) {
					System.out.println(" enter etape_1_if_2 : " );
					j.deplacer(p, d, 1);
					s.insereTete(p1) ;
					etape = 1 ;
				}
					
				else if( j.MangerDirection(j.joueur(),p.x,p.y,d,0) && j.MangerDirection(j.joueur(),p.x,p.y,d,1) ) {
					System.out.println(" enter etape_1_if_3 : " );
					s.insereTete(p1) ;
					etape ++ ;
				}
					
			}
			else if ( etape == 2 ) {
				System.out.println(" enter etape_2 : ");
				
				Iterateur<Point> it = s.iterateur() ;
				Point p_2 = it.prochain() ;
				Point p_1 = it.prochain() ;
				
				Point d = direction(p_1, p_2) ;
				Point d1 = direction(p_1, p1) ;
				
				System.out.println(" d : " + d );
				System.out.println(" d1 : " + d1 );
				
				d1 = new Point(arrondir(d1.x), arrondir(d1.y));
				System.out.println(" new d1 : " + d1 );
				
				if(d.x == d1.x && d.y == d1.y )
					j.deplacer(p_1, d, 0);
				else if(d.x == -d1.x && d.y == -d1.y )
					j.deplacer(p_1, d, 1);
				etape = 1 ;
			}
		}
			
		return etape ;
	}
	    
    
    
    
    
    
    
    

   /* public Coup(SequenceListe<Point> point, SequenceListe<Point> direction, int type) {
        this.points = point;
        this.direction = direction;
        this.type = type;

    }

    public void jouer(Jeu j){
        Iterateur<Point> itp=points.iterateur();
        Iterateur<Point> itd=direction.iterateur();
        Iterateur<Integer> itt=type.iterateur();
        while(itt.aProchain()){
            Point point=itp.prochain();
            Point direc=itd.prochain();
            int typ=itt.prochain();
            j.deplacer(point,direc,typ);
        }
    }*/
    
}
