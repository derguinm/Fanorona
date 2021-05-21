import Structures.Iterateur;
import Structures.SequenceListe;

import java.awt.*;

public class Coup {
    SequenceListe<Point> points;
    SequenceListe<Point> direction;
    int type;//coup par approche ou par eloignement

    public Coup(SequenceListe<Point> point, SequenceListe<Point> direction, int type) {
        this.points = point;
        this.direction = direction;
        this.type = type;

    }

    public jouer(Jeu j){
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
}
