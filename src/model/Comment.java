package model;

public class Comment {

	private int cid;
	private double score;
	private String comment;
	
	public Comment(int cid, double score, String comment)
	{
	  	this.cid=cid;
	  	this.score=score;
	  	this.comment=comment;
	}
	
	public int getCid() {
		return cid;
	}
	public double getScore() {
		return score;
	}
	public String getComment() {
		return comment;
	}
}

