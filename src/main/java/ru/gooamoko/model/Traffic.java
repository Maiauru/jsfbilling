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
@Table(name="stack")
public class Traffic implements Serializable {
	
	@Id
	@Column(name="stk_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="stk_timestamp", nullable=false, columnDefinition="timestamp NOT NULL DEFAULT current_timestamp")
  @Temporal(javax.persistence.TemporalType.DATE)
	private Date timestamp;
	
	@Column(name="stk_hstcode", nullable=false)
	private int hostCode;
	
	@Column(name="stk_kbytes", nullable=false, columnDefinition="numeric(8,2) NOT NULL DEFAULT 0")
	private float kbytes;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public int getHostCode() {
		return hostCode;
	}

	public void setHostCode(int hostCode) {
		this.hostCode = hostCode;
	}

	public float getKbytes() {
		return kbytes;
	}

	public void setKbytes(float kbytes) {
		this.kbytes = kbytes;
	}
}
