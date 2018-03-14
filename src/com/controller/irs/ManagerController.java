package com.controller.irs;

import java.io.File;
import java.util.Vector;
import com.model.irs.FileObj;
import com.model.irs.ManagerModel;
import com.view.irs.ManagerUI;
import com.view.irs.ManagerUI.FileBaseComponentClass;



	public class ManagerController {
		
	private static ManagerModel managerModel = null;
	private static ManagerController managerController = null;
	private static ManagerUI managerUI = null;
	private  FileBaseComponentClass[] fileBaseComponentClass = null;
	
	 protected ManagerController(){
		 managerModel = ManagerModel.getManagerModel();
		 managerUI = ManagerUI.getManagerUI(); 
	 };
		
	public static ManagerController getManagerController(){
		if(managerController == null){
			managerController = new ManagerController();
		}
		
		return managerController;
	}

	 public  FileBaseComponentClass[] getAllFilesToAsComponents() {
		Vector<FileObj> filesArray = null;
		fileBaseComponentClass = null;
		
		managerModel = ManagerModel.getManagerModel();
		filesArray = managerModel.getAllFiles();
		
		if(filesArray.size()> 0){
			fileBaseComponentClass = new FileBaseComponentClass[filesArray.size()];
			
			for(int i = 0 ; i < filesArray.size() ; i ++){
				fileBaseComponentClass[i] = new FileBaseComponentClass(filesArray.elementAt(i).getName() , Integer.toString(filesArray.elementAt(i).getNumber())  , filesArray.elementAt(i).getStatus() ); 
			}
			
		}
		return fileBaseComponentClass;
	}

	public void setChangeFilesVisibilityStatus(FileBaseComponentClass[] fileBaseComponentClasses) {
		
		if(fileBaseComponentClasses == null){
			return;
		}
		
		managerModel.setChangeFilesVisibilityStatus(fileBaseComponentClasses);
		
		
	}


	public void addFileToDBandIndex(File selectedFile) {
		
		if(selectedFile == null){
			return;
		}
		managerModel.addFileToDBandIndex(selectedFile);
		
	}

	
	
	
	
	
	}
