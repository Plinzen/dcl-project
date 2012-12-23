/**
 * 
 */
package de.schott.gae.football.dao.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import com.google.appengine.api.datastore.Key;

import de.schott.gae.football.dao.ICommentDao;
import de.schott.gae.football.dto.Account;
import de.schott.gae.football.dto.Comment;
import de.schott.gae.football.dto.Game;
import de.schott.gae.football.shared.DatabaseException;

/**
 * Comment-DAO implementation for JPA.
 * 
 * @author plinzen
 *
 */
public class CommentDao implements ICommentDao {
	
	private EntityManager mEntityManager;
	
	CommentDao(){
		mEntityManager = EMF.getEntityManager();
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#get(com.google.appengine.api.datastore.Key)
	 */
	@Override
	public Comment get(Key id) throws DatabaseException {
		return mEntityManager.find(Comment.class, id);	
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getForUser(de.schott.gae.football.dto.Account)
	 */
	@Override
	public List<Comment> getForUser(Account user) throws DatabaseException {
		Query q = mEntityManager.createQuery("Select c from " + Comment.class.getName() + " c where c.account = ?1 order by c.minute");
		q.setParameter(1, user);
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = q.getResultList();
		return comments;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getAll()
	 */
	@Override
	public List<Comment> getAll() throws DatabaseException {
		List<Comment> comments = mEntityManager.createQuery("Select a from " + Comment.class.getName() + " a").getResultList();
		return comments;
	}
	
	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#persist(de.schott.gae.football.dto.Comment)
	 */
	@Override
	public Comment persist(Comment comment) throws DatabaseException {
		return mEntityManager.merge(comment);			
	}

	/*
	 * (non-Javadoc)
	 * @see de.schott.gae.football.dao.ICommentDao#getForGame(de.schott.gae.football.dto.Game)
	 */
	@Override
	public List<Comment> getForGame(Game game) throws DatabaseException {
		Query q = mEntityManager.createQuery("Select c from " + Comment.class.getName() + " c where c.game = ?1 order by c.minute");
		q.setParameter(1, game.getId());
		
		@SuppressWarnings("unchecked")
		List<Comment> comments = q.getResultList();
		return comments;
	}

	@Override
	public Comment persist(Key accountKey, Key gameId, Long minute,
			String message) {
		EntityTransaction txn = mEntityManager.getTransaction();
		txn.begin();
		// Merge Object, as they become attached to this EntityManager
		Account mergeAccount = mEntityManager.find(Account.class, accountKey);
		
		Comment comment = new Comment();
		comment.setAccount(mergeAccount);
		comment.setGame(gameId);
		comment.setMinute(minute);
		comment.setMessage(message);
		mEntityManager.persist(comment);
		
		mergeAccount.getComments().add(comment);
		mEntityManager.merge(mergeAccount);
		
		txn.commit();
		return comment;
	}

}
