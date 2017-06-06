package model;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LabReagent {
	
	private String lid;
    private String lname;
    private String institute;
    private String address;
    private String rsarea;
    double pbalance;
	private String rname;
	private String rtype;
	private String rsource;
    private Date expDate;
    private int scoreNum;
	private double avrscore;
	private String sqty;
	private double askp;
	//private InputStream image;

	public LabReagent() {
		
	}
	
	public LabReagent(String lid, String lname, String institute, String address, String rsarea, double pbalance, String rname, String rsource, String rtype,  Date expDate, int scoreNum, double avrscore, String sqty, double askp) {
		
		this.lid = lid;
		this.lname = lname;
		this.institute = institute;
		this.address = address;
		this.rsarea = rsarea;
		this.pbalance = pbalance;
		this.rname = rname;
		this.rtype = rtype;
		this.rsource = rsource;
		this.expDate = expDate;
		this.scoreNum = scoreNum;
		this.avrscore = avrscore;
		this.sqty = sqty;
		this.askp=askp;
		//this.image=image;
	 }

	public String getLid() {
		return lid;
	}

	public String getLname() {
		return lname;
	}

	public String getInstitute() {
		return institute;
	}

	public String getAddress() {
		return address;
	}

	public String getRsarea() {
		return rsarea;
	}

	public double getPbalance() {
		return pbalance;
	}
	
    public String getRname() {
    	return rname;
    }

	public String getRtype() {
		return rtype;
	}

	public String getRsource() {
		return rsource;
	}

	public Date getExpDate() {
		return expDate;
	}

	public int getScoreNum() {
		return scoreNum;
	}

	public double getAvrscore() {
		return avrscore;
	}

	public String getSqty() {
		return sqty;
	}

	public double getAskp() {
		return askp;
	}
	
	/*public InputStream getImage() {
		return image;
	}*/
	
	public LabReagent getReagentInfo(String lid, String rname) throws Exception {
		Connection myConn = null;
		CallableStatement myStmt = null;
		ResultSet myRs = null;
		LabReagent lr = null;
		try {	
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/biobay", "student" , "student");
			myStmt = myConn.prepareCall("{call get_labreagent_info(?,?)}");
			myStmt.setString(1, lid);
			myStmt.setString(2, rname);
			myRs = myStmt.executeQuery();
			while(myRs != null && myRs.next()) {
				String lname = myRs.getString("lname");
				String institute = myRs.getString("institute");
				String address = myRs.getString("address");
				String rsarea = myRs.getString("rsarea");
				double pbalance = myRs.getDouble("pbalance");
				String rsource = myRs.getString("rsource");
				String rtype = myRs.getString("rtype");
				Date expdate = myRs.getDate("expdate");
				int scorenum = myRs.getInt("scorenum");
				double avrscore = myRs.getDouble("avrscore");
				String sqty = myRs.getString("sqty");
				double askp = myRs.getDouble("askp");
				//InputStream image = myRs.getBinaryStream("image");
				lr = new LabReagent(lid, lname, institute, address, rsarea, pbalance, rname, rsource, rtype, expdate, scorenum, avrscore, sqty, askp);
			}
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {
			close(myConn, myStmt, myRs);
		}
		return lr;
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
		
	

}
