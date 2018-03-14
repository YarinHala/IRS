package com.model.irs;

import java.io.File;
import java.io.Serializable;

public class FileObj implements Serializable{


	private static final long serialVersionUID = 1L;
		File file = null;
		String name;
		int number;
		int line;
		boolean fileVisible = true;
		int hits=0;
	
	
		public FileObj(File curFile,String fileName, int fileNumber){
			file =new File(curFile.getPath());
			name = fileName;
			number = fileNumber;
			line = -1;
		}


		@Override
		public String toString() {
			return "\nFileObj {name=" + name + ", number=" + number + ", line=" + line + "}";
		}


		public File getFile() {
			return file;
		}


		public void setFile(File file) {
			this.file = file;
		}


		public String getName() {
			return name;
		}


		public void setName(String name) {
			this.name = name;
		}


		public int getNumber() {
			return number;
		}


		public void setNumber(int nubmer) {
			this.number = nubmer;
		}
	
		public void setLine(int line){
			this.line = line;
		}


		public int getLine() {
			return line;
		}
		
		public String getStatus(){
			
			if(fileVisible == true){
				return "Visible";
			}
			
			return  "Hidden";
		}


		public void setFileVisible(boolean change) {
			fileVisible = change;
			
		}
	
	
}
