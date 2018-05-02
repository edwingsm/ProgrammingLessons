package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import ie.edwin.lessons.search.core.RangeContainer;
import ie.edwin.lessons.search.core.RangeContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinRangeContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamRangeContainerFactory;

public class ContianerNotCreatedTest {

	@Test
	public void invalidContainer_parallel_null_data() {
		RangeContainerFactory rf = new ParallelStreamRangeContainerFactory();
		RangeContainer pContainer = rf.createContainer(null);
		assertEquals(null, pContainer);
	}

	@Test
	public void invalidContainer_forkjoin_null_data() {
		RangeContainerFactory rf = new ForkJoinRangeContainerFactory();
		RangeContainer fkContainer = rf.createContainer(null);
		assertEquals(null, fkContainer);
	}

	@Test
	public void invalidContainer_parallel_Empty_data() {
		RangeContainerFactory rf = new ParallelStreamRangeContainerFactory();
		RangeContainer pContainer = rf.createContainer(new long[] {});
		assertEquals(null, pContainer);
	}

	@Test
	public void invalidContainer_forkjoin_Empty_data() {
		RangeContainerFactory rf = new ForkJoinRangeContainerFactory();
		RangeContainer fkContainer = rf.createContainer(new long[] {});
		assertEquals(null, fkContainer);
	}

}
