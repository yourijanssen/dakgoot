package com.dakgoot.dakgoot.model;

import jakarta.persistence.*;

@Entity
public class Gutter {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private String photoUrl;
	private String comments;
	private boolean photoAdded;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public boolean isPhotoAdded() {
		return photoAdded;
	}

	public void setPhotoAdded(boolean photoAdded) {
		this.photoAdded = photoAdded;
	}
}