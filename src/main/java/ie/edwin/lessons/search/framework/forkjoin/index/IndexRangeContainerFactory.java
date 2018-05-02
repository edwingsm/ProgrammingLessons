package ie.edwin.lessons.search.framework.forkjoin.index;

import ie.edwin.lessons.search.core.RangeContainer;
import ie.edwin.lessons.search.core.RangeContainerFactory;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;

public class IndexRangeContainerFactory implements RangeContainerFactory {

	

	public RangeContainer createContainer(long[] data) {
		 RangeContainer rangeContainer = null;
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
