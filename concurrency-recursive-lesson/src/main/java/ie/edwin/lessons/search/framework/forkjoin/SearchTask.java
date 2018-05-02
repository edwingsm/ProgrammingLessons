package ie.edwin.lessons.search.framework.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * RecursiveTask for searching data
 * 
 * @author sajuadw
 *
 */
public class SearchTask extends RecursiveTask<Map<Short, Long>> {

	private static final long serialVersionUID = 1L;
	private int majorDataThreshold = 150001;
	private ConcurrentNavigableMap<Short, Long> dataMap;
	private long fromRange;
	private long toRange;
	private boolean fromInclusive;
	private boolean toInclusive;

	public SearchTask(final Map<Short, Long> dataSource, final long fromRange, final long toRange,
			final boolean fromInclusive, final boolean toInclusive) {
		this.dataMap = new ConcurrentSkipListMap<>(dataSource);
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.fromInclusive = fromInclusive;
		this.toInclusive = toInclusive;
	}

	@Override
	protected Map<Short, Long> compute() {
		final int size = dataMap.size();
		// This is not a perfect RecursiveTask, because the if condition is designed to overcome a stackoverflow error when map filled with 32k data
		if (size > majorDataThreshold+1000) {
			// List<SearchTask> tasks = createSubtasks();
			// tasks.get(0).fork();
			// tasks.get(1).fork();

			// Map<Short, Long> map = new ConcurrentHashMap<>(tasks.get(0).join());
			// map.putAll(tasks.get(1).join());
			// return map;

			return ForkJoinTask.invokeAll(createSubtasks()).stream().map(ForkJoinTask::join)
					.flatMap(map -> map.entrySet().parallelStream())
					.collect(Collectors.toConcurrentMap(Entry::getKey, Entry::getValue));
		}
		return search();
	}

	private List<SearchTask> createSubtasks() {
		final short lastKey = dataMap.lastKey();
		final short midkey = (short) (lastKey / 2);
		final short firstKey = dataMap.firstKey();
		final List<SearchTask> dividedTasks = new ArrayList<>();
		dividedTasks.add(
				new SearchTask(new ConcurrentSkipListMap<Short, Long>(dataMap.subMap(firstKey, true, midkey, false)),
						fromRange, toRange, fromInclusive, toInclusive));
		dividedTasks
				.add(new SearchTask(new ConcurrentSkipListMap<Short, Long>(dataMap.subMap(midkey, true, lastKey, true)),
						fromRange, toRange, fromInclusive, toInclusive));
		return dividedTasks;
	}

	private Map<Short, Long> search() {
		final Map<Short, Long> result = dataMap.entrySet().stream()
				.filter(serchPredicate(fromRange, toRange, fromInclusive, toInclusive))
				.collect(Collectors.toConcurrentMap(p -> p.getKey(), p -> p.getValue()));
		return result;
	}

	private static Predicate<? super Entry<Short, Long>> serchPredicate(final long fromValue, final long toValue,
			final boolean fromInclusive, final boolean toInclusive) {
		if (fromInclusive && !toInclusive)
			return p -> (p.getValue() >= fromValue && p.getValue() < toValue);
		else if (!fromInclusive && toInclusive)
			return p -> (p.getValue() > fromValue && p.getValue() <= toValue);
		else if (fromInclusive && toInclusive)
			return p -> (p.getValue() >= fromValue && p.getValue() <= toValue);
		else
			return p -> (p.getValue() > fromValue && p.getValue() < toValue);
	}

}