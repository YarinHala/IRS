package com.model.irs;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.channels.FileChannel;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Vector;

import com.view.irs.ManagerUI.FileBaseComponentClass;

public class FilesManager {

	public static FilesManager filesManager = null;
	Vector<FileObj> filesArray = null;
	Vector<Term> termArray = null;
	Vector<String>unWantedWord = null;
	LinkedList<Term> linkedListTerms = null;
	int numberOfFiles = 0;
	private  DataBase db;
	File sourceDir = null;
	File storageDir = null;
	
	
	
	protected FilesManager(){
		sourceDir = new File("Source");
		storageDir = new File("Storage");
		filesArray = new Vector<FileObj>();
		receptionOfDocuments(false);
		
	}
	
	public static FilesManager getFilesManager(){
		if(filesManager == null){
		filesManager = new FilesManager();
		}
		return filesManager;
	}
	
	/******************************************************/

	
	public  void receptionOfDocuments(boolean newFile){
		
		if(linkedListTerms == null){
			db = getDatatFromFile();
		}
		int CurrentNumOfFiles = 0;
		termArray = new Vector<Term>();
	     File[] filesList = sourceDir.listFiles();
	     if(newFile == false){
	    	 CurrentNumOfFiles = filesList.length;
	     }
	     else{
	    	 CurrentNumOfFiles = numberOfFiles+1/*may change if add argument*/;
	     }
	     
	        for( ; numberOfFiles < CurrentNumOfFiles ; numberOfFiles++){
	            if(filesList[numberOfFiles].isFile()){
		            filesArray.addElement(new FileObj(filesList[numberOfFiles],filesList[numberOfFiles].getName(),numberOfFiles));
		            copyFileFromSourceToStorage(filesArray.elementAt(numberOfFiles).getFile(),storageDir,filesArray.elementAt(numberOfFiles));
		            parseFile(filesArray.elementAt(numberOfFiles),termArray);
	            }
		
	        }
	        if(termArray.size() > 0){
	        	billdingPostingFile(termArray);
	        	setDataToFile(linkedListTerms);
	        }
	    
	        for (int i = 0; i < filesList.length; i++) {
	        	filesList[i].delete();
			}
	} 
	
