package application.model;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class BuyerCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique = true)*/
	@Id
	private String code;
	private String name;
    @OneToMany(fetch = FetchType.EAGER, cascade=CascadeType.ALL)
	private List<ConsumptionTreshold> consumptionTresholds;
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<ConsumptionTreshold> getConsumptionTresholds() {
		return consumptionTresholds;
	}
	public void setConsumptionTresholds(List<ConsumptionTreshold> consumptionTresholds) {
		this.consumptionTresholds = consumptionTresholds;
	}
	/*
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	
	
}
