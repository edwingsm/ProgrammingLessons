package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinRangeContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamRangeContainerFactory;

public class ContianerNotCreatedTest {

	@Test
	public void invalidContainer_parallel_null_data() {
		IntervalContainerFactory rf = new ParallelStreamRangeContainerFactory();
		IntervalContainer pContainer = rf.createContainer(null);
		assertEquals(null, pContainer);
	}

	@Test
	public void invalidContainer_forkjoin_null_data() {
		IntervalContainerFactory rf = new ForkJoinRangeContainerFactory();
		IntervalContainer fkContainer = rf.createContainer(null);
		assertEquals(null, fkContainer);
	}

	@Test
	public void invalidContainer_parallel_Empty_data() {
		IntervalContainerFactory rf = new ParallelStreamRangeContainerFactory();
		IntervalContainer pContainer = rf.createContainer(new long[] {});
		assertEquals(null, pContainer);
	}

	@Test
	public void invalidContainer_forkjoin_Empty_data() {
		IntervalContainerFactory rf = new ForkJoinRangeContainerFactory();
		IntervalContainer fkContainer = rf.createContainer(new long[] {});
		assertEquals(null, fkContainer);
	}

}
