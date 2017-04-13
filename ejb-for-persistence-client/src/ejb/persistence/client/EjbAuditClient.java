package ejb.persistence.client;

import java.util.Hashtable;
import java.util.List;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import ejb.persistence.definition.AuditManager;
import ejb.persistence.entity.AuditReport;
import ejb.persistence.entity.Person;

public class EjbAuditClient {

	public static void main(String[] args) {

		EjbAuditClient ejbClient = new EjbAuditClient();

		try {

			InitialContext ic = ejbClient.getInitialContext();

			AuditManager manager = (AuditManager) ic.lookup("AuditBean#ejb.persistence.definition.AuditManager");

			Person person = new Person();
			person.setId(0L);

			// Por person
			ejbClient.printAuditReport(manager, person);

			System.out.println("/*************************************************************/");

			// Recuperar todos
			ejbClient.printAuditReport(manager);

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

	private void printAuditReport(AuditManager manager, Person person) {
		List<AuditReport> reports = manager.getAllByPerson(person);
		this.print(reports);
	}

	private void printAuditReport(AuditManager manager) {
		List<AuditReport> reports = manager.getAll();
		this.print(reports);
	}

	private void print(List<AuditReport> reports) {
		for (AuditReport report : reports) {
			System.out.println("###\t" + report);
		}
	}
}