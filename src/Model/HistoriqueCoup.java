package Model;

import Structures.SequenceListe;

public class HistoriqueCoup {
    SequenceListe<Coup> coupasse;
    SequenceListe<Coup> coupannule;
    Plateau pl;
    public HistoriqueCoup(Plateau pl) {
        this.pl=pl;
        coupasse=new SequenceListe<>();
        coupannule=new SequenceListe<>();
    }

    void ajouterConfiguration( Coup c ) throws CloneNotSupportedException {
        coupasse.insereTete( (Coup) c.clone() );
        coupannule=new SequenceListe<Coup>() ;
    }
    Coup retablirCoup(){
        if(!coupannule.estVide()){
            Coup courant=coupannule.extraitTete();
            coupasse.insereTete(courant);
            return courant;
        }
        return null;
    }

    Coup annulerCoup() {
        if(!coupasse.estVide()){
            Coup tmp=coupasse.extraitTete();
            coupannule.insereTete(tmp);
            //effacer le coup
        }
        return null;
    }
}
