package gloodb.associations;

import gloodb.memory.MemoryBasedRepositoryFactory;

public class MemoryBasedUniqueIndexTest extends UniqueIndexTestBase {

    public MemoryBasedUniqueIndexTest() {
        super(new MemoryBasedRepositoryFactory().buildRepository("target/UnitTests/UniqueIndex"));
    }
}
