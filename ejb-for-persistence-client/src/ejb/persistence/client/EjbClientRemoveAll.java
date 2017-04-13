package ejb.persistence.client;

import java.util.Hashtable;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.persistence.definition.PersonManager;

public class EjbClientRemoveAll {

	public static void main(String[] args) {

		EjbClientRemoveAll ejbClient = new EjbClientRemoveAll();

		try {

			InitialContext ic = ejbClient.getInitialContext();

			PersonManager manager = (PersonManager) ic.lookup("PersonBean#ejb.persistence.definition.PersonManager");

			manager.removeAll();

			System.out.println("Registros removidos com sucesso!");

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

}