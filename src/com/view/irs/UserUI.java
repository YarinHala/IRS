package com.view.irs;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.widgets.Text;

import com.controller.irs.UserController;


public class UserUI {

		public static  UserController  userController = null;
	public static UserUI userView = null;
	protected Shell shell;
	private Text text;

	/**
	 * Launch the application.
	 * @param args
	 */
	public UserUI(){
		userController = UserController.getUserController();
	}
	
	public static UserUI getUserUI(){
		if(userView == null){
			userView = new UserUI();
		}
		return userView;
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		shell = new Shell();
		shell.setSize(950, 620);
		shell.setText("SWT Application");
		
		Button btnLoginManger = new Button(shell, SWT.NONE);
		btnLoginManger.setBounds(319, 10, 93, 25);
		btnLoginManger.setText("Login manger");
		btnLoginManger.addControlListener(new ControlListener() {
			
			@Override
			public void controlResized(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		text = new Text(shell, SWT.BORDER);
		text.setBounds(23, 59, 389, 21);
		
		Button btnSearch = new Button(shell, SWT.NONE);
		btnSearch.setBounds(179, 95, 75, 25);
		btnSearch.setText("Search");

	}
}
