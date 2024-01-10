package dovetaildb.bagindex;

import gnu.trove.TLongLongHashMap;

import java.util.Collection;


public class BagIndexUtil {

	static long assignIds(Collection<EditRec> edits, long nextDocId) {
		TLongLongHashMap idMap = new TLongLongHashMap();
		for(EditRec edit : edits) {
			long docId = edit.docId; 
			if (docId < 0) {
				if (idMap.contains(docId)) {
					edit.docId = idMap.get(docId);
				} else {
					long newDocId = nextDocId++;
					idMap.put(docId, newDocId);
					edit.docId = newDocId;
				}
			}
		}
		return nextDocId;
	}
	

}
