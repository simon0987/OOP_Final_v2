package controll;

import java.sql.SQLException;
import java.util.Vector;

import javax.swing.JOptionPane;

import dao.DAOFactory;
import dao.TicketDAO;
import entity.Station;
import entity.Train;
import parser.MysqlExe;
import parser.MysqlExe.RetVal;

/**
 * This class is used to search the ticket by the booking code.
 * @author Jerry
 */
public class SearchTicket {
	private String uid;
	private String code;
	
	/**
	 * @param uid User ID.
	 * @param code Booking code.
	 */
	public SearchTicket(String uid, String code) {
		this.uid = uid;
		this.code = code;
	}

	/**
	 * Search the ticket.
	 * @param tostr Return string or pop the message box.
	 * @return The search result.
	 */
	public String exec(int tostr) {
		Vector<String> arr = new Vector<String>();
		TicketDAO ticketdao = DAOFactory.getTicket_DAO();
		boolean userCheck = ticketdao.getTicket(arr,uid,code);
		
		if (arr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "您輸入的訂位代號有誤，請重新輸入!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
		if (!userCheck) {
			JOptionPane.showMessageDialog(null, "您輸入的身份識別號碼有誤，請重新輸入!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return "";
		}
		if (tostr == 1)
			return String.join("\n", arr);
		else
			JOptionPane.showMessageDialog(null, String.join("\n", arr), "InfoBox: 查詢訂票",
				JOptionPane.INFORMATION_MESSAGE);
		return "";
	}
}
