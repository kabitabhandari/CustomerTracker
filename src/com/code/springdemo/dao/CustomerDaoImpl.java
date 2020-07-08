package com.code.springdemo.dao;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.code.springdemo.entity.Customer;


@Repository
public class CustomerDaoImpl implements CustomerDao {

	// need to inject the session factory
	@Autowired
	private SessionFactory sessionFactory;
	
	
	@Override
	//@Transactional //--------> moved to service layer/class
	public List<Customer> getCustomers() {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		// create a query  ... sort by last name USING HQL
				Query<Customer> theQuery = 
						currentSession.createQuery("from Customer order by lastName",
													Customer.class);
				
				// execute query and get result list
				List<Customer> customersResult = theQuery.getResultList();
				
						
				// return the results		
				return customersResult;
		
	}


	@Override
	public void saveCustomer(Customer theCustomer) {
		// get the current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		//   ... save new customer to DB
		currentSession.saveOrUpdate(theCustomer);
			
						
		
	}


	@Override
	public Customer getCustomer(int theId) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		//now retrieve or read from database using the primary key
		Customer cust= currentSession.get(Customer.class, theId);
		
		
		return cust;
	}


	@Override
	public void deleteCustomer(int theId) {
		//get current hibernate session
		Session currentSession = sessionFactory.getCurrentSession();
		
		// delete object with primary key
				Query theQuery = 
						currentSession.createQuery("delete from Customer where id=:thecustomerId");
				theQuery.setParameter("thecustomerId", theId);
				
				theQuery.executeUpdate();	
		
		
		
	}


	
	@Override
    public List<Customer> searchCustomers(String theSearchName) {

        // get the current hibernate session
        Session currentSession = sessionFactory.getCurrentSession();
        
        Query theQuery = null;
        
        //
        // only search by name if theSearchName is not empty
        //
        if (theSearchName != null && theSearchName.trim().length() > 0) {

            // search for firstName or lastName ... case insensitive
            theQuery =currentSession.createQuery("from Customer where lower(firstName) like :theName or lower(lastName) like :theName", Customer.class);
            theQuery.setParameter("theName", "%" + theSearchName.toLowerCase() + "%");

        }
        else {
            // theSearchName is empty ... so just get all customers
            theQuery =currentSession.createQuery("from Customer", Customer.class);            
        }
        
        // execute query and get result list
        List<Customer> customers = theQuery.getResultList();
                
        // return the results        
        return customers;
        
    }
	
	
}
