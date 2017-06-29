package application.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;


@Entity
public class Product implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum StatusOfRecord{Archived,Active};
	
	/*@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)*/
	@Id
	private String code;
	private String name;
	@OneToOne
	private ProductCategory category;
	private double price;
	private int inStock;
	private Date recordCreationDate;
	private boolean fillTheStock;
	private StatusOfRecord status;
	private int minimumInStock;
	
	/*
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}*/
	
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
	public ProductCategory getCategory() {
		return category;
	}
	public void setCategory(ProductCategory category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getInStock() {
		return inStock;
	}
	public void setInStock(int inStock) {
		this.inStock = inStock;
	}
	public Date getRecordCreationDate() {
		return recordCreationDate;
	}
	public void setRecordCreationDate(Date recordCreationDate) {
		this.recordCreationDate = recordCreationDate;
	}
	public boolean isFillTheStock() {
		return fillTheStock;
	}
	public void setFillTheStock(boolean fillTheStock) {
		this.fillTheStock = fillTheStock;
	}
	public StatusOfRecord getStatus() {
		return status;
	}
	public void setStatus(StatusOfRecord status) {
		this.status = status;
	}
	public int getMinimumInStock() {
		return minimumInStock;
	}
	public void setMinimumInStock(int minimumInStock) {
		this.minimumInStock = minimumInStock;
	}

	public boolean isCategory(String catCode){
		return helpingFunction(category, catCode);
	}
	
	public boolean helpingFunction(ProductCategory c,String catCode){
		while(c!=null){
			if(c.getCode().equals(catCode)){
				return true;
			}else{
				c=c.getAboveCategory();
			}
		}
		return false;
	}
	
	
}
