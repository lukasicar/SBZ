package application.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Receipt {

	/**
	 * 
	 */
	//private static final long serialVersionUID = 1L;
	public enum ReceiptState{ordered,cancelled,realized}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)
	private String code;
	private Date date;
	@ManyToOne
	//@JsonBackReference
	//@JsonIgnore
	//@JsonManagedReference
	private Buyer buyer;
	private ReceiptState state;
	private double originalPrice;
	private double discount;
	private double finalPrice;
	private int numberOfSpentPoints;
	private int numberOfGainedPoints;
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receipt")
	@JsonIgnore
	private List<ReceiptDiscount> appliedDiscounts=new ArrayList<>();
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receipt")
	@JsonIgnore
	private List<ReceiptItem> items=new ArrayList<>();
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Buyer getBuyer() {
		return buyer;
	}
	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
	}
	public ReceiptState getState() {
		return state;
	}
	public void setState(ReceiptState state) {
		this.state = state;
	}
	public double getOriginalPrice() {
		return originalPrice;
	}
	public void setOriginalPrice(double originalPrice) {
		this.originalPrice = originalPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public double getFinalPrice() {
		return finalPrice;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
	public int getNumberOfSpentPoints() {
		return numberOfSpentPoints;
	}
	public void setNumberOfSpentPoints(int numberOfSpentPoints) {
		this.numberOfSpentPoints = numberOfSpentPoints;
	}
	public int getNumberOfGainedPoints() {
		return numberOfGainedPoints;
	}
	public void setNumberOfGainedPoints(int numberOfGainedPoints) {
		this.numberOfGainedPoints = numberOfGainedPoints;
	}
	
	public List<ReceiptDiscount> getAppliedDiscounts() {
		return appliedDiscounts;
	}
	public void setAppliedDiscounts(List<ReceiptDiscount> appliedDiscounts) {
		this.appliedDiscounts = appliedDiscounts;
	}
	public List<ReceiptItem> getItems() {
		return items;
	}
	public void setItems(List<ReceiptItem> items) {
		this.items = items;
	}
	
	
}
