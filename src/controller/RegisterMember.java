package controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Scientist;

/**
 * Servlet implementation class RegisterLab
 */
@WebServlet("/RegisterMember")
public class RegisterMember extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String lid = request.getParameter("lid");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String sname = request.getParameter("sname");
		String title = request.getParameter("title");
		String ssince = request.getParameter("ssince");
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sdate = null;
		try {
			sdate = new java.sql.Date(formatter.parse(ssince).getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		Scientist scientist = new Scientist(username, password, sname, title, lid, sdate);
		request.getSession().setAttribute("scientist", scientist);
		try {
			insertScientist(username, password, sname, title, lid, sdate);
			response.sendRedirect("memberProfile.jsp");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void insertScientist(String username, String password, String sname, String title, String lid, java.sql.Date sdate) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		
		try {
		    Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call insert_scientist(?,?,?,?,?,?)}");
			myStmt.setString(1, username);
			myStmt.setString(2, password);
			myStmt.setString(3, sname);
			myStmt.setString(4, title);
			myStmt.setString(5, lid);
			myStmt.setDate(6, sdate);
			myStmt.executeUpdate();
	   } catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close (myConn, myStmt);
		}
	}
	
	private static void close(Connection myConn, Statement myStmt) throws SQLException {
		if (myStmt != null) {
			myStmt.close();
		}
		if (myConn != null) {
			myConn.close();
		}
	}
}


