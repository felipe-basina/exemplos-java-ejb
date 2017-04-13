package ejb.persistence.definition;

import java.util.List;

import javax.ejb.Remote;

import ejb.persistence.entity.Person;
import ejb.persistence.exception.PersonNotFoundException;

@Remote
public interface PersonManager {

	public Person getPersonByName(String name);
	
	public List<Person> getAllPerson();
	
	public Person savePerson(Person person);
	
	public void removePerson(Person person);
	
	public void removeAll() throws PersonNotFoundException ;
	
}
