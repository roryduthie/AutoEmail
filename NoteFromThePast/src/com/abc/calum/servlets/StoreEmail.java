package com.abc.calum.servlets;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;


import com.abc.calum.lib.CassandraHosts;
import com.datastax.driver.core.*;

/**
 * Servlet implementation class StoreEmail
 */
@WebServlet("/StoreEmail")
public class StoreEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;

	String email_Sender= "";
	String email_Receiver;
	String email_Subject;
	String email_Message;
	String email_Date;

       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public StoreEmail() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		cluster = CassandraHosts.getCluster();
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Date dNow = new Date( );
		Calendar cal = new GregorianCalendar();
		SimpleDateFormat ft =  new SimpleDateFormat("yyyy-MM-dd");
		RequestDispatcher rd;
		String fromEmail = request.getParameter("emailFrom");
		String toEmail = request.getParameter("emailTo");
		String subject = request.getParameter("emailSubject");
		String message = request.getParameter("emailMessage");
	    String date = request.getParameter("Year") + "-" + request.getParameter("Month") + "-" + request.getParameter("Day");
	    int year = Integer.parseInt(request.getParameter("Year"));
	    int month = Integer.parseInt(request.getParameter("Month"));
	    int day = Integer.parseInt(request.getParameter("Day"));
	    
	    int realYear = cal.get(Calendar.YEAR);
	    int realMonth = (cal.get(Calendar.MONTH) + 1);
	    int realDay = cal.get(Calendar.DAY_OF_MONTH);
	    
		//Input Validation
		if ((year == realYear && month < realMonth) || (year < realYear) || (year == realYear && month == realMonth && day < realDay) 
				|| (!toEmail.contains("@")) || (!toEmail.contains(".")) 
				|| (!fromEmail.contains("@")) || (!fromEmail.contains("."))
				|| ((month == 2 && day == 29) && year%4 != 0) //Leap Years
				|| (month == 2 && day >29) //February
				|| (month == 4 && day >30) //April
				|| (month == 6 && day >30) //June
				|| (month == 9 && day >30) //September
				|| (month == 11 && day >30) //November
				)
		{
			request.setAttribute("emailFrom", fromEmail);
			request.setAttribute("emailTo", toEmail);
			request.setAttribute("subject", subject);
			request.setAttribute("message", message);
			request.setAttribute("day", day);
			request.setAttribute("month", month);
			request.setAttribute("year", year);
			request.setAttribute("dateError", "");
			request.setAttribute("emailToError","");
			request.setAttribute("emailFromError","");
			
			if ((year == realYear && month < realMonth) || (year < realYear) || (year == realYear && month == realMonth && day < realDay)
					|| ((month == 2 && day == 29) && year%4 != 0) //Leap Years
					|| (month == 2 && day >29) //February
					|| (month == 4 && day >30) //April
					|| (month == 6 && day >30) //June
					|| (month == 9 && day >30) //September
					|| (month == 11 && day >30) //November
					)
			{
				request.setAttribute("dateError", "true");
			}
			if ((!toEmail.contains("@")) || (!toEmail.contains(".")))
			{
				request.setAttribute("emailToError","true");
			}
			if ((!fromEmail.contains("@")) || (!fromEmail.contains(".")))
			{
				request.setAttribute("emailFromError","true");
			}
			
			
			rd = request.getRequestDispatcher("enterEmail.jsp");
			
			rd.forward(request,response);
		} else {
		
		
		
		
		PreparedStatement ps = null;

		String uuid = String.valueOf(UUID.randomUUID().getMostSignificantBits());

		Session session = cluster.connect("email_system");

		String stmt = "INSERT INTO Email (email_id, email_Sender, email_Receiver, email_Subject, email_Message, email_Date) VALUES (?, ?, ?, ?, ?, ?)";

		ps= session.prepare(stmt);
    	session.execute(ps.bind(uuid, fromEmail, toEmail, subject, message, date));
    	
		session.close();
		System.out.println("Message Stored");
		response.sendRedirect("CheckDate");
		}

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}