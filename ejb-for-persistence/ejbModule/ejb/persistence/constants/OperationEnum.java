package ejb.persistence.constants;

public enum OperationEnum {

	SAVE(1L), SAVE_ERROR(2L), LIST_ALL(3L), LIST_ALL_ERROR(4L), FIND_ONE(5L), FIND_ONE_ERROR(
			6L), REMOVE(7L), REMOVE_ERROR(8L), UPDATE(9L), UPDATE_ERRRO(10L);

	private Long id;

	private OperationEnum(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}


}