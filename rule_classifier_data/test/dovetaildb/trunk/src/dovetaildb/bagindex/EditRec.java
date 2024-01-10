package dovetaildb.bagindex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dovetaildb.bytes.ArrayBytes;
import dovetaildb.bytes.Bytes;

public class EditRec {

	long docId;
	Bytes term;
	boolean isDeletion;

	public EditRec(long docId, Bytes term, boolean isDeletion) {
		this.docId = docId;
		this.term = term;
		this.isDeletion = isDeletion;
	}
	
	public EditRec(long i, Bytes bytes) {
		this(i, bytes, false);
	}

	public EditRec(EditRec r) {
		this(r.docId, r.term, r.isDeletion);
	}

	public long getDocId() {
		return docId;
	}
	public void setDocId(long docId) {
		this.docId = docId;
	}

	public boolean isDeletion() {
		return isDeletion;
	}
	public void setDeletion(boolean isDeletion) {
		this.isDeletion = isDeletion;
	}

	public Bytes getTerm() { return term; }
	public void setTerm(Bytes t) { term = t; }
	
	public byte[] getTermBytes() {
		return term.getBytes();
	}
	public void setTermBytes(byte[] term) {
		this.term = new ArrayBytes(term);
	}

	public static Comparator<EditRec> cmpById = new Comparator<EditRec>() {
		public int compare(EditRec o1, EditRec o2) {
			long ret = o1.docId - o2.docId;
			if (ret == 0) return o1.term.compareTo(o2.term);
			else return (ret > 0) ? 1 : -1;
		}
	};
	public static void sortById(ArrayList<EditRec> edits) {
		Collections.sort(edits, cmpById);
	}
	
	public static Comparator<EditRec> cmpByTerm = new Comparator<EditRec>() {
		public int compare(EditRec o1, EditRec o2) {
			int ret = o1.term.compareTo(o2.term);
			if (ret != 0) return ret;
			long diff = o1.docId - o2.docId;
			return (diff > 0) ? 1 : -1;
		}
	};
	public static void sortByTerm(ArrayList<EditRec> edits) {
		Collections.sort(edits, cmpByTerm);
	}
	
	public String toString() {
		return "EditRec("+docId+":"+term+(isDeletion? "<del>":"")+")";
	}

	public static List<EditRec> cloneList(List<EditRec> edits) {
		ArrayList<EditRec> newList = new ArrayList<EditRec>(edits.size());
		for(EditRec r : edits) {
			newList.add(new EditRec(r));
		}
		return newList;
	}
	
	public boolean equals(Object o) {
		if (o==null || !(o instanceof EditRec)) return false;
		EditRec other = (EditRec)o;
		return docId == other.docId && term.equals(other.term);
	}
}
