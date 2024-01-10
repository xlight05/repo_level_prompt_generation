package gloodb.associations;

import gloodb.memory.MemoryBasedRepositoryFactory;

public class MemoryBasedOneToOneAssociationTest extends OneToOneAssociationTestBase {

    public MemoryBasedOneToOneAssociationTest() {
        super(MemoryBasedRepositoryFactory.buildRepository("target/UnitTests/OneToOne", null));
    }
}
