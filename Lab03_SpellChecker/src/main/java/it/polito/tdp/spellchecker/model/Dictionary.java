package it.polito.tdp.spellchecker.model;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


public class Dictionary {
	private List <String> dizionario;
	private String language;
	
	public void loadDictionary (String language) {
		
		if( this.language==null || !(this.language.equals(language))){
			
		this.language=language;
		dizionario=new ArrayList<>();
		try {
			FileReader fr = new FileReader("src/main/resources/"+language+".txt");
			BufferedReader br = new BufferedReader(fr);
			String word;
			while ((word = br.readLine()) != null) {
			dizionario.add(word.toLowerCase());
			}
			br.close();
			} catch (IOException e){
			throw new RuntimeException("Errore nella creazione del dizionario",e);
			}
		}
		  
	}
	public List <RichWord> spellCheckText(List<String> inputTextList){
		//List <RichWord> parole= new LinkedList<>();
		List <RichWord> parole= new ArrayList<>();
		for (String s : inputTextList) {
			RichWord rw= new RichWord(s);
			if(dizionario.contains(s)) {
				rw.setCorretto(true);
			}else {
				rw.setCorretto(false);
			}
			parole.add(rw);
		}
		 return parole;
	 }
	public List<RichWord> spellCheckTextLinear(List<String> inputTextList) {

		//List<RichWord> parole = new ArrayList<RichWord>();
		List<RichWord> parole= new LinkedList<RichWord>();

		for (String str : inputTextList) {

			RichWord richWord = new RichWord(str);
			
			boolean found = false;
			for (String word : dizionario) {
				if (word.equalsIgnoreCase(str)) {
					found = true;
					break;
				}
			}
			
			if (found) {
				richWord.setCorretto(true);	
			} else {
				richWord.setCorretto(false);
			}

			parole.add(richWord);
		}

		return parole;
	}
	
	public List<RichWord> spellCheckTextDichotomic(List<String> inputTextList) {
		int inizio;
		int fine;
		int medio;
		List<RichWord> parole = new ArrayList<RichWord>();
		//List<RichWord> parole = new LinkedList<RichWord>();
		for(String s : inputTextList) {
			RichWord rw = new RichWord(s);
			inizio=0;
			fine=dizionario.size();
			while(inizio!=fine) {
				medio= inizio+(fine-inizio)/2;
				if(s.compareToIgnoreCase(dizionario.get(medio))==0) {
					rw.setCorretto(true);
					break;
				}else if(s.compareToIgnoreCase(dizionario.get(medio))>0) {
					
					inizio=medio+1;
					
				}else {
					
					fine=medio;
				}
			}
			parole.add(rw);
		}
		return parole;
		
	}
	
	
	
}
	
