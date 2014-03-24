package com.abc.rory.servlets;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.abc.rory.lib.CassandraHosts;
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
		// TODO Auto-generated method stub
		
		PreparedStatement ps = null;
		
		String uuid = String.valueOf(UUID.randomUUID().getMostSignificantBits());
		
		Session session = cluster.connect("email_system");
		
		String stmt = "INSERT INTO Email (email_id, email_Sender, email_Receiver, email_Subject, email_Message, email_Date) VALUES (?, ?, ?, ?, ?, ?)";
		
		//PreparedStatement statement = session.prepare("INSERT INTO Email (email_id, email_Sender, email_Receiver, email_Subject, email_Message, email_Date) VALUES ('1', 'roryduthie@gmail.com', 'abc@gmail.com', 'Test', 'TEST test TESTS', '2014-03-24') ");
		String fromEmail = request.getParameter("emailFrom");
		String toEmail = request.getParameter("emailTo");
		String subject = request.getParameter("emailSubject");
		String message = request.getParameter("emailMessage");
	    String date = request.getParameter("Year") + "-" + request.getParameter("Month") + "-" + request.getParameter("Day");
		
		
		
		
		ps= session.prepare(stmt);
    	session.execute(ps.bind(uuid, fromEmail, toEmail, subject, message, date));
    	 /*
    	session.
    	ps.setString(2, email_Receiver);
    	ps.setString(3, reporterId);
    	ps.setString(4, sectionId);
    	ps.setString(5, severity);
    	
		BoundStatement boundStatement = new BoundStatement(ps);
		session.execute(boundStatement);
		*/
		session.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
