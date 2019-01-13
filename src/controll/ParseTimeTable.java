package controll;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import dao.TimeTableDAO;


public class ParseTimeTable {
	
	public void parseTime() {
		Date myDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
		try {
			for (int i = 0; i < Convert_Time.FETCH_DAYS; i++) {
				String date = formatter.format(myDate);
				new TimeTableDAO().updateDay(date);
				Calendar c = Calendar.getInstance();
				c.setTime(myDate);
				c.add(Calendar.DATE, 1);
				myDate = c.getTime();
			}
		} catch (SQLException | IOException e) {
			System.out.println("Error while parsing time table");
			e.printStackTrace();
		}
	}
	
}
