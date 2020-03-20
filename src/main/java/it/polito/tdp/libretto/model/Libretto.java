package it.polito.tdp.libretto.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Memorizza e gestiosce un insieme di voti superati
 * @author elisa
 *
 */

public class Libretto {
	
	private List<Voto> voti= new ArrayList<>(); //lista vuota, che potrà contenere un numero arbitrario di voti
	
	//usiamo sempre oggetti e non dati sparsi-->va bene questo metodo

	/**
	 * crea un libretto nuovo (e vuoto)
	 */
	public Libretto() {
		super();
	}
	
	/**
	 * copy constructor
	 * "Shallow" (copia superficiale)
	 * 
	 * "Deep" (copia profonda)-->mi dà oggetto totalmente separati, non condivide nulla (NON in questo caso)
	 * @param lib
	 */
	public Libretto(Libretto lib) {
		super();
		this.voti.addAll(lib.voti); //copio il libretto, ma condivido gli oggetti Voto (non vengono copiati)
	}
	
	/**
	 * aggiunge un nuovo voto al libretto
	 * 
	 * @param v Voto da aggiungere
	 * @return {@code true} se ha inserito il voto, {@code false} se non l'ha inserito perchè era duplicato o in conflitto
	 */
	public boolean add(Voto v) { //se uso questo metodo e poi cambio la classe voto, questo metodo cambia automaticamente
		if(this.isConflitto(v) || this.isDuplicato(v)) {
			//non inserire il voto
			return false; //segnala al chiamante che non ha avuto successo
		}else {
			//inserisci il voto perchè non è in conflitto nè duplicato
			this.voti.add(v);
			return true;
		}
	}//questo metodo delega l'operazione di aggiunta all'arrayList
	
	//public void add(String corso, int voto, LocalDate data) { //se uso questo metodo e poi modifico la classe voto, 
	//devo cambiare manualmente questo metodo e tutti quelli che lo usano
	
	
	/** dato un libretto restituisce una stringa nella quale vi 
	 * sono solo i voti pari al valore passato come parametro
	 * 
	 * @param voto valore specificato
	 * @return stringa formattata per visualizzare il sotto-libretto
	 */
	public String stampaVotiUguali(int voto) {
		String s="";
		for(Voto v:voti) {
			if(v.getVoto()==voto) {
				s+=v.toString()+"\n";
			}
		}
		return s;
	}
	
	/**
	 * genera un nuovo libretto, che ottengo da quello attuale, 
	 * che contiene esclusivamente i voti con votazione pari a quella specificata
	 * @param voto votazione specificata
	 * @return nuovo libretto "ridotto"
	 */
	
	public Libretto estraiVotiUguali(int voto) { //generalizzato
		Libretto nuovo = new Libretto();
		for(Voto v: voti) {
			if(v.getVoto()==voto) {
				nuovo.add(v);
			}
		}
		return nuovo;
	}
	
	public String toString() {
		String s="";
		for(Voto v:voti) {
			s+=v.toString()+"\n";
		}
		return s;
	}

	/**
	 * dato il nome del corso, ricerca se quell'esame esiste nel libretto e 
	 * in caso affermativo, restituisce l'oggetto {@link Voto} corrispondente
	 * se l'esame non esiste, restituisce {@null}
	 * 
	 * @param nomeCorso nome esame da cercare
	 * @return {@link Voto} corrispondente oppure {@code null} se non esiste
	 */
	public Voto cercaNomeCorso(String nomeCorso) {
		/*for(Voto v: voti) {
			if(v.getCorso().equals(nomeCorso)) {
				return v;
			}
		}
		return null;*/
		int pos= this.voti.indexOf(new Voto(nomeCorso, 0, null)); //restituisce indice nell'arraylist di dove si trova il voto con il nome del corso corrispondente
		if(pos!=-1) {
			return this.voti.get(pos);
		}else {
			return null;
		}
	
	}
	
	/** 
	 * ritorna {@code true} se il corso specificato da {@code v} esiste nel libretto con la stessa valutazione
	 * se non esiste o se la valutazione è diversa, ritorna {@code false}
	 * @param v il {@link Voto} da ricercare
	 * @return l'esistenza di un duplicato
	 */
	public boolean isDuplicato(Voto v) {
		Voto esiste= this.cercaNomeCorso(v.getCorso());
		if(esiste==null) { //non l'ho trovato quindi non è duplicato
			return false;
		}
		/*if(esiste.getVoto()==v.getVoto()) {
			return true;
		}else {
			return false;
		}*/
		return (esiste.getVoto()==v.getVoto());
	}
	
	/**
	 * Determina se esiste un voto con lo stesso nome corso ma valutazione diversa
	 * @param v
	 * @return
	 */
	public boolean isConflitto(Voto v) {
		Voto esiste= this.cercaNomeCorso(v.getCorso());
		if(esiste==null) { //non l'ho trovato quindi non è duplicato
			return false;
		}
		return (esiste.getVoto()!=v.getVoto());
	}
		
	/**
	 * Restituisce un NUOVO libretto migliorando i voti del libretto attuale
	 * @return
	 */
	public Libretto creaLibrettoMigliorato() {
		Libretto nuovo = new Libretto();
		//nuovo-->si riferisce al libretto nuovo
		//this. -->si riferisce al libretto attuale
		for(Voto v: this.voti) {
			//Voto v2=v; in questo modo condivido l'oggetto voto e le modifiche di uno si riflettono sull'altro
			//ma io voglio fare una COPIA/clone dell'oggetto (con gli stessi valori di quello precedente)
			//quindi posso usare un COPY CONSTRUCTOR di Voto oppure uso un metodo clone creato in Voto
			
			//Voto v2= new Voto(v); //-->copy contructor
			Voto v2= v.clone();  //-->metodo clone
			if(v2.getVoto()>=24) {
				v2.setVoto(v2.getVoto()+2);
				if(v2.getVoto()>30)
					v2.setVoto(30);
			} else if(v2.getVoto()>=18) {
				v2.setVoto(v2.getVoto()+1);
			}
			
			nuovo.add(v2);
		}
		return nuovo;
	}
	
	/**
	 * riordina i voti presenti nel libretto corrente alfabeticamente per corso
	 * -->stessi oggetti Voto ma rimescolati 
	 */
	public void ordinaPerCorso() {
		Collections.sort(this.voti);
	}
	
	public void ordinaPerVoto() {
		Collections.sort(this.voti, new ConfrontaVotiPerValutazione());
		//this.voti.sort(new ConfrontaVotiPerValutazione());
	}
	/**
	 * elimina dal libretto tutti i voti minori di 24
	 */
	//non posso moficare una lista che sto iterando, devo farlo in due passaggi
	public void cancellaVotiScarsi() { 
		List<Voto> daRimuovere= new ArrayList<Voto>();
		for(Voto v:this.voti) {
			if(v.getVoto()<24) {
				daRimuovere.add(v);
			}
		}
		
		this.voti.removeAll(daRimuovere);
	/*	for(Voto v: daRimuovere) {
			this.voti.remove(v);
		}*/
	}

}
