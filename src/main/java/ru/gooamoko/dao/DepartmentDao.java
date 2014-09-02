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
		try {
			Department result = null;
			begin();
			Query query = session.createQuery("from Department where grp_pcode=:id")
					.setInteger("id", id);
			result = (Department) query.uniqueResult();
			if ((null == result)) {
				throw new DaoException("Department with identifier " + id
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
  
  public void setBallance(float ballance) {
    // TODO добавить логики
  }

}
