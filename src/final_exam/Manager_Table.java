package final_exam;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
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
			
		}
		
		else if(text.equals("저장")){
			
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
 
	
	