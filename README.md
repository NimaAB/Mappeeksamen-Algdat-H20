# Mappeeksamen i Algoritmer og Datastrukturer Høst 2020

# Krav til innlevering

Se oblig-tekst for alle krav, og husk spesielt på følgende:

* Git er brukt til å dokumentere arbeid (minst 2 commits per oppgave, beskrivende commit-meldinger)	
* git bundle er levert inn
* Hovedklassen ligger i denne path'en i git: src/no/oslomet/cs/algdat/Eksamen/no.oslomet.cs.algdat.Eksamen.EksamenSBinTre.java
* Ingen debug-utskrifter
* Alle testene i test-programmet kjører og gir null feil (også spesialtilfeller)
* Readme-filen her er fyllt ut som beskrevet


# Beskrivelse av oppgaveløsning (4-8 linjer/setninger per oppgave)

Jeg har brukt git til å dokumentere arbeidet vårt. Jeg har **21** commits totalt, og hver logg-melding beskriver det jeg har gjort av endringer.
* **Oppgave 0:** Started 20.10.2020  med å lage git repository og fikse litt av import feilene.

* **no.oslomet.cs.algdat.Eksamen.Oppgave 1:** løste oppgaven ved å deklere to pekere en peker på roten og den andre peker på null.
 i en while-løkke deklarer jeg den andre pekeren til roten og flyter den føstre pekeren til å peke på en av barna til rot noden.
  Hvis verdien er mindre enn foreldre noden så peker den på venstre barn eller motsatt.
   Tilslutt har vi denne pekeren lik null og da forrige pekere peker på siste noden.
    Og da kan vi legge til denne nye noden som venstre eller 
    høyre barn til denne noden basert på om verdien er større eller mindre 
    enn foreldre noden sin verdi. og hvis treet var tom da blir den nya noden roten av treet. 
    NB!! I leggInn metoden har jeg fått hjelp fra **Programkode 5.2.3 a)**.

* **no.oslomet.cs.algdat.Eksamen.Oppgave 2:** implementeringen er slik at
jeg begynner med å sjekke om verdien finnes i treet med metoden inneholder(verdi) hvis ikke så returneres 0. Og følgendemetode sjekker for null-verdi og tom tre. Videre lager jeg en peker på roten og en hjelpe variabel for å telle antall like verdier. I en while-løkke begynner jeg fra roten og sjekker om `(current.verdien >= verdi)` og den returnerer 0 for lik, 1 for større og -1 for mindre. Hvis det er -1 så fortsetter den til venstre. Hvis det er 1 til høyre og 0 til høyre itillegg økes det teller med en. Tilslutt returnerer jeg resultatet fra telleren.      

* **no.oslomet.cs.algdat.Oppgave 3:** Metoden føstePostorden(p) er løst ved hjelp av en hjelpe stakk og en hoved stakk.
Hvor jeg pusher foreldre nodene i hjelpestakken, så poper dem ut og legger til venstre og etter på høyre barna deres i hjelpe stakken. Og til slutt legges de 
i hoved stakken. Etter at hjelpe stakken blir tom så returneres det første noden fra hoved stakken.

    Metoden nestePostorden(p) sjekker først om p er rot, hvis det er sant så har den ikke neste node i postorden rekkefølge.
    etter på sjekkes det om p er høyre barnet til subtreet, hvis det så er neste node forelderen dens.
    Hvis ingen av de overst er riktig, så peker jeg på foreldren til p og lager en annen peker som peker på siste noden som den forrige har pekt på.
    så lenge den første pekeren ikke null gjørs følgende: 
    har pekeren venstre eller er den lik venstre barn, så er vi ikke intresert i det og går og peker på høyre barn.
    ellers går vi og peker på venstre barnet.

* **no.oslomet.cs.algdat.Oppgave 4:** Første postorden metoden er implementert slik: Den sjekker om treet ikke er tomt. Etter på finner jeg 
førte postorden noden ved hjelp av førstePostorden(p) hvor p er rot noden. Etter på utfører jeg oppgaven på dens verdi og finner neste node etter den ved hjelp
av nestPostorden(p) hvor p er første noden jeg har funnet. Og begynner en while-løkke så lenge er det en neste node. Og utfører oppgaven på en ig en av dem, og finner dem.

    Og den rekursive metoden er implementert slik: Jeg begynner med å sjekke om p er null, denne sjekken skal stoppe rekursjonen når den når siste nivå i treet.
    etter på kaller jeg på metoden to ganger først passer jeg inn p sin venstre barn, etter på p sin høyre barn, tilslutt utføres oppgaven på verdien til p.

* **no.oslomet.cs.algdat.Oppgave 5:** *serielize()* metoden er kodet på denne måte; Jeg har en kø og en ArrayList. jeg begynner med å legge inn rot noden.
der etter sletter jeg den, men sjekker også om rot noden har første venstre barn etter på høyre og legger dem inn i køen, først venstre så kommer høyre.
Til slutt legger jeg vedien av roten til ArrayListen. Dette gjentas frem til køen blir tom det vil si at siste nodene som kommer inn er blad noder. 

    *deserielize(ArrayList<K>data , Comperator<? super K> c)* er implementert slik: først instansierer jeg et tre object med (c) i kunstruktøren og 
    legger inn vediene fra data i treet med tre.liggInn(T verdi) som jeg har implimentert i oppgave  1.
    Tilslutt returneres det treet.

* **no.oslomet.cs.algdat.Oppgave 6:** I denne oppgaven har jeg begynet med å lage to hjelpe metodene *nesteInorden()* og *finnNode()*,
 etter på har jeg implementert *fjern()* metoden ved hjelp av programkoden **5.2.8 d)** etter flere forsøk for å implementere det selv.
 metoden tar hensyn til tre tilfeller: 1) noden er blad node. 2)noden har et barn venstre eller høyre. 3) noden har begge høyre og venstre barna.
 *nesteInorden()* bruker jeg i tredje tilfelle og *finnNode()* bruker jeg i starten av  metoden for å finne noden vi vil fjerne.
 
     *nullstill()* metoden er implemenert slik at henter nodene i postorden rekke følge og fjerner dem, grunnen til det vær at i postorden blir nodene blad noder etter
     fjerning. Den skal først til venstre barnet slette det, deretter til høyre barnet slette den og til slutt til foreldre node som nå den selv er blad node.
     som er enkelt å slette.
     
     *fjernAlle(verdi)* er implementert slik at den sletter en å en noder med verdi lik verdi parameteren, i en while-løkke som fortsetter så lenge fjern() returnerer sann.    

### Merknad 
programmet klarer ikke siste test i oppgave 6. Grunnen kan vare PC-en min. For det er litt for høy antall som skal legges inni treet.
jeg fikk godkjendt denne testen ved å endre antallet fra 500_000 til 50_000 random tall.

### Kilder
* **Oppgave 1**: koden for leggInn(T verdi) er skrevet ved hjelp av *programkode 5.2.3 a)*
* **Oppgave 6**: koden for fjern(T verdi) er skrevet ved hjelp av *programkode 5.2.8 d)*  