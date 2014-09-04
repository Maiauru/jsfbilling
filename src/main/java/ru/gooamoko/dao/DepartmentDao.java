package ru.gooamoko.dao;

import java.util.List;

import org.hibernate.Query;

import ru.gooamoko.model.Department;

public class DepartmentDao extends GenericDao {
  
  @SuppressWarnings("unchecked")
  public List<Department> fetchAll() {
    begin();
    List<Department> list = session.createQuery("from Department").list();
    commit();
    return list;
  }

  public Department get(int id) throws DaoException {
    Department result;
    begin();
    Query query = session.createQuery("from Department where dep_pcode=:id")
            .setInteger("id", id);
    result = (Department) query.uniqueResult();
    commit();
    if ((null == result)) {
      throw new DaoException("Department with identifier " + id
              + " not found!");
    }
    return result;
  }

  public void save(Department item) {
    begin();
    session.saveOrUpdate(item);
    commit();
  }

  public void delete(Department item) {
    begin();
    session.delete(item);
    commit();
  }

  public void setBallance(Department item, float ballance) {
    begin();
    Query query = session.createSQLQuery("UPDATE hosts SET hst_ballance=:ballance WHERE (hst_depcode=:depcode)");
    query.setFloat("ballance", ballance);
    query.setInteger("depcode", item.getId());
    query.executeUpdate();
    commit();
    // TODO добавить логики
  }

}
