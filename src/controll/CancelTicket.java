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
			return "�z��J���q��N�����~�A�Э��s��J!";
		}
		if(!ticketDAO.confirmUser(orderNumber, userID)) {
			return "�z��J�������ѧO���X���~�A�Э��s��J!";
		}
		if(ticketCount > ticketDAO.getTicketNum(orderNumber)) {
			return "����ֲ��Ʀh���`����!";
		}
		
		if(ticketCount == 0) {
			if(ticketDAO.deleteTicketsfromBase(orderNumber, userID, ticketCount)) {
				return "�ק令�\�A�w�N�z�H���ܧ�" + ticketDAO.getTicketNum(orderNumber) + "��";
			}
			else {
				return "�ק異��";
			}
		}
		else {
			if(Order.deleteOrder(orderNumber, userID)) {
				return "�h�����\�A�w�����z���q�����";
			}
			else {
				return "�h�����\";
			}
		}
	}
}
