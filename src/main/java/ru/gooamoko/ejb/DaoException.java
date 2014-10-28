package ru.gooamoko.ejb;

public class DaoException extends Exception {

  private static final long serialVersionUID = 1L;

  public DaoException(String message) {
    super(message);
  }

  public DaoException(Exception e) {
    super("Exception " + e.getClass() + " with message " + e.getMessage());
  }

}
