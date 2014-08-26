package ru.gooamoko.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="daily")
public class DailyTraffic {
	@Id
	@Column(name="day_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="day_date", nullable=false, columnDefinition="date NOT NULL DEFAULT current_date")
	private Date date;
	
	@Column(name="day_hstcode", nullable=false)
	private int hostCode;
	
	@Column(name="day_mbytes", nullable=false, columnDefinition="numeric(11,2) NOT NULL DEFAULT 0")
	private float mbytes;
	
	
}
