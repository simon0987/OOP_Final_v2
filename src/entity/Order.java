package entity;
import java.util.*;
import dao.TicketDAO;
public class Order {
	private int OrderNumber;
	private String userID;
	private int howmanytickets;
	private int favorseat;
	private ArrayList <ticket> tickets = new ArrayList<ticket>();
	//0 隨機 1靠窗2靠走道
	public Order(String userID_,int howmanyTickets,int Tickettype,int startStation,int endStation,Train train,Train train2,int seat,boolean toback,int orderNumber){//ture有回來false單程
		//OrderNumber=getOrderNumberFromBase(); //in interface.
		String date=""+train.date;
		int trainNumber=train.train_id;
		this.favorseat=seat;
		OrderNumber=orderNumber;
		userID=userID_;
		
		if(toback==true) {
			String date2=""+train2.date;
			int trainNumber2=train2.train_id;
			howmanytickets=howmanyTickets*2;
			for(int i=0;i<howmanyTickets*2;i++) {
				if(i<howmanyTickets) {
					tickets.add(new ticket(Tickettype,date,startStation,endStation,trainNumber,true,train.t1,train.t2,train));
				}
				else tickets.add(new ticket(Tickettype,date2,startStation,endStation,trainNumber2,false,train2.t1,train2.t2,train2));
			}
		}
		else {
			howmanytickets=howmanyTickets;
			for(int i=0;i<howmanyTickets;i++) {	
				tickets.add(new ticket(Tickettype,date,startStation,endStation,trainNumber,true,train.t1,train.t2,train));			
			}
		}
	}
	
	public static ArrayList<Integer> getOrderNum(String uid, int train_id, int date, int start, int end) {
		return new TicketDAO().getOrderNumberFromBase(uid,train_id,date,start,end);
	}
	
	public int getTotalPrice() {
		int price=0;
		for(ticket tmp: tickets) {
			price+=tmp.getprice();
		}
		return price;
	}
	
	public boolean deleteTicket(int index) {
		try {
		tickets.remove(index);
		howmanytickets-=1;
		return true;
		//call delete from database.//
		}
		catch(Exception e){
			return false;
		}
	}
	
/*	public static Order getOrder(String ordernumber,String userID) {
		try {
			ArrayList<ticket>ticketstmp=new TicketDAO().getTicketsfromBase(ordernumber);		
			if(ticketstmp.get(ticketstmp.size()-1).to==true)
				return new Order(userID,ticketstmp.size(),ticketstmp.get(0).tickettype_,ticketstmp.get(0).startStation_,ticketstmp.get(0).endStation_,
						ticketstmp.get(0).date_,ticketstmp.get(0).date_,ticketstmp.get(0).trainNumber_,ticketstmp.get(0).trainNumber_,false);//new Order("rID_",5,"String Tickettype","String date",5,5,5);
			else 
				return new Order(userID,ticketstmp.size()/2,ticketstmp.get(0).tickettype_,ticketstmp.get(0).startStation_,ticketstmp.get(0).endStation_,
						ticketstmp.get(0).date_,ticketstmp.get(ticketstmp.size()-1).date_,ticketstmp.get(0).trainNumber_,ticketstmp.get(ticketstmp.size()-1).trainNumber_,true);
		}
		catch(Exception e) {
			return null;//new Order("rID_",5,"String Tickettype","String date",5,5,5);
		}
	}*/
	
