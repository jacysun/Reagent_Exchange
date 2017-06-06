package model;

import java.sql.Date;

public class Request {
	 private String username;
	 private String lid;
	 private String rname;
     private Date rqdate;
     private String rqqty;
     private double payp;
    
    
	public Request(String lid, String rname, String username,  String rqqty, double payp, Date rqdate) {
		this.username = username;
		this.lid = lid;
		this.rname = rname;
		this.rqdate = rqdate;
		this.rqqty = rqqty;
		this.payp = payp;
	}

	public String getUsername() {
		return username;
	}

	public String getLid() {
		return lid;
	}

	public String getRname() {
		return rname;
	}

	public Date getRqdate() {
		return rqdate;
	}

	public String getRqqty() {
		return rqqty;
	}

	public double getPayp() {
		return payp;
	}
	
	
     
}
