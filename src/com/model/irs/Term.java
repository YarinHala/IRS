package com.model.irs;

import java.io.Serializable;
import java.util.Vector;

public class Term implements Serializable{

	private static final long serialVersionUID = 1L;
	String termName;
	Vector<FileObj> termFileArray = null;
	boolean termVisible = true;
	int hits = 0;
	
	
	public Term(String termName,FileObj termFile,int line) {
		hits=1;
		this.termName = termName;
		this.termFileArray = new Vector<FileObj>();
		this.termFileArray .addElement(termFile);
	}
	

	public String getTermName() {
		return termName;
	}
	public void setTermName(String termName) {
		this.termName = termName;
	}
	public Vector<FileObj> getTermFileArray() {
		return termFileArray;
	}
	public void setTermFileArray(Vector<FileObj> termFileArray) {
		this.termFileArray = termFileArray;
	}
	
	public void addTermFileArray(FileObj termFile,int line) {
		termFile.setLine(line);
		hits++;
		this.termFileArray .addElement(termFile);
	}


	@Override
	public String toString() {
		return "Term\n {\ntermName=" + termName +":\n"+ "termFileArray=" + termFileArray + "\n}\n@@@@@@@@@@\n";
	}


	public void changeFilesVisibilityStatus(String fileName) {
		
		for(int index = 0 ;index < termFileArray.size() ; index++){
			if(termFileArray.elementAt(index).getName().compareTo(fileName) == 0){
				termFileArray.elementAt(index);
			}
				
		}
		
	}
	
	
	
	
	
}
