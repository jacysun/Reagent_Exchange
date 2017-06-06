package model;

import java.sql.Date;

public class Review {
	private String username;
	private String lid;
	private String rname;
	private Date rvdate;
	private int cid;
	
	public Review (String username, String lid, String rname, Date rvdate, int cid) {
		this.username = username;
		this.lid = lid;
		this.rname = rname;
		this.rvdate = rvdate;
		this.cid = cid; 
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

	public Date getRvdate() {
		return rvdate;
	}

	public int getCid() {
		return cid;
	}
    

}
