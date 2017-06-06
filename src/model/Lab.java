package model;

public class Lab {
      private String lid;
      private String lname;
      private String institute;
      private String address;
      private String rsarea;
      private double pbalance;
      
	  public Lab(String lid, String lname, String institute, String address, String rsarea, double pbalance) {
	
		this.lid = lid;
		this.lname = lname;
		this.institute = institute;
		this.address = address;
		this.rsarea = rsarea;
		this.pbalance = pbalance;
	 }

	public String getLid() {
		return lid;
	}

	public String getLname() {
		return lname;
	}
	
	public String getRsarea() {
		return rsarea;
	}

	public String getInstitute() {
		return institute;
	}
	
	public String getAddress() {
		return address;
	}

	public double getPbalance() {
		return pbalance;
	}
	  
      
}

