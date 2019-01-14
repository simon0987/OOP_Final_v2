package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import controll.Convert_Time;
import parser.MysqlExe;
import parser.MysqlExe.RetVal;
import entity.Station;
import entity.Ticket;
import entity.Train;

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
	public boolean deleteTicketsfromBase(int orderNumber, String userID, int decreaseCount) {
		RetVal ret = null;
		int st = 0, ed = 0;
		int dir = 0;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT count(*), start, end FROM tickets WHERE code=%d GROUP BY start, end", orderNumber
					));
			
			while (ret.res.next()) {
				st = ret.res.getInt("start");
				ed = ret.res.getInt("end");
				dir++;
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
		
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%d AND start=%d LIMIT %d", orderNumber, st, decreaseCount));
			if (dir == 2)
				MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%d AND start=%d LIMIT %d", orderNumber, ed, decreaseCount));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	/*
	 * '(non-Javadoc)
	 * @see dao.TicketDAO_I#deletesOrderfromBase(int)
	 * 
	 * This part is in UpdateTicket.java of sample code.
	 */
	@Override
	public boolean deleteOrderfromBase(int orderNumber, String userID) {
		try {
			MysqlExe.execStmt(String.format("DELETE FROM tickets WHERE code=%d", orderNumber));
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
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
					"SELECT * FROM tickets WHERE code=%d", orderNumber
					));
			while (ret.res.next()) {
				count++;
			}
			// Should have a better solution!!!!
			ret = MysqlExe.execQuery(String.format(
					"SELECT count(*), start FROM tickets WHERE code=%d GROUP BY start, end", orderNumber
					));
			int dir = 0;
			while (ret.res.next()) {
				dir++;
			}
			if(dir == 2) { 
				count /= 2; // 來回票是同時取消的
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
					"SELECT uid FROM tickets WHERE code=%d", orderNumber
					));
			if(ret.res.next()) { 
				String uid = ret.res.getString("uid");
				if (!uid.equals(userID)) {
					userCheck = false;
				}
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
	
	public boolean getTicket(Vector<String> arr,String uid_in, String code) {
		RetVal ret = null;
		boolean userCheck = true;
		try {
			ret = MysqlExe.execQuery(String.format(
					"SELECT * FROM tickets WHERE code=\"%s\"", code
					));
			while (ret.res.next()) {
				String uid = ret.res.getString("uid");
				if (!uid.equals(uid_in)) userCheck = false;
				int train_id = ret.res.getInt("train_id");
				int date = ret.res.getInt("date");
				int st = ret.res.getInt("start");
				int ed = ret.res.getInt("end");
				int t1 = ret.res.getInt("start_time");
				int t2 = ret.res.getInt("end_time");
				int dur = Convert_Time.getDur(t1, t2);
				String type = Train.TICKET_TYPE_2[ret.res.getInt("ticketsType")];
				String seat = ret.res.getString("seat_id");
				StringBuffer sb = new StringBuffer();
				sb.append("車次: " + train_id);
				sb.append(", 日期: " + date);
				sb.append(", 起/訖站: " + Station.CHI_NAME[st] + "->" + Station.CHI_NAME[ed]);
				sb.append(", 出發/到達時間: " + t1 + "->" + t2);
				sb.append(", 行車時間: " + dur);
				sb.append(", 座位: " + seat);
				sb.append(", 類型: " + type);
				arr.add(sb.toString());
			}
			ret.conn.close();
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
