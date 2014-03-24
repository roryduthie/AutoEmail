package com.abc.rory.servlets;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;




import com.abc.rory.lib.CassandraHosts;
import com.datastax.driver.core.BoundStatement;
import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.PreparedStatement;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;

import java.util.*;
import java.text.*;

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
		
		PreparedStatement ps = null;
		ResultSet rs = null;
		System.out.println("Current Date: " + ft.format(dNow));
		
		Session session = cluster.connect("email_system");

		String statement = "SELECT * from Email WHERE email_date = '" + ft.format(dNow) + "'";
		System.out.println(statement);
		ps = session.prepare(statement);
		System.out.println("ps = " + ps);
		BoundStatement boundStatement = new BoundStatement(ps);
		System.out.println("BoundStatement = " + boundStatement);
		rs = session.execute(boundStatement);
		if (rs.isExhausted()) {
			System.out.println("No Emails Today");
		} else {
			for (Row row : rs) {
				System.out.println("DATA:  " + row.getString("email_subject"));
			}
		}
		session.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
