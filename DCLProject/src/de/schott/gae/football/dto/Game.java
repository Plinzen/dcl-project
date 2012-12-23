package de.schott.gae.football.dto;

import java.util.Date;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

@Entity(name="Game")
@PersistenceCapable(table="Game")
public class Game{
	
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	private Key id;

	@Persistent
	private Date date;
	@Persistent
	private League league;
	@Persistent
	private Key team1;
	@Persistent
	private Key team2;

	@Persistent
	private Long goals1;
	@Persistent
	private Long goals2;
	
	public Game(){
		
	}
	
	public Game(Date date, League league, Key team1, Key team2, Long goals1, Long goals2){
		setDate(date);
		setLeague(league);
		setTeam1(team1);
		setTeam2(team2);
		setGoals1(goals1);
		setGoals2(goals2);
	}

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Key getId() {
		return id;
	}

	public void setId(Key id) {
		this.id = id;
	}

	public Key getTeam1() {
		return team1;
	}

	public void setTeam1(Key team1) {
		this.team1 = team1;
	}

	public Key getTeam2() {
		return team2;
	}

	public void setTeam2(Key team2) {
		this.team2 = team2;
	}

	public Long getGoals1() {
		return goals1;
	}

	public void setGoals1(Long goals1) {
		this.goals1 = goals1;
	}

	public Long getGoals2() {
		return goals2;
	}

	public void setGoals2(Long goals2) {
		this.goals2 = goals2;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@ManyToOne
	public League getLeague() {
		return league;
	}

	public void setLeague(League league) {
		this.league = league;
	}

}
