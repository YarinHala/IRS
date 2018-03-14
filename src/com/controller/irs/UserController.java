package com.controller.irs;

import com.model.irs.UserModel;

public class UserController {

	public static UserModel userModel = null;
	public static UserController userController = null;
	
	
	 protected UserController(){
		 userModel = UserModel.getUserModel();
	 };
		
	public static UserController getUserController(){
		if(userController == null){
			userController = new UserController();
		}
		
		return userController;
	}
	
	
	
	
	
	
	
	
}
