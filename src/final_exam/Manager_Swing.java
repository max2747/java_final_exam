package final_exam;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;

import final_exam.Manager_Swing.table_manager;

public class Manager_Swing extends JFrame{
	Container c;
	Manager manager;
	
	Manager_Swing(){ //ù ȭ��
		setTitle("���� �Ŵ���");
		c = getContentPane();
		JTabbedPane pane = createTabbedPane();
		c.add(pane);
		
		setSize(1800,1000);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	
	JTabbedPane createTabbedPane() {
		JTabbedPane pane = new JTabbedPane(JTabbedPane.LEFT); // �� ���⼭ ����
		pane.addTab("�ֹ� ó��", new table_manager());
		pane.addTab("�ֹ���Ȳ", new Manager_Table());
		pane.addTab("tab3", new JLabel());
		return pane;
	}
	
	
	static class  table_manager extends JPanel implements ActionListener{
		static ArrayList<JButton> table = new ArrayList<JButton>(); //��ư ���� ����Ʈ
		static int [] table_switch = new int[8]; //�ֹ��� ���¸� ����ġ 1�� �ٲ��ش�.
		table_manager(){
			
			for(int i = 1 ; i <= 8 ; i ++){ //��ư �ʱ�ȭ �� �߰�
				JButton table_num = new JButton(i+"�� ���̺�");
				table_num.addActionListener(this);
				add(table_num);
				table.add(table_num);
				table_switch[i-1] = 0;
			}
			setLayout(new GridLayout(2, 4 , 20 , 20));
			
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton b = (JButton)e.getSource();
			String table = b.getText();
			System.out.println(table);			
			try{
				String table_num = String.valueOf(b.getText().charAt(0));
				if(table_manager.table_switch[Integer.parseInt(table_num)-1] == 0){
					new Menupan(this, table +"�� �޴��� �Դϴ�.",Integer.parseInt(table_num));
				}
			}catch(Exception exception){
				String table_num = String.valueOf(b.getText().charAt(6));
				new popupmenu(Integer.parseInt(table_num));
			}
						
		}

	}	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Manager_Swing();
		
	}

}
class popupmenu extends JPopupMenu implements ActionListener{ // �ֹ��� �Ϸ�� ���̺��� Ŭ���Ҷ� ����Ǵ� �޴�
	JMenuItem item;
	int table_num;
	
//	static void setTable_Num(int tablenum){
//		popupmenu.table_num = tablenum;
//	}
	popupmenu(int table_num){
		menu_popup.a = 1;
		this.table_num = table_num;

		
		JMenuItem delete = new JMenuItem("����");
		JMenuItem change = new JMenuItem("����");
		JMenuItem count = new JMenuItem("���");
		
		delete.addActionListener(this);change.addActionListener(this);count.addActionListener(this);
		add(count); add(change); add(delete);  
		//setBorder(new BevelBorder(BevelBorder.RAISED));
		show(table_manager.table.get(table_num-1),table_manager.table.get(table_num-1).getWidth()/2,table_manager.table.get(table_num-1).getHeight()/2);
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		JMenuItem c = (JMenuItem)e.getSource();
		String choice = c.getText();
		System.out.println(choice);
		if(choice.equals("����")){ //���߿� JTable������ �ֹ� ��Ҷ�� ǥ��
			table_manager.table_switch[table_num - 1] = 0;
			table_manager.table.get(table_num - 1).setText(table_num+"�� ���̺�");
			Manager.delete(table_num );
			Manager_Table.Delete_Order(table_num);
		}
		else if(choice.equals("���")){//������ ������ ���߿� JTable���� ��� �Ϸ�� ǥ����.
			int pay_sum = Manager_Table.count(table_num);
			new pay(pay_sum,table_num);
		}
		
		else if(choice.equals("����")){
			Menupan modify = new Menupan(null,"�޴��� �����մϴ�.",table_num);
			modify.a = 1;
			Manager_Table.modify(table_num);
		}
	}
}

class pay extends JDialog implements ActionListener{
	int pay_sum;
	int table_num;
	JLabel label = new JLabel("���� �ݾ��� �Է��Ͻÿ�(�ݾ��� ���ų� ���ڸ� �Է��ϸ� �ȵ˴ϴ�.)");
	JTextArea custom_money = new JTextArea(2,10);
	JButton btn_ok = new JButton("OK");
	JButton btn_cancel = new JButton("Cancel");
	JPanel center = new JPanel();
	JPanel south = new JPanel();
	
