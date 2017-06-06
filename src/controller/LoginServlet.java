package controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.sql.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.Scientist;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		boolean result;
		try {
			result = authenticate(username, password);
			if (result) {
				Scientist scientist = getScientistInfo(username);
				request.getSession().setAttribute("scientist", scientist);
				response.sendRedirect("basicSearch.jsp");
				return;
			} else {
				response.sendRedirect("index.html");
				return;	
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
    public boolean authenticate(String username, String password) throws SQLException {
		
		if (password == null || password.trim() == "") {
			return false;
		}
		
		Connection myConn = null;
		CallableStatement myStmt = null;
		String pw = "";
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_password_for_username(?, ?)}");
			myStmt.setString(1, username);
			myStmt.registerOutParameter(2, Types.CHAR);
			myStmt.execute();
			pw = myStmt.getString(2);
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt);
		}
		if (password.equals(pw)) {
        	return true;
        } else {
        	return false;
        }
		
	}

	public static Scientist getScientistInfo(String username) throws SQLException {
	    Scientist scientist = null;
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_scientist_info(?)}");
			myStmt.setString(1, username);
			myStmt.execute();
			myRs = myStmt.getResultSet();
			if (myRs != null && myRs.next()) {
				String password = myRs.getString("psword");
				String sname = myRs.getString("sname");
				String title = myRs.getString("title");
				String lid = myRs.getString("lid");
				Date ssince = myRs.getDate("ssince");
				scientist = new Scientist(username, password, sname, title, lid, ssince);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return scientist;
	}

	private static void close(Connection myConn, Statement myStmt, ResultSet myRs) throws SQLException {
			if (myConn != null) {
	    		myConn.close();
	    	}
			if (myStmt != null) {
	    		myStmt.close();
	    	}
			if (myRs != null) {
				myRs.close();
			}
	}
	
	private static void close(Connection myConn, Statement myStmt) throws SQLException {
		close(myConn, myStmt, null);
    }

}
