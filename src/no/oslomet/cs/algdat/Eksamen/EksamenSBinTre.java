package no.oslomet.cs.algdat.Eksamen;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Objects;
import java.util.StringJoiner;

public class EksamenSBinTre<T> {
    private static final class Node<T>   // en indre nodeklasse
    {
        private T verdi;                   // nodens verdi
        private Node<T> venstre, høyre;    // venstre og høyre barn
        private Node<T> forelder;          // forelder

        // konstruktør
        private Node(T verdi, Node<T> v, Node<T> h, Node<T> forelder) {
            this.verdi = verdi;
            venstre = v;
            høyre = h;
            this.forelder = forelder;
        }

        private Node(T verdi, Node<T> forelder)  // konstruktør
        {
            this(verdi, null, null, forelder);
        }

        @Override
        public String toString() {
            return "" + verdi;
        }

    } // class Node

    private Node<T> rot;                            // peker til rotnoden
    private int antall;                             // antall noder
    private int endringer;                          // antall endringer

    private final Comparator<? super T> comp;       // komparator

    public EksamenSBinTre(Comparator<? super T> c)    // konstruktør
    {
        rot = null;
        antall = 0;
        comp = c;
    }

    public boolean inneholder(T verdi) {
        if (verdi == null) return false;

        Node<T> p = rot;

        while (p != null) {
            int cmp = comp.compare(verdi, p.verdi);
            if (cmp < 0) p = p.venstre;
            else if (cmp > 0) p = p.høyre;
            else return true;
        }

        return false;
    }

    public int antall() {
        return antall;
    }

    public String toStringPostOrder() {
        if (tom()) return "[]";

        StringJoiner s = new StringJoiner(", ", "[", "]");

        Node<T> p = førstePostorden(rot); // går til den første i postorden
        while (p != null) {
            s.add(p.verdi.toString());
            p = nestePostorden(p);
        }

        return s.toString();
    }

    public boolean tom() {
        return antall == 0;
    }

    public boolean leggInn(T verdi) {
        Objects.requireNonNull(verdi, "Nullverdi er ikke lov!"); //sjekker om verdi ikke lik "null"

        //Her begynner fra rot noden, og deklarer to pekere:
        Node<T> current = rot;
        Node<T> previous = null; //denne noden skal peke på siste node før vi når null.

        int comperatorVerdi = 0; //den skal lagre verdien (-1,0,1) fra compare(o1,o2).

        //går gjennom treet frem til current peker er null, dvs. et nivå etter siste nivå:
        while (current != null) {

            previous = current; //vi pegker på noden som vi skal flyte fra til neste node.

            //før vi flytter til neste node sjekker vi om (current.verdi > verdi):
            comperatorVerdi = comp.compare(current.verdi, verdi); //den skal returnere 1 for sant og -1 for usant.

            //current skal peke på venstre barn hvis foreldre node var større en verdi, eller til høyre barn om verdi var større.
            current = (comperatorVerdi > 0) ? current.venstre : current.høyre;
        }

        //etter løkken så har vi current lik null, der med instansierer vi en node med verdien og til ordner den til current pekker.
        //det er for minske antall hjelpe variabler, siden current noden har blitt null.
        current = new Node<T>(verdi, previous); //den nye noden skal ha verdien og foreldren blir den forrige pekere bak.
        //og hvis den forrige pekkeren er null dvs. at treet er tom og vi lager en rot med denne noden.

        if (previous == null) { //treet er tom
            rot = current;
        } else if (comperatorVerdi > 0) { //foreldere sin verdi er større enn ny verdi.
            //legger til som venstre barn:
            previous.venstre = current;
        } else { //foreldere sin verdi er mindre enn ny verdi.
            //legger til som høyre barn:
            previous.høyre = current;
        }

        antall++; //oppdaterer antall tallet med +1
        return true; // returnerer sant
    }

    public boolean fjern(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int fjernAlle(T verdi) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public int antall(T verdi) {
        if(!inneholder(verdi)) return 0;
        int antall_forekomster = 0;
        Node<T> current = rot;
        while(current!=null){
            int comperator_verdi = comp.compare(current.verdi,verdi);
            if(comperator_verdi>0){
                current = current.venstre;
            }else if(comperator_verdi<0){
                current = current.høyre;
            }else {
                antall_forekomster++;
            }
        }

        return antall_forekomster;
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> nestePostorden(Node<T> p) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postorden(Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
