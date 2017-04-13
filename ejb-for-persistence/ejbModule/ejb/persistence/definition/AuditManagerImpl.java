package ejb.persistence.definition;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

import org.apache.log4j.Logger;

import ejb.persistence.entity.Audit;
import ejb.persistence.entity.AuditReport;
import ejb.persistence.entity.Person;

@Stateful(mappedName = "AuditBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AuditManagerImpl implements AuditManager {

	private static final Logger LOGGER = Logger.getLogger(AuditManagerImpl.class);
	
	@PersistenceContext(unitName = "ejb-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager em;
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AuditReport> getAllByPerson(Person person) {
		
		List<AuditReport> auditReports = new ArrayList<AuditReport>();
		
		try {

			/**
			 * Não é possível definir CONSULTA NATIVA como NamedNativeQuery via anotação na classe entidade.
			 * Exceção lançada: Pure native scalar queries are not yet supported
			 * É necessário que o retorno seja uma entidade válida e não uma classe POJO
			 * 
			 * Como alternativa, executar uma consulta como NativeQuery, funciona normalmente 
			 */
			StringBuilder consulta = new StringBuilder(" SELECT a.id, a.operation, a.creationDate, a.personId, p.name, p.email, p.creationDate ");
			consulta.append(" FROM AUDIT_REGISTER a LEFT OUTER JOIN PERSON_REGISTER p ");
			consulta.append(" ON p.id = a.personId WHERE a.personId = ? ");

			Query query = em.createNativeQuery(consulta.toString());
			query.setParameter(1, person.getId());
			
			List<Object[]> resultSet = (ArrayList<Object[]>) query.getResultList();
			
			auditReports = this.getAuditReportList(resultSet);
			
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		return auditReports;
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<AuditReport> getAll() {
		
		List<AuditReport> auditReports = new ArrayList<AuditReport>();
		
		try {

			/**
			 * Não é possível definir CONSULTA NATIVA como NamedNativeQuery via anotação na classe entidade.
			 * Exceção lançada: Pure native scalar queries are not yet supported
			 * É necessário que o retorno seja uma entidade válida e não uma classe POJO
			 * 
			 * Como alternativa, executar uma consulta como NativeQuery, funciona normalmente 
			 */
			StringBuilder consulta = new StringBuilder(" SELECT a.id, a.operation, a.creationDate, a.personId, p.name, p.email, p.creationDate ");
			consulta.append(" FROM AUDIT_REGISTER a LEFT OUTER JOIN PERSON_REGISTER p ");
			consulta.append(" ON p.id = a.personId ORDER BY a.id DESC ");

			Query query = em.createNativeQuery(consulta.toString());
			
			List<Object[]> resultSet = (ArrayList<Object[]>) query.getResultList();
			
			auditReports = this.getAuditReportList(resultSet);
			
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}
		
		return auditReports;
	}

	private List<AuditReport> getAuditReportList(List<Object[]> resultSet) {
		LOGGER.info("Recuperar registros de auditoria...");
		
		List<AuditReport> auditReports = new ArrayList<AuditReport>();
		
		for (Object[] result : resultSet) {
			AuditReport auditReport = new AuditReport.AuditReportBuilder()
				.auditId(((BigInteger) result[0]).longValue())
				.operation((String) result[1])
				.creationDateAuditRegister((Date) result[2])
				.personId(((BigInteger) result[3]).longValue())
				.name(result[4] != null ? (String) result[4] : null)
				.email(result[5] != null ? (String) result[5] : null)
				.creationDatePersonRegister(result[6] != null ? (Date) result[6] : null)
				.createAuditReport();
			
			auditReports.add(auditReport);
		}
		
		LOGGER.info("Total de registros de auditoria (AuditReport): " + resultSet.size());
		
		Set<AuditReport> setAuditReports = new HashSet<AuditReport>(auditReports);
		
		return new ArrayList<AuditReport>(setAuditReports); 
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Audit saveAudit(Audit audit) {
		LOGGER.info("Preparando para salvar audit [" + audit + "]...");
		
		try {

			em.persist(audit);
			
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);

		}
		
		LOGGER.info("Objeto salvo [" + audit + "]");
		
		return audit;
	}

}
