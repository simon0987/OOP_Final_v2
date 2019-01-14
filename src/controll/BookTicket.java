package controll;

import java.util.ArrayList;

import javax.swing.JComboBox;

import entity.Candidate;
import entity.Train;
import entity.Order;


public class BookTicket {
	private Candidate go;
	private Candidate back = null;
	private Order myOrder = null;
	
	
	public String checkCondition(String uid, int startStationIndex, int endStationIndex, boolean ifGoBack, int count,
			int goDateFlag, int backDateFlag, int goStartTime, int goEndTime, int backStartTime, int backEndTime) {
		String err = new Candidate().isAvailable(uid, startStationIndex, endStationIndex, ifGoBack, count,
				goDateFlag, backDateFlag, goStartTime, goEndTime, backStartTime, backEndTime);
		return err;
	}
	
	public String showCandidate(JComboBox<String> goBox, JComboBox<String> backBox, String goDate, String backDate, 
			int start, int end, int got1, int got2, int backt1, int backt2, int count, int side, int type, 
			int goEarly, int backEarly, boolean ifGoBack) {
		go = new Candidate();
		back = new Candidate();
		go.listTrain(goBox, goDate, start, end, got1, got2, count, side, type, goEarly);
		if (go.getSize() == 0) {
			return "座位已售完";
		}
		if (ifGoBack) {
			back.listTrain(backBox, backDate, end, start, backt1, backt2, count, side, type, backEarly);
			if (back.getSize() == 0) {
				return "座位已售完";
			}
		}
		return "ok";
	
	}
	
	public void showPrice(boolean ifGoBack, int goIndex, int backIndex, String uid, int count, int type, 
			int startStation, int endStation, int side, int code) {
		Train go_train = null;
		Train back_train = null;
		go_train = go.getTrain(goIndex);
		if (ifGoBack)
			back_train = back.getTrain(backIndex);
		myOrder = new Order(uid, count, type, startStation, endStation, go_train, back_train, side, ifGoBack, code);
	}
	
	public int getTotalPrice() {
		int price = myOrder.getTotalPrice();
		return price;
	}
	
	public ArrayList<Integer> getTicketTime() {
		return myOrder.getTime();
	}
	
	public int getCode() {
		return myOrder.getOrderNumber();
	}
	
	public ArrayList<String> showOrder() {
		return myOrder.setTicketSeat();
	}
	
}
