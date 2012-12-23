package de.schott.gae.football.dto;

import java.util.ArrayList;
import java.util.List;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;

@Entity(name="Account")
@PersistenceCapable(table="Account")
public class Account {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;	
	@Persistent
	private User googleAccount;
	@Persistent(mappedBy="account")
	private List<Comment> comments;

	public Account(){
		comments = new ArrayList<Comment>();
	}
	
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
