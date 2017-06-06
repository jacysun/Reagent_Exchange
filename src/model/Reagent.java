package model;

import java.sql.Date;

public class Reagent {
	
	private String lid;
	private String rname;
	private String rtype;
	private String rsource;
    private Date expDate;
    private int scoreNum;
	private double avrscore;
	private String sqty;
	private double askp;
	//private InputStream image;
	
	public Reagent(String lid, String rname, String rsource, String rtype, Date expDate, int scoreNum, double avrscore, String sqty, double askp) {
		
		this.lid = lid;
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
	
}
