package ejb.persistence.definition;

import java.util.List;

import javax.ejb.Remote;

import ejb.persistence.entity.Audit;
import ejb.persistence.entity.AuditReport;
import ejb.persistence.entity.Person;

@Remote
public interface AuditManager {
	
	public List<AuditReport> getAllByPerson(Person person);
	
	public List<AuditReport> getAll();
	
	public Audit saveAudit(Audit audit);
	
}
