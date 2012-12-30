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

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Key;

/**
 * DTO for comments
 * 
 * @author Christopher Schott
 *
 */
@Entity(name="Comment")
@PersistenceCapable(table="Comment")
public class Comment {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;
	/**
	 * Unowned: Game-reference.
	 */
	@Persistent
	private Key game;
	/**
	 * Owned: Account reference
	 */
	@Persistent
	private Account account;

	private Long minute;
	private String message;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}
	
	@ManyToOne
	public Key getGame() {
		return game;
	}

	public void setGame(Key game) {
		this.game = game;
	}

	@ManyToOne
	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Long getMinute() {
		return minute;
	}

	public void setMinute(Long minute) {
		this.minute = minute;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
