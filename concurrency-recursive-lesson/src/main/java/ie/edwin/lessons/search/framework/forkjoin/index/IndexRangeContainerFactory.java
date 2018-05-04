package ie.edwin.lessons.search.framework.forkjoin.index;

import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;

public class IndexRangeContainerFactory implements IntervalContainerFactory {

	

	public IntervalContainer createContainer(long[] data) {
		 IntervalContainer rangeContainer = null;
		try {
			rangeContainer = new IndexRangeContainer(data);
		} catch (ContainerOutofBoundException e) {
			//Since instruction given not modify any interface i din't add throws in method 
			//Also forced to return null container which is not good
			System.out.println("Something worng happened during Contianer creation");
		}
		return rangeContainer;
	}

}
