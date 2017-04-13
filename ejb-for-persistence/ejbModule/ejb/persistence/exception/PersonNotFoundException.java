package ejb.persistence.exception;

public class PersonNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public PersonNotFoundException(String m) {
		super(m);
	}
}
