package com.model.irs;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Vector;

public class DataBase implements Serializable{

	private static final long serialVersionUID = 1L;
	LinkedList<Term> linkedListTerms = null;
	Vector<FileObj> filesArray = null;
	
	public DataBase(){
		linkedListTerms = new LinkedList<Term>();
	};
				
	
	void setDataBase(LinkedList<Term> list,Vector<FileObj> files){
		linkedListTerms = list;
		filesArray = files;
	}
	

	public LinkedList<Term> getlinkedListTerm(){
		
		return linkedListTerms;
	}
	
	public Vector<FileObj> getFilesArray(){
		
		return filesArray;
	}
	
	void printAllTerm(){
		System.out.println("***********Terms**************");
		while(linkedListTerms.iterator().hasNext())
			linkedListTerms.iterator().next().getTermName();
		
		System.out.println("************Files*************");
		while(filesArray.iterator().hasNext())
			filesArray.iterator().next().getName();
		
	}
	
	
}
