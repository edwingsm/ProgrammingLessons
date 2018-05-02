package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import ie.edwin.lessons.search.core.Ids;
import ie.edwin.lessons.search.core.RangeContainer;
import ie.edwin.lessons.search.core.RangeContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinRangeContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.index.IndexRangeContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamRangeContainerFactory;
import javafx.scene.control.IndexRange;



public class Container32kUniqueDataTest {
	
	private RangeContainer parallelRangeContainer;
	private RangeContainer forkJoinContianer;
	private RangeContainer indexContianer;
	
	@Before
	public void setUp(){
		RangeContainerFactory rf = new ParallelStreamRangeContainerFactory();
		RangeContainerFactory rf2 = new ForkJoinRangeContainerFactory();
		RangeContainerFactory rf3 = new IndexRangeContainerFactory();
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
		Ids ids = parallelRangeContainer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin(){
		Ids ids = forkJoinContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_index(){
		Ids ids = indexContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Parallel_exclusive(){
		Ids ids = parallelRangeContainer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin_exclusive(){
		Ids ids = forkJoinContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_index_exclusive(){
		Ids ids = indexContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Parallel_bound_exclusive(){
		Ids ids = parallelRangeContainer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin_bound_exclusive(){
		Ids ids = forkJoinContianer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_forkJoin_index_exclusive(){
		Ids ids = indexContianer.findIdsInRange(31999, 32000, true, true);
		assertEquals(31998, ids.nextId());
		assertEquals(31999, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}

}
