package gloodb.associations;

import gloodb.file.FileBasedRepositoryFactory;

public class FileBasedOneToOneAssociationTest extends OneToOneAssociationTestBase {

    public FileBasedOneToOneAssociationTest() {
        super(FileBasedRepositoryFactory.buildRepository("target/UnitTests/OneToOneAssociation", null));
    }
}