	pay(int pay_sum, int table_num){
		this.pay_sum = pay_sum;
		this.table_num = table_num;
		setLayout(new BorderLayout());
		center.setLayout(new BorderLayout());
		center.add(label,BorderLayout.CENTER); center.add(custom_money,BorderLayout.SOUTH);
		south.add(btn_ok); south.add(btn_cancel);
		btn_ok.addActionListener(this); btn_cancel.addActionListener(this);
		add(center,BorderLayout.CENTER); add(south,BorderLayout.SOUTH);
		
		
		setSize(250,180);
		setTitle("����ϴ� â");
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton btn = (JButton) e.getSource();
		String text = btn.getText();
		if(text.equals("OK")){
			try{
				int sum = Integer.parseInt(custom_money.getText());
				if(sum >= pay_sum){
					table_manager.table_switch[table_num - 1] = 0;
					table_manager.table.get(table_num - 1).setText(table_num+"�� ���̺�");
					Manager.pay_ok();
					Manager_Table.pay_ok(table_num);
					dispose();
				}
				else{
					custom_money.setText("");
					return;
				}
				
			}
			catch(Exception exception){
				custom_money.setText("");
				return;
			}
		}
		
		else if(text.equals("Cancel")){
			dispose();
			
		}
		
	}
	
}	

class Menupan extends JDialog implements ActionListener{
	
	 // ó������ ���̺���  �ֹ������� ��.
	static int menu_howmany = 0; //�� �޴��� ������ ������ ����. �ʱ�ȭ
	Manager manager; 

	JButton btn_ok = new JButton("OK");
	String [] menu = {"�Ķ��̵� ����(16,000)","��� ����(18,000)", "���� ����(17,000)", "���� 500cc(4,000)", "���� 1000cc(7,000)", "�����(1,000)"}; //�޴� ����
	int [] menu_price = {16000, 18000, 17000, 4000,7000, 1000}; //�޴� ����
	JButton btn_cancel = new JButton("Cancel");
//	JTextArea sum = new JTextArea("�� �հ� ",2, 10);	
	
	JPanel center = new JPanel();
	JPanel south = new JPanel();	
	int table_num; //���̺� ��ȣ �����ϱ� ���� ����
	int a = 0; //a=1�� �Ǵ°� ���������� ��� �ݿ�
	
	static ArrayList<JTextArea> area = new ArrayList<JTextArea>(); //�޴��� ������ �Է¹޾� ����� JTextArea ����Ʈ
	
