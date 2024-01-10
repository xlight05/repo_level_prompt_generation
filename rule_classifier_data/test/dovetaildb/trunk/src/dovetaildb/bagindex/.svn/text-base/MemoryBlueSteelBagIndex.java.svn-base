package dovetaildb.bagindex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import dovetaildb.bytes.ArrayBytes;

public class MemoryBlueSteelBagIndex extends BlueSteelBagIndex {

	private static final long serialVersionUID = 2831033961482268260L;
	
	Map<Long,TokenTable> history = new HashMap<Long,TokenTable>();
	TokenTable root = new MemoryTokenTable(ArrayBytes.EMPTY_BYTES, new ArrayList<TokenRec>());
	@Override
	protected TokenTable getRootTokenTable(long revNum) {
		if (revNum == topRevNum) return root;
		return history.get(revNum);
	}
	@Override
	protected void setNewTokenTable(TokenTable newTokenTable) {
		history.put(topRevNum, root);
		topRevNum++;
		root = newTokenTable;
	}
	@Override
	public void close() {}
};

