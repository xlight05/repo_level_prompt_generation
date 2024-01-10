package gloodb.associations;

import gloodb.file.FileBasedRepositoryFactory;

public class FileBasedManyToManyAssociationTest extends ManyToManyAssociationTestBase {

    public FileBasedManyToManyAssociationTest() {
        super(new FileBasedRepositoryFactory().buildRepository("target/UnitTests/ManyToManyAssociation"));
    }
}
