package ru.gooamoko.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.hibernate.Query;

import ru.gooamoko.model.Traffic;

@Stateless
public class TrafficDao {
  
  @PersistenceContext(unitName="netstatPU")
  EntityManager em;
  
	public List<Traffic> fetchAll() throws DaoException {
    try {
      TypedQuery<Traffic> q = em.createQuery("SELECT t FROM Traffic t", Traffic.class);
      return q.getResultList();
    } catch (Exception e) {
      throw new DaoException(e);
    }
	}

	public Traffic get(int id) throws DaoException {
		try {
			Traffic result = em.find(Traffic.class, id);
			if ((null == result)) {
				throw new DaoException("Traffic with identifier " + id
						+ " not found!");
			}
			return result;
		} catch (Exception e) {
			throw new DaoException(e);
		}
	}

	public void save(Traffic item) throws DaoException {
    try {
      em.persist(item);
    } catch (Exception e) {
      throw new DaoException(e);
    }
	}

	public void delete(Traffic item) throws DaoException {
    try {
      Traffic t = em.find(Traffic.class, item.getId());
      if (t != null) {
        em.remove(t);
      } else {
        throw new DaoException("Traffic item not fount while deleting!");
      }
    } catch (Exception e) {
      throw new DaoException(e);
    }
	}
}