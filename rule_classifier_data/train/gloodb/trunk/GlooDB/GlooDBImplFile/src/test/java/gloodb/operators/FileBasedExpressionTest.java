package gloodb.operators;

import gloodb.file.FileBasedRepositoryFactory;


public class FileBasedExpressionTest extends ExpressionTestBase {
	
    public FileBasedExpressionTest() {
        super(new FileBasedRepositoryFactory().buildRepository("target/UnitTests/Expressions"));
    }
}