	Menupan(table_manager table , String title, int table_num){
		setTitle(title);
		this.table_num = table_num;
		if(area.size() != 0){
			area.clear();
		}
		for(int i = 0; i < menu_price.length ; i ++){ //�޴� ���� JTextArea �ʱ�ȭ
			JTextArea num = new JTextArea("0", 2, 10);
			num.setEditable(false);
			area.add(num);

		}
		
//		sum.setText("0"); //�հ� ĭ
//		sum.setEditable(false);
		center.setLayout(new GridLayout(6 ,2, 5, 5));
		for(int i = 1 ; i <= 6 ; i ++){
			JButton btn_i = new JButton(menu[i-1]);
			btn_i.addActionListener(this);
			center.add(btn_i);			
			center.add(area.get(i-1));
			
		}
		setLayout(new BorderLayout());

		
		south.add(btn_ok); south.add(btn_cancel); //south.add(sum); //�հ� ����
		btn_ok.addActionListener(this);btn_cancel.addActionListener(this);
		setModal(true);
		add(center,BorderLayout.CENTER);
		add(south,BorderLayout.SOUTH);
		setSize(400,300);
		setVisible(true);
		
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		setModal(false);
		JButton b = (JButton)e.getSource();
		String btn_text = b.getText();
		if(btn_text.equals("�Ķ��̵� ����(16,000)")){
			int menu_num = 1;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(0).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		else if(btn_text.equals("��� ����(18,000)")){
			int menu_num = 2;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(1).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		else if(btn_text.equals("���� ����(17,000)")){
			int menu_num = 3;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(2).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		else if(btn_text.equals("���� 500cc(4,000)")){
			int menu_num = 4;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(3).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		else if(btn_text.equals("���� 1000cc(7,000)")){
			int menu_num = 5;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(4).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		else if(btn_text.equals("�����(1,000)")){
			int menu_num = 6;
			new menu_popup(menu_num,this, btn_text+"�� ������ �Է��ϼ���");
			setModal(true);
			area.get(5).setText(String.valueOf(Menupan.menu_howmany));
			btn_text="";
		}
		
		else if(btn_text.equals("OK")){
			Date date = new Date();
			SimpleDateFormat dateFormat = new SimpleDateFormat(" yyyy�� MM�� dd��  HH�� mm�� "); 
			String date_info = dateFormat.format(date).toString();
			if(a == 1){
				
			}
			int [] order = new int [7]; //�� �޴� ������ �迭
			for(int i = 0; i <6 ; i ++){
				order[i] = Integer.parseInt(area.get(i).getText());
			}
			order[6] = table_num; //������ ���̺� ��ȣ�� ����
			manager.listadd(date_info,order[0], order[1], order[2],order[3],order[4],order[5],order[6],0);
			//show_order(table_num);//�Ķ��̵� ����","��� ����", "���� ����", "���� 500cc", "���� 1000cc", "�����
			int total_sum = 0;
			for(int i = 0; i < menu_price.length ; i ++){
				int menu = order[i] * menu_price[i];
				total_sum = menu+total_sum;
			}
			table_manager.table.get(table_num - 1).setText("<html>"+table_num+"�� ���̺� �ֹ����� <br/> <br/>" +"�Ķ��̵� ���� : "+order[0]+"<br/>"
			+"��� ���� : "+order[1] +"<br/>" +"���� ���� : "+order[2] +"<br/>"+"���� 500cc : "+order[3] +"<br/>"+	"���� 1000cc : "+order[4] +"<br/>"
			+"����� : "+order[5] +"<br/>"+"�� �ݾ� : "+total_sum+"<br/>"+date_info +"</html>"); //��ư��  �ؽ�Ʈ �ٲ�
			for(int i = 0; i < area.size() ; i ++){
				area.get(i).setText("");
			}
			
			
			//String [] index = {"��¥","���̺� ��ȣ","�Ķ��̵� ����","��� ����", "���� ����", "���� 500cc", "���� 1000cc", "�����","�� �ݾ�","�������"};
			
			Object[] contents = {date_info, table_num, order[0], order[1],order[2],order[3],order[4],order[5], total_sum, "NO"};			
			Manager_Table.defaultTableModel.addRow(contents);
			//Manager_Table.clearFields();
			table_manager.table_switch[table_num-1] = 1;
			
			dispose();
		}
		else if(btn_text.equals("Cancel")){
			
			dispose();
		}
		
	}


	private void show_order(int table_num) {
		System.out.println(table_num);
		
	}


	
}

class menu_popup extends JDialog implements ActionListener{ //�޴� ���� �Է¹޴� â
	JPanel north = new JPanel();
	JPanel center = new JPanel();
	JPanel south = new JPanel();
	String [] num = {"7","8","9","4","5","6","1","2","3","0","�ʱ�ȭ","���ϱ�"};
	JTextArea show_num = new JTextArea("0",6,20);
	JButton btn_ok = new JButton("OK");
	JButton btn_cancel = new JButton("cancel");
	int before_num = 0;
	int menu_num;
	static int a = 0;

	menu_popup(int menu_num,Menupan menupan,String title){
		this.menu_num = menu_num;
		setTitle(title);
		setLayout(new BorderLayout());
		center.setLayout(new GridLayout(4,3, 6, 6));
		for(int i = 0; i < num.length ;i ++){
			JButton btn_i = new JButton(num[i]);
			btn_i.addActionListener(this);
			center.add(btn_i);
		}
		
		show_num.setEditable(false);
		north.add(show_num);
		
		south.add(btn_ok); south.add(btn_cancel);
		btn_ok.addActionListener(this); btn_cancel.addActionListener(this);
		setModal(true);
		add(center,BorderLayout.CENTER); add(south,BorderLayout.SOUTH); add(north,BorderLayout.NORTH);
		
		setSize(500,400);
		setVisible(true);
		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int num = Integer.parseInt(show_num.getText());
		JButton btn_num = (JButton) e.getSource();
		
		try{			
			num = Integer.parseInt(btn_num.getText());
			if(before_num != 0){				
				show_num.setText(String.valueOf(before_num + num));
				before_num = 0;
				return;
			}
			
			show_num.setText(String.valueOf(num));
			
		}catch(Exception exception){
			String text = btn_num.getText();
			if(text. equals("�ʱ�ȭ")){
				show_num.setText("0");
			}
			else if( text.equals("���ϱ�")){
				before_num = num;
				return;				

			}
			else if(text.equals("OK")){
				
				System.out.println("a : "+a);
				Menupan.menu_howmany = Integer.parseInt(show_num.getText());

				dispose();
				
			}
			else if(text.equals("cancel")){
				dispose();
			}
			
				
			}
		}
		
}

