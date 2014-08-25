package ru.gooamoko.beans;

import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import ru.gooamoko.dao.HostDao;
import ru.gooamoko.model.Host;

public class ClientUtils {
	
	private HostDao hostDao;

	public ClientUtils() {
		hostDao = new HostDao();
	}
	
	public String getClientAddress() {
		FacesContext context = FacesContext.getCurrentInstance();
		HttpServletRequest request = (HttpServletRequest)context.getExternalContext().getRequest();
		return request.getRemoteHost();
	}	
	
    public List<Host> getHosts() {
    	return hostDao.fetchAll();
    }

}
