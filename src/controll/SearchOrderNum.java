package controll;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.JOptionPane;

import dao.DAOFactory;
import dao.TicketDAO;
import entity.Order;
import parser.MysqlExe;
import parser.MysqlExe.RetVal;

/**
 * This class is used to search the ticket by the ticket information.
 * @author Jerry
 */
public class SearchOrderNum {
	private String uid;
	private String train_id;
	private int date;
	private int start;
	private int end;

	/**
	 * @param uid User ID.
	 * @param train_id Train ID.
	 * @param date Departure date.
	 * @param start Departure Station.
	 * @param end Destination Station.
	 */
	public SearchOrderNum(String uid, String train_id, int date, int start, int end) {
		this.uid = uid;
		this.train_id = train_id;
		this.date = date;
		this.start = start;
		this.end = end;
	}
	
	/**
	 * Search the tickets.
	 */
	public void exec() {
		ArrayList<Integer> a = new ArrayList<Integer>();
		Vector<String> arr = new Vector<String>();
		
		a = Order.getOrderNum(uid, Integer.parseInt(train_id), date, start, end);
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < a.size();i++) {
			sb.append("訂單代號: " + a.get(i));
			sb.append(", 定位明細: 未付款 / " + uid + "\n");
			arr.add(sb.toString());
		}
		
		if (arr.isEmpty()) {
			JOptionPane.showMessageDialog(null, "查無紀錄!", "InfoBox: Failed",
					JOptionPane.ERROR_MESSAGE);
			return;
		}
		JOptionPane.showMessageDialog(null,  String.join("\n", arr), "InfoBox: 查詢訂票",
				JOptionPane.INFORMATION_MESSAGE);
	}
	
}
