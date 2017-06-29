package application.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
@Entity
public class ReceiptDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public enum DiscountLabel{regular,extra};
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)
	private String code;
	@ManyToOne
	//@JsonBackReference
//	@JsonIgnore
	private Receipt receipt;
	private double discount;
	private DiscountLabel label;
	
	public ReceiptDiscount(){
		
	}
	
	public ReceiptDiscount(Receipt r,double disc,DiscountLabel dl){
		this.receipt=r;
		this.discount=disc;
		this.label=dl;
	}
	
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
	public Receipt getReceipt() {
		return receipt;
	}
	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		this.discount = discount;
	}
	public DiscountLabel getLabel() {
		return label;
	}
	public void setLabel(DiscountLabel label) {
		this.label = label;
	}
	
	
}
