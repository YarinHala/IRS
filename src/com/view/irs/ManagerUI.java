package com.view.irs;


import com.controller.irs.ManagerController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.BevelBorder;
import java.awt.Font;
import javax.swing.JCheckBox;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import java.io.File;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;



public class ManagerUI extends JFrame{

	private static final long serialVersionUID = 1L;
	private static ManagerController managerController = null;
	private static ManagerUI managerUI =null;
	protected static JFrame ManagerBack;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JPanel filePanel;
	private static FileBaseComponentClass[] displayedFilesOnPanel = null;
	
	
	public ManagerUI(){
		
		new JFrame();
	
		setBounds(100, 100, 950, 620);
		setTitle("IRS Manager Back Office");
		getContentPane().setLayout(null);
		
		
		JLabel lblFileInventorry = new JLabel("File Inventory");
		lblFileInventorry.setBounds(271, 0, 267, 35);
		lblFileInventorry.setFont(new Font("Tahoma", Font.PLAIN, 20));
		getContentPane().add(lblFileInventorry);
		

		textField = new JTextField();
		textField.setBounds(10, 46, 568, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		JButton btnSearch = new JButton("Search");
		btnSearch.setBounds(573, 46, 134, 31);
		getContentPane().add(btnSearch);
		
		JButton btnAddFiles = new JButton("Add Files");
		btnAddFiles.setBounds(712, 46, 120, 31);
		getContentPane().add(btnAddFiles);
		btnAddFiles.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
 
					JFileChooser  chooser = null;
					if (chooser == null) {
                        chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                        chooser.setAcceptAllFileFilterUsed(false);
                        chooser.addChoosableFileFilter(new FileFilter() {
                            @Override
                            public boolean accept(File f) {
                                return f.isDirectory() || f.getName().toLowerCase().endsWith(".txt");
                            }

                            @Override
                            public String getDescription() {
                                return "Text Files (*.txt)";
                            }
                        });
                 }
                switch (chooser.showOpenDialog(null)) {
                    case JFileChooser.APPROVE_OPTION:
                    	  if(managerController == null){
   						   managerController = ManagerController.getManagerController();
                    	  }
                    	managerController.addFileToDBandIndex( chooser.getSelectedFile());
                       
						
                }
			}
		});
		
		JButton btnRefresh = new JButton("Refresh");
		btnRefresh.setBounds(10, 83, 266, 23);
		getContentPane().add(btnRefresh);
				
		JButton btnGetAllDocumenst = new JButton("Get All Documenst");
		btnGetAllDocumenst.setBounds(281, 83, 267, 23);
		getContentPane().add(btnGetAllDocumenst);/*done*/
		btnGetAllDocumenst.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					   if(managerController == null){
						   managerController = ManagerController.getManagerController();
					   }
					   displayedFilesOnPanel = managerController.getAllFilesToAsComponents();
					   setFileArrayAtPanel(managerController.getAllFilesToAsComponents());
				}
			});
		
		JButton btnCommitChanges = new JButton("Commit Changes");
		btnCommitChanges.setBounds(553, 83, 279, 23);
		getContentPane().add(btnCommitChanges);
		btnCommitChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				   if(managerController == null){
					   managerController = ManagerController.getManagerController();
				   }
				   filePanel.removeAll();
				   managerController.setChangeFilesVisibilityStatus(displayedFilesOnPanel);
				   setFileArrayAtPanel(managerController.getAllFilesToAsComponents());
			}
		});
		
		JLabel lblDocumentName = new JLabel("Document Name");
		lblDocumentName.setBounds(10, 136, 147, 14);
		getContentPane().add(lblDocumentName);

		JLabel lblDocumentNumber = new JLabel("Document Number");
		lblDocumentNumber.setBounds(400, 136, 88, 14);
		getContentPane().add(lblDocumentNumber);
		
		JLabel lblDocumentStatus = new JLabel("Document Status");
		lblDocumentStatus.setBounds(600, 136, 124, 14);
		getContentPane().add(lblDocumentStatus);
		
		
		
		scrollPane = new JScrollPane();
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setBounds(10, 164, 820, 400);
		getContentPane().add(scrollPane);
		
		filePanel = new JPanel();
		filePanel.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		filePanel.setLayout(new BoxLayout(filePanel, BoxLayout.Y_AXIS));
		
		
		
		
		/*************************************** FILE INVENTOTY  BUTTENS************************************** */
		
	
		
		
	};
	
	public static ManagerUI getManagerUI() {
		if(managerUI ==null){
			managerUI = new ManagerUI();
			managerUI.setVisible(true);
		}
		return managerUI;
	}
	
	
	/********** FUNCUALITY MANAGER INTERFACE 	 fileBaseComponentClasses *********/
	
	public Boolean setFileArrayAtPanel(FileBaseComponentClass[] fileBaseComponentClasses){
		
	
		filePanel.removeAll();
		if(fileBaseComponentClasses == null){
			return false;
		}
		for(int i= 0 ; i <fileBaseComponentClasses.length ; i++){
			filePanel.add (fileBaseComponentClasses[i]);
			
		}
		filePanel.setPreferredSize(new Dimension(800, fileBaseComponentClasses.length * 40));
		scrollPane.setViewportView(filePanel);
		this.setVisible(true);
		return true;
	}
	

	/********** BASE COMPSITE FILE COMPONENT *********/
	
	static public class FileBaseComponentClass  extends JPanel{
		
	
		private static final long serialVersionUID = 1L;
		
		
		//public JPanel panel;
		public JLabel lblFileName;
		public JLabel lblNumber ;
		public JLabel lblStatus;
		public JCheckBox showHideCheckButton;
		public boolean isVisible = true;
		
		public FileBaseComponentClass(String fileName, String fileNumber, String showHideStatus){	
	
			setBounds(10, 500, 820, 40);
			setLayout(null);
			
			 lblFileName = new JLabel(fileName);
			 lblFileName.setBounds(10, 11, 355, 19);
			 lblFileName.setFont(new Font("Tahoma", Font.PLAIN, 15));
			add(lblFileName);
			
			 lblNumber = new JLabel(fileNumber);
			 lblNumber.setFont(new Font("Tahoma", Font.PLAIN, 15));
			lblNumber.setBounds(398, 15, 64, 14);
			add(lblNumber);
			
			lblStatus = new JLabel(showHideStatus);
			lblStatus.setBounds(601, 15, 46, 14);
			add(lblStatus);
			
			showHideCheckButton = new JCheckBox(showHideStatus);
			showHideCheckButton.setBounds(682, 11, 20, 23);
			add(showHideCheckButton);
			showHideCheckButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(displayedFilesOnPanel == null){
						return;
					}
					for(int i = 0 ; i < displayedFilesOnPanel.length ; i++){
						if(displayedFilesOnPanel[i].getFileNameComponent().compareTo(getFileBaseComponent().getFileNameComponent())  == 0 ){
							displayedFilesOnPanel[i].setChangeStatusOfFileComponent();
						}
					}
					
				}
			});
			
		}
		
		public  FileBaseComponentClass getFileBaseComponent(){
			return this;
		}
		public String getFileNameComponent(){
			return lblFileName.getText();
		}
		public void setChangeStatusOfFileComponent(){
			if( isVisible == false){
				isVisible = true;
			}else
			{
				isVisible = false;				
			}
		}
		public boolean getChangeStatusOfFileComponent(){
			return isVisible;
		}
		public int getNumberOfFileComponent(){
			return Integer.parseInt(lblNumber.getText());
		}
		
	
	}
	
	
	
	
	
	


	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}










