package ie.edwin.lessons.search.framework.forkjoin;

import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.core.IntervalContainerFactory;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;

public class ForkJoinRangeContainerFactory implements IntervalContainerFactory {

	public IntervalContainer createContainer(long[] data) {
		 IntervalContainer rangeContainer = null;
		try {
			rangeContainer = new ForkJoinRangeContainer(data);
		} catch (ContainerOutofBoundException e) {
			System.out.println("Something worng happened during Contianer creation");
		}

		return rangeContainer;
	}

}
