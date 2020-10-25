package no.oslomet.cs.algdat.Eksamen;

import java.util.*;

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
        if (!inneholder(verdi)) return 0; //Hvis treet ikke inneholder verdien så returnerer jeg 0.
        //og følgende if kontrol behandler tilfeldene om treet er tom eller verdien er null.

        int antall_forekomster = 0; //en tellende variable for antall like node med verdi lik verdi.
        Node<T> current = rot; // Begynner fra rot noden og går ned over i treet.
        while (current != null) { //frem til siste nivå.
            int comperator_verdi = comp.compare(current.verdi, verdi); //den skal returnere 1 for sant, -1 for usant og 0 om de er like.
            if (comperator_verdi > 0) {//hvis det er 1:
                current = current.venstre; //går videre til venstre barnet.
            } else if (comperator_verdi < 0) { //hvis det er -1:
                current = current.høyre; //går ned til høre barnet
            } else { //ellers så er det 0 og verdi == current.verdi:
                antall_forekomster++; //øker antall med 1
                current = current.høyre; //fortsetter til høyre barn det slik legginn metoden er implementert også.
            }
        }
        return antall_forekomster; //returnerer antall like virdier tilslutt.
    }

    public void nullstill() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    private static <T> Node<T> førstePostorden(Node<T> p) {
        ArrayDeque<Node<T>> stakEn = new ArrayDeque<>();//lager en stak for å legge nodene middlertidig inn.
        ArrayDeque<Node<T>> stakTo = new ArrayDeque<>();//denne stakken skal lagre noden i riktig rekkefølge.
        stakEn.push(p); //legger til noden vi begynner fra i stakken (f.eks. rot)
        while (!stakEn.isEmpty()) {//så lenge stakk en er ikke tom
            Node<T> current = stakEn.pop(); //tar ut første noden fra stakk en og beolder den i current
            if (current.venstre != null) { //hvis current har venstre barn
                stakEn.push(current.venstre);//så legges den inn i stakk en
            }
            if (current.høyre != null) {//der etter sjekker for høyre barn
                stakEn.push(current.høyre); //så legges den etter venstre barnet.
            }
            stakTo.push(current);//tilslutt legger vi foreldren dere til stakTo.
        }
        return stakTo.peekFirst();//og i slutten returneres føste noden i stakk to.
    }


    private static <T> Node<T> nestePostorden(Node<T> p) {
        if (p.forelder == null) { //hvis p ikke har forledre, dvs. p er rot
            return null; //og ingen neste node i postorden rekkefølge.
        }
        if (p == p.forelder.høyre) {//hvis noden er lik høyre barnet til foreldren
            return p.forelder; //returneres foreldren til p.
        }
        Node<T> current = p.forelder; // deklarerer current node som er foreldren til p
        Node<T> prev = null; //og prev blir siste node som current har pekt på.
        while (current != null) { //så lenge current ikke nådd under siste nivå
            prev = current; //prev peker på den siste som current har pekt på.
            if (current.venstre == null || current.venstre == p) {//current sin venstre barn er null eller lik p
                current = current.høyre; //da går vi til høyre barnet til current, fordi p er enten venstre barn eller null.
            } else { //ellers p er ikke foreldren sin venstre barn og heller ikke null
                current = current.venstre; // da går vi til venstre.
            }
        }
        return prev; // til slutt returneres prev som holder på siste node før current ble null.
    }

    public void postorden(Oppgave<? super T> oppgave) {
        ArrayDeque<Node<T>> stakEn = new ArrayDeque<>();//lager en stak for å legge nodene middlertidig inn.
        ArrayDeque<Node<T>> stakTo = new ArrayDeque<>();//denne stakken skal lagre noden i riktig rekkefølge.
        stakEn.push(rot); //legger til noden vi begynner fra i stakken (f.eks. rot)
        while (!stakEn.isEmpty()) {//så lenge stakk en er ikke tom
            Node<T> current = stakEn.pop(); //tar ut første noden fra stakk en og beolder den i current
            if (current.venstre != null) { //hvis current har venstre barn
                stakEn.push(current.venstre);//så legges den inn i stakk en
            }
            if (current.høyre != null) {//der etter sjekker for høyre barn
                stakEn.push(current.høyre); //så legges den etter venstre barnet.
            }
            stakTo.push(current);//tilslutt legger vi foreldren dere til stakTo.
        }
        while(!stakTo.isEmpty()){
            T valueOfCurrentNode =  stakTo.removeFirst().verdi;
            oppgave.utførOppgave(valueOfCurrentNode);
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {

    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


} // ObligSBinTre
