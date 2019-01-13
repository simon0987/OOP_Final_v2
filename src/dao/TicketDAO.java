package dao;

import java.sql.SQLException;
import java.util.ArrayList;

//import javax.swing.JOptionPane;

import parser.MysqlExe;
import parser.MysqlExe.RetVal;
import entity.Ticket;

/**
 * @author Tim
 *
 */
public class TicketDAO implements TicketDAO_I {
	
	/* (non-Javadoc)
	 * @see dao.TicketDAO_I#checkSeat(java.lang.String, int, int)
	 * 
	 * This part is in BuyTicket.java of sample code.
	 */
	@Override
	public boolean checkSeat(String seatID, int trainNumber, String date) {
		boolean booked = false; // initially not booked
		RetVal ret = null;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE train_id= %d AND date=\"%s\" AND seat_id=\"%s\"",
					trainNumber, date, seatID
					));
			booked = ret.res.next();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return booked;
	}
	
	/*
	 * (non-Javadoc)
	 * @see dao.TicketDAO_I#bookfromBase(entity.Ticket, java.lang.String)
	 * 
	 * This part is in BuyTicket.java of sample code.
	 */
	@Override
	public boolean bookfromBase(Ticket ticket, String userID) {
		try {
			MysqlExe.execStmt(String.format(
					"INSERT INTO tickets VALUES "
					+ "(%d, \"%s\", %s, %d, %d, %d, %d, %d, %d, \"%s\", %d, %d)",
					ticket.orderNumber_, userID, ticket.date_, ticket.tickettype_, ticket.startStation_, ticket.endStation_, 
					0, ticket.getprice(), ticket.trainNumber_, ticket.seatNumber_, ticket.starttime, ticket.endtime
					)); // 0 is side
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	/* (non-Javadoc)
	 * @see dao.TicketDAO_I#getOrderNumberFromBase(java.lang.String, int, int, int, int)
	 * 
	 * This part is in SearchTicketID.java of sample code.
	 */
	@Override
	public ArrayList<Integer> getOrderNumberFromBase(String userId, int trainNumber, String date, int start, int end) {
		ArrayList<Integer> orderNumberArr = new ArrayList<Integer>();
		RetVal ret = null;
		
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT DISTINCT code FROM tickets WHERE uid=\"%s\" AND train_id=%d AND date=\"%s\" AND start=%d AND end=%d",
					userId, trainNumber, date, start, end
					));
			while (ret.res.next()) {
				int code = ret.res.getInt("code");
				orderNumberArr.add(code);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orderNumberArr;
	}
	/*
	 * (non-Javadoc)
	 * @see dao.TicketDAO_I#deleteTicketsfromBase(java.lang.String, java.lang.String, int)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public void deleteTicketfromBase(int orderNumber, String userID, String seatID) {
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%d AND seat=\"%s\"", orderNumber, seatID));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * '(non-Javadoc)
	 * @see dao.TicketDAO_I#deleteOrderfromBase(int)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public void deleteOrderfromBase(int orderNumber, String userID) {
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%d", orderNumber));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/*
	 * (non-Javadoc)
	 * @see dao.TicketDAO_I#getTicketsfromBase(int)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public int getTicketNum(int orderNumber) {
		int count = 0;
		RetVal ret = null;
		
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT COUNT(*) FROM tickets WHERE code=\"%s\"", orderNumber
					));
			assert(ret.res.next());
			count = ret.res.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return count;
	}

	/*
	 * (non-Javadoc)
	 * @see dao.TicketDAO_I#searchOrder(int, java.lang.String)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public boolean searchOrder(int orderNumber, String userID) {
		boolean orderCheck = true;
		RetVal ret = null;
		
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=%d LIMIT 1", orderNumber
					));
			orderCheck = ret.res.next(); 
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return orderCheck;
	}
	/*
	 * (non-Javadoc)
	 * @see dao.TicketDAO_I#confirmUser(int, java.lang.String)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public boolean confirmUser(int orderNumber, String userID) {
		boolean userCheck = true;
		RetVal ret = null;
		
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=%d LIMIT 1", orderNumber
					));
			assert(ret.res.next()); 
			String uid = ret.res.getString("uid");
			if (!uid.equals(userID)) {
				userCheck = false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (ret.conn != null) ret.conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return userCheck;
	}
	
}
