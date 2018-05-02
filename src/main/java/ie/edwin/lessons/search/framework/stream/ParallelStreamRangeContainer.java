package ie.edwin.lessons.search.framework.stream;

import java.util.Map;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

import ie.edwin.lessons.search.core.Ids;
import ie.edwin.lessons.search.core.RangeContainer;
import ie.edwin.lessons.search.exceptions.ContainerOutofBoundException;
import ie.edwin.lessons.search.framework.ResultSetIds;
import ie.edwin.lessons.search.utils.SearchUtils;

public class ParallelStreamRangeContainer implements RangeContainer {

	private ConcurrentNavigableMap<Short, Long> dataSource = new ConcurrentSkipListMap<Short, Long>();

	ParallelStreamRangeContainer(long[] data) throws ContainerOutofBoundException {
		if (data == null || data.length == 0)
			throw new ContainerOutofBoundException("Contianor can't be created with empty data");
		else if (data.length > 32000)
			throw new ContainerOutofBoundException(
					"Input data excceds contianer data limit , Data should be limited to 32k");
		populateData(data);
	}

	private void populateData(long[] data) {
		for (short i = 0; i < data.length; i++) {
			dataSource.put(i, data[i]);
		}
	}

	public Ids findIdsInRange(final long fromValue, final long toValue, final boolean fromInclusive,
			final boolean toInclusive) {
		if (fromValue > toValue || (fromValue == toValue) || fromValue < 0) {
			throw new IllegalArgumentException(
					"The give Search range in not valid, Please provide a valid search range");
		}

		final Map<Short, Long> result = SearchUtils.searchParallel(dataSource, fromValue, toValue, fromInclusive, toInclusive);
		return new ResultSetIds(result.keySet());
	}

}
