package com.model.irs;



public class UserModel {

	public  static UserModel userManager = null;
	public  static FilesManager filesManager = null;
	
	protected UserModel(){
		filesManager = FilesManager.getFilesManager();
	};
	
	public static UserModel getUserModel() {
		if(userManager == null){
			userManager = new UserModel();
			
		}
			return userManager;
	}
	
	
	

	
}
