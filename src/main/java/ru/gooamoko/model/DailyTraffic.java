package ru.gooamoko.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name="daily")
public class DailyTraffic implements Serializable {
	@Id
	@Column(name="day_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="day_date", nullable=false, columnDefinition="date NOT NULL DEFAULT current_date")
  @Temporal(javax.persistence.TemporalType.DATE)
	private Date date;
	
	@Column(name="day_hstcode", nullable=false)
	private int hostCode;
	
	@Column(name="day_mbytes", nullable=false, columnDefinition="numeric(11,2) NOT NULL DEFAULT 0")
	private float mbytes;

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public int getHostCode() {
    return hostCode;
  }

  public void setHostCode(int hostCode) {
    this.hostCode = hostCode;
  }

  public float getMbytes() {
    return mbytes;
  }

  public void setMbytes(float mbytes) {
    this.mbytes = mbytes;
  }

  public int getId() {
    return id;
  }
}
