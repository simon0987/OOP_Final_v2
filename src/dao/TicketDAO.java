package dao;

import java.util.ArrayList;

import entity.ticket;

public class TicketDAO implements OrderTicketContact{
	public TicketDAO(){}
	@Override
	public  boolean  checkSeat(String SeatID, int trainID,String Date) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ArrayList<Integer> getOrderNumberFromBase(String uid, int train_id, int date, int start, int end) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean deleteTicketsfromBase(String OrderNumber,String userID, int howmany) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean deleteOrderfromBase(String OrderNumber,String userID) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ArrayList<ticket> getTicketsfromBase(String OrderNumber) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean searchOrder(String OrderNumber, String userID) {
		// TODO Auto-generated method stub
		return false;
	}
	 public boolean bookfromBase(int OrderNumber,String userID_,int howmanyTickets,int Tickettype,int startStation,int endStation,String date,int trainNumber,String seat){
		return true;
	}
}
