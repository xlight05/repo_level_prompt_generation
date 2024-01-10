package gloodb.associations;

import gloodb.file.FileBasedRepositoryFactory;

public class FileBasedNonUniqueIndexTest extends NonUniqueIndexTestBase {

    public FileBasedNonUniqueIndexTest() {
        super(FileBasedRepositoryFactory.buildRepository("target/UnitTests/UniqueIndex", null));
    }
}
