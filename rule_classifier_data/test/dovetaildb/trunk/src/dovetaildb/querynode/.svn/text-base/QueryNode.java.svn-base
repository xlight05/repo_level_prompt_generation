package dovetaildb.querynode;

import dovetaildb.bagindex.Range;
import dovetaildb.bytes.Bytes;
import dovetaildb.bytes.CompoundBytes;

/*
 *
 * Use cases:
 *  sort  (narrow existing range)
 *  specializing querynodes for content fetching (new narrowed copy)
 *  many ID fetch (unifying identical list traversals)
 * 
 */

public interface QueryNode {

	/** The document ID of the currently pointed to item.  This is valid at the time of creation. */
	public long doc();
	
	/** Moves to the next result.  Returns true if there is such a result. */
	public boolean next();
	
	/** Skips to the first term at or following the given doc id and term.  Remains in place is this requirement is already satisfied.  */
	public void seek(long seekDoc, Bytes seekTerm);
	
	public enum NextStatus {NEXT_TERM, NEXT_DOC, AT_END};
	/** Next term for current doc, false if no such term.  After a failed call, we are already pointing to the next document. 
	 * Does not advance the checkpoint used by resetTerms(). */
	public NextStatus nextTerm();
	
	/** The returned Bytes object is "borrowed" 
	 * Once nextTerm() or next() is called, 
	 * the second member of the CompoundBytes result may change, or 
	 * the second member may remain the same but with new or changed data.
	 * The first member is guaranteed to never to change. */
	public Bytes term();

	/** Returns a completely independent copy of this QueryNode */
	public QueryNode copy();
	
	/** 
	 * @param range Must be a range that's no wider than the range covered by this QueryNode
	 * @return A new, independent QueryNode (this QueryNode may be partially or fully rewound. it is not guaranteed to be correctly reporting all terms until you've caught back up to where you started)  
	 * or null (if there are no items exist matching this range)
	 * or this (if the new range does not permit an optimized query structure)
	 * If this is returned, the QueryNode's range is not narrowed
	 */
	public QueryNode specialize(Range range);

	public int compareTo(long docId, Bytes term);

	public Bytes findAnyMatching(long docId, Bytes prefix);
	
	public int cost();
	
	public boolean positionSet(long docId, Bytes prefix);
	
	public boolean positionNext();
	
	/** 
	 * Returns the smallest document ID in this
	 * querynode at or after docId.  QueryNode is positioned at
	 * some (any!) term for the returned doc id.
	 * Initial position of the QueryNode is irrelevant.
	 * Returns MAX_LONG if there is no such document id.
	 **/
	public long nextValidDocId(long docId);

	boolean anyNext();
	
}
