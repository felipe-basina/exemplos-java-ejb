package ejb.persistence.entity;

import java.io.Serializable;
import java.util.Date;

public final class AuditReport implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5212965952857709317L;

	private Long auditId;
	
	private String operation;
	
	private Date creationDateAuditRegister;
	
	private Long personId;
	
	private String name;
	
	private String email;
	
	private Date creationDatePersonRegister;

	private AuditReport(Long auditId, String operation,
			Date creationDateAuditRegister, Long personId, String name,
			String email, Date creationDatePersonRegister) {
		this.auditId = auditId;
		this.operation = operation;
		this.creationDateAuditRegister = creationDateAuditRegister;
		this.personId = personId;
		this.name = name;
		this.email = email;
		this.creationDatePersonRegister = creationDatePersonRegister;
	}

	public Long getAuditId() {
		return auditId;
	}

	public void setAuditId(Long auditId) {
		this.auditId = auditId;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public Date getCreationDateAuditRegister() {
		return creationDateAuditRegister;
	}

	public void setCreationDateAuditRegister(Date creationDateAuditRegister) {
		this.creationDateAuditRegister = creationDateAuditRegister;
	}

	public Long getPersonId() {
		return personId;
	}

	public void setPersonId(Long personId) {
		this.personId = personId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getCreationDatePersonRegister() {
		return creationDatePersonRegister;
	}

	public void setCreationDatePersonRegister(Date creationDatePersonRegister) {
		this.creationDatePersonRegister = creationDatePersonRegister;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((auditId == null) ? 0 : auditId.hashCode());
		result = prime
				* result
				+ ((creationDateAuditRegister == null) ? 0
						: creationDateAuditRegister.hashCode());
		result = prime
				* result
				+ ((creationDatePersonRegister == null) ? 0
						: creationDatePersonRegister.hashCode());
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result
				+ ((operation == null) ? 0 : operation.hashCode());
		result = prime * result
				+ ((personId == null) ? 0 : personId.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AuditReport other = (AuditReport) obj;
		if (auditId == null) {
			if (other.auditId != null)
				return false;
		} else if (!auditId.equals(other.auditId))
			return false;
		if (creationDateAuditRegister == null) {
			if (other.creationDateAuditRegister != null)
				return false;
		} else if (!creationDateAuditRegister
				.equals(other.creationDateAuditRegister))
			return false;
		if (creationDatePersonRegister == null) {
			if (other.creationDatePersonRegister != null)
				return false;
		} else if (!creationDatePersonRegister
				.equals(other.creationDatePersonRegister))
			return false;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (operation == null) {
			if (other.operation != null)
				return false;
		} else if (!operation.equals(other.operation))
			return false;
		if (personId == null) {
			if (other.personId != null)
				return false;
		} else if (!personId.equals(other.personId))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AuditReport [auditId=");
		builder.append(auditId);
		builder.append(", operation=");
		builder.append(operation);
		builder.append(", creationDateAuditRegister=");
		builder.append(creationDateAuditRegister);
		builder.append(", personId=");
		builder.append(personId);
		builder.append(", name=");
		builder.append(name);
		builder.append(", email=");
		builder.append(email);
		builder.append(", creationDatePersonRegister=");
		builder.append(creationDatePersonRegister);
		builder.append("]");
		return builder.toString();
	}

	public static class AuditReportBuilder {
		private Long auditIdNested;
		
		private String operationNested;
		
		private Date creationDateAuditRegisterNested;
		
		private Long personIdNested;
		
		private String nameNested;
		
		private String emailNested;
		
		private Date creationDatePersonRegisterNested;
		
		public AuditReportBuilder auditId(Long auditId) {
			this.auditIdNested = auditId;
			return this;
		}
		
		public AuditReportBuilder operation(String operation) {
			this.operationNested = operation;
			return this;
		}
		
		public AuditReportBuilder creationDateAuditRegister(Date creationDateAuditRegister) {
			this.creationDateAuditRegisterNested = creationDateAuditRegister;
			return this;
		}
		
		public AuditReportBuilder personId(Long personId) {
			this.personIdNested = personId;
			return this;
		}
		
		public AuditReportBuilder name(String name) {
			this.nameNested = name;
			return this;
		}
		
		public AuditReportBuilder email(String email) {
			this.emailNested = email;
			return this;
		}
		
		public AuditReportBuilder creationDatePersonRegister(Date creationDatePersonRegister) {
			this.creationDatePersonRegisterNested = creationDatePersonRegister;
			return this;
		}
		
		public AuditReport createAuditReport() {
			return new AuditReport(auditIdNested, 
					operationNested, 
					creationDateAuditRegisterNested, 
					personIdNested, 
					nameNested, 
					emailNested, 
					creationDatePersonRegisterNested);
		}
	}
	
}
