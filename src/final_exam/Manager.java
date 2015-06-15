package final_exam;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Manager {
	
	protected String [] menupan = {"순살 후라이드","양념 순살","간장 순살", "맥주 500cc","맥주 1000cc","음료수"};
	protected int [] menu_howmany = {0,0,0,0,0,0};
	protected static int [] price = {16000, 18000, 17000, 4000, 7000, 1000};
	protected int [] sum;
	protected static ArrayList<Manager> order = new ArrayList<Manager>();
	
	int sunsal;
	int yangnum;
	int ganjang;
	int beer_500;
	int beer_1000;
	int beberage;
	int table_num;
	int cash_finish;
	String date;
	
	Manager(String date,int sunsal , int yangnum , int ganjang , int beer_500, int beer_1000, int beberage,int table_num,int cash_finish){
		this.date = date;
		this.sunsal = sunsal;		
		this.yangnum = yangnum;
		this.ganjang = ganjang;
		this.beer_500 = beer_500;
		this.beer_1000 = beer_1000;
		this.beberage = beberage;
		this.table_num = table_num;
		this.cash_finish = cash_finish;
	}
	
	protected static int total_sum(int num){
		int sum_1 = order.get(num).sunsal * price[0];
		int sum_2 = order.get(num).yangnum * price[1];
		int sum_3 = order.get(num).ganjang * price[2];
		int sum_4 = order.get(num).beer_500 * price[3];
		int sum_5 = order.get(num).beer_1000 * price[4];
		int sum_6 = order.get(num).beberage * price[5];		
		
		return sum_1 + sum_2 + sum_3 + sum_4 + sum_5 + sum_6;
	}
	
	
	static void delete(int num, String date){
		for(int i = 0; i < order.size(); i ++){
			if(order.get(i).table_num == num && order.get(i).cash_finish == 0 && order.get(i).date.equals(date)){
				String date_before = Manager.order.get(i).date;
				int sunsal = Integer.parseInt(String.valueOf(Manager.order.get(i).sunsal));
				int yangnum = Integer.parseInt(String.valueOf(Manager.order.get(i).yangnum));
				int ganjang = Integer.parseInt(String.valueOf(Manager.order.get(i).ganjang));
				int beer_500 = Integer.parseInt(String.valueOf(Manager.order.get(i).beer_500));
				int beer_1000 = Integer.parseInt(String.valueOf(Manager.order.get(i).beer_1000));
				int beberage = Integer.parseInt(String.valueOf(Manager.order.get(i).beberage));
				int table_num_before = Integer.parseInt(String.valueOf(Manager.order.get(i).table_num));
				Manager.order.remove(i);
				Manager.order.add(i,new Manager(date_before, sunsal,yangnum,ganjang,beer_500,beer_1000,beberage,table_num_before,2));
				break;
			}
		}
	}
	
	static void listadd(String date ,int sunsal , int yangnum , int ganjang , int beer_500, int beer_1000, int beberage, int table_num,int cash_finish){
		order.add(new Manager(date,sunsal,yangnum,ganjang,beer_500, beer_1000, beberage, table_num,cash_finish));
	}
	
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Manager> manager = new ArrayList<Manager>();
		
		
	}


	public static void pay_ok(int table_num) {
		// TODO Auto-generated method stub
		for(int i = 0; i < Manager.order.size() ; i ++){
			if(Manager.order.get(i).table_num == table_num){
				if(Manager.order.get(i).cash_finish == 0){
					String date = Manager.order.get(i).date;
					int sunsal = Integer.parseInt(String.valueOf(Manager.order.get(i).sunsal));
					int yangnum = Integer.parseInt(String.valueOf(Manager.order.get(i).yangnum));
					int ganjang = Integer.parseInt(String.valueOf(Manager.order.get(i).ganjang));
					int beer_500 = Integer.parseInt(String.valueOf(Manager.order.get(i).beer_500));
					int beer_1000 = Integer.parseInt(String.valueOf(Manager.order.get(i).beer_1000));
					int beberage = Integer.parseInt(String.valueOf(Manager.order.get(i).beberage));
					int table_num_before = Integer.parseInt(String.valueOf(Manager.order.get(i).table_num));
					Manager.order.remove(i);
					Manager.order.add(i,new Manager(date, sunsal,yangnum,ganjang,beer_500,beer_1000,beberage,table_num_before,1));
					break;
				}
				
			}
		}
		
	}

	public static void modify(int table_num) {
		for(int i = 0; i < Manager.order.size() ; i ++){
			if(Manager.order.get(i).table_num == table_num){
				if(Manager.order.get(i).cash_finish == 0){
					Manager.order.remove(i);
					break;
				}
				
			}
		}
		
	}

}
