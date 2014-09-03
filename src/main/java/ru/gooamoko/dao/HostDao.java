package ru.gooamoko.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import ru.gooamoko.model.Department;

import ru.gooamoko.model.Host;

public class HostDao extends GenericDao {

  @SuppressWarnings("unchecked")
  public List<Host> fetchAll() {
    begin();
    List<Host> list = session.createQuery("from Host").list();
    commit();
    return list;
  }

  public List<Host> fetchForGroup(Department grp) throws DaoException {
    try {
      begin();
      Query query = session.createQuery("from Host where hst_grpcode=:grp")
          .setInteger("grp", grp.getId());
      List<Host> result = query.list();
      if (null == result) {
        result = new ArrayList<>();
      }
      commit();
      return result;
    } catch (Exception e) {
      rollback();
      throw new DaoException("Exception " + e.getClass().getName()
          + " thrown with message " + e.getMessage());
    }
  }

  public Host get(int id) throws DaoException {
    try {
      Host result;
      begin();
      Query query = session.createQuery("from Host where hst_pcode=:id")
          .setInteger("id", id);
      result = (Host) query.uniqueResult();
      if (null == result) {
        throw new DaoException("Host with identifier " + id
            + " not found!");
      }
      commit();
      return result;
    } catch (Exception e) {
      rollback();
      throw new DaoException("Exception " + e.getClass().getName()
          + " thrown with message " + e.getMessage());
    }
  }

  public Host get(int net, int addr) throws DaoException {
    try {
      Host result;
      begin();
      Query query = session.createQuery("from Host where (hst_net=:net) and (hst_addr=:addr)")
          .setInteger("net", net)
          .setInteger("addr", addr);
      result = (Host) query.uniqueResult();
      if (null == result) {
        throw new DaoException("Host with IP 192.168." + net + "." + addr
            + " not found!");
      }
      commit();
      return result;
    } catch (Exception e) {
      rollback();
      throw new DaoException("Exception " + e.getClass().getName()
          + " thrown with message " + e.getMessage());
    }
  }

  public void save(Host item) {
    begin();
    session.saveOrUpdate(item);
    commit();
  }

  public void delete(Host item) {
    begin();
    session.delete(item);
    commit();
  }
}
