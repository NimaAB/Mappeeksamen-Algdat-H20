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
        if(antall==0) return false;

        if (verdi == null) return false;
        Node<T> node = finnNode(verdi, rot, comp);
        if (node == null) return false;

        Node<T> nodes_forelder = (node != rot) ? node.forelder : node;

        //noden har ikke venstre og/eller høyre barn:
        //noden er blad node eller den har bare et barn.
        if (node.venstre == null || node.høyre == null) {
            Node<T> nodes_barn = (node.venstre != null) ? node.venstre : node.høyre;
            if (node == rot) {
                rot = nodes_barn;
            } else if (node == nodes_forelder.venstre) {
                nodes_forelder.venstre = nodes_barn;
            } else {
                nodes_forelder.høyre = nodes_barn;
            }
        } else {
            Node<T> nesteInorden = nesteInorden(node);

            if (nesteInorden != null) {
                Node<T> neste_sin_forelder = nesteInorden.forelder;

                node.verdi = nesteInorden.verdi;
                if (nesteInorden.venstre == null && nesteInorden.høyre==null) {
                    neste_sin_forelder.venstre = null;
                } else {
                    neste_sin_forelder.venstre = nesteInorden.høyre;
                    nesteInorden.høyre.forelder = neste_sin_forelder;
                }
            }
        }
        return true;
    }

    public int fjernAlle(T verdi) {
        if (antall == 1 || antall == 0) {
            if (inneholder(verdi)) {
                rot = null;
                antall--;
                return 1;
            }
            return 0;
        }
        int teller = 0;
        while (fjern(verdi)) {
            teller++;
        }
        return teller;
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
        if (antall == 0) {
            return;
        }
        if (antall == 1) {
            rot = null;
            antall--;
            return;
        }

        Node<T> forste = førstePostorden(rot);
        Node<T> neste = nestePostorden(forste);

        if (forste == forste.forelder.høyre) {
            forste.forelder.høyre = null;
        } else {
            forste.forelder.venstre = null;
        }

        while (neste != null) {
            if (neste == neste.forelder.høyre) {
                neste.forelder.høyre = null;
            } else {
                neste.forelder.venstre = null;
            }

            neste = nestePostorden(neste);
            antall--;
        }
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
        if (rot == null) return; //sjekker om treet ikke tom

        Node<T> forsteNode = førstePostorden(rot); //finner første node i postorder med start i rot
        oppgave.utførOppgave(forsteNode.verdi); //utfører oppgaven på verdien av første node.

        Node<T> nesteNode = nestePostorden(forsteNode); //finner neste node av første node.

        while (nesteNode != null) { //så lenge er det en neste node
            oppgave.utførOppgave(nesteNode.verdi); //utfører den en oppgave på dens verdi
            nesteNode = nestePostorden(nesteNode);//og itererer til neste node.
        }
    }

    public void postordenRecursive(Oppgave<? super T> oppgave) {
        postordenRecursive(rot, oppgave);
    }

    private void postordenRecursive(Node<T> p, Oppgave<? super T> oppgave) {
        if (p == null) return; //sjekker om p er null den skal stoppe rekursjonen.
        postordenRecursive(p.venstre, oppgave);// gå til venstre barnet til p og utfører samme oppgave.
        postordenRecursive(p.høyre, oppgave);//går til høyre barnet etter at venstre barna er ferdi.
        oppgave.utførOppgave(p.verdi);//utfører oppgaven på verdien til noden.
    }

    public ArrayList<T> serialize() {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }

    static <K> EksamenSBinTre<K> deserialize(ArrayList<K> data, Comparator<? super K> c) {
        throw new UnsupportedOperationException("Ikke kodet ennå!");
    }


    // hjelpe Metoder for oppgave 6:

    /**
     * metoden finner neste inorden node for
     *
     * @param p noden
     * @return neste inorden nodenm, og null hvis p ikke har høyre barn.
     */
    private static <T> Node<T> nesteInorden(Node<T> p) {
        if (p.høyre == null) {
            return null;
        }
        Node<T> current = p.høyre;
        Node<T> prev = null;
        while (current != null) {
            prev = current;
            if (current.venstre != null) {
                current = current.venstre;
            } else {
                current = null;
            }
        }
        return prev;
    }

    /**
     * metoden finner noden med dens verdi.
     *
     * @param verdi noden vi letter etter har denne verdi.
     * @param rot   søk begynner fra denne roten.
     * @param comp  Comperator
     * @param <T>   generiske typen T
     * @return null hvis den noden ikke er funnet, ellers noden.
     */
    private static <T> Node<T> finnNode(T verdi, Node<T> rot, Comparator<? super T> comp) {
        Node<T> current = rot;
        Node<T> riktig_node = null;
        int comperator;
        while (current != null) {
            comperator = comp.compare(current.verdi, verdi);
            if (comperator > 0) {
                current = current.venstre;
            } else if (comperator < 0) {
                current = current.høyre;
            } else {
                riktig_node = current;
                break;
            }
        }
        return riktig_node;
    }

} // ObligSBinTre
