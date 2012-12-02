package de.schott.gae.dto;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.google.appengine.api.datastore.Key;

@Entity
public class Game {

	private Key id;

	private Date date;
	private League league;
	private Team team1;
	private Team team2;

	private Integer goals1;
	private Integer goals2;
	
	public Game(){
		
	}
	
	public Game(Date date, League league, Team team1, Team team2, Integer goals1, Integer goals2){
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

	@ManyToOne
	public Team getTeam1() {
		return team1;
	}

	public void setTeam1(Team team1) {
		this.team1 = team1;
	}

	@ManyToOne
	public Team getTeam2() {
		return team2;
	}

	public void setTeam2(Team team2) {
		this.team2 = team2;
	}

	public Integer getGoals1() {
		return goals1;
	}

	public void setGoals1(Integer goals1) {
		this.goals1 = goals1;
	}

	public Integer getGoals2() {
		return goals2;
	}

	public void setGoals2(Integer goals2) {
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
