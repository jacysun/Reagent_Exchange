package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.sql.Date;


public class Scientist {
	
	public String username;
	public String password;
	public String sname;
	public String title;
	public String lid;
	public Date ssince;
	
	public Scientist(String username, String password, String sname, String title, String lid, Date ssince){
		this.username = username;
		this.password = password;
		this.sname = sname;
		this.title = title;
		this.lid = lid;
		this.ssince = ssince;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getSname() {
		return sname;
	}

	public String getTitle() {
		return title;
	}

	public String getLid() {
		return lid;
	}
	
	public Date getSsince() {
		return ssince;
	}
	
	public String[] viewProfile() throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		String[] sp=new String[6];
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_member_profile(?)}");
			myStmt.setString(1, this.username);
			myRs = myStmt.executeQuery();
			while ( myRs !=null && myRs.next()) {
				sp[0]=myRs.getString(1);
				sp[1]=myRs.getString(2);
				sp[2]=myRs.getString(3);
				sp[3]=myRs.getString(4);
				sp[4]=myRs.getString(5);
				sp[5]=myRs.getDate(6).toString();
			}	
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return sp;	
	}
	
	public boolean shareReagent(String rname, String rsource, String rtype, Date expdate, double avrscore, String sqty, double askp, String comment) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		try {	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call insert_reagent(?,?,?,?,?,?,?,?,?)}");
			myStmt.setString(1, rname);
			myStmt.setString(2, rsource);
			myStmt.setString(3, rtype);
			myStmt.setDate(4, expdate);
			myStmt.setInt(5, 0);
			myStmt.setDouble(6, 0);
			myStmt.setString(7, this.lid);
			myStmt.setString(8, sqty);
			myStmt.setDouble(9, askp);
			//myStmt.setBinaryStream(10, image);
			myStmt.executeUpdate();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close (myConn, myStmt);
		}
		System.out.println("insert reagent done");
		return reviewReagent(this.lid, rname, avrscore, comment);
	}
	
	public void updateBbp(String lid, double bbp) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call add_bbp_to_lab(?, ?)}");
			myStmt.setString(1, lid);
			myStmt.setDouble(2, bbp);
			myStmt.executeUpdate();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close (myConn, myStmt);
		}
	}
	
	public boolean reviewReagent(String lid, String rname, double score, String comment) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		int cid = 0;
		double newAvrscore = 0;
		int newScorenum = 0;
		boolean success = false;
		
		try {	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myConn.setAutoCommit(false);
			myStmt = myConn.prepareCall("{call get_comment_count(?)}");
			myStmt.registerOutParameter(1, Types.INTEGER);
			myStmt.execute();
			int count = myStmt.getInt(1);
			cid = count + 1;
			myStmt.close();
		
			java.util.Date currentDate = new java.util.Date();   
			java.sql.Date rvdate = new java.sql.Date(currentDate.getTime());

			myStmt = myConn.prepareCall("{call insert_comment(?,?,?)}");
			myStmt.setInt(1, cid);
			myStmt.setDouble(2, score);
			myStmt.setString(3, comment);
			myStmt.executeUpdate();
			myStmt.close();

			myStmt = myConn.prepareCall("{call insert_review(?,?,?,?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myStmt.setString(3, this.username);
			myStmt.setInt(4, cid);
			myStmt.setDate(5, rvdate);
			myStmt.executeUpdate();
			myStmt.close();

			myStmt = myConn.prepareCall("{call get_reagent_info(?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myRs = myStmt.executeQuery();
			while(myRs != null && myRs.next()) {
				int scorenum = myRs.getInt("scorenum");
				double avrscore = myRs.getDouble("avrscore");
				newScorenum = scorenum + 1;
				newAvrscore = (avrscore * scorenum + score) / newScorenum;
			}
			myStmt.close();

			myStmt = myConn.prepareCall("{call update_score(?,?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myStmt.setDouble(3, newAvrscore);
			myStmt.executeUpdate();
			myConn.commit();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close (myConn, myStmt);
		}
		updateBbp(this.lid, 50.0);	
		success = true;
		return success;
	}
	
	public double requestReagent(String lid, String rname, String rqqty, double qty) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		double payp = 0;
		double pbalance = 0;
		try {	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_pbalance(?,?)}");
			myStmt.setString(1, this.lid);
			myStmt.registerOutParameter(2, Types.DOUBLE);
			myStmt.execute();
			pbalance = myStmt.getDouble(2);
			myStmt.close();
			
			myStmt = myConn.prepareCall("{call get_reagent_askp(?,?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myStmt.registerOutParameter(3, Types.DOUBLE);
			myStmt.execute();
			double askp = myStmt.getDouble(3);
			payp = askp * qty;
			myStmt.close();
			
			if(pbalance < payp) {
				return 0;
			}
			
			java.util.Date currentDate = new java.util.Date();   
			java.sql.Date rqdate = new java.sql.Date(currentDate.getTime());
			
			myStmt = myConn.prepareCall("{call insert_request(?,?,?,?,?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myStmt.setString(3, this.username);
			myStmt.setString(4, rqqty);
			myStmt.setDouble(5, payp);
			myStmt.setDate(6, rqdate);
			System.out.println(lid + rname + this.username + rqqty + payp + rqdate);
			myStmt.execute();
			myStmt.close();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt);
		}
		updateBbp(this.lid, -payp);
		updateBbp(lid, payp);
		return payp;		
	}
	
	public ArrayList<Request> getRequest() throws Exception {
		ArrayList<Request> requestList = new ArrayList<Request>();
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_scientist_request(?)}");
			myStmt.setString(1, this.username);
			myRs = myStmt.executeQuery();
			while (myRs != null && myRs.next()) {
				String lid = myRs.getString("lid");
				String rname = myRs.getString("rname");
				String username = myRs.getString("username");
				String rqqty = myRs.getString("rqqty");
				double payp = myRs.getDouble("payp");
				Date rqdate = myRs.getDate("rqdate");
				Request request = new Request(lid, rname, username, rqqty, payp, rqdate);
				requestList.add(request);	
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return requestList;
	}
	
	public String[] getLab() throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		String[] lab = new String[6];
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_lab_info(?)}");
			myStmt.setString(1, this.lid);
			myRs = myStmt.executeQuery();
			while (myRs != null && myRs.next()) {
				lab[0] = myRs.getString("lid");
				lab[1] = myRs.getString("lname");
				lab[2] = myRs.getString("rsarea");
				lab[3] = myRs.getString("institute");
				lab[4] = myRs.getString("address");
				lab[5] = String.valueOf(myRs.getDouble("pbalance"));	
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return lab;
	}
	
	public ArrayList<Reagent> getReagent() throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		ArrayList<Reagent> rl = new ArrayList<Reagent>();
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_lab_reagent(?)}");
			myStmt.setString(1, this.lid);
			myRs = myStmt.executeQuery();
			while (myRs != null && myRs.next()) {
				String rname = myRs.getString("rname");
				String rsource = myRs.getString("rsource");
				String rtype = myRs.getString("rtype");
				Date expDate = myRs.getDate("expDate");
				int scoreNum = myRs.getInt("scoreNum");
				double avrscore = myRs.getDouble("avrscore");	
				String sqty = myRs.getString("sqty");
				double askp = myRs.getDouble("askp");
				Reagent reagent = new Reagent(this.lid, rname, rsource, rtype, expDate, scoreNum, avrscore, sqty, askp);
				rl.add(reagent);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return rl;
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
	
	/*public static void main(String[] args) throws Exception {
		Scientist scientist = new Scientist("sz6", "107", "Paul Mak", "Postdoctoral Fello", "ls2", null);
		scientist.reviewReagent("am1", "PI3K antibody", 8.5, "Works for me.");
		scientist.shareReagent("Lysis Buffer", "Thermo")
		rname, String rsource, String rtype, Date expdate, int scorenum, double avrscore, String lid, String sqty, double askp, String image
	}*/
}
