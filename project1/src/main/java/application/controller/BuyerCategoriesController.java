package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.model.BuyerCategory;
import application.model.ConsumptionTreshold;
import application.repository.BuyerCategoryDAO;

@RestController
@RequestMapping("/buyerCategory")
public class BuyerCategoriesController {
	
	@Autowired
	private BuyerCategoryDAO buyerCategoryDAO;

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET,value = "/getAll")
	public ResponseEntity getAll(){
		return new ResponseEntity<>(buyerCategoryDAO.findAllByOrderByNameAsc(),HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public ResponseEntity delete(@RequestBody BuyerCategory buyerCategory){
		buyerCategoryDAO.delete(buyerCategory.getCode());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/new")
	public ResponseEntity neww(@RequestBody BuyerCategory buyerCategory){
		BuyerCategory bc=buyerCategoryDAO.findByCode(buyerCategory.getCode());
		if(bc!=null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		for(ConsumptionTreshold c : buyerCategory.getConsumptionTresholds()){
			System.out.println(c);
			System.out.println(c.getId());
		}
		buyerCategoryDAO.save(buyerCategory);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.PUT,value = "/edit")
	public ResponseEntity edit(@RequestBody BuyerCategory buyerCategory){
		//BuyerCategory bc=buyerCategoryDAO.findByCode(buyerCategory.getCode());
		/*if(bc!=null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}*/
		
		buyerCategoryDAO.save(buyerCategory);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
