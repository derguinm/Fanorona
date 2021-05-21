import Structures.Iterateur;
import Structures.SequenceListe;

import java.awt.*;

public class Coup {
    SequenceListe<Point> points;
    SequenceListe<Point> direction;
    SequenceListe<Integer> type;//coup par approche ou par eloignement

    public Coup(SequenceListe<Point> point, SequenceListe<Point> direction,SequenceListe<Point> type) {
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
    }
    // ajoute le mouvement si le point n'est pas encore parcouru
    public void ajouterMouv(Point p,Point direction,int type){
        boolean exist=false;
        Iterateur<Point> itp=points.iterateur();
        while(!exist && itp.aProchain()){
            Point point_courrant=itp.prochain();
            if(point_courrant.x==p.x && point_courrant.y==p.y)
                exist=true;
        }
        if(!exist)
        {
            points.insereQueue(p);
            this.direction.insereQueue(direction);
            this.type.insereQueue(type);
        }

    }
}
