package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ForkJoinPool;

import ie.edwin.lessons.search.core.IdHolder;
import ie.edwin.lessons.search.core.IntervalContainer;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;
import ie.edwin.lessons.search.framework.ResultSetIdHolder;

public class IndexIntervalContainer implements IntervalContainer {
	//Keep Insertion order
	private Map<Short, Long> dataSource = new LinkedHashMap<Short, Long>();
	private SortedMap<Long, List<Short>> index = new TreeMap<>();

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
		
		final DataIndexGenrationTask task  = new DataIndexGenrationTask(dataSource, true,1);
		final ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
		index =new TreeMap<>(forkJoinPool.invoke(task));
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
		return new ResultSetIdHolder(map);
	}

}
