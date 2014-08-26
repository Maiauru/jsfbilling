package ru.gooamoko.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="hosts")
public class Host {
	
	@Id
	@Column(name="hst_pcode")
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name="hst_grpcode", insertable=false, updatable=false)
	private Group group;
	
	@Column(name="hst_description", length=50, nullable=false)
	private String description;
	
	@Column(name="hst_net", nullable=false)
	private short net;
	
	@Column(name="hst_addr", nullable=false)
	private short addr;
	
	@Column(name="hst_ballance", nullable=false, columnDefinition="numeric(11,2) NOT NULL DEFAULT 0")
	private float ballance;
	
	@Column(name="hst_price", nullable=false, columnDefinition="numeric(5,2) NOT NULL DEFAULT 0")
	private float price;
	
	@Column(name="hst_enabled", nullable=false, columnDefinition="boolean NOT NULL DEFAULT false")
	private boolean enabled;
	
	@Column(name="hst_still", nullable=false, columnDefinition="boolean NOT NULL DEFAULT false")
	private boolean steel;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public short getNet() {
		return net;
	}

	public void setNet(short net) {
		this.net = net;
	}

	public short getAddr() {
		return addr;
	}

	public void setAddr(short addr) {
		this.addr = addr;
	}

	public float getBallance() {
		return ballance;
	}

	public void setBallance(float ballance) {
		this.ballance = ballance;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isSteel() {
		return steel;
	}

	public void setSteel(boolean steel) {
		this.steel = steel;
	}
}
