package de.schott.gae.dto;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@Entity
public class Account {
	
	private Key id;	
	private User googleAccount;	
	private List<Comment> comments;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	@Column(name="googleAccount")
	public User getGoogleAccount() {
		return googleAccount;
	}

	public void setGoogleAccount(User googleAccount) {
		this.googleAccount = googleAccount;
	}

	@OneToMany(mappedBy="account")
	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	
	
	
}
