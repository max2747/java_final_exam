package final_exam;

import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;

public class Manager {
	
	protected String [] menupan = {"순살 후라이드","양념 순살","간장 순살", "맥주 500cc","맥주 1000cc","음료수"};
	protected int [] menu_howmany = {0,0,0,0,0,0};
	protected int [] price = {16000, 18000, 17000, 4000, 7000, 1000};
	protected int [] sum;
	protected static ArrayList<Manager> order = new ArrayList<Manager>(5);
	
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
	
	
	
	static void delete(int num){
		for(int i = 0; i < order.size(); i ++){
			if(order.get(i).table_num == num && order.get(i).cash_finish == 0){
				order.remove(i);
			}
		}
	}

	static void listadd(String date,int sunsal , int yangnum , int ganjang , int beer_500, int beer_1000, int beberage, int table_num,int cash_finish){
//		Manager man = new Manager(sunsal,yangnum,ganjang,beer_500, beer_1000, beberage,table_num);
		order.add(new Manager(date,sunsal,yangnum,ganjang,beer_500, beer_1000, beberage, table_num,cash_finish));
	}
	
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		ArrayList<Manager> manager = new ArrayList<Manager>();
		
		
	}



	public static void pay_ok() {
		// TODO Auto-generated method stub
		
	}

}
