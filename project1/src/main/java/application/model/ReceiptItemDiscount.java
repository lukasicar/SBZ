package application.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;

import application.model.ReceiptDiscount.DiscountLabel;
@Entity
public class ReceiptItemDiscount implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(unique=true)
	private String code;
	@ManyToOne
	private Receipt receipt;
	@ManyToOne
	//@JsonBackReference
	//@JsonIgnore
	private ReceiptItem receiptItem;
	private double discount;
	private DiscountLabel label;
	
	public ReceiptItemDiscount(){
		
	}
	
	public ReceiptItemDiscount(Receipt receipt,ReceiptItem receiptItem,double discount,DiscountLabel dlabel){
		this.label=dlabel;
		this.receipt=receipt;
		this.receiptItem=receiptItem;
		this.discount=discount;
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
	public ReceiptItem getItem() {
		return receiptItem;
	}
	public void setItem(ReceiptItem item) {
		this.receiptItem = item;
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
