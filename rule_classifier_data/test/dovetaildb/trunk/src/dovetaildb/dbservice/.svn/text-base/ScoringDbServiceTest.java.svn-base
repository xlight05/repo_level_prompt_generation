package dovetaildb.dbservice;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import dovetaildb.StdLib;
import dovetaildb.api.ApiBuffer;
import dovetaildb.bagindex.MemoryBlueSteelBagIndex;
import dovetaildb.bagindex.TrivialBagIndex;
import dovetaildb.util.Util;

public class ScoringDbServiceTest extends DbServiceTest {

	protected DbService createService() {
		return StdLib.makeMemoryDb();
	}
	
	public void testScoring() {
		HashMap<String,ApiBuffer> batch = new HashMap<String,ApiBuffer>();
		ApiBuffer buffer = new ApiBuffer();
		Object loc;
		loc = m().p("lat", 17.6).p("lng", 122.0);
		Map<String,Object> jane = m().p("name", "Jane").p("age", 30.0).p("gender", "f").p("loc", loc);
		buffer.put("ID2", jane);
		loc = m().p("lat", 22.8).p("lng", 122.8);
		Map<String,Object> phil = m().p("name", "Phil").p("age", 32.0).p("gender", "m").p("loc", loc);
		buffer.put("ID1", phil);
		batch.put("people", buffer);
		long commitId = db.commit(db.getHighestCompletedTxnId(), batch);
		
		List<Object> janeFirst = l().a(jane).a(phil); 
		List<Object> philFirst = l().a(phil).a(jane); 
		Object score;
		score = m().p("age", l().a("NumericScore"));
		assertEquals(philFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		score = m().p("age", l().a("ReverseScore").a(l().a("NumericScore")));
		assertEquals(janeFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		
		score = m().p("age", l().a("LinearlyInterpolatedScore").a(l().a( l().a(29).a(0) ).a( l().a(31).a(1) ).a( l().a(33  ).a(4) )));
		assertEquals(philFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		score = m().p("age", l().a("LinearlyInterpolatedScore").a(l().a( l().a(29).a(8) ).a( l().a(31).a(1) ).a( l().a(33  ).a(4) )));
		assertEquals(janeFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		score = m().p("age", l().a("LinearlyInterpolatedScore").a(l().a( l().a(21).a(8) ).a( l().a(31).a(1) ).a( l().a(33  ).a(4) )));
		assertEquals(philFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		score = m().p("age", l().a("LinearlyInterpolatedScore").a(l()                    .a( l().a(31).a(5) ).a( l().a(33  ).a(4) )));
		assertEquals(janeFirst, yank(db.query("people", commitId, m(), m().p("score", score))));
		score = m().p("age", l().a("LinearlyInterpolatedScore").a(l()                    .a( l().a(31).a(4) ).a( l().a(31.1).a(5) )));
		assertEquals(philFirst, yank(db.query("people", commitId, m(), m().p("score", score))));

		
	}
	
}
