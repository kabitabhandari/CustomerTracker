package com.code.springdemo.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.code.springdemo.entity.Customer;
import com.code.springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {

	/*
	 * // need to inject our customer service
	 * 
	 * @Autowired private CustomerDao customerDao;
	 */
	
	@Autowired
	private CustomerService customerService;

		
	 @GetMapping("/list")
		public String listCustomers(Model theModel){
			
			// get customers from the service
			List<Customer> theCustomers = customerService.getCustomers(); 
					
			// add the customers to the model
			theModel.addAttribute("customers", theCustomers);
			
			return "list-customers";
		}
		
		/*@GetMapping("/showFormForAdd")
		public String showFormForAdd(Model theModel) {
			
			// create model attribute to bind form ko data
			Customer theCustomer = new Customer();
			
			theModel.addAttribute("customerAtt", theCustomer);
			
			return "customer-form";
		}*/
		
		@GetMapping("/showFormForAdd")
		public String showFormForAdd(@ModelAttribute("customerAtt") Customer theCustomer) {
	
			
			return "customer-form";
		}
		
		
		
		
		@PostMapping("/saveCustomer")
		public String saveCustomer(@ModelAttribute("customerAtt") Customer theCustomer) {
			
			// save the customer using our service
			customerService.saveCustomer(theCustomer);
			
			
			return "redirect:/customer/list";
		}
		
		
		//updating...
		@GetMapping("/showFormForUpdate")
		public String showFormForUpdate(@RequestParam("customerIdIs") int theId, Model theModel ){
			//get customer from service
			Customer c = customerService.getCustomer(theId);
			
			//set customer as a model attribute to prepopulate the form
			theModel.addAttribute("customerAtt", c);
		
			//send over to our form
			return "customer-form";
		}
		//deleting....
		@GetMapping("/delete")
		public String deleteCustomer(@RequestParam("customerIdIs") int theId ){
			// delete the customer
			customerService.deleteCustomer(theId);
			
			return "redirect:/customer/list";
		}
		
		
		@GetMapping("/search")
	    public String searchCustomers(@RequestParam("theSearchName") String theSearchName,
	                                    Model theModel) {

	        // search customers from the service
	        List<Customer> theCustomers = customerService.searchCustomers(theSearchName);
	                
	        // add the customers to the model
	        theModel.addAttribute("customers", theCustomers);

	        return "list-customers";        
	    }
		
		
		
}
