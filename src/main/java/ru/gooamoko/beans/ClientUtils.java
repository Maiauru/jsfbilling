package ru.gooamoko.beans;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import ru.gooamoko.ejb.DaoException;
import ru.gooamoko.ejb.HostEJB;
import ru.gooamoko.model.Host;

public class ClientUtils {

  private final HostEJB hostDao;
  private String clientAddress;
  private Host clientHost;
  private boolean unknown = false;

  public ClientUtils() {
    hostDao = new HostEJB();
    FacesContext context = FacesContext.getCurrentInstance();
    HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
    clientAddress = request.getRemoteHost();
    try {
      String[] octets = clientAddress.split("\\.");
      if (octets.length == 4) {
        // it's correct TCP/IPv4 address
        clientHost = hostDao.get(Integer.parseInt(octets[2]), Integer.parseInt(octets[3]));
      } else {
        // Seems like it's not correct IPv4 address
        unknown = true;
      }
    } catch (DaoException | NumberFormatException e) {
      // It's unknown host or something else.
      unknown = true;
    }
  }

  public String getClientAddress() {
    return clientAddress;
  }

  public Host getClientHost() {
    return clientHost;
  }

  public boolean isUnknown() {
    return unknown;
  }
}
