package application.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class ConsumptionTreshold implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	
	private int consumptionLow;
	private int consumptionHigh;
	private double percentage;
	
	public int getConsumptionLow() {
		return consumptionLow;
	}
	public void setConsumptionLow(int consumptionLow) {
		this.consumptionLow = consumptionLow;
	}
	public int getConsumptionHigh() {
		return consumptionHigh;
	}
	public void setConsumptionHigh(int consumptionHigh) {
		this.consumptionHigh = consumptionHigh;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getPercentage() {
		return percentage;
	}
	public void setPercentage(double percentage) {
		this.percentage = percentage;
	}
	
	
}
