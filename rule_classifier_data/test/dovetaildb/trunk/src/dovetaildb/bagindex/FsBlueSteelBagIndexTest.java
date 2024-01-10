package dovetaildb.bagindex;



public class FsBlueSteelBagIndexTest extends BlueSteelBagIndexTest {

	@Override
	protected BagIndex createIndex() {
		FsBlueSteelBagIndex index = new FsBlueSteelBagIndex(false);
		index.setTermTableDepth(3);
		return index;
	}

}
