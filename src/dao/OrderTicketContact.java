package dao;

import java.util.ArrayList;

import entity.ticket;

public interface OrderTicketContact {

	/*
	 * please return the train of the seats that is "not" occupied
	 * OP means occupied.
	 * favorSeat means close to window or not.
	 * 1 for close to window 0 for not.
	 */ 
	public  boolean  checkSeat(String SeatID, int trainID,String Date);
	
	/*
	 * get which orderNumber that hasn't been used.
	*/
	public ArrayList<Integer> getOrderNumberFromBase(String uid,int train_id, int date, int start, int end) ;
	/*
	 *delete Ticket from database.
	 *please return True or false in order to let me know if the function work well. 
	 */
	public boolean deleteTicketsfromBase(String OrderNumber,String userID,int howmany);
	/*
	 * delete order from database
	 */
	
	 public boolean deleteOrderfromBase(String OrderNumber,String userID);
	 /*
	  * you can store an object in the ArrayList ,I'll Cooperate to get the deta. 
	  */
	 public ArrayList<ticket> getTicketsfromBase(String OrderNumber);
	 /*
	  *search whether the order exist or not. 
	  */
	 public boolean searchOrder(String OrderNumber,String userID);
	 
	 public boolean bookfromBase(int OrderNumber,String userID_,int howmanyTickets,int Tickettype,int startStation,int endStation,String date,int trainNumber,String seat);
}
