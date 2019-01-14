package controll;


import dao.DAOFactory;
import entity.Order;
import dao.TicketDAO;

/**
 * This class is used to cancel the ticket by the booking code(order number).
 * @author Tim
 */
public class CancelTicket {
	
	
	/**
	 * cancel the ticket.
	 *  
	 * delete the whole order, if ticketCount == 0
	 * delete "ticketCount" tickets, if ticketCount > 0 && ticketCount <= (the number of tickets in this order)
	 */
	public String deleteTickets(String userID, int orderNumber, int ticketCount) {
		TicketDAO ticketDAO = DAOFactory.getTicket_DAO();
		
		if(!ticketDAO.searchOrder(orderNumber, userID)) {
			return "您輸入的訂位代號有誤，請重新輸入!";
		}
		if(!ticketDAO.confirmUser(orderNumber, userID)) {
			return "您輸入的身份識別號碼有誤，請重新輸入!";
		}
		if(ticketCount > ticketDAO.getTicketNum(orderNumber)) {
			return "欲減少票數多於總票數!";
		}
		
		if(ticketCount == 0) {
			if(ticketDAO.deleteTicketsfromBase(orderNumber, userID, ticketCount)) {
				return "修改成功，已將您人數變更為" + ticketDAO.getTicketNum(orderNumber) + "位";
			}
			else {
				return "修改失敗";
			}
		}
		else {
			if(Order.deleteOrder(orderNumber, userID)) {
				return "退票成功，已取消您的訂位紀錄";
			}
			else {
				return "退票成功";
			}
		}
	}
}
