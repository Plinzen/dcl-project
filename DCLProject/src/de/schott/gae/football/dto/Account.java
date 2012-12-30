/*
Copyright 2012 Christopher Schott

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
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

/**
 * Entity storing user information
 * 
 * @author Christopher Schott
 *
 */
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
