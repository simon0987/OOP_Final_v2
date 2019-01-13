package entity;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import parser.MysqlExe;
import java.util.Calendar;
/**
 * This class record some constant value and provide some useful methods.
 * @author Jerry
 */
public class Ticket {
	public int tickettype_=0;
	public String date_="";
	public int startStation_=-1;
	public int endStation_=-1;
	public int trainNumber_=-1;
	public String seatNumber_="";
	public int orderNumber_=-1;
	public int starttime;
	public int endtime;
	private int price_=-1;
	public boolean to;//true去程false回程
	public Train train;
	
	public Ticket(int tickettype,String date,int startStation,int endStation,int trainNumber,boolean togo,int starttim,int endtim,Train train){
		starttime=starttim;
		endtime=endtim;
		tickettype_=tickettype;
		date_=date;
		startStation_=startStation;
		endStation_=endStation;
		trainNumber_= trainNumber;
		to=togo;
		this.train=train;
		price_=price(tickettype,startStation,endStation);
	}

	String setSeatNumber(int favorSeat,int orderNumber,String s){
		orderNumber_=orderNumber;
		seatNumber_=s;
		//price_=price(tickettype_,startStation_,endStation_);		
		return null;
		
	}

	private String getTrainofSeats(int favorSeat,int trainnumber,int startStation,int endStation) {
		return seatNumber_;
	}
	
	private int price(int tickettype,int startStation,int endStation) {
		
		int price1 = 2000;
		int s1 = startStation;
		int s2 = endStation;
		if (s1 < s2) {
			int tmp = s1;
			s1 = s2;
			s2 = tmp;
		}
		System.out.print(s1 + ", " + s2);
		if (tickettype == 3)
			price1 = Station.BusPrice[s1][s2];
		else
			price1 =  Station.StdPrice[s1][s2];

		if (tickettype == 2) {
			price1 /= 2;
			
		}
		if (tickettype == 1) {
			price1 = (int) (price1 *  train.student);
		}
		if (tickettype == 0) {
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			 Date date;
			try {
				date = sdf.parse(date_);
				 String u=sdf.format(date);
				 String u2=sdf.format( getDateBefore(date,5));
				 int iu = Integer.parseInt(u);
				 int iu2 = Integer.parseInt(u2);
				if (iu-iu2>=4)
					price1 = (int) (price1 *  train.early);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
		}
		
		return price1;
		
	}
	
	public int getprice() {return price_;}
	
	private Date getDateBefore(Date d,int day){  
		   Calendar now =Calendar.getInstance();  
		   now.setTime(d);  
		   now.set(Calendar.DATE,now.get(Calendar.DATE)-day);  
		   return now.getTime();  
		  }
	 
}
