package ru.gooamoko.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group {
	
	@Id
	@Column(name="grp_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@Column(name="grp_name", nullable=false, length=50)
	private String name;
	
	@OneToMany(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
	@OrderBy("hst_description")
	private List<Host> hosts;

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
	
	public void addHost(Host h) {
		hosts.add(h);
	}
}
