package final_exam;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

 public class Manager_Table extends JPanel implements ActionListener{
	 Manager manager;
	protected static DefaultTableModel defaultTableModel;
	protected static JTable table;
	protected JScrollPane jscrollPane;
	protected String rowData[][] = null;
	String [] index = {"날짜","테이블 번호","후라이드 순살","양념 순살", "간장 순살", "맥주 500cc", "맥주 1000cc", "음료수","총 금액","계산유무"};
	BorderLayout bout = new BorderLayout();
	protected JButton open = new JButton("열기");
	protected JButton  save = new JButton("저장");
	protected JPanel south = new JPanel();
	
	Date date = new Date();
	SimpleDateFormat dateFormat = new SimpleDateFormat("a hh:mm:ss"); 
	String date_info = dateFormat.format(date).toString(); 
	
	Manager_Table(){
		
		south.add(open); south.add(save);
		open.addActionListener(this); save.addActionListener(this);
		defaultTableModel = new DefaultTableModel(rowData,index);
		table = new JTable(defaultTableModel);
		
		jscrollPane = new JScrollPane(table);
		setLayout(new BorderLayout());
		add(jscrollPane,BorderLayout.CENTER);
	    add(south,BorderLayout.SOUTH);  

		
	}
	
	protected static void Delete_Order(int table_num){ 
		int num;
		int row = defaultTableModel.getRowCount();
		for(int i = 0; i < row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				String pay = (String.valueOf(defaultTableModel.getValueAt(i,9)));
				if(pay.equals("NO")){
					defaultTableModel.setValueAt("Cancel", i, 9);
				}
			}
		}
	}
	
	protected static void pay_ok(int table_num, String date){
		int num;
		int row = defaultTableModel.getRowCount();
		for(int i = 0; i < row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				if(defaultTableModel.getValueAt(i,0).equals(date)){
					defaultTableModel.setValueAt("YES", i, 9);
					break;
				}
			
			}
		}
	}
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String text = btn.getText();
		if(text.equals("열기")){
			openfile();			
		}
		
		else if(text.equals("저장")){	
			savefile();
		}	
			
			
		
	}
	
	
	protected void openfile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.showOpenDialog(this);
		ArrayList menu_ordered = new ArrayList();
		try{
			File f = fileChooser.getSelectedFile();
			BufferedReader br = new BufferedReader (new FileReader(f));
			try{
				
				StringBuilder sb = new StringBuilder();
		        String line = br.readLine();
				int num = 0;		
				
				while(line != null){ 
					line = br.readLine() ;
					if(line == null){
						break;
					}			            
		            String a = line.replaceAll("\\p{Z}", "");
					menu_ordered.add(a);
					
				}   
				
			}finally{ 
				br.close(); 
				 
			}
		}catch(IOException exception){
			}
		String [] order_data = new String [menu_ordered.size()];
		for(int i = 0; i < menu_ordered.size() ; i ++){
			order_data[i] = "";
		}
		for(int i = 0; i < menu_ordered.size() ; i ++){
			System.out.println(menu_ordered.get(i));
			order_data[i] = String.valueOf(menu_ordered.get(i));
			String order = String.valueOf(menu_ordered.get(i));
			int english = order.indexOf("NO") + order.indexOf("YES") + order.indexOf("CANCEL") +2; // 계산 여부는 NO, YES, CANCEL 중 하나이므로 +2를 해줌
 			String date = String.valueOf(order_data[i].substring(0,17));
			String date_info = modify_date(date);
			
			Object contents [] = {date_info , order_data[i].charAt(17),order_data[i].charAt(18),order_data[i].charAt(19),order_data[i].charAt(20),
								  order_data[i].charAt(21),order_data[i].charAt(22),order_data[i].charAt(23), order_data[i].substring(24,english), order_data[i].substring(english)};
			
			add_table(contents);
			
			if(contents[9].equals("NO")){
				contents[9] = 0;
			}
			if(contents[9].equals("YES")){
				contents[9] = 1;
			}
			if(contents[9].equals("CANCEL")){
				contents[9] = 2;
			}
			Manager.listadd(date_info, Integer.parseInt(String.valueOf(contents[2])), Integer.parseInt(String.valueOf(contents[3])), Integer.parseInt(String.valueOf(contents[4])),
					Integer.parseInt(String.valueOf(contents[5])), Integer.parseInt(String.valueOf(contents[6])), Integer.parseInt(String.valueOf(contents[7])), 
					Integer.parseInt(String.valueOf(contents[1])), Integer.parseInt(String.valueOf(contents[9])));
			
		}
		
	}
	
	protected void add_table(Object[] contents){
		Manager_Table.defaultTableModel.addRow(contents);		
	}
	
	protected String modify_date(String date){
		String date_info = " "+date.substring(0,5)+" "+date.substring(5,8)+" "+date.substring(8,11)+"  "+date.substring(11,14)+" "+ date.substring(14)+" ";
		return date_info;
	}
	
	
	protected void savefile(){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int x=JOptionPane.showConfirmDialog(this,"저장하시겠습니까?","저장",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
	    if(x==JOptionPane.YES_OPTION){
	    	fileChooser.showSaveDialog(this);
	        File f=fileChooser.getSelectedFile();
	        FileWriter fw;
        	BufferedWriter bw;
	        int i = 0;
	        
	        try {
	        	fw= new FileWriter(f);
	        	bw = new BufferedWriter(fw);;
	        	String first = String.format("%16s%16s%8s%6s%6s%10s%11s%6s%5s%8s","날짜","테이블번호","후라이드순살","양념순살","간장순살","맥주500cc","맥주1000cc","음료수","총금액","계산유무");
				bw.write(first);
				bw.newLine();
				try{
					for(int a = 0; a < Manager.order.size(); a ++){
	        	 
	        			  bw.write(Manager.order.get(a).date);
	        			  bw.write(String.format("%6s",String.valueOf(Manager.order.get(a).table_num)));
	        			  bw.write(String.format("%14s",String.valueOf(Manager.order.get(a).sunsal)));
	        			  bw.write(String.format("%12s",String.valueOf(Manager.order.get(a).yangnum)));
	        			  bw.write(String.format("%10s",String.valueOf(Manager.order.get(a).ganjang)));
	        			  bw.write(String.format("%10s",String.valueOf(Manager.order.get(a).beer_500)));
	        			  bw.write(String.format("%13s",String.valueOf(Manager.order.get(a).beer_1000)));
	        			  bw.write(String.format("%11s",String.valueOf(Manager.order.get(a).beberage)));
	        			  bw.write(String.format("%10s",String.valueOf(Manager.total_sum(a))));
	        			  
	        			  if(String.valueOf(Manager.order.get(a).cash_finish).equals("0")){
	        				  System.out.println("0이다");
	        				  bw.write(String.format("%10s","NO"));
	        			  }
	        			  if(String.valueOf(Manager.order.get(a).cash_finish).equals("1")){
	        				  System.out.println("1이다");
	        				  bw.write(String.format("%10s","YES"));  
	        			  }
	        			  if(String.valueOf(Manager.order.get(a).cash_finish).equals("2")){
	        				  System.out.println("2이다");
	        				  bw.write(String.format("%10s","CANCEL"));  
	        			  }
	        			  
        				  bw.flush();
	        			  bw.newLine();
	        			  
	        		  
	        		  }
	        		 
	        		}catch(IOException exception){
	        			  
	        		  }
				bw.close();
	        } catch (IOException exception) {
			}
	     
	        }
	        
	}
	

	public static int count(int table_num) {
		String sum="";
		int num;
		int row = defaultTableModel.getRowCount();
		
		for(int i = 0; i < row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				String pay = (String.valueOf(defaultTableModel.getValueAt(i,9)));
				if(pay.equals("NO")){
					sum = String.valueOf(defaultTableModel.getValueAt(i, 8));
				}
			}
		}
		int total_sum = Integer.parseInt(sum);
		return total_sum;
	}

	public static void modify(int table_num, String date) {
		int num;
		int row = defaultTableModel.getRowCount();
		
		for(int i = 0; i <= row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				if(defaultTableModel.getValueAt(i,0).equals(date) && defaultTableModel.getValueAt(i,9).equals("NO")){
					defaultTableModel.removeRow(i);
					break;
				}
				
			}
		}
	}



}
 
	
	