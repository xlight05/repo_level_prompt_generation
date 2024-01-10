package gloodb.associations;

import gloodb.file.FileBasedRepositoryFactory;

public class FileBasedOneToManyAssociationTest extends OneToManyAssociationTestBase {

    public FileBasedOneToManyAssociationTest() {
        super(new FileBasedRepositoryFactory().buildRepository("target/UnitTests/OneToManyAssociation"));
    }
}
