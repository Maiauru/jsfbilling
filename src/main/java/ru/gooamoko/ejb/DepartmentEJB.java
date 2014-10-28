package ru.gooamoko.ejb;

import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import ru.gooamoko.model.Department;

@Stateless
public class DepartmentEJB {

  @PersistenceContext(unitName = "netstatPU")
  EntityManager em;

  @SuppressWarnings("unchecked")
  public List<Department> fetchAll() throws DaoException {
    try {
      TypedQuery<Department> q = em.createQuery("SELECT d FROM Department d", Department.class);
      return q.getResultList();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public Department get(int id) throws DaoException {
    try {
      Department result = em.find(Department.class, id);
      if ((null == result)) {
        throw new DaoException("Department with identifier " + id
                + " not found!");
      }
      return result;
    } catch (Exception e) {
      throw new DaoException(e);
    }

  }

  public void save(Department item) throws DaoException {
    try {
      em.persist(item);
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void delete(Department item) throws DaoException {
    try {
      Department dep = em.find(Department.class, item.getId());
      if (dep != null) {
        em.remove(dep);
      } else {
        throw new DaoException("Department record for deleting not found!");
      }
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }

  public void setBallance(Department item, float ballance) throws DaoException {
    try {
      Query query = em.createNativeQuery("UPDATE hosts SET hst_ballance=:ballance WHERE (hst_depcode=:depcode)");
      query.setParameter("ballance", ballance);
      query.setParameter("depcode", item.getId());
      query.executeUpdate();
    } catch (Exception e) {
      throw new DaoException(e);
    }
  }
}
