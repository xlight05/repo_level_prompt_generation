package br.com.dyad.infrastructure.audit;

import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import br.com.dyad.infrastructure.entity.BaseEntity;
import br.com.dyad.infrastructure.persistence.PersistenceUtil;

public class UndoRedoLogResult {
	
	private List<BaseEntity> deletedBaseEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> insertedBaseEntities = new ArrayList<BaseEntity>();
	private List<BaseEntity> updatedBaseEntities = new ArrayList<BaseEntity>();
	public Session transactionalSession;
	
	public UndoRedoLogResult(String database) {
		transactionalSession  = PersistenceUtil.getSession(database);
	}
	
	public List<BaseEntity> getDeletedBaseEntities() {
		return deletedBaseEntities;
	}
	public List<BaseEntity> getInsertedBaseEntities() {
		return insertedBaseEntities;
	}
	public List<BaseEntity> getUpdatedBaseEntities() {
		return updatedBaseEntities;
	}
}
