package ru.gooamoko.dao;

import java.util.List;

import org.hibernate.Query;

import ru.gooamoko.model.Traffic;

public class TrafficDao extends GenericDao {
	@SuppressWarnings("unchecked")
	public List<Traffic> fetchAll() {
		begin();
		List<Traffic> list = session.createQuery("from Traffic").list();
		commit();
		return list;
	}

	public Traffic get(int id) throws DaoException {
		try {
			Traffic result = null;
			begin();
			Query query = session.createQuery(
					"from Traffic where stk_pcode=:id").setInteger("id", id);
			result = (Traffic) query.uniqueResult();
			if ((null == result)) {
				throw new DaoException("Traffic with identifier " + id
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

	public void save(Traffic item) {
		begin();
		session.save(item);
		commit();
	}

	public void Traffic(Traffic item) {
		begin();
		session.delete(item);
		commit();
	}
}