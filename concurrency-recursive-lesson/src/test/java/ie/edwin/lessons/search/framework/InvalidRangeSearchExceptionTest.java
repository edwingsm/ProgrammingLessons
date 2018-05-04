package ie.edwin.lessons.search.framework;

import org.junit.Before;
import org.junit.Test;

import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.framework.forkjoin.ForkJoinIntervalContainerFactory;
import ie.edwin.lessons.search.framework.stream.ParallelStreamIntervalContainerFactory;



public class InvalidRangeSearchExceptionTest {
	
	private IntervalContainer parallelRangeContainer;
	private IntervalContainer forkJoinContianer;
	
	@Before
	public void setUp(){
		IntervalContainerFactory rf = new ParallelStreamIntervalContainerFactory();
		IntervalContainerFactory rf2 = new ForkJoinIntervalContainerFactory();
		parallelRangeContainer = rf.createContainer(new long[]{10,12,17,21,2,15,16});
		forkJoinContianer = rf2.createContainer(new long[]{10,12,17,21,2,15,16});
	}
	

	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_Parallel_to_lt_from(){
		 parallelRangeContainer.findIdsInRange(2, 1, true, true);
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_ForkJoin_to_lt_from(){
		 forkJoinContianer.findIdsInRange(2, 1, true, true);
		
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_Parallel_to_eq_from(){
		parallelRangeContainer.findIdsInRange(2, 2, true, true);
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_ForkJoin_to_eq_from(){
		forkJoinContianer.findIdsInRange(2, 2, true, true);
		
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_Parallel_from_negative(){
		parallelRangeContainer.findIdsInRange(-2, 2, true, true);
	}
	
	@Test(expected= IllegalArgumentException.class)
	public void invalidRange_ForkJoin_from_negative(){
		forkJoinContianer.findIdsInRange(-2, 2, true, true);
		
	}
	
	
	

}
