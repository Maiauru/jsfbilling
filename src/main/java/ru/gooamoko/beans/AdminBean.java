package ru.gooamoko.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import ru.gooamoko.ejb.DaoException;
import ru.gooamoko.ejb.DepartmentEJB;
import ru.gooamoko.ejb.HostEJB;
import ru.gooamoko.model.Department;
import ru.gooamoko.model.Host;

public class AdminBean implements Serializable {

  @EJB
  private transient DepartmentEJB departmentDao;
  @EJB
  private transient HostEJB hostDao;
  private transient Department group;
  private transient Host host;
  private boolean editGroup = false;
  private boolean deleteGroup = false;
  private boolean editHost = false;
  private boolean deleteHost = false;
  private boolean ballanceHost = false;
  private boolean ballanceGroup = false;
  private String errorMessage = null;
  private boolean error = false;

  private void resetFlags() {
    editGroup = false;
    editHost = false;
    deleteGroup = false;
    deleteHost = false;
    ballanceGroup = false;
    ballanceHost = false;
    error = false;
  }

  public AdminBean() {
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

  public boolean isBallanceHost() {
    return ballanceHost;
  }

  public boolean isBallanceGroup() {
    return ballanceGroup;
  }

  public boolean isEdit() {
    return editGroup || editHost;
  }

  public boolean isDelete() {
    return deleteGroup || deleteHost;
  }

  public boolean isBallance() {
    return ballanceGroup || ballanceHost;
  }

  public boolean isShow() {
    return !isEdit() && !isDelete() && !isBallance();
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
    try {
      return departmentDao.fetchAll();
    } catch (DaoException e) {
      errorMessage = e.getMessage();
      error = true;
      return new ArrayList<>();
    }
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
    try {
      resetFlags();
      departmentDao.delete(group);
    } catch (DaoException e) {
      errorMessage = e.getMessage();
      error = true;
    }
  }

  public void deleteHost() {
    try {
    resetFlags();
    hostDao.delete(host);
    } catch (DaoException e) {
      error = true;
      errorMessage = e.getMessage();
    }
  }

  public void saveGroup() {
    try {
      resetFlags();
      departmentDao.save(group);
    } catch (DaoException e) {
      error = true;
      errorMessage = e.getMessage();
    }
  }

  public void saveHost() {
    try {
    resetFlags();
    hostDao.save(host);
    } catch (DaoException e) {
      
    }
  }

  public void ballanceGroup(Department item) {
    resetFlags();
    ballanceGroup = true;
    group = item;
  }

  public void ballanceHost(Host item) {
    resetFlags();
    ballanceHost = true;
    host = item;
  }

  public void setBallanceGroup() {
    try {
      resetFlags();
      departmentDao.setBallance(group, group.getBallance());
    } catch (DaoException e) {
      error = true;
      errorMessage = e.getMessage();
    }
  }

  public void setBallanceHost() throws DaoException {
    try {
    resetFlags();
    hostDao.save(host);
    } catch (DaoException e) {
      error = true;
      errorMessage = e.getMessage();
    }
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
    host.setDepartmentId(group.getId());
  }

  public Department getGroup() {
    return group;
  }

  public Host getHost() {
    return host;
  }
}
