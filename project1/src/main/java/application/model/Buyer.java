package application.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Buyer extends User implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

	public Buyer(){
		
	}
	
	public Buyer(User u){
		super();
		setUsername(u.getUsername());
		setPassword(u.getPassword());
		setRegistrationDate(u.getRegistrationDate());
		setRole(Role.BUYER);
		setDeliveryAddress("Adresa");
		setPrizePoints(0);
	}
	
	
	private String deliveryAddress;
	private int prizePoints;
	@OneToOne
	private BuyerCategory buyerCategory;
	@OneToMany(cascade = CascadeType.ALL,mappedBy="buyer")
	//@JsonManagedReference
	//@JsonBackReference
	@JsonIgnore
	private List<Receipt> shoppingHistory=new ArrayList<>();
	
	
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public int getPrizePoints() {
		return prizePoints;
	}
	public void setPrizePoints(int prizePoints) {
		this.prizePoints = prizePoints;
	}
	public BuyerCategory getBuyerCategory() {
		return buyerCategory;
	}
	public void setBuyerCategory(BuyerCategory buyerCategory) {
		this.buyerCategory = buyerCategory;
	}
	public List<Receipt> getShoppingHistory() {
		return shoppingHistory;
	}
	public void setShoppingHistory(List<Receipt> shoppingHistory) {
		this.shoppingHistory = shoppingHistory;
	}
	
}
