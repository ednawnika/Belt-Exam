package com.tony.admindashboard.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.tony.admindashboard.models.Role;
import com.tony.admindashboard.models.User;
import com.tony.admindashboard.models.Subscription;
import com.tony.admindashboard.services.RoleService;
import com.tony.admindashboard.services.UserService;

import com.tony.admindashboard.validations.UserValidator;

import com.tony.admindashboard.services.SubscriptionService;


@Controller
public class UserController {
	@Autowired
	private SubscriptionService subscriptionService;
	
	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserValidator userValidator;



	public UserController(UserService userService, SubscriptionService subscriptionService, RoleService roleService,UserValidator userValidator){
		this.userService = userService;
		this.subscriptionService=subscriptionService;
		this.roleService = roleService;
		this.userValidator = userValidator;
	}
	
	@RequestMapping(value={"/login","/register"})
	public String login(Model model,@RequestParam(value="error",required=false) String error,@RequestParam(value="logout",required=false) String logout){
		if(error != null){model.addAttribute("errorMessage","Invalid Credentials.");}
		if(logout != null){model.addAttribute("logoutMessage","Logout Successful");}
		
		model.addAttribute("user",new User());
		return "login_register";
	}
	
	@PostMapping("/register")
	public String register(@Valid @ModelAttribute("user") User user,BindingResult res,Model model){
		userValidator.validate(user,res);
		if(res.hasErrors()){return "login_register";}
		
		if(roleService.findByName("ROLE_ADMIN").getUsers().size() < 1){ 
			userService.create(new String[]{"ROLE_USER","ROLE_ADMIN"}, user);
		}else{
			userService.create(new String[]{"ROLE_USER"}, user);
		}
		return "redirect:/login";
	}

	
	@RequestMapping("/admin")
	public String admin(@Valid @ModelAttribute("subscriptions") Subscription subscription, BindingResult result, Principal principal, Model model){		
		model.addAttribute("user",userService.findByEmail(principal.getName()));
		model.addAttribute("users",userService.all());
		model.addAttribute("subscriptions", subscriptionService.all());

		

		return "admin";
	}
	
	@RequestMapping("/admin/delete/{id}")
	public String delete(@PathVariable("id") long id){
		userService.destroy(id);
		return "redirect:/admin";
	}
	
	@RequestMapping("/admin/promote/{id}")
	public String promote(@PathVariable("id") long id){
		User user = userService.getById(id);
		List<Role> userRoles = user.getRoles();
		userRoles.add(roleService.findByName("ROLE_ADMIN"));
		userService.update(user);
		
		return "redirect:/admin";
	}
	
	@RequestMapping(value={"/","/dashboard"})
	public String dashboard(@Valid @ModelAttribute("subscription") Subscription subscription,Principal principal, Model model, BindingResult result){
		User user = userService.findByEmail(principal.getName());
		model.addAttribute("user",user);
		
	

		userService.update(user);
		
		if(user.isSuperAdmin()){
			return "redirect:/superadmin";
		}else if(user.isAdmin()){
			return "redirect:/admin";
		}else{
		
			model.addAttribute("users", userService.all());
			model.addAttribute("subscriptions", subscriptionService.all());
			return "dashboard";
		}


	}
	@RequestMapping(path="/subscription/new", method=RequestMethod.POST)
	public String newpack(@Valid @ModelAttribute("subscriptions") Subscription subscription , @RequestParam(value="name") String name, @RequestParam(value="cost", required=false) double cost, Model model){
		model.addAttribute("name", name);
		model.addAttribute("cost", cost);
		model.addAttribute("subscriptions", subscription);
		subscription.setActive(true);

			subscriptionService.create(subscription);
		  
			return "redirect:/admin";
		


	}

	@PostMapping("/adduser")
	public String addsub(Principal principal, @RequestParam("subscription") Long id) {
		
		
		
		
		Subscription subs = subscriptionService.getById(id);
		User user = userService.findByEmail(principal.getName());
		subs.setUser(user);
		subscriptionService.create(subs);
		
	
		System.out.println(subs);



			return "redirect:/dashboard";

	}


	@RequestMapping("/admin/deactivate/{id}")
	public String toggle(@PathVariable("id") Long id, Model model){
		
		Subscription subs = subscriptionService.getById(id);

		subs.setActive(!subs.isActive());

		subscriptionService.update(subs);
		
		return "redirect:/admin";





		
	}
}




