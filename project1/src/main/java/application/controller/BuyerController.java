package application.controller;

import java.util.List;
import java.util.Random;

import javax.servlet.http.HttpSession;

import org.kie.api.KieServices;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import application.model.ActionEvent;
import application.model.Buyer;
import application.model.Product;
import application.model.Receipt;
import application.model.Receipt.ReceiptState;
import application.model.ReceiptItem;
import application.model.User;
import application.repository.ActionEventDAO;
import application.repository.BuyerDAO;
import application.repository.ProductDAO;
import application.repository.ReceiptDAO;

@RequestMapping("/buyer")
@RestController
public class BuyerController {

	@Autowired
	private BuyerDAO buyerDAO;
	
	@Autowired 
	private ActionEventDAO actionEventDAO;
	
	@Autowired
	private ReceiptDAO receiptDAO;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping(method = RequestMethod.GET,value = "/getMyBuyer")
	public ResponseEntity<User> getMyBuyer(){
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		if(user==null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}else{
			Buyer buyer=buyerDAO.findByUsername(user.getUsername());
			if(buyer==null){
				return new ResponseEntity<>(HttpStatus.ACCEPTED);
			}
			return new ResponseEntity<>(buyer,HttpStatus.OK);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.POST,value = "/createBill")
	public Receipt createBill(@RequestBody Receipt receipt){
		
		
		Random r=new Random();
		
		
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		Buyer buyer=buyerDAO.findByUsername(user.getUsername());
		
		receipt.setBuyer(buyer);
		
		receipt.setCode(String.valueOf(r.nextInt(21341)));
		receipt.setState(ReceiptState.ordered);
		System.out.println(receipt.getItems().isEmpty());
		double sum=0;
		for(ReceiptItem ritem : receipt.getItems()){
			ritem.setReceipt(receipt);
			sum+=ritem.getOriginalSumPrice();
		}
		receipt.setOriginalPrice(sum);
		
		System.out.println( "Bootstrapping the Rule Engine ..." );
        // Bootstrapping a Rule Engine Session
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession =  kContainer.newKieSession("ksession-rules");
		kSession.insert(receipt);
		for(ReceiptItem ritem : receipt.getItems()){
			kSession.insert(ritem);
		}
		
		List<ActionEvent> aes=actionEventDAO.findAllByOrderByNameAsc();
		for(ActionEvent ae : aes){
			kSession.insert(ae);
		}
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
		
		
		return receipt;
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/buy")
	public ResponseEntity buy(@RequestBody Receipt receipt){
		
		
		Receipt r=receiptDAO.findById(receipt.getId());
		
		r.setState(ReceiptState.realized);
		
		
		for(ReceiptItem ritem : receipt.getItems()){
			Product p=productDAO.findByCode(ritem.getProduct().getCode());
			if(p.getInStock()-ritem.getAmount()<0){
				return new ResponseEntity<>(receipt,HttpStatus.ACCEPTED);
			}
			p.setInStock(p.getInStock()-ritem.getAmount());
			productDAO.save(p);
		}
		
		receiptDAO.save(r);
		
		r=receiptDAO.findById(receipt.getId());
	
		Buyer b=buyerDAO.findByUsername(receipt.getBuyer().getUsername());
		b.getShoppingHistory().add(r);
		b.setPrizePoints(b.getPrizePoints()-receipt.getNumberOfSpentPoints()
				+receipt.getNumberOfGainedPoints());
		
		buyerDAO.save(b);
		return new ResponseEntity<>(receipt,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/preBuy")
	public ResponseEntity preBuy(@RequestBody Receipt receipt){
		
		receipt.setFinalPrice(receipt.getFinalPrice()-receipt.getNumberOfSpentPoints());
		System.out.println( "Bootstrapping the Rule Engine ..." );
        // Bootstrapping a Rule Engine Session
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession =  kContainer.newKieSession("ksession-rules1");
        
		kSession.insert(receipt);
		//System.out.println(receipt.getBuyer());
		
		/*
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		Buyer buyer=buyerDAO.findByUsername(user.getUsername());
		kSession.insert(buyer);*/
		
		kSession.insert(receipt.getBuyer());
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
		
		receiptDAO.save(receipt);
	
		return new ResponseEntity<>(receipt,HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET,value="receipts")
	public ResponseEntity getReceipts(){
		return new ResponseEntity<>(receiptDAO.findAllByOrderByCodeAsc(),HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET,value="receiptsMy")
	public ResponseEntity getReceiptsMy(){
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		Buyer buyer=buyerDAO.findByUsername(user.getUsername());
		return new ResponseEntity<>(buyer.getShoppingHistory(),HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.POST,value="removeR")
	public ResponseEntity deleteR(@RequestBody Receipt r){
		receiptDAO.delete(r.getId());
		return new ResponseEntity<>(HttpStatus.OK);
	}
}
