package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import application.model.ReceiptDiscount.DiscountLabel;
@Entity
public class ReceiptItem implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	//@Column(unique=true)
	@ManyToOne
	@JsonBackReference
	//@JsonIgnore
	private Receipt receipt;
	private int ordinalNumber;
	@OneToOne
	private Product product;
	private double price;
	private int amount;
	private double originalSumPrice;
	private double discount;
	private double finalPrice;
	@OneToMany(cascade = CascadeType.ALL,mappedBy="receiptItem")
	@JsonIgnore
	//@JsonManagedReference
	private List<ReceiptItemDiscount> appliedDiscounts=new ArrayList<>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public Receipt getReceipt() {
		return receipt;
	}
	public void setReceipt(Receipt receipt) {
		this.receipt = receipt;
	}
	public int getOrdinalNumber() {
		return ordinalNumber;
	}
	public void setOrdinalNumber(int ordinalNumber) {
		this.ordinalNumber = ordinalNumber;
	}
	public Product getProduct() {
		return product;
	}
	public void setProduct(Product product) {
		this.product = product;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public double getOriginalSumPrice() {
		return originalSumPrice;
	}
	public void setOriginalSumPrice(double originalSumPrice) {
		this.originalSumPrice = originalSumPrice;
	}
	public double getDiscount() {
		return discount;
	}
	public void setDiscount(double discount) {
		if(discount<product.getCategory().getMaximumDiscount())
			this.discount = discount;
		else
			this.discount=product.getCategory().getMaximumDiscount();
	}
	public List<ReceiptItemDiscount> getAppliedDiscounts() {
		return appliedDiscounts;
	}
	public void setAppliedDiscounts(List<ReceiptItemDiscount> appliedDiscounts) {
		this.appliedDiscounts = appliedDiscounts;
	}
	
	/*public double resolveBiggestDiscount(){
		if(getIndexOfFirstRegularDiscount()==-1){
			return 0;
		}
		double biggest=getIndexOfFirstRegularDiscount();
		for(ReceiptItemDiscount rit:appliedDiscounts){
			System.out.println(rit.getDiscount());
			if(rit.getDiscount()>biggest && rit.getLabel().equals(DiscountLabel.regular))
				biggest=rit.getDiscount();
		}
		System.out.println(biggest);
		return biggest;
	}
	
	public int getIndexOfFirstRegularDiscount(){
		for(ReceiptItemDiscount rid : appliedDiscounts){
			System.out.println("eeej "+rid.getDiscount()+rid.getLabel());
			if(rid.getLabel().equals(DiscountLabel.regular)){
				return appliedDiscounts.indexOf(rid);
			}
		}
		return -1;
	}*/
	
	public boolean iisBuyedinLast15Days(){
		Date now=new Date();
		for(Receipt rec : receipt.getBuyer().getShoppingHistory()){
			for(ReceiptItem ritem : rec.getItems()){
				if(ritem.getProduct().getCode().equals(product.getCode())){
					if((System.currentTimeMillis() - rec.getDate().getTime()) / (24 * 60 * 60 * 1000d)<15){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public boolean iisBuyedinLast30Days(){
		for(Receipt rec : receipt.getBuyer().getShoppingHistory()){
			for(ReceiptItem ritem : rec.getItems()){
				if(ritem.getProduct().getCategory().getCode().equals(product.getCategory().getCode())){
					if((System.currentTimeMillis() - rec.getDate().getTime()) / (24 * 60 * 60 * 1000d)<30){
						return true;
					}
				}
			}
		}
		return false;
	}
	public double getFinalPrice() {
		return originalSumPrice*(100-discount)/100;
	}
	public void setFinalPrice(double finalPrice) {
		this.finalPrice = finalPrice;
	}
}
