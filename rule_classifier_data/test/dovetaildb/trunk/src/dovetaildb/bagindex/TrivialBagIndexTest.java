package dovetaildb.bagindex;

public class TrivialBagIndexTest  extends BagIndexTest {

	protected BagIndex createIndex() {
		return new TrivialBagIndex();
	}	
	
}
