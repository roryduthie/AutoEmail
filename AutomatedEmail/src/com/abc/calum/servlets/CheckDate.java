package com.abc.calum.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;







import com.abc.calum.lib.CassandraHosts;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.sun.mail.iap.Response;

import java.util.*;
import java.text.*;
import java.io.*;

import javax.mail.*;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.InternetAddress;



/**
 * Servlet implementation class CheckDate
 */
@WebServlet("/CheckDate")
public class CheckDate extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Cluster cluster;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckDate() {
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
		SimpleDateFormat ft =  new SimpleDateFormat("yyyy-MM-dd");
		List<String> sentEmail = new ArrayList<String>();
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("Current Date: " + ft.format(dNow));

		Session session = cluster.connect("email_system");

		String statement = "SELECT * from Email WHERE email_date = '" + ft.format(dNow) + "'";
		ps = session.prepare(statement);
		BoundStatement boundStatement = new BoundStatement(ps);
		rs = session.execute(boundStatement);
		if (rs.isExhausted()) {
			System.out.println("No Emails Today");
		} else {
			for (Row row : rs) {
				System.out.println("Message Found. Subject: " + row.getString("email_subject"));
				sendEmail(row.getString("email_id"), row.getString("email_Sender"), row.getString("email_Receiver"), row.getString("email_Subject"), row.getString("email_Message"));
				String key = row.getString("email_id");
				sentEmail.add(key);
			}
		}
		session.close();
		deleteEmail(sentEmail);
		
		response.sendRedirect("enterEmail.jsp");
	}
	
	protected void deleteEmail(List<String> sentEmail)
	{
		PreparedStatement ps = null;

		Session session = cluster.connect("email_system");

		String stmt = "DELETE FROM Email WHERE email_id = ?";
		
		for (int i=0; i< sentEmail.size(); i++)
		{
			ps=session.prepare(stmt);
			session.execute(ps.bind(sentEmail.get(i)));
			System.out.println("Message Deleted");
		}
		
		sentEmail.clear();
    	
		session.close();
	}
	
	protected void sendEmail(String email_id, String email_from, String email_to, String email_subject, String email_message) throws IOException
	{
		String to = email_to;
		String from = email_from;
		String subject = email_subject;
		String mymessage = email_message;
		
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		javax.mail.Session session = javax.mail.Session.getDefaultInstance(props, new javax.mail.Authenticator() {
												protected PasswordAuthentication getPasswordAuthentication() {
													return new PasswordAuthentication("anotefromthepast@gmail.com","pintsmakeprizes");
													}
												});
		
		try {
			 
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO,
					InternetAddress.parse(to));
			message.setSubject(subject);
			message.setText(mymessage + "\n\n Message Sent from A Note From The Past by user: " + email_from);
 
			Transport.send(message);
 
			System.out.println("Done");
 
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}