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



public class Container32kDuplicateDataTest {
	
	private RangeContainer parallelRangeContainer;
	private RangeContainer forkJoinContianer;
	private RangeContainer indexRangeContianer;
	
	@Before
	public void setUp(){
		RangeContainerFactory rf = new ParallelStreamRangeContainerFactory();
		RangeContainerFactory rf2 = new ForkJoinRangeContainerFactory();
		RangeContainerFactory rf3 = new IndexRangeContainerFactory();
		long[] data = genrateTestData();
		parallelRangeContainer = rf.createContainer(data);
		forkJoinContianer = rf2.createContainer(data);
		indexRangeContianer=rf3.createContainer(data);
	}
	
	private long[] genrateTestData(){
		long[] data= new long[32000];
		for (int i = 0; i < 32000; i++) {
			if(i<16000)
				data[i]=i+1;
			else
				data[i]=i-15999;
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
		assertEquals(16013, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(16016, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin(){
		Ids ids = forkJoinContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(16013, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(16016, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_index(){
		Ids ids = indexRangeContianer.findIdsInRange(14, 17, true, true);
		assertEquals(13, ids.nextId());
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16, ids.nextId());
		assertEquals(16013, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(16016, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	
	@Test
	public void runARangeQuery_forkJoin_NoResult(){
		Ids ids = forkJoinContianer.findIdsInRange(40000, 50000, true, true);
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	@Test
	public void runARangeQuery_Parallel_NoResult(){
		Ids ids = forkJoinContianer.findIdsInRange(40000, 50000, true, true);
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Index_NoResult(){
		Ids ids = indexRangeContianer.findIdsInRange(40000, 50000, true, true);
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	
	@Test
	public void runARangeQuery_Parallel_exclusive(){
		Ids ids = parallelRangeContainer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
	}
	
	@Test
	public void runARangeQuery_forkJoin_exclusive(){
		Ids ids = forkJoinContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}
	
	@Test
	public void runARangeQuery_Index_exclusive(){
		Ids ids = indexRangeContianer.findIdsInRange(14, 17, false, false);
		assertEquals(14, ids.nextId());
		assertEquals(15, ids.nextId());
		assertEquals(16014, ids.nextId());
		assertEquals(16015, ids.nextId());
		assertEquals(Ids.END_OF_IDS, ids.nextId());
		
	}

}
