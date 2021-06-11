package Model;

import Structures.Iterateur;
import Structures.SequenceListe;
import java.io.Serializable;

import java.awt.*;

public class HistoriqueCoup implements Serializable {
    int taille=0;
    public SequenceListe<Coup> coupasse;
    SequenceListe<Coup> coupannule;
    Plateau pl;

    public HistoriqueCoup(Plateau pl) {
        this.pl = pl;
        coupasse = new SequenceListe<>();
        coupannule = new SequenceListe<>();
    }

    public void ajouterConfiguration(Coup c) throws CloneNotSupportedException {
        taille++;
        coupasse.insereTete((Coup) c.clone());
        coupannule = new SequenceListe<Coup>();
    }

    public int getTaille() {
        return taille;
    }

    public int retablirCoup2() {
        // jouer le coup
        Coup c=null;
        if (!coupannule.estVide()) {
            c = coupannule.extraitTete();
            Point fin = c.position_final();
            Point debut = c.position_init();
            int pionjoueur = pl.cases[debut.x][debut.y];
            // je vide les cases des pions a eliminer
            Iterateur<SequenceListe<Point>> it = c.pionspris.iterateur();
            while (it.aProchain()) {
                SequenceListe<Point> liste = it.prochain();
                Iterateur<Point> pionsamang = liste.iterateur();
                while (pionsamang.aProchain()) {
                    Point courant = pionsamang.prochain();
                    pl.ajoute(Plateau.VIDE, courant.x, courant.y);
                }
            }
            // je deplace le pion mangeur
            pl.ajoute(Plateau.VIDE, debut.x, debut.y);
            pl.ajoute(pionjoueur, fin.x, fin.y);
            // ajout du coup a couppasse
            coupasse.insereTete(c);
            taille++;
            return c.getjoueur();
        }
        return 0;

    }

    public int annulerCoup2() {
        Coup c=null;
        if (!coupasse.estVide()) {

            c = coupasse.extraitTete();
            Point fin = c.position_final(); 
            Point debut = c.position_init();
            int pionjoueur = pl.cases[fin.x][fin.y];
            int couleur_pion_elimine;
            if (pionjoueur == 1)
                couleur_pion_elimine = 2;
            else
                couleur_pion_elimine = 1;
            // je vide les cases des pions a eliminer
            Iterateur<SequenceListe<Point>> it = c.pionspris.iterateur();
            while (it.aProchain()) {
                SequenceListe<Point> liste = it.prochain();
                Iterateur<Point> pionsamang = liste.iterateur();
                while (pionsamang.aProchain()) {
                    Point courant = pionsamang.prochain();
                    pl.ajoute(couleur_pion_elimine, courant.x, courant.y);
                }
            }
            // je deplace le pion mangeur
            if(!pl.aPionX(couleur_pion_elimine,fin.x,fin.y))
                pl.ajoute(Plateau.VIDE, fin.x, fin.y);
            pl.ajoute(pionjoueur, debut.x, debut.y);
            // deplace le coup vers coupannule
            coupannule.insereTete(c);
            taille--;
            return c.getjoueur();
        }
        return 0;
        
    }

    public Coup sommet() {
        if(!coupasse.estVide())
            return coupasse.sommet();
        return null;
    }

}
