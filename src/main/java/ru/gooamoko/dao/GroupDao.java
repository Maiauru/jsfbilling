package ru.gooamoko.dao;

import java.util.List;

import org.hibernate.Query;

import ru.gooamoko.model.Group;

public class GroupDao extends GenericDao {
	@SuppressWarnings("unchecked")
	public List<Group> fetchAll() {
		begin();
		List<Group> list = session.createQuery("from Group").list();
		commit();
		return list;
	}

	public Group get(int id) throws DaoException {
		try {
			Group result = null;
			begin();
			Query query = session.createQuery("from Host where grp_pcode=:id")
					.setInteger("id", id);
			result = (Group) query.uniqueResult();
			if ((null == result)) {
				throw new DaoException("Group with identifier " + id
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

	public void save(Group item) {
		begin();
		session.save(item);
		commit();
	}

	public void delete(Group item) {
		begin();
		session.delete(item);
		commit();
	}
	
}
