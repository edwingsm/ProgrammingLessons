package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ForkJoinPool;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;
import ie.edwin.lessons.search.framework.ResultSetIds;

public class IndexIntervalContainer implements IntervalContainer {

	private HashMap<Short, Long> dataSource = new HashMap<Short, Long>();
	private NavigableMap<Long, List<Short>> index = new ConcurrentSkipListMap<>();

	public IndexIntervalContainer(long[] data) throws ContainerOutofBoundException {
		if (data == null || data.length == 0)
			throw new ContainerOutofBoundException("Contianor can't be created with empty data");
		else if (data.length > 32000)
			throw new ContainerOutofBoundException(
					"Input data excceds contianer data limit , Data should be limited to 32k");
		populateData(data);
		generateIndex(dataSource);
	}

	private void populateData(long[] data) {
		for (short i = 0; i < data.length; i++) {
			dataSource.put(i, data[i]);
		}
	}
	
	private void generateIndex(Map<Short,Long> data) {
		if(dataSource.isEmpty()) {	
		}
		
		final DataIndexGenrationTask task  = new DataIndexGenrationTask(dataSource, true);
		final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		index =new ConcurrentSkipListMap<>(forkJoinPool.invoke(task));
		forkJoinPool.shutdown();
	};

	
	
	public IdHolder findIdsInRange(final long fromValue, final long toValue, final boolean fromInclusive,
			final boolean toInclusive) {
		if (fromValue > toValue || (fromValue == toValue) || fromValue < 0) {
			throw new IllegalArgumentException(
					"The give Search range in not valid, Please provide a valid search range");
		}
		final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		final IndexSearchTask task = new IndexSearchTask(index, fromValue, toValue, fromInclusive, toInclusive);
		Set<Short> map = forkJoinPool.invoke(task);
		forkJoinPool.shutdown();
		return new ResultSetIds(map);
	}

}
