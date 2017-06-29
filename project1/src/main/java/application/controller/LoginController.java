package application.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import application.model.Buyer;
import application.model.BuyerCategory;
import application.model.User;
import application.model.User.Role;
import application.repository.BuyerCategoryDAO;
import application.repository.BuyerDAO;
import application.repository.UserDAO;

@RestController
@RequestMapping("/login")
public class LoginController {
	
	@Autowired
	private UserDAO userDAO;
	
	@Autowired
	private BuyerDAO buyerDAO;
	
	@Autowired
	private BuyerCategoryDAO buyerCategoryDAO;
	
	@RequestMapping(method = RequestMethod.POST,value = "/login")
	public ResponseEntity<User> login(@RequestBody User user){
		User u=userDAO.findByUsername(user.getUsername());
		
		if(u==null){
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}else if(u.getPassword().equals(user.getPassword())){
			ServletRequestAttributes attr = (ServletRequestAttributes) 
				    RequestContextHolder.currentRequestAttributes();
				HttpSession session= attr.getRequest().getSession(true); 
				session.setAttribute("user", u);
		}else{
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(u,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.POST,value = "/register")
	public ResponseEntity<?> register(@RequestBody User user){
		User u=userDAO.findByUsername(user.getUsername());
		if(u==null){
			user.setRegistrationDate(new Date());
			if(user.getRole().equals(Role.BUYER)){
				Buyer b=new Buyer(user);
				/*ArrayList<BuyerCategory> list=
						(ArrayList<BuyerCategory>) buyerCategoryDAO.findAllByOrderByNameAsc();
				if(list.isEmpty()){
					System.out.println("nema nijedne kategorije");
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}else{
					
				}*/
				BuyerCategory bc=buyerCategoryDAO.findByCode("regular");
				if(bc==null){
					return new ResponseEntity<>(HttpStatus.CONFLICT);
				}else{
					b.setBuyerCategory(bc);
					buyerDAO.save(b);
				}
			}else{
				userDAO.save(user);
			}
		}else{
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		}
		return new ResponseEntity<User>(user,HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/logOut")
	public ResponseEntity<?> logOut(){
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		session.invalidate();
		return new ResponseEntity<User>(HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET,value = "/getLogged")
	public ResponseEntity<?> getLoggedUser(){
		ServletRequestAttributes attr = (ServletRequestAttributes) 
				RequestContextHolder.currentRequestAttributes();
		HttpSession session= attr.getRequest().getSession(true);
		User user = (User) session.getAttribute("user");
		
		if(user==null)
			return new ResponseEntity<User>(HttpStatus.ACCEPTED);
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
}
