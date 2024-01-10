package gloodb.file.storage.block;

import static org.junit.Assert.*;
import gloodb.file.storage.block.BlockStorageConfiguration;

import javax.crypto.Cipher;
import javax.crypto.NullCipher;

import org.junit.Test;


public class BlockStorageConfigurationTest {

	@Test
	public void testConstructorAndDefaultValues() {
		Cipher inputCipher = new NullCipher();
		Cipher outputCipher = new NullCipher();
		BlockStorageConfiguration config = new BlockStorageConfiguration("Test", inputCipher, outputCipher);
		
		assertEquals(256, config.getBlockSize());
		assertSame(inputCipher, config.getInputCipher());
		assertSame(outputCipher, config.getOutputCipher());
		assertEquals(8 * Integer.MAX_VALUE, config.getMaxBlocks());
		assertEquals("Test", config.getNameSpace());
		assertEquals(".repository", config.getRepositoryExtension());
		assertEquals("object", config.getRepositoryName());
		assertEquals("%028d", config.getSnapshotName());
		assertEquals(255, config.getWritableBlockSize());
	}
	
	@Test
	public void testGetterAndSetters() {
		Cipher inputCipher = new NullCipher();
		Cipher outputCipher = new NullCipher();
		BlockStorageConfiguration config = new BlockStorageConfiguration("Test", inputCipher, outputCipher);

		config.setBlockSize(12)
			.setRepositoryExtension("bla")
			.setRepositoryName("foo")
			.setSnapshotName("bar/");
		
		assertEquals(12, config.getBlockSize());
		assertEquals(8 * Integer.MAX_VALUE, config.getMaxBlocks());
		assertEquals("bla", config.getRepositoryExtension());
		assertEquals("foo", config.getRepositoryName());
		assertEquals("bar/", config.getSnapshotName());
		assertEquals(11, config.getWritableBlockSize());
	}
}
