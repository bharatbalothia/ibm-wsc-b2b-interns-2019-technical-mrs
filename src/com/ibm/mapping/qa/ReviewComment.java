package com.ibm.mapping.qa;

import java.util.ArrayList;

public class ReviewComment {
	String directoryName;
	ArrayList<String> comments;
	public ReviewComment() {
		directoryName = "";
		comments = new ArrayList<String>();
	}
	public void setDirectoryName(String directoryName) {
		this.directoryName= directoryName;
	}
	public void addReviewComment(String comment) {
		comments.add(comment);
	}
	public void changeReviewComment(int index, String comment) {
		comments.set(index, comment);
	}
	public void appendReviewComment(int index, String comment) {
		comments.set(index, comments.get(index) + " " + comment);
	}
	public ArrayList<String> getReviewComments() {
		return this.comments;
	}
	public String getDirectoryName() {
		return this.directoryName;
	}
}
