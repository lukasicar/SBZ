package application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import application.model.ActionEvent;
import application.repository.ActionEventDAO;

@RestController
@RequestMapping("/actionEvent")
public class ActionEventsController {

	@Autowired
	private ActionEventDAO actionEventDAO;

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.GET,value = "/getAll")
	public ResponseEntity getAll(){
		return new ResponseEntity<>(actionEventDAO.findAllByOrderByNameAsc(),HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value="/delete")
	public ResponseEntity delete(@RequestBody ActionEvent actionEvent){
		actionEventDAO.delete(actionEvent.getCode());
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/new")
	public ResponseEntity neww(@RequestBody ActionEvent actionEvent){
		ActionEvent bc=actionEventDAO.findByCode(actionEvent.getCode());
		if(bc!=null){
			return new ResponseEntity<>(HttpStatus.ACCEPTED);
		}
		actionEventDAO.save(actionEvent);
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.PUT,value = "/edit")
	public ResponseEntity edit(@RequestBody ActionEvent actionEvent){
		//ProductCategory bc=productCategoryDAO.findByCode(productCategory.getCode());
		actionEventDAO.save(actionEvent);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST,value = "/getCategories")
	public ResponseEntity getCategories(@RequestBody ActionEvent actionEvent){
		ActionEvent ae=actionEventDAO.findByCode(actionEvent.getCode());
		return new ResponseEntity<>(ae.getCategories(),HttpStatus.OK);
	}
	
}
