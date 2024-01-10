package dovetaildb.dbrepository;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryDbRepository extends StandardDbRepository {

	private static final long serialVersionUID = 3564782778193929906L;

	public MemoryDbRepository() {
		super();
		repo = new ConcurrentHashMap<String, DbRecord>();
	}

}
