package com.model.irs;

import java.io.File;
import java.util.Vector;

import com.view.irs.ManagerUI.FileBaseComponentClass;

public class ManagerModel {
	public static ManagerModel managerModel = null;
	public static FilesManager filesMangager = null; 
	Vector<FileObj> filesArray = null;
	
	
	
	protected ManagerModel(){
		filesMangager = FilesManager.getFilesManager();
	}


	public static ManagerModel getManagerModel() {
		
		if(managerModel == null){
			managerModel = new ManagerModel();
		}
		
		return managerModel;
	}


	public Vector<FileObj> getAllFiles() {
		
		filesArray = filesMangager.getAllFiles();
		
		return filesArray;
	}


	
	public void setChangeFilesVisibilityStatus(FileBaseComponentClass[] fileBaseComponentClasses) {
		
		filesMangager.setChangeFilesVisibilityStatus(fileBaseComponentClasses);
		}


	public void addFileToDBandIndex(File selectedFile) {
		
		filesMangager.addFileToDBandIndex(selectedFile);
		
	}
		
	}

