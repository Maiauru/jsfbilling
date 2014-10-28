package ru.gooamoko.ejb;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import ru.gooamoko.model.Department;
import ru.gooamoko.model.Host;

@Stateless
public class HostEJB {

  @PersistenceContext(unitName = "netstatPU")
  EntityManager em;

  public List<Host> fetchAll() throws DaoException {
    try {
      TypedQuery<Host> q = em.createQuery("SELECT h FROM Host h", Host.class);
      return q.getResultList();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public List<Host> fetchForGroup(Department grp) throws DaoException {
    try {
      Query query = em.createQuery("SELECT h FROM Host h where h.departmentId = :grp");
      query.setParameter("grp", grp.getId());
      List<Host> result = query.getResultList();
      if (null == result) {
        result = new ArrayList<>();
      }
      return result;
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public Host get(int id) throws DaoException {
    try {
      Host result = em.find(Host.class, id);
      if (null == result) {
        throw new DaoException("Host with identifier " + id
                + " not found!");
      }
      return result;
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public Host get(int net, int addr) throws DaoException {
    try {
      Host result;
      TypedQuery<Host> query = em.createQuery("SELECT h FROM Host h WHERE (h.net = :net) and (h.addr = :addr)", Host.class);
      query.setParameter("net", net);
      query.setParameter("addr", addr);
      result = query.getSingleResult();
      if (null == result) {
        throw new DaoException("Host with IP 192.168." + net + "." + addr
                + " not found!");
      }
      return result;
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void save(Host item) throws DaoException {
    try {
      em.persist(item);
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void delete(Host item) throws DaoException {
    try {
      Host h = em.find(Host.class, item.getId());
      if (h != null) {
        em.remove(h);
      } else {
        throw new DaoException("Host not found while deleting!");
      }
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
