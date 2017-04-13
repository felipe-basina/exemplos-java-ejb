package ejb.persistence.client;

import java.util.Date;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.persistence.definition.PersonManager;
import ejb.persistence.entity.Address;
import ejb.persistence.entity.Person;

public class EjbClient {

	public static void main(String[] args) {
		
		EjbClient ejbClient = new EjbClient();
		
		try {

			InitialContext ic = ejbClient.getInitialContext();
			
			PersonManager manager = (PersonManager) ic.lookup("PersonBean#ejb.persistence.definition.PersonManager");
			
			ejbClient.createPerson(manager);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}

	private InitialContext getInitialContext() throws NamingException {
		Hashtable<String, String> ht = new Hashtable<String, String>();
		ht.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
		ht.put(Context.PROVIDER_URL, "t3://localhost:7001");
		ht.put(Context.SECURITY_PRINCIPAL, "weblogic");
		ht.put(Context.SECURITY_CREDENTIALS, "weblogic123");
		return new InitialContext(ht);
	}
	
	private void createPerson(PersonManager manager) {
		for (int outIndex = 1; outIndex <= 10; outIndex++) {
			Person person = new Person();
			person.setCreationDate(new Date());
			person.setEmail("person".concat(String.valueOf(outIndex).concat("@domain.com")));
			person.setName("person".concat(String.valueOf(outIndex)));

			Set<Address> addresses = new HashSet<Address>();
			
			for (int index = 1; index <= 2; index++) {
				Address address = new Address();
				address.setNumber(String.valueOf(index).concat(String.valueOf(System.nanoTime())));
				address.setStreet("street".concat(String.valueOf(index).concat(String.valueOf(outIndex))));
				address.setPerson(person);				
				
				addresses.add(address);
			}
			
			person.setAddresses(addresses);
			
			manager.removePerson(person);
			
			person = manager.savePerson(person);
		}
		
		this.printPerson(manager);
	}
	
	private void printPerson(PersonManager manager) {
		List<Person> persons = manager.getAllPerson();
		for (Person personC : persons) {
			System.out.println("###\t" + personC);	
		}		
	}
}