/*
 * Sokoban - Encore une nouvelle version (à but pédagogique) du célèbre jeu
 * Copyright (C) 2018 Guillaume Huard
 * 
 * Ce programme est libre, vous pouvez le redistribuer et/ou le
 * modifier selon les termes de la Licence Publique Générale GNU publiée par la
 * Free Software Foundation (version 2 ou bien toute autre version ultérieure
 * choisie par vous).
 * 
 * Ce programme est distribué car potentiellement utile, mais SANS
 * AUCUNE GARANTIE, ni explicite ni implicite, y compris les garanties de
 * commercialisation ou d'adaptation dans un but spécifique. Reportez-vous à la
 * Licence Publique Générale GNU pour plus de détails.
 * 
 * Vous devez avoir reçu une copie de la Licence Publique Générale
 * GNU en même temps que ce programme ; si ce n'est pas le cas, écrivez à la Free
 * Software Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307,
 * États-Unis.
 * 
 * Contact:
 *          Guillaume.Huard@imag.fr
 *          Laboratoire LIG
 *          700 avenue centrale
 *          Domaine universitaire
 *          38401 Saint Martin d'Hères
 */
package Structures ;
import java.io.Serializable; 

public class SequenceListe<E> implements Sequence<E>, Serializable{
	Maillon<E> tete, queue;
	public int size;
	// Les méthodes implémentant l'interface
	// doivent être publiques
	@Override
	public void insereQueue(E element) {
		Maillon<E> m = new Maillon<>(element, null);
		if (queue == null) {
			tete = queue = m;
		} else {
			queue.suivant = m;
			queue = m;
		}
		size++;
	}

	@Override
	public void insereTete(E element) {
		Maillon<E> m = new Maillon<>(element, tete);
		if (tete == null) {
			tete = queue = m;
		} else {
			tete = m;
		}
		size++;
	}
	public E sommet(){
		return tete.element;
	}
	@Override
	public E extraitTete() {
		E resultat;
		// Exception si tete == null (sequence vide)
		resultat = tete.element;
		tete = tete.suivant;
		if (tete == null) {
			queue = null;
		}
		size--;
		return resultat;
	}

	@Override
	public boolean estVide() {
		return tete == null;
	}

	@Override
	public String toString() {
		String resultat = "SequenceListe [ ";
		boolean premier = true;
		Maillon<E> m = tete;
		while (m != null) {
			if (!premier)
				resultat += ", ";
			resultat += m.element;
			m = m.suivant;
			premier = false;
		}
		resultat += " ]";
		return resultat;
	}

	@Override
	public Iterateur<E> iterateur() {
		return new IterateurSequenceListe<>(this);
	}
}
