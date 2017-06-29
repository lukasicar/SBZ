package application.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ProductCategory implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)*/
	@Id
	private String code;
	private String name;
	//@JsonBackReference
	@JsonIgnore
	private ProductCategory aboveCategory;
	private double maximumDiscount;
	
	/*
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	*/
	
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
	public ProductCategory getAboveCategory() {
		return aboveCategory;
	}
	public void setAboveCategory(ProductCategory aboveCategory) {
		this.aboveCategory = aboveCategory;
	}
	public double getMaximumDiscount() {
		return maximumDiscount;
	}
	public void setMaximumDiscount(double maximumDiscount) {
		this.maximumDiscount = maximumDiscount;
	}
	
	
}
