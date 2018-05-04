package ie.edwin.lessons.search.framework;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinIntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.index.IndexIntervalContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamIntervalContainerFactory;

public class HighLimitTest {
	
	private IntervalContainer parallelRangeContainer;
	private IntervalContainer forkJoinContianer;
	private IntervalContainer indexContianer;
	private List<Integer> resultSetHolder= new ArrayList<>(); 
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
			resultSetHolder.add(i);
		}
		return data;
	}
	
	@Test
	public void runARangeQuery_forkJoin_index_inclusive(){
		IdHolder ids = indexContianer.findIdsInRange(1, 32000, true, true);
		verify(resultSetHolder.subList(0, resultSetHolder.size()-1), ids);
		
	}
	
	@Test
	public void runARangeQuery_forkJoin_bound_inclusive(){
		IdHolder ids = forkJoinContianer.findIdsInRange(1, 32000, true, true);
		verify(resultSetHolder.subList(0, resultSetHolder.size()-1), ids);
	}
	
	@Test
	public void runARangeQuery_Parallel_bound_inclusive(){
		IdHolder ids = parallelRangeContainer.findIdsInRange(1, 32000, true, true);
		verify(resultSetHolder.subList(0, resultSetHolder.size()-1), ids);
	}
	
	@Test
	public void runARangeQuery_forkJoin_index_exclusive(){
		IdHolder ids = indexContianer.findIdsInRange(1, 32000, false, false);
		verify(resultSetHolder.subList(1, resultSetHolder.size()-2), ids);
		
	}
	
	@Test
	public void runARangeQuery_forkJoin_bound_exclusive(){
		IdHolder ids = forkJoinContianer.findIdsInRange(1, 32000, false, false);
		verify(resultSetHolder.subList(1, resultSetHolder.size()-2), ids);
	}
	
	@Test
	public void runARangeQuery_Parallel_bound_exclusive(){
		IdHolder ids = parallelRangeContainer.findIdsInRange(1, 32000, false, false);
		verify(resultSetHolder.subList(1, resultSetHolder.size()-2), ids);
	}

	private void verify(List<Integer> result, IdHolder idHolder) {
		for (int integer : result) {
			assertEquals((short)integer, idHolder.nextId());
		}
	}
}
