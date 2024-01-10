package gloodb.associations;

import gloodb.memory.MemoryBasedRepositoryFactory;

public class MemoryBasedNonUniqueIndexTest extends NonUniqueIndexTestBase {

    public MemoryBasedNonUniqueIndexTest() {
        super(new MemoryBasedRepositoryFactory().buildRepository("target/UnitTests/NonsUniqueIndex"));
    }
}
