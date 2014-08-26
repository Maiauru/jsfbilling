package ru.gooamoko.dao;

import java.util.List;

import org.hibernate.Query;

import ru.gooamoko.model.DailyTraffic;

public class DailyTrafficDao extends GenericDao {
	@SuppressWarnings("unchecked")
	public List<DailyTraffic> fetchAll() {
		begin();
		List<DailyTraffic> list = session.createQuery("from DailyTraffic").list();
		commit();
		return list;
	}

	public DailyTraffic get(int id) throws DaoException {
		try {
			DailyTraffic result = null;
			begin();
			Query query = session.createQuery(
					"from DailyTraffic where day_pcode=:id").setInteger("id", id);
			result = (DailyTraffic) query.uniqueResult();
			if ((null == result)) {
				throw new DaoException("DailyTraffic with identifier " + id
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

	public void save(DailyTraffic item) {
		begin();
		session.save(item);
		commit();
	}

	public void Traffic(DailyTraffic item) {
		begin();
		session.delete(item);
		commit();
	}
}
