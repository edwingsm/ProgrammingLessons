package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedMap;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;

public class IndexSearchTask extends RecursiveTask<TreeSet<Short>> {

	private NavigableMap<Long, List<Short>> index;
	private long fromRange;
	private long toRange;
	private boolean fromInclusive;
	private boolean toInclusive;
	private long rangeDiffernce;

	public IndexSearchTask(SortedMap <Long, List<Short>> map, final long fromRange, final long toRange,
			final boolean fromInclusive, final boolean toInclusive) {
		this.index = new ConcurrentSkipListMap<>(map);
		this.fromRange = fromRange;
		this.toRange = toRange;
		this.fromInclusive = fromInclusive;
		this.toInclusive = toInclusive;
		this.rangeDiffernce=  toRange-toRange;
	}

	@Override
	protected TreeSet<Short> compute() {
		final NavigableMap<Long, List<Short>> tailIndex = index.tailMap(fromRange, fromInclusive);
		//final SortedMap<Long, List<Short>> index =index.subMap(fromRange, toRange);
		//No data found greater than this range, 
		if(tailIndex.isEmpty()){
			return new TreeSet<>();
		}
		//Generating result set by joining indexes of data, thought to use parallel stream
		return  generateResult(tailIndex.headMap(toRange, toInclusive));//new TreeSet<>(tailIndex.headMap(toRange, toInclusive).values().stream().flatMap(List::stream).collect(Collectors.toSet()));
		
	}
	
	private TreeSet<Short> generateResult(Map<Long, List<Short>> index){
		return new TreeSet<>(index.values().stream().flatMap(List::stream).collect(Collectors.toSet()));
	}

}
