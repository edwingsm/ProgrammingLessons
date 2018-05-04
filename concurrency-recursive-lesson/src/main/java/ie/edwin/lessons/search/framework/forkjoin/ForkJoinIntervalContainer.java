package ie.edwin.lessons.search.framework.forkjoin;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;
import ie.edwin.lessons.search.framework.ResultSetIds;

public class ForkJoinIntervalContainer implements IntervalContainer {

	private Map<Short, Long> dataSource = new HashMap<Short, Long>();

	public ForkJoinIntervalContainer(long[] data) throws ContainerOutofBoundException {

		if (data == null || data.length == 0)
			throw new ContainerOutofBoundException("Contianor can't be created with empty data");
		else if (data.length > 32000)
			throw new ContainerOutofBoundException(
					"Input data excceds contianer data limit , Data should be limited to 32k");

		populateData(data);

	}

	private void populateData(final long[] data) {
		for (short i = 0; i < data.length; i++) {
			dataSource.put(i, data[i]);
		}
	}

	public IdHolder findIdsInRange(final long fromValue, long toValue, boolean fromInclusive, boolean toInclusive) {
		if (fromValue > toValue || (fromValue == toValue) || fromValue < 0 || toValue < 0) {
			throw new IllegalArgumentException(
					"The give Search range in not valid, Please provide a valid search range");
		}
		
		
		ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		SearchTask task = new SearchTask(dataSource, fromValue, toValue, fromInclusive, toInclusive);
		Map<Short, Long> map = forkJoinPool.invoke(task);
		forkJoinPool.shutdown();
		return new ResultSetIds(map.keySet());
	}
}
