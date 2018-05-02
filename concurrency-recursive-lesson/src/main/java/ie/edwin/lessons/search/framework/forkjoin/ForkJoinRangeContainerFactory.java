package ie.edwin.lessons.search.framework.forkjoin;

import ie.edwin.lessons.search.core.RangeContainer;
import ie.edwin.lessons.search.core.RangeContainerFactory;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;

public class ForkJoinRangeContainerFactory implements RangeContainerFactory {

	public RangeContainer createContainer(long[] data) {
		 RangeContainer rangeContainer = null;
		try {
			rangeContainer = new ForkJoinRangeContainer(data);
		} catch (ContainerOutofBoundException e) {
			System.out.println("Something worng happened during Contianer creation");
		}

		return rangeContainer;
	}

}
