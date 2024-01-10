package gloodb.tutorials;

import gloodb.Repository;
import gloodb.file.FileBasedRepositoryFactory;
import gloodb.memory.MemoryBasedRepositoryFactory;

/**
 * Factory which builds the tutorials repository. 
 */
public class RepositoryFactory {
	/**
	 * The repository type. Can be: 
	 * <ul><li> File
	 * <li>Memory
	 * </ul>
	 */
	public enum Type {
		File,
		Memory
	};
	
	/**
	 * Creates the repository with the given namespace. If the system property "type"
	 * has the value "File", the method creates an file based repository. Otherwise
	 * it builds an in-memory repository.
	 * @param nameSpace The namespace.
	 * @return The new repository.
	 */
	public static Repository createRepository(String nameSpace) {
		String type = System.getProperty("type");
		if(type == null) throw new RuntimeException("Unspecified repository implementation. Please invoke the test -Dtype=Memory or -Dtype=File");
		if(type.equals(Type.File.toString())) {
			return createEmbeddedFileRepository(nameSpace);
		} else {
			return createInMemoryRepository(nameSpace);
		}
	}
	
	private static Repository createEmbeddedFileRepository(String nameSpace) {
		return FileBasedRepositoryFactory.buildRepository(nameSpace, null);
	}

	private static Repository createInMemoryRepository(String nameSpace) {
		return MemoryBasedRepositoryFactory.buildRepository(nameSpace, null);
	}
}
