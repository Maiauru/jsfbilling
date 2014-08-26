package ru.gooamoko.beans;

import java.util.List;

import ru.gooamoko.dao.DaoException;
import ru.gooamoko.dao.GroupDao;
import ru.gooamoko.model.Group;

public class GroupBean {
	private GroupDao groupDao = null;
	private Group group = null;
	private boolean edit = false;
	private boolean delete = false;
	private String errorMessage = null;
	private boolean error = false;
	
	public void resetFlags() {
		edit = false;
		delete = false;
		error = false;
	}
	
	public GroupBean() {
		groupDao = new GroupDao();
	}
	
	public void edit(int groupId) {
		try {
			resetFlags();
			edit = true;
			group = groupDao.get(groupId);
		} catch (DaoException e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public List<Group> getGroupList() {
		return groupDao.fetchAll();
	}
	
	public void confirm(int groupId) {
		try {
			resetFlags();
			delete = true;
			group = groupDao.get(groupId);
		} catch (DaoException e) {
			error = true;
			errorMessage = e.getMessage();
		}	
	}
	
	public void delete() {
		try {
			resetFlags();
			groupDao.delete(group);
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public void save() {
		try {
			resetFlags();
			groupDao.save(group);
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public void add() {
		resetFlags();
		edit = true;
		group = new Group();
	}

	public Group getGroup() {
		return group;
	}

	public boolean isEdit() {
		return edit;
	}

	public boolean isDelete() {
		return delete;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public boolean isError() {
		return error;
	}
	
	public boolean isList() {
		return !edit && !delete;
	}
}
