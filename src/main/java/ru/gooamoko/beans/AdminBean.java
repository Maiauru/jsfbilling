package ru.gooamoko.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import ru.gooamoko.dao.DaoException;

import ru.gooamoko.dao.DepartmentDao;
import ru.gooamoko.dao.HostDao;
import ru.gooamoko.model.Department;
import ru.gooamoko.model.Host;

public class AdminBean implements Serializable {
  private final transient DepartmentDao departmentDao;
	private final transient HostDao hostDao;
  private transient Department group;
  private transient Host host;
  private boolean editGroup = false;
	private boolean deleteGroup = false;
  private boolean editHost = false;
	private boolean deleteHost = false;
	private String errorMessage = null;
	private boolean error = false;
	
	private void resetFlags() {
		editGroup = false;
    editHost = false;
		deleteGroup = false;
    deleteHost = false;
		error = false;
	}
  
  public AdminBean() {
		departmentDao = new DepartmentDao();
    hostDao = new HostDao();
    resetFlags();
	}
	
  public void cancel() {
    resetFlags();
  }

	public boolean isEditGroup() {
		return editGroup;
	}

	public boolean isDeleteGroup() {
		return deleteGroup;
	}

  public boolean isEditHost() {
    return editHost;
  }

  public boolean isDeleteHost() {
    return deleteHost;
  }
  
  public boolean isEdit() {
    return editGroup || editHost;
  }
  
  public boolean isDelete() {
    return deleteGroup || deleteHost;
  }

	public boolean isShow() {
		return !isEdit() && !isDelete();
	}

  public boolean isError() {
		return error;
	}
	
	public String getErrorMessage() {
		return errorMessage;
	}
	
	public void editGroup(Department item) {
			resetFlags();
			editGroup = true;
			this.group = item;
	}
	
  public void editHost(Host item) {
    resetFlags();
    editHost = true;
    this.host = item;
  }
  
	public List<Department> getGroups() {
		return departmentDao.fetchAll();
	}
  
  public List<Host> getHosts(Department item) {
    try {
      return hostDao.fetchForGroup(item);
    } catch (DaoException e) {
      error = true;
      errorMessage = e.getMessage();
      // Пока возвращаем пустой список, но на будущее надо подумать об обработке исключения
      return new ArrayList<>();
    }
  }
	
	public void confirmDeleteGroup(Department item) {
			resetFlags();
			deleteGroup = true;
			this.group = item;
	}
  
  public void confirmDeleteHost(Host item) {
    resetFlags();
    deleteHost = true;
    this.host = item;
  }
	
	public void deleteGroup() {
			resetFlags();
			departmentDao.delete(group);
	}
  
  public void deleteHost() {
    resetFlags();
    hostDao.delete(host);
  }
	
	public void saveGroup() {
			resetFlags();
			departmentDao.save(group);
	}
  
  public void saveHost() {
    resetFlags();
    hostDao.save(host);
  }
	
	public void addGroup() {
		resetFlags();
		editGroup = true;
		group = new Department();
	}
  
  public void addHost(Department item) {
    resetFlags();
    group = item;
    editHost = true;
    host = new Host();
    host.setGroup(group);
  }
  
	public Department getGroup() {
		return group;
	}
  
  public Host getHost() {
    return host;
  }
}
