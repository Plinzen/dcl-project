package de.schott.gae.football;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.*;

import de.schott.gae.football.dao.jpa.EMF;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.dto.League;
import de.schott.gae.football.dto.Team;

@SuppressWarnings("serial")
public class DCLProjectServlet extends HttpServlet {
	
	private EntityManager em;
	
	public DCLProjectServlet(){
		em = EMF.getEntityManager();
	}
	
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/plain");
		PrintWriter writer = resp.getWriter();
		
		EntityTransaction txn = em.getTransaction();
		txn.begin();
		
		League l = new League();
		l.setName("1. Bundesliga");
		List<Team> teams = new ArrayList<Team>();
		
		Team fcb = new Team("FC Bayern München", l);
		fcb = em.merge(fcb);
		teams.add(fcb);
		
		Team bayer = new Team("Bayer Leverkusen", l);
		bayer = em.merge(bayer);
		teams.add(bayer);
		
		Team bvb = new Team("Borussia Dortmund", l);
		bvb = em.merge(bvb);
		teams.add(bvb);	
				
		Team s04 = new Team("Schalke 04", l);
		s04 = em.merge(s04);
		teams.add(s04);
		
		Team frank = new Team("Eintracht Frankfurt", l);
		frank = em.merge(frank);
		teams.add(frank);
		
		Team mainz = new Team("FSV Mainz 05", l);
		mainz = em.merge(mainz);
		teams.add(mainz);
		
		Team vfb = new Team("VFB Stuttgart", l);
		vfb = em.merge(vfb);
		teams.add(vfb);
		
		Team gladbach = new Team("Borussia Mönchengladbach", l);
		gladbach = em.merge(gladbach);
		teams.add(gladbach);
		
		Team sc = new Team("SC Freiburg", l);
		sc = em.merge(sc);
		teams.add(sc);
		
		Team h96 = new Team("Hannover 96", l);
		h96 = em.merge(h96);
		teams.add(h96);
		
		Team hsv = new Team("Hamburger SV", l);
		hsv = em.merge(hsv);
		teams.add(hsv);
		
		Team svw = new Team("SV Werder Bremen", l);
		svw = em.merge(svw);
		teams.add(svw);
		
		Team f95 = new Team("Fortuna Düsseldorf", l);
		f95 = em.merge(f95);
		teams.add(f95);
		
		Team fcn = new Team("FC Nürnberg", l);
		fcn = em.merge(fcn);
		teams.add(fcn);
		
		Team vfl = new Team("VFL Wolfsburg", l);
		vfl = em.merge(vfl);
		teams.add(vfl);
		
		Team hoff = new Team("1899 Hoffenheim", l);
		hoff = em.merge(hoff);
		teams.add(hoff);
		
		Team fca = new Team("FC Augsburg", l);
		fca = em.merge(fca);
		teams.add(fca);
		
		Team fuert = new Team("Greuther Fürth", l);
		fuert = em.merge(fuert);
		teams.add(fuert);
		
		l.setTeams(teams);
		l = em.merge(l);		
		txn.commit();
		
		// Create games:
		EntityTransaction txn2 = em.getTransaction();
		txn2.begin();
		
		List<Game> games = l.getGames();
		
		Calendar cal = Calendar.getInstance(Locale.GERMANY);
		cal.set(2012, 8, 20);
		Date time = cal.getTime();
		
		Game g1 = new Game(time, l, bvb.getId(), fcb.getId(), 3L, 1L);
		em.persist(g1);
		games.add(g1);
		
		Game g2 = new Game(time, l, fcn.getId(), fuert.getId(), 1L, 0L);
		em.persist(g2);
		games.add(g2);
		
		Game g3 = new Game(time, l, h96.getId(), hsv.getId(), 2L, 2L);
		em.persist(g3);
		games.add(g3);
		
		Game g4 = new Game(time, l, vfb.getId(), svw.getId(), 3L , 4L);
		em.persist(g4);
		games.add(g4);
		
		Game g5 = new Game(time, l, vfl.getId(), f95.getId(), 1L, 1L);
		em.persist(g5);
		games.add(g5);

		Game g6 = new Game(time, l, fca.getId(), gladbach.getId(), 2L, 0L);
		em.persist(g6);
		games.add(g6);
		
		Game g7 = new Game(time, l, sc.getId(), hoff.getId(), 3L, 0L);
		em.persist(g7);
		games.add(g7);
		
		Game g8 = new Game(time, l, s04.getId(), bayer.getId(), 1L, 0L);
		em.persist(g8);
		games.add(g8);

		Game g9 = new Game(time, l, frank.getId(), mainz.getId(), 0L, 0L);
		em.persist(g9);
		games.add(g9);	
		
		em.merge(l);
		
		txn2.commit();
		
		writer.print("Installation-Done");
	}
}
