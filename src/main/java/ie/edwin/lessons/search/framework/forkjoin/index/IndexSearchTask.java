package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeSet;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class IndexSearchTask extends RecursiveTask<TreeSet<Short>> {

	private NavigableMap<Long, List<Short>> index;
	private long fromRange;
	private long toRange;
	private boolean fromInclusive;
	private boolean toInclusive;

	public IndexSearchTask(NavigableMap<Long, List<Short>> map, final long fromRange, final long toRange,
			final boolean fromInclusive, final boolean toInclusive) {
		this.index = map;
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.fromInclusive = fromInclusive;
		this.toInclusive = toInclusive;
	}

	@Override
	protected TreeSet<Short> compute() {
		final NavigableMap<Long, List<Short>> tailIndex = index.tailMap(fromRange, fromInclusive);
		
		if(tailIndex.isEmpty()){
			return new TreeSet<>();
		}
		return  new TreeSet<>(tailIndex.headMap(toRange, toInclusive).values().parallelStream().flatMap(List::stream).collect(Collectors.toSet()));
		
	}

}
