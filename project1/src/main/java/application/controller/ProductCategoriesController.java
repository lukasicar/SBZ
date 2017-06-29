package application.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.model.ProductCategory;
import application.repository.ProductCategoryDAO;

@RestController
@RequestMapping("/productCategory")
public class ProductCategoriesController {

	@Autowired
	private ProductCategoryDAO productCategoryDAO;

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET,value = "/getAll")
	public ResponseEntity getAll(){
		return new ResponseEntity<>(productCategoryDAO.findAllByOrderByNameAsc(),HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public ResponseEntity delete(@RequestBody ProductCategory productCategory){
		productCategoryDAO.delete(productCategory.getCode());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/new")
	public ResponseEntity neww(@RequestBody ProductCategory productCategory){
		ProductCategory bc=productCategoryDAO.findByCode(productCategory.getCode());
		if(bc!=null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		productCategoryDAO.save(productCategory);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.PUT,value = "/edit")
	public ResponseEntity edit(@RequestBody ProductCategory productCategory){
		//ProductCategory bc=productCategoryDAO.findByCode(productCategory.getCode());
		productCategoryDAO.save(productCategory);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/getAvailible")
	public ResponseEntity getAvailibleCategories(@RequestBody ProductCategory productCategory){
		List<ProductCategory> list1=productCategoryDAO.findAllByOrderByNameAsc();
		List<ProductCategory> list2=new ArrayList<>();
		for(ProductCategory p1 : list1){
			if(!(p1.getCode().equals(productCategory.getCode()))){
				boolean x=false;
				while(p1.getAboveCategory()!=null){
					if((p1.getAboveCategory().getCode().equals(productCategory.getCode()))){
						x=true;
						break;
					}else{
						p1=p1.getAboveCategory();
					}
				}
				if(!x){
					list2.add(p1);
				}
			}
		}
		
		return new ResponseEntity<>(list2,HttpStatus.OK);
	}
	
}
