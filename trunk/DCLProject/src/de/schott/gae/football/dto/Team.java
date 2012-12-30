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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.Key;

/**
 * Dto  for a Team.
 * 
 * @author Christopher Schott
 *
 */
@Entity(name="Team")
@PersistenceCapable(table="Team")
public class Team {
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)	
	private Key id;
	@Persistent
	private String name;
	@Persistent
	private League league;
	@Persistent
	private Blob logo;
	
	public Team(){
		
	}
	
	public Team(String name, League league){
		setName(name);
		setLeague(league);
	}
	
	public Team(String name, League league, Blob logo){
		setName(name);
		setLeague(league);
		setLogo(logo);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}
	
	public void setId(Key id) {
		this.id = id;
	}
	
	@Column(name="name")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	@ManyToOne
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

	
	public Blob getLogo() {
		return logo;
	}

	public void setLogo(Blob logo) {
		this.logo = logo;
	}

}
