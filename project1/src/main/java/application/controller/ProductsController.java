package application.controller;

import java.util.List;

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

import application.model.Product;
import application.repository.ProductDAO;

@RestController
@RequestMapping("/product")
public class ProductsController {

	@Autowired
	private ProductDAO productDAO;
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.GET,value="/getAll")
	public ResponseEntity getAll(){
		System.out.println( "Bootstrapping the Rule Engine ..." );
        // Bootstrapping a Rule Engine Session
        KieServices ks = KieServices.Factory.get();
        KieContainer kContainer = ks.getKieClasspathContainer();
        KieSession kSession =  kContainer.newKieSession("ksession-rules1");
        
		List<Product> list=productDAO.findAllByOrderByNameAsc();
		for(Product p : list){
			kSession.insert(p);
		}
		
		System.out.println("krecu pravila");
		System.out.println(kSession.fireAllRules());
		
		System.out.println("zavrsila pravila");
		
		return new ResponseEntity<>(list,HttpStatus.OK);
	}
	
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.POST,value="/delete")
	public ResponseEntity delete(@RequestBody Product product){
		productDAO.delete(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.POST,value="/create")
	public ResponseEntity create(@RequestBody Product product){
		Product p=productDAO.findByCode(product.getCode());
		if(p!=null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		productDAO.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.PUT,value="/edit")
	public ResponseEntity edit(@RequestBody Product product){
		productDAO.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method=RequestMethod.PUT,value="/fillTheStocks")
	public ResponseEntity fill(@RequestBody Product product){
		product.setInStock(product.getMinimumInStock());
		productDAO.save(product);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
}
