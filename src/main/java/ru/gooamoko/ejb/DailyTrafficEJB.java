package ru.gooamoko.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import ru.gooamoko.model.DailyTraffic;

@Stateless
public class DailyTrafficEJB {

  @PersistenceContext(unitName = "netstatPU")
  EntityManager em;

  public List<DailyTraffic> fetchAll() throws DaoException {
    try {
      TypedQuery<DailyTraffic> q = em.createQuery("SELECT dt FROM DailyTraffic dt", DailyTraffic.class);
      return q.getResultList();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public DailyTraffic get(int id) throws DaoException {
    try {
      DailyTraffic result = em.find(DailyTraffic.class, id);
      if ((null == result)) {
        throw new DaoException("DailyTraffic with identifier " + id
                + " not found!");
      }
      return result;
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void save(DailyTraffic item) throws DaoException {
    try {
      em.persist(item);
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void delete(DailyTraffic item) throws DaoException {
    try {
      DailyTraffic dt = em.find(DailyTraffic.class, item.getId());
      if (dt != null) {
        em.remove(dt);
      } else {
        throw new DaoException("DailyTraffic record for deleting not found!");
      }
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