	public static boolean deleteOrder(String ordernumber,String userID) {
		try {
			//get specific order from database//
			new TicketDAO().deleteOrderfromBase(ordernumber,userID);
			return true;
		}
		catch(Exception e) {
			return false;
		}
	}
	
	
	public ArrayList<String> setTicketSeat() {
		ArrayList<Integer> tknum =new ArrayList<Integer>();
		ArrayList<String> tkdate=new ArrayList<String>();
		ArrayList<Integer> tktype=new ArrayList<Integer>();
		ArrayList<Integer> tkstart =new ArrayList<Integer>();
		ArrayList<Integer> tkend =new ArrayList<Integer>();
		ArrayList<String> tkseatnum=new ArrayList<String>();
		TicketDAO tmpticket=new TicketDAO();
		for(ticket tmp: tickets) {
			tknum.add(tmp.trainNumber_);
			tkdate.add(tmp.date_);
			tktype.add(tmp.tickettype_);
			tkstart.add(tmp.startStation_);
			tkend.add(tmp.startStation_);
		}
		int howlong=tknum.size();
		boolean flag=true;
		System.out.println(favorseat);
		for(int k=0;k<howlong;k++) {
			flag=true;
			for(int i=0;i<9;i++) {
				if(flag==false)break;
				for(int j=0;j<Train.VALID_SEATS[i].length;j++) {
					if(favorseat==0) {
						if(tmpticket.checkSeat("0"+i+Train.VALID_SEATS[i][j], tknum.get(k),tkdate.get(k))==false&&flag==true) {
							System.out.println(2);
							tmpticket.bookfromBase(OrderNumber,userID,howmanytickets,tktype.get(k),tkstart.get(k),tkend.get(k),tkdate.get(k),tknum.get(k),"0"+i+Train.VALID_SEATS[i][j]);
							tkseatnum.add("0"+i+Train.VALID_SEATS[i][j]);
							flag=false;
						}	
					}
					
					if(Train.VALID_SEATS[i][j].substring(Train.VALID_SEATS[i][j].length()-1)=="A"||Train.VALID_SEATS[i][j].substring(Train.VALID_SEATS[i][j].length() -1)=="E") {
						if( favorseat!=1||tmpticket.checkSeat("0"+i+Train.VALID_SEATS[i][j], tknum.get(k),tkdate.get(k))==true) {
							;
						}
						else if(favorseat==1||tmpticket.checkSeat("0"+i+Train.VALID_SEATS[i][j], tknum.get(k),tkdate.get(k))==false){
							if(flag==true)  {
								System.out.println(1);
								tmpticket.bookfromBase(OrderNumber,userID,howmanytickets,tktype.get(k),tkstart.get(k),tkend.get(k),tkdate.get(k),tknum.get(k),"0"+i+Train.VALID_SEATS[i][j]);
								tkseatnum.add("0"+i+Train.VALID_SEATS[i][j]);
								flag=false;
							}
						}
					}
					else if (Train.VALID_SEATS[i][j].substring(Train.VALID_SEATS[i][j].length()-1)=="C"||Train.VALID_SEATS[i][j].substring(Train.VALID_SEATS[i][j].length()-1)=="D") {
						if( favorseat!=2||tmpticket.checkSeat("0"+i+Train.VALID_SEATS[i][j], tknum.get(k),tkdate.get(k))==true) {
							;
						}
						else if(favorseat==2||tmpticket.checkSeat("0"+i+Train.VALID_SEATS[i][j], tknum.get(k),tkdate.get(k))==false){
							if(flag==true)	{
								System.out.println(2);
								tmpticket.bookfromBase(OrderNumber,userID,howmanytickets,tktype.get(k),tkstart.get(k),tkend.get(k),tkdate.get(k),tknum.get(k),"0"+i+Train.VALID_SEATS[i][j]);
								tkseatnum.add("0"+i+Train.VALID_SEATS[i][j]);
								
								flag=false;
							}
						}
					}
				}
			}
		}
		if(tkseatnum.isEmpty()) {
			tkseatnum.add("0");
			return tkseatnum;
		}
		int count=0;
		for(String s: tkseatnum) {
			System.out.println(s);
		}
		for(ticket tmp: tickets) {
			System.out.println(tkseatnum.get(count)+" "+count);
			tmp.seatNumber_=tkseatnum.get(count);
			count++;
		}
		//bookfromBase(String OrderNumber,String userID_,int howmanyTickets,String Tickettype,int startStation,int endStation,String date,int trainNumber,String seat)
		//getemptySeats(favorSeat)
		//getTrainofSeatsOP()
		//setSeatNumber(favorSeat,orderNumber)
		return tkseatnum;
	}
	
	public static ArrayList<ticket> getTicketSeat(String ordernumber,String UserID){
		if(new TicketDAO().searchOrder(ordernumber, UserID)==true)return new TicketDAO().getTicketsfromBase(ordernumber);
		else return null;
	}
	
	public ArrayList<Integer> getTime(){
		ArrayList<Integer> timetmp=new ArrayList<Integer>();
		if(tickets.get(tickets.size()-1).to==true) {//只有去程
			timetmp.add(tickets.get(0).starttime);
			timetmp.add(tickets.get(0).endtime);
			timetmp.add(-1);
			timetmp.add(-1);
		}
		else {
			timetmp.add(tickets.get(0).starttime);
			timetmp.add(tickets.get(0).endtime);
			timetmp.add(tickets.get(tickets.size()-1).starttime);
			timetmp.add(tickets.get(tickets.size()-1).endtime);
		}
		return timetmp;
	}
	
	public int gethowmanytickets() {return howmanytickets;}
	
	public int getOrderNumber() {return OrderNumber;}
	
	
}
