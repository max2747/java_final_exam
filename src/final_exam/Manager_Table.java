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
		int column = defaultTableModel.getColumnCount();
		System.out.println("row : "+row);
		System.out.println("column : " +column);
		
		
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
	
	protected static void pay_ok(int table_num){
		int num;
		int row = defaultTableModel.getRowCount();
		for(int i = 0; i < row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				String pay = (String.valueOf(defaultTableModel.getValueAt(i,9)));
				if(pay.equals("NO")){
					defaultTableModel.setValueAt("YES", i, 9);
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
						//sb.append(line);					
			            //sb.append(System.lineSeparator());
						line = br.readLine() ;
						if(line == null){
							break;
						}			            
			            String a = line.replaceAll("\\p{Z}", "");
						menu_ordered.add(a);
						System.out.println("a 출력 : " +a);
						
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
				int english = order.indexOf("NO") + order.indexOf("YES") + order.indexOf("CANCEL") +2;
				System.out.println("order.indexOf(NO): " +order.indexOf("NO"));
				System.out.println("order.indexOf(YES): " +order.indexOf("YES"));
				System.out.println("order.indexOf(CANCEL): " +order.indexOf("CANCEL"));
				System.out.println("english : " +english);
				Object contents [] = {order_data[i].substring(0,17) , order_data[i].charAt(17),order_data[i].charAt(18),order_data[i].charAt(19),order_data[i].charAt(20),
									  order_data[i].charAt(21),order_data[i].charAt(22),order_data[i].charAt(23), order_data[i].substring(24,english), order_data[i].substring(english)};
				for(int j = 0; j < contents.length ; j ++){
					System.out.println("contents" +j +"번 : " +contents[j]);
				}
				Manager_Table.defaultTableModel.addRow(contents);
				// menu_ordered - 날짜 , 테이블번호, 후라이드순살, 양념순살, 간장순살, 맥주 500cc, 맥주 1000cc, 음료수, 총금액, 계산유무
				//listadd - 날짜 ,int sunsal , int yangnum , int ganjang , int beer_500, int beer_1000, int beberage, int table_num,int cash_finish{}
				if(contents[9].equals("NO")){
					contents[9] = 0;
				}
				if(contents[9].equals("YES")){
					contents[9] = 1;
				}
				if(contents[9].equals("CANCEL")){
					contents[9] = 2;
				}
				Manager.listadd(String.valueOf(contents[0]), Integer.parseInt(String.valueOf(contents[2])), Integer.parseInt(String.valueOf(contents[3])), Integer.parseInt(String.valueOf(contents[4])),
						Integer.parseInt(String.valueOf(contents[5])), Integer.parseInt(String.valueOf(contents[6])), Integer.parseInt(String.valueOf(contents[7])), 
						Integer.parseInt(String.valueOf(contents[1])), Integer.parseInt(String.valueOf(contents[8])), Integer.parseInt(String.valueOf(contents[9])));
				//String split[] = menu_ordered[i].split(" ");
				
				
				
				//Object[] contents = {split[1], split[2], split[5], split[4], split[0]};			
				//sco_UI.defaultTableModel.addRow(contents);
				
				
			}
			System.out.println("메뉴오더 : " +menu_ordered.size());
			
			
		}
		
		else if(text.equals("저장")){
			
			
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			int x=JOptionPane.showConfirmDialog(this,"저장하시겠습니까?","저장",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE);
		    if(x==JOptionPane.YES_OPTION){
		    	fileChooser.showSaveDialog(this);
		        File f=fileChooser.getSelectedFile();
		        FileWriter fw;
	        	BufferedWriter bw;
		        int i = 0;
		        
		    	//String [] index = {"날짜","테이블 번호","후라이드 순살","양념 순살", "간장 순살", "맥주 500cc", "맥주 1000cc", "음료수","총 금액","계산유무"};
		        try {
		        	fw= new FileWriter(f);
		        	bw = new BufferedWriter(fw);;
		        	String first = String.format("%16s%16s%8s%6s%6s%10s%11s%6s%5s%7s","날짜","테이블번호","후라이드순살","양념순살","간장순살","맥주500cc","맥주1000cc","음료수","총금액","계산유무");
					bw.write(first);
					bw.newLine();
					for(int a = 0; a < Manager.order.size(); a ++){
		        	 
		        		  try{
		        			  
		        			  bw.write(Manager.order.get(a).date);
		        			 // bw.flush();
		        			  bw.write(String.format("%6s",String.valueOf(Manager.order.get(a).table_num)));
		        			  //bw.flush();
		        			  bw.write(String.format("%14s",String.valueOf(Manager.order.get(a).sunsal)));
		        			  //bw.flush();
		        			  bw.write(String.format("%12s",String.valueOf(Manager.order.get(a).yangnum)));
		        			  //bw.flush();
		        			  bw.write(String.format("%10s",String.valueOf(Manager.order.get(a).ganjang)));
		        			  //bw.flush();
		        			  bw.write(String.format("%10s",String.valueOf(Manager.order.get(a).beer_500)));
		        			  //bw.flush();
		        			  bw.write(String.format("%13s",String.valueOf(Manager.order.get(a).beer_1000)));
		        			  //bw.flush();
		        			  bw.write(String.format("%11s",String.valueOf(Manager.order.get(a).beberage)));
		        			  //bw.flush();
		        			  bw.write(String.format("%10s",String.valueOf(Manager.total_sum(a))));
		        			  //bw.flush();
		        			  if(Manager.order.get(i).cash_finish == 0){
		        				  bw.write(String.format("%10s","NO"));  
		        			  }
		        			  if(Manager.order.get(i).cash_finish == 1){
		        				  bw.write(String.format("%10s","YES"));  
		        			  }
		        			  if(Manager.order.get(i).cash_finish == 2){
		        				  bw.write(String.format("%10s","CANCEL"));  
		        			  }
		        			  
		        			  bw.flush();
		        			  bw.newLine();
		        			  
		        			  System.out.println(Manager.order.get(a).table_num);
		        			  System.out.println(Manager.order.get(a).sunsal);
		        			  System.out.println(Manager.order.get(a).yangnum);
		        			  
		        		   //String str = data.get(i);
		        		  // fw.write(String.valueOf(savedata));
		        		  
		        		  }catch(IOException exception){
		        			  
		        		  }
		        		 
		        		}
					//bw.flush();
					bw.close();
		        } catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		     
		        }
		        
		}
		
	}

	public static int count(int table_num) {
		String sum="";
		int num;
		int row = defaultTableModel.getRowCount();
		int column = defaultTableModel.getColumnCount();
		
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

	public static void modify(int table_num) {
		String sum="";
		int num;
		int row = defaultTableModel.getRowCount();
		int column = defaultTableModel.getColumnCount();
		int a = 0;
		
		for(int i = 0; i < row ; i ++){
			num = Integer.parseInt(String.valueOf(defaultTableModel.getValueAt(i,1)) );
			if(num == table_num){
				if(a == 0){
					a++;
					defaultTableModel.removeRow(i);
					break;
				}
				
				a =0;
			}
		}
	}



}
 
	
	