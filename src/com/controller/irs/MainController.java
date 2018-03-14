package com.controller.irs;
import java.awt.EventQueue;

import com.view.irs.ManagerUI;


public class MainController {
	

	private static MainController mainController =null;
	//private static UserController userController = null;
	private static ManagerController managerController = null;
	private static ManagerUI  managerUI = null;
	
	
		public static void main(String[] args) {
				
			EventQueue.invokeLater(new Runnable() {
				public void run() {
					try {
						
						mainController = getMainController();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			
			

		}

		protected MainController(){
			managerController = ManagerController.getManagerController();
		}
		
		public static MainController getMainController(){
			if(mainController == null){
				mainController = new MainController();
			}
			
			return mainController;
			
		}
		
	}