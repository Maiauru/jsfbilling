package ru.gooamoko.beans;

import java.io.Serializable;
import java.util.List;

import ru.gooamoko.dao.GroupDao;
import ru.gooamoko.model.Group;

public class GroupBean implements Serializable {
  private transient GroupDao groupDao = null;
	private transient Group item = null;
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
	
	public void edit(Group item) {
		try {
			resetFlags();
			edit = true;
			this.item = item;
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public List<Group> getGroupList() {
		return groupDao.fetchAll();
	}
	
	public void confirmDelete(Group item) {
		try {
			resetFlags();
			delete = true;
			this.item = item;
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}	
	}
	
	public void delete() {
		try {
			resetFlags();
			groupDao.delete(item);
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public void save() {
		try {
			resetFlags();
			groupDao.save(item);
		} catch (Exception e) {
			error = true;
			errorMessage = e.getMessage();
		}
	}
	
	public void add() {
		resetFlags();
		edit = true;
		item = new Group();
	}
  
  public void cancel() {
    resetFlags();
  }

	public Group getItem() {
		return item;
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
	
	public boolean isShow() {
		return !edit && !delete;
	}
}