	public DataBase getDatatFromFile(){
		
		db = new DataBase();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream("db/DataBase.txt");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ObjectInputStream ois = null;
		try {
			if(fis == null){
				return null;
			}
			ois = new ObjectInputStream(fis);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			//e1.printStackTrace();
			return null;
		}
		try {
			db = (DataBase) ois.readObject();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			ois.close();
			fis.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(db.getlinkedListTerm()  == null){
			return null;
		}
		linkedListTerms = db.getlinkedListTerm();
		filesArray = db.getFilesArray();
		return db;
	}
		
	@SuppressWarnings("resource")
	public void copyFileFromSourceToStorage(File sourceFile, File destFile, FileObj file){

		   if(!destFile.exists()) {
		        try {
					destFile.createNewFile();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    }

		    FileChannel source = null;
		    FileChannel destination = null;

		    try {
		        try {
					source = new FileInputStream(sourceFile).getChannel();					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		        try {
					destination = new FileOutputStream(destFile + "\\" + file.getNumber() + ".txt" ).getChannel();
					
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
		        try {
					destination.transferFrom(source, 0, source.size());
				} catch (IOException e) {
					e.printStackTrace();
				}
		    }
		    finally {
		        if(source != null) {
		            try {
		      			source.close();
						
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		        if(destination != null) {
		            try {
						destination.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
		        }
		    }
		
	}
	
	public void parseFile(FileObj sourceFile,Vector<Term> tempTermArray){
		
		
	    BufferedReader fileReader =null;
		try {
			fileReader = new BufferedReader(new FileReader(sourceFile.getFile()));
		} catch (FileNotFoundException e2) {
			e2.printStackTrace();
		}
		String tempTerm = null ;
	    String str;
	    try {
	    	int lineNumber = 0;
			while((str = fileReader.readLine()) != null){
				str.replaceAll("[^a-zA-Z0-9]", " ").toLowerCase();
			    String[] parts = str.split(" ");
			    for(int i = 0 ; i < parts.length ;  i++){  
			    	if(compareUnWantedWords(parts[i]) == true)
			    		tempTerm =parts[i].replaceAll("[^a-zA-Z]", "").toLowerCase();
			    		if(tempTerm.compareTo("") != 0){
			    			tempTermArray.addElement(new Term(tempTerm,sourceFile,lineNumber));
			    		}
				    }
			  lineNumber++;	  
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	    try {
			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
}

	private boolean compareUnWantedWords(String word) {
		/*
		unWantedWord = new Vector<String>();
		unWantedWord.add("to");
		unWantedWord.add("is");
		unWantedWord.add("the");
		unWantedWord.add("as");
		unWantedWord.add("in");
		unWantedWord.add("of");
		unWantedWord.add("or");
		unWantedWord.add("a");
		unWantedWord.add(" ");
		unWantedWord.add("");
		
		String tempWord = word.replaceAll("[^a-zA-Z]", "").toLowerCase();
		for(int i = 0 ; i < unWantedWord.size(); i ++){
			
			if(unWantedWord.elementAt(i).compareTo(tempWord) == 0){
				return false;
			}
		}
		*/
		return true;
		
	}

	public void billdingPostingFile(Vector<Term> termArray){

		Vector<Term> tempTermArray = new Vector<Term>();
		  Collections.sort(termArray, new Comparator<Term>() {
			@Override
			public int compare(Term t1, Term t2) {
				return t1.toString().compareTo(t2.toString());
			}
		  });
		  if(termArray.size() != 0){
			  tempTermArray.add(termArray.elementAt(0));
			  for(int i = 0 ,Index =0; i < termArray.size()-1; i++){
				 String term1 = tempTermArray.elementAt(Index).getTermName();
				 String term2 =termArray.elementAt(i+1).getTermName() ;
				 //System.out.println(term1 + " == " + term2);
				  if( term1.compareTo(term2) == 0){
					  tempTermArray.elementAt(Index).addTermFileArray(termArray.elementAt(i+1).getTermFileArray().elementAt(0),termArray.elementAt(i+1).getTermFileArray().elementAt(0).getLine());
				  }
				  else{
					  Index++;
					  tempTermArray.add(termArray.elementAt(i+1));
				  }
			  }
		}
		  
		 if(linkedListTerms == null){
			 linkedListTerms = new LinkedList<Term>();
		     for( int i = 0 ; i < tempTermArray.size() ; i++ ){
		    	 linkedListTerms.add(tempTermArray.elementAt(i));
		     } 
		 }
		 else{
			 for(int index = 0 ,listIndex = 0 ; index <linkedListTerms.size() ; index++ ){
				 
				 if(tempTermArray.elementAt(index).termName.compareTo(linkedListTerms.get(listIndex).getTermName())  == 0){
					 linkedListTerms.get(listIndex).addTermFileArray(tempTermArray.elementAt(index).getTermFileArray().elementAt(0), tempTermArray.elementAt(index).getTermFileArray().elementAt(0).getLine());
				 }
				 else if(tempTermArray.elementAt(index).termName.compareTo(linkedListTerms.get(listIndex).getTermName())  < 0){
						 if(listIndex == 0){
							 linkedListTerms.addFirst(tempTermArray.elementAt(index));
						 }
						 else if(listIndex == linkedListTerms.size()-1){
							 linkedListTerms.addLast(tempTermArray.elementAt(index));						
							 }
						 else{
							 linkedListTerms.add(listIndex,tempTermArray.elementAt(index));	
						 }
						 
				 }
				 else{
					 listIndex++;
					 continue;
				 }
				 
			 }
			 
		 }   
	}
	
	public boolean setDataToFile(LinkedList<Term> linkedListTerms){
		
		if(db == null){
			db = new DataBase();
		}
		db.setDataBase(linkedListTerms,filesArray);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream("db/DataBase.txt");
		} catch (FileNotFoundException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(fos);
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			//oos.reset();
			oos.writeObject(db);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			oos.close();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return true;
	}
		



/******************************************************/



public Vector<FileObj> getAllFiles() {
	return filesArray;
}

public void changeFilesVisibilityStatus(String fileName){
	
	   for( int index= 0 ; index< linkedListTerms.size() ; index++ ){
	    	 linkedListTerms.get(index).changeFilesVisibilityStatus(fileName);
	     } 
	
}

public void setChangeFilesVisibilityStatus(FileBaseComponentClass[] fileBaseComponentClasses) {
	   for( int index= 0 ; index< fileBaseComponentClasses.length ; index++ ){
			   filesArray.get(index).setFileVisible(fileBaseComponentClasses[index].getChangeStatusOfFileComponent());
	   } 
	
}

public void addFileToDBandIndex(File selectedFile) {
		if(selectedFile == null){
			return;
		}
		receptionOfDocuments(true);
}




}
