package controller;

import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.LabReagent;

/**
 * Servlet implementation class BasicSearch
 */
@WebServlet("/BasicSearch")
public class BasicSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String institute = request.getParameter("institute");
		String lname = request.getParameter("lname");
		String rtype = request.getParameter("rtype");
		String rname = request.getParameter("rname");
		double score = 0; 
		
		if(institute.equals("")) institute = null;
		if(lname.equals("")) lname = null;
		if(rtype.equals("")) rtype = null;
		if(rname.equals("")) rname = null;
		if(!request.getParameter("score").equals("")) score = Double.valueOf(request.getParameter("score"));
		
		try {
			ArrayList<LabReagent> labReagentList = searchLabReagent(institute, lname, rtype, rname, score);
			request.getSession().setAttribute("labReagentList", labReagentList);
			RequestDispatcher dispatcher = request.getRequestDispatcher("basicSearchResult.jsp");
			dispatcher.forward(request, response);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
		
	public static ArrayList<LabReagent> searchLabReagent(String institute, String lname, String rtype, String rname, double score) throws Exception {
		ArrayList<LabReagent> labReagentList = new ArrayList<LabReagent>();
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			if (institute != null && lname == null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute(?)}");
				myStmt.setString(1, institute);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_lname(?)}");
				myStmt.setString(1, lname);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute != null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute_and_lname(?, ?)}");
				myStmt.setString(1, institute);
				myStmt.setString(2, lname);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute_and_score(?,?)}");
				myStmt.setString(1, lname);
				myStmt.setDouble(2, score);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute != null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_lname_and_score(?, ?)}");
				myStmt.setString(1, lname);
				myStmt.setDouble(2, score);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute != null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute_and_lname_and_score(?, ?,?)}");
				myStmt.setString(1, institute);
				myStmt.setString(2, lname);
				myStmt.setDouble(3, score);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute_and_rname(?,?)}");
				myStmt.setString(1, institute);
				myStmt.setString(2, rname);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname != null && rtype == null && rname == null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_institute_and_rtype(?,?)}");
				myStmt.setString(1, institute);
				myStmt.setString(2, rtype);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname == null && rtype == null && rname != null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_rname(?)}");
				myStmt.setString(1, rname);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname == null && rtype != null && score == 0 && rname == null) {
				myStmt = myConn.prepareCall("{call get_reagent_by_rtype(?)}");
				myStmt.setString(1, rtype);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname == null && rtype == null && score != 0 && rname == null) {
				myStmt = myConn.prepareCall("{call get_reagent_by_score(?)}");
				myStmt.setDouble(1, score);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname == null && rtype != null && rname != null && score == 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_rname_and_rtype(?, ?)}");
				myStmt.setString(1, rname);
				myStmt.setString(2, rtype);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (institute == null && lname == null && rtype != null && rname != null && score != 0) {
				myStmt = myConn.prepareCall("{call get_reagent_by_name_and_type_and_score(?, ?, ?)}");
				myStmt.setString(1, rname);
				myStmt.setString(2, rtype);
				myStmt.setDouble(3, score);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return labReagentList;
	}
	
	public static void getLabReagentInfo(ResultSet myRs, ArrayList<LabReagent> labReagentList) throws SQLException {
		while (myRs != null && myRs.next()) {
		String lid = myRs.getString("lid");
		String lname = myRs.getString("lname");
		String institute = myRs.getString("institute");
		String address = myRs.getString("address");
		String rsarea = myRs.getString("rsarea");
		double pbalance = myRs.getDouble("pbalance");
		String rname = myRs.getString("rname");
		String rsource = myRs.getString("rsource");
		String rtype = myRs.getString("rtype");
		Date expDate = myRs.getDate("expDate");
		int scoreNum = myRs.getInt("scoreNum");
		double avrscore = myRs.getDouble("avrscore");
		String sqty = myRs.getString("sqty");
		double askp = myRs.getDouble("askp");
		//String image = myRs.getString("image");
		LabReagent labReagent = new LabReagent(lid, lname, institute, address, rsarea, pbalance, rname, rsource, rtype, expDate, scoreNum, avrscore, sqty, askp);
		labReagentList.add(labReagent);
		}
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
	
	 //For debugging
	 /*public static void main(String[] args) throws Exception {
		ArrayList<LabReagent> labReagentList = new ArrayList<LabReagent>();
		labReagentList=searchLabReagent("university of massachusetts medical school", null, null , null, 0);
		for(LabReagent labReagent: labReagentList) {
			System.out.println(labReagent.getRname());
		}
	}	*/
}
