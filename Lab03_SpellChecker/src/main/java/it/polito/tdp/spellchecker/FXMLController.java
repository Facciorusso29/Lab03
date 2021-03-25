package it.polito.tdp.spellchecker;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import it.polito.tdp.spellchecker.model.Dictionary;
import it.polito.tdp.spellchecker.model.RichWord;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;

public class FXMLController {
	private Dictionary model;
    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<String> boxLanguage;

    @FXML
    private TextArea txtDaCorreggere;

    @FXML
    private TextArea txtCorretto;

    @FXML
    private Label lblErrori;

    @FXML
    private Label lblStato;

    @FXML
    void doClearText(ActionEvent event) {
    	txtCorretto.clear();
    	this.txtDaCorreggere.clear();
    	lblErrori.setText("Number of Errors:");
		lblStato.setText("Spell Check Status:");
    }

    @FXML
    void doSpellCheck(ActionEvent event) {
    	
    	List <String> inputTextList=new ArrayList<>();
    	String lingua= this.boxLanguage.getValue();
    	if(lingua==null) {
    		this.txtDaCorreggere.setText("Inserire una lingua!");
    		return;
    	}
    	model.loadDictionary(lingua);
    	String inputText = txtDaCorreggere.getText().toLowerCase();
    	
    	if (inputText.isEmpty()) {
			txtDaCorreggere.setText("Inserire un testo da correggere!");
			return;
		}
    	
    	
    	inputText = inputText.replaceAll("\n", " ");
		inputText = inputText.replaceAll("[.,\\/#!$%\\^&\\*;:{}=\\-_`~()\\[\\]]", "");
		StringTokenizer st= new StringTokenizer(inputText," ");
		while (st.hasMoreTokens()) {
			inputTextList.add(st.nextToken());
		}
		long start = System.nanoTime();
		//List <RichWord> outputTextList=model.spellCheckText(inputTextList);
		//List <RichWord> outputTextList=model.spellCheckTextLinear(inputTextList);
		List <RichWord> outputTextList=model.spellCheckTextDichotomic(inputTextList);
		long end = System.nanoTime();
		String daCorreggere="";
		int numErrori=0;
		for(RichWord r: outputTextList) {
			if(!r.isCorretto()) {
				numErrori++;
				daCorreggere+=r.getParola()+"\n";
			}
		}
    	this.txtCorretto.setText(daCorreggere);
    	lblStato.setText("Spell check completed in " + (end - start) / 1E9 + " seconds");
		lblErrori.setText("The text contains " + numErrori + " errors");
    }
    
    
    public void setModel(Dictionary m) {
    	boxLanguage.getItems().addAll("English","Italian");
    	this.model=m;
    }

    @FXML
    void initialize() {
        assert boxLanguage != null : "fx:id=\"boxLanguage\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtDaCorreggere != null : "fx:id=\"txtDaCorreggere\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtCorretto != null : "fx:id=\"txtCorretto\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblErrori != null : "fx:id=\"lblErrori\" was not injected: check your FXML file 'Scene.fxml'.";
        assert lblStato != null : "fx:id=\"lblStato\" was not injected: check your FXML file 'Scene.fxml'.";

    }
}
