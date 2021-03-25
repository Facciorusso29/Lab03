package it.polito.tdp.spellchecker.model;

public class RichWord {
	String parola;
	boolean corretto;
	public RichWord(String parola) {
		super();
		this.parola = parola;
	}
	public String getParola() {
		return parola;
	}
	public boolean isCorretto() {
		return corretto;
	}
	public void setCorretto(boolean corretto) {
		this.corretto = corretto;
	}
	

}
