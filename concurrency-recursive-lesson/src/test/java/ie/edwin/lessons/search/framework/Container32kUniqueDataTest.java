package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinIntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.index.IndexIntervalContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamIntervalContainerFactory;



public class Container32kUniqueDataTest {
	
	private IntervalContainer parallelRangeContainer;
	private IntervalContainer forkJoinContianer;
	private IntervalContainer indexContianer;
	
	@Before
	public void setUp(){
		IntervalContainerFactory rf = new ParallelStreamIntervalContainerFactory();
		IntervalContainerFactory rf2 = new ForkJoinIntervalContainerFactory();
		IntervalContainerFactory rf3 = new IndexIntervalContainerFactory();
		long[] data = genrateTestData();
		parallelRangeContainer = rf.createContainer(data);
		forkJoinContianer = rf2.createContainer(data);
		indexContianer = rf3.createContainer(data);
	}
	
	private long[] genrateTestData(){
		long[] data= new long[32000];
		for (int i = 0; i < 32000; i++) {
			data[i]=i+1;
		}
		return data;
	}

	@Test
	public void runARangeQuery_Parallel(){
		IdHolder ids = parallelRangeContainer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin(){
		IdHolder ids = forkJoinContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_index(){
		IdHolder ids = indexContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Parallel_exclusive(){
		IdHolder ids = parallelRangeContainer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin_exclusive(){
		IdHolder ids = forkJoinContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_index_exclusive(){
		IdHolder ids = indexContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Parallel_bound_exclusive(){
		IdHolder ids = parallelRangeContainer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin_bound_exclusive(){
		IdHolder ids = forkJoinContianer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_forkJoin_index_exclusive(){
		IdHolder ids = indexContianer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(IdHolder.END_OF_IDS, ids.nextId());
		
	}

}
