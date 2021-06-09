package IO;

import Model.Jeu;
import Model.Coup;

import java.io.*;
import java.io.File;

import java.io.FileInputStream;

import java.io.FileOutputStream;

import java.util.ArrayList;

public class SaveLoad {

    public static ArrayList<Object> GameToObj(Jeu J, Coup C, int p1, int p2 , int init , int niveau) {
        ArrayList<Object> l = new ArrayList<Object>();
        l.add(J);
        l.add(C);
        l.add(p1);
        l.add(p2);
        l.add(init);
        l.add(niveau);
        return l;
    }

    public static Jeu ObjToJeu(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        return ((Jeu) (l.get(0)));
        // stroke = ( Coup ) ( l.get(1) ) ;
        // S = l.get(2) ;

    }

    public static Coup ObjToCoup(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        Coup stroke = (Coup) (l.get(1));
        return ((Coup) (l.get(1)));
    }

    public static int ObjToP1(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        return ((int) (l.get(2)));
    }

    public static int ObjToP2(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        return ((int) (l.get(3)));
    }

    public static int ObjToInit(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        return ((int) (l.get(4)));
    }

    public static int ObjToNiveau(Object O) {
        ArrayList<Object> l = (ArrayList<Object>) O;
        return ((int) (l.get(5)));
    }

    public static void save(Object obj, String file) {

        try {
            File fichier = new File(file);
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fichier));
            out.writeObject(obj);
        } catch (Exception ex) {
            System.err.println(ex + " problem in close");
        }
    }

    public static Object load(String file) {

        try {

            File fichier = new File("saved/" + file);
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(fichier));
            return in.readObject();
        } catch (Exception ex) {
            System.err.println(" problem in load  " + ex);
            return null;
        }
    }

}