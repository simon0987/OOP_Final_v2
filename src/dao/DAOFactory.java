package dao;

public class DAOFactory {
	private static Train_DAO myTrain_DAO;
	private static TicketDAO myTicket_DAO;
	
	public static Train_DAO getTrain_DAO() {
		myTrain_DAO = new Train_DAO();
		return myTrain_DAO;
	}
	public static TicketDAO getTicket_DAO() {
		myTicket_DAO = new TicketDAO();
		return myTicket_DAO;
	}
}
