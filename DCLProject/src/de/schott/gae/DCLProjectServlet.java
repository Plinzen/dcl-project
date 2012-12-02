package de.schott.gae;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.http.*;

import de.schott.gae.dao.jpa.EMF;
import de.schott.gae.dto.Game;
import de.schott.gae.dto.League;
import de.schott.gae.dto.Team;

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
		List<Team> teams = l.getTeams();
		List<Game> games = l.getGames();
		
		Team fcb = new Team("FC Bayern München", l);
		em.persist(fcb);
		teams.add(fcb);
		
		Team bayer = new Team("Bayer Leverkusen", l);
		em.persist(bayer);
		teams.add(bayer);
		
		Team bvb = new Team("Borussia Dortmund", l);
		em.persist(bvb);
		teams.add(bvb);	
		
		
		Team s04 = new Team("Schalke 04", l);
		em.persist(s04);
		teams.add(s04);
		
		Team frank = new Team("Eintracht Frankfurt", l);
		em.persist(frank);
		teams.add(frank);
		
		Team mainz = new Team("FSV Mainz 05", l);
		em.persist(mainz);
		teams.add(mainz);
		
		Team vfb = new Team("VFB Stuttgart", l);
		em.persist(vfb);
		teams.add(vfb);
		
		Team gladbach = new Team("Borussia Mönchengladbach", l);
		em.persist(gladbach);
		teams.add(gladbach);
		
		Team sc = new Team("SC Freiburg", l);
		em.persist(sc);
		teams.add(sc);
		
		Team h96 = new Team("Hannover 96", l);
		em.persist(h96);
		teams.add(h96);
		
		Team hsv = new Team("Hamburger SV", l);
		em.persist(hsv);
		teams.add(hsv);
		
		Team svw = new Team("SV Werder Bremen", l);
		em.persist(svw);
		teams.add(svw);
		
		Team f95 = new Team("Fortuna Düsseldorf", l);
		em.persist(f95);
		teams.add(f95);
		
		Team fcn = new Team("FC Nürnberg", l);
		em.persist(fcn);
		teams.add(fcn);
		
		Team vfl = new Team("VFL Wolfsburg", l);
		em.persist(vfl);
		teams.add(vfl);
		
		Team hoff = new Team("1899 Hoffenheim", l);
		em.persist(hoff);
		teams.add(hoff);
		
		Team fca = new Team("FC Augsburg", l);
		em.persist(fca);
		teams.add(fca);
		
		Team fuert = new Team("Greuther Fürth", l);
		em.persist(fuert);
		teams.add(fuert);
		
		Calendar cal = Calendar.getInstance(Locale.GERMANY);
		cal.set(2012, 8, 20);
		Date time = cal.getTime();
		
		Game g1 = new Game(time, l, bvb, fcb, 3, 1);
		em.persist(g1);
		games.add(g1);
		
		Game g2 = new Game(time, l, fcn, fuert, 1, 0);
		em.persist(g2);
		games.add(g2);
		
		Game g3 = new Game(time, l, h96, hsv, 2, 2);
		em.persist(g3);
		games.add(g3);
		
		Game g4 = new Game(time, l, vfb, svw, 3 , 4);
		em.persist(g4);
		games.add(g4);
		
		Game g5 = new Game(time, l, vfl, f95, 1, 1);
		em.persist(g5);
		games.add(g5);

		Game g6 = new Game(time, l, fca, gladbach, 2, 0);
		em.persist(g6);
		games.add(g6);
		
		Game g7 = new Game(time, l, sc, hoff, 3, 0);
		em.persist(g7);
		games.add(g7);
		
		Game g8 = new Game(time, l, s04, bayer, 1, 0);
		em.persist(g8);
		games.add(g8);

		Game g9 = new Game(time, l, frank, mainz, 0, 0);
		em.persist(g9);
		games.add(g9);	
		
		em.persist(l);
		
		txn.commit();
		
		writer.print("Installation-Done");
	}
}
