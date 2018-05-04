package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinIntervalContainerFactory;

public class RangeQueryBasicTest {

	private IntervalContainer rc;

	@Before
	public void setUp() {
		IntervalContainerFactory rf = new ForkJoinIntervalContainerFactory();
		rc = rf.createContainer(new long[] { 10, 12, 17, 21, 2, 15, 16 });
	}

	@Test
	public void runARangeQuery() {
		IdHolder ids = rc.findIdsInRange(14, 17, true, true);
		assertEquals(2, ids.nextId());
		assertEquals(5, ids.nextId());
		assertEquals(6, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		ids = rc.findIdsInRange(14, 17, true, false);
		assertEquals(5, ids.nextId());
		assertEquals(6, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
		assertEquals(3, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());

		ids = rc.findIdsInRange(20, Long.MAX_VALUE, false, true);
		assertEquals(3, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
	}

}
