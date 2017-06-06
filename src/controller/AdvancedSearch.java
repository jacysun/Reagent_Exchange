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

import model.Lab;
import model.LabReagent;

/**
 * Servlet implementation class AdvancedSearch
 */
@WebServlet("/AdvancedSearch")
public class AdvancedSearch extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static ArrayList<Integer> count = new ArrayList<Integer>();
	private static ArrayList<Double> labavr = new ArrayList<Double>();
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String selection = request.getParameter("selection");
		String institute = request.getParameter("institute");
		String rsarea = request.getParameter("rsarea");
		String rtype = request.getParameter("rtype");
		String rname = request.getParameter("rname");
		
		if (institute.equals("")) institute = null;
		if (rsarea.equals("")) rsarea = null;
		if (rtype.equals("")) rtype = null;
		if (rname.equals("")) rname = null;
		
		try {
			if (rtype != null || rname != null) {
				ArrayList<LabReagent> labReagentList = searchBestLab(rtype, rname);
				request.setAttribute("labReagentList", labReagentList);
			} else {
				ArrayList<Lab> labList = searchLab(selection, institute, rsarea);
				request.setAttribute("labList", labList);
				request.setAttribute("count", count);
				request.setAttribute("labavr", labavr);
			}
			RequestDispatcher dispatcher = request.getRequestDispatcher("advancedSearchResult.jsp");
			dispatcher.forward(request, response);
		} catch (Exception exc) {
			exc.printStackTrace();
		}
	}
	
	public static ArrayList<Lab> searchLab(String selection, String institute, String rsarea) throws Exception {
		ArrayList<Lab> labList = new ArrayList<Lab>();
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			if (selection.equals("sharemost")) {
				if (institute == null && rsarea == null) {
					myStmt = myConn.prepareCall("{call get_count()}");
					myRs = myStmt.executeQuery();
					getLabInfo(myRs, labList);
				} else if (institute != null && rsarea == null) {
					myStmt = myConn.prepareCall("{call get_count_by_institute(?)}");
					myStmt.setString(1, institute);
					myRs = myStmt.executeQuery();
					getLabInfo(myRs, labList);
				} else if (institute == null && rsarea != null) {
					myStmt = myConn.prepareCall("{call get_count_by_rsarea(?)}");
					myStmt.setString(1, rsarea);
					myRs = myStmt.executeQuery();
					getLabInfo(myRs, labList);
				} else if (institute != null && rsarea != null) {
					myStmt = myConn.prepareCall("{call get_count_by_institute_and_rsarea(?, ?)}");
					myStmt.setString(1, institute);
					myStmt.setString(2, rsarea);
					myRs = myStmt.executeQuery();
					getLabInfo(myRs, labList);
				}
			} else if (selection.equals("reliable")) {
                if (institute == null && rsarea == null) {
                	myStmt = myConn.prepareCall("{call get_reliable()}");
                	myRs = myStmt.executeQuery();
					getLabInfo2(myRs, labList);
				} else if (institute != null && rsarea == null) {
					myStmt = myConn.prepareCall("{call get_reliable_by_institute(?)}");
					myStmt.setString(1, institute);
					myRs = myStmt.executeQuery();
					getLabInfo2(myRs, labList);
				} else if (institute == null && rsarea != null) {
					myStmt = myConn.prepareCall("{call get_reliable_by_rsarea(?)}");
					myStmt.setString(1, rsarea);
					myRs = myStmt.executeQuery();
					getLabInfo2(myRs, labList);
				} else if (institute != null && rsarea != null) {
					myStmt = myConn.prepareCall("{call get_reliable_by_institute_and_rsarea(?, ?)}");
					myStmt.setString(1, institute);
					myStmt.setString(2, rsarea);
					myRs = myStmt.getResultSet();
					getLabInfo2(myRs, labList);
				}
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return labList;
	}
	
	public static ArrayList<LabReagent> searchBestLab(String rtype, String rname) throws Exception {
		ArrayList<LabReagent> labReagentList = new ArrayList<LabReagent>();
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			if (rtype != null && rname == null) {
				myStmt = myConn.prepareCall("{call get_best_by_rtype(?)}");
				myStmt.setString(1, rtype);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (rtype == null && rname != null) {
				myStmt = myConn.prepareCall("{call get_best_by_rname(?)}");
				myStmt.setString(1, rname);
				myRs = myStmt.executeQuery();
				getLabReagentInfo(myRs, labReagentList);
			} else if (rtype != null && rname != null) {
				myStmt = myConn.prepareCall("{call get_best_by_rtype_and_rname(?,?)}");
				myStmt.setString(1, rtype);
				myStmt.setString(2, rname);
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
	
	public static void getLabInfo(ResultSet myRs, ArrayList<Lab> labList) throws SQLException {
		while (myRs != null && myRs.next()) {
		String lid = myRs.getString("lid");
		String lname = myRs.getString("lname");
		String institute = myRs.getString("institute");
		String address = myRs.getString("address");
		String rsarea = myRs.getString("rsarea");
		double pbalance = myRs.getDouble("pbalance");
		int cnt = myRs.getInt("cnt");
		Lab lab = new Lab(lid, lname, institute, address, rsarea, pbalance);
		labList.add(lab);
		count.add(cnt);
		}
	}
	
	public static void getLabInfo2(ResultSet myRs, ArrayList<Lab> labList) throws SQLException {
		while (myRs != null && myRs.next()) {
		String lid = myRs.getString("lid");
		String lname = myRs.getString("lname");
		String institute = myRs.getString("institute");
		String address = myRs.getString("address");
		String rsarea = myRs.getString("rsarea");
		double pbalance = myRs.getDouble("pbalance");
		double average = myRs.getDouble("labavr");
		Lab lab = new Lab(lid, lname, institute, address, rsarea, pbalance);
		labList.add(lab);
		labavr.add(average);
		}
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
    public static void main(String[] args) throws Exception {
    	/*labList=searchLab("reliable", "university of massachusetts medical school", null);
			for(int i = 0; i < labavr.size(); i++) {
				System.out.println(labList.get(i).getLname() + labavr.get(i));
			}*/
    	    ArrayList<LabReagent> labReagentList = new ArrayList<LabReagent>();
			labReagentList=searchBestLab("antibody", null);
			for(int i = 0; i < labReagentList.size(); i++) {
				System.out.println(labReagentList.get(i).getLname());
			}
			
		}
}
