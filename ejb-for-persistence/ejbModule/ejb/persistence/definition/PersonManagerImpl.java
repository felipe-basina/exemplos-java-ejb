package ejb.persistence.definition;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.EJBContext;
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

import ejb.persistence.constants.OperationEnum;
import ejb.persistence.entity.Audit;
import ejb.persistence.entity.Person;
import ejb.persistence.exception.PersonNotFoundException;

@Stateful(mappedName = "PersonBean")
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PersonManagerImpl implements PersonManager {

	private static final Logger LOGGER = Logger.getLogger(PersonManagerImpl.class);

	@PersistenceContext(unitName = "ejb-persistence-unit", type = PersistenceContextType.EXTENDED)
	private EntityManager em;

	@EJB
	private AuditManager auditManager;

	/**
	 * Utilizado para executar roll-back em transações gerenciadas pelo
	 * CONTAINER (padrão JEE a partir da versão 1.5)
	 * 
	 * Referência:
	 * http://www.developerscrappad.com/547/java/java-ee/ejb3-x-jpa-when-to-use-
	 * rollback-and-setrollbackonly/
	 */
	@Resource
	private EJBContext context;

	@Override
	public Person getPersonByName(String name) {
		LOGGER.info("Preparando para recuperar pessoa por nome = [".concat(name).concat("]..."));

		Person person = null;

		try {

			Query query = em.createNamedQuery("getPersonByName");
			query.setParameter("name", name);

			person = (Person) query.getSingleResult();

			this.setAuditInfo(person, OperationEnum.FIND_ONE);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			this.setAuditInfo(person, OperationEnum.FIND_ONE_ERROR);
		}

		LOGGER.info("Pessoa recuperada [" + person + "]");

		return person;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> getAllPerson() {
		LOGGER.info("Preparando para recuperar lista de pessoas...");

		List<Person> personList = new ArrayList<Person>();

		try {

			Query query = em.createNamedQuery("getAllPerson");
			personList = (List<Person>) query.getResultList();

			/**
			 * Adiciona a listagem no SET para remoção de registros duplicados
			 * causado pelo uso do LEFT JOIN FETCH
			 * 
			 * Referência:
			 * http://stackoverflow.com/questions/18753245/one-to-many-
			 * relationship-gets-duplicate-objects-whithout-using-distinct-why
			 */
			Set<Person> personSet = new HashSet<Person>(personList);
			personList = new ArrayList<Person>(personSet);

			this.setAuditInfo(null, OperationEnum.LIST_ALL);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			this.setAuditInfo(null, OperationEnum.LIST_ALL_ERROR);
		}

		LOGGER.info("Total de pessoas recuperadas = [" + personList.size() + "]");

		return personList;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Person savePerson(Person person) {
		LOGGER.info("Preparando para salvar person [" + person + "]...");

		try {

			em.persist(person);

			LOGGER.info("Objeto salvo [" + person + "]");

			this.setAuditInfo(person, OperationEnum.SAVE);

			throw new Exception("Excecao para roll-back...");

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			this.setAuditInfo(person, OperationEnum.SAVE_ERROR);
			context.setRollbackOnly();
			LOGGER.info("########## Roll-back executado!");
		}

		return person;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void removePerson(Person person) {
		LOGGER.info("Preparando para remover pessoa [" + person + "]...");

		try {

			person = this.getPersonByName(person.getName());

			if (person != null) {
				em.remove(person);
			}

			this.setAuditInfo(person, OperationEnum.REMOVE);

		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
			this.setAuditInfo(person, OperationEnum.REMOVE_ERROR);
			context.setRollbackOnly();
		}

		LOGGER.info("Pessoa removida [" + person + "] removida com sucesso!");
	}

	@Override
	public void removeAll() throws PersonNotFoundException {
		LOGGER.info("Preparando para remover todos os registros...");

		int total = 0;

		try {

			List<Person> persons = this.getAllPerson();

			total = persons.size();
			
			if (total <= 0) {
				throw new PersonNotFoundException("Nao existem registros para remocao");
			}

			for (Person person : persons) {
				this.removePerson(person);
			}

		} catch (PersonNotFoundException pex) {
			throw new PersonNotFoundException(pex.getMessage());
		} catch (Exception ex) {
			LOGGER.error(ex.getMessage(), ex);
		}

		LOGGER.info("Total de registros removidos = [" + total + "]");
	}

	private void setAuditInfo(Person person, OperationEnum operation) {
		if (person == null || person.getId() == null || person.getId() <= 0) {
			person = new Person();
			person.setId(0L);
		}

		Audit audit = new Audit();
		audit.setCreationDate(new Date());
		audit.setOperation(operation);
		audit.setPerson(person.getId());

		auditManager.saveAudit(audit);
	}
}