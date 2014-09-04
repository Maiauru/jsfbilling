package ru.gooamoko.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="departments")
public class Department implements Serializable {
	
	@Id
	@Column(name="dep_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="dep_name", nullable=false, length=50)
	private String name;
	
	@OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="hst_depcode")
	private List<Host> hosts;
  
  // Балланс группы
  private transient float ballance = 0;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Host> getHosts() {
		return hosts;
	}

	public void setHosts(List<Host> hosts) {
		this.hosts = hosts;
	}

  public float getBallance() {
    return ballance;
  }

  public void setBallance(float ballance) {
    this.ballance = ballance;
  }
}
