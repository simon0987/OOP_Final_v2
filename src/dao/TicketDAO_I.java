package dao;

import java.util.ArrayList;
import java.util.Vector;

import entity.Ticket;

public interface TicketDAO_I {
	/*
	 * please return the train of the seats that is "not" occupied OP means
	 * occupied. favorSeat means close to window or not. 1 for close to window 0 for
	 * not.
	 */
	public boolean checkSeat(String SeatID, int trainNumber, String date);

	/*
	 * get which orderNumber that hasn't been used.
	 */
	public ArrayList<Integer> getOrderNumberFromBase(String userID, int trainNumber ,String date, int start, int end);

	/*
	 * delete Ticket from database. please return True or false in order to let me
	 * know if the function work well.
	 */
	public void deleteTicketfromBase(int OrderNumber, String userID, String seatID);
	/*
	 * delete order from database
	 */
	
	public boolean getTicket(Vector<String> arr,String uid_in,String code);
	
	public void deleteOrderfromBase(int OrderNumber, String userID);

	/*
	 * you can store an object in the ArrayList ,I'll Cooperate to get the deta.
	 */
	public int getTicketNum(int OrderNumber);

	/*
	 * search whether the order exist or not.
	 */
	public boolean searchOrder(int OrderNumber, String userID);
	
	public boolean confirmUser(int OrderNumber, String userID);
	
	public boolean bookfromBase(Ticket ticket, String userID);
}