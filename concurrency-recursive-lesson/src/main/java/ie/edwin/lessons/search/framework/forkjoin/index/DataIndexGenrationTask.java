package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.RecursiveTask;

public class DataIndexGenrationTask extends RecursiveTask<Map<Long, List<Short>>> {

	
	private HashMap<Short, Long> data;
	public final int dataThreshold = 10000;
	// This flag is to prevent too much recursive call on the task
	private boolean intialsplit;

	public DataIndexGenrationTask(Map<Short, Long> dataMap, boolean intialspilt) {
		this.data = new HashMap<>(dataMap);
		this.intialsplit = intialspilt;
	}

	@Override
	protected Map<Long, List<Short>> compute() {
		if (data.size() > dataThreshold && intialsplit) {
			NavigableMap<Short, Long> dataMap = new ConcurrentSkipListMap<>(data);
			NavigableMap<Short, Long> firsthalf = new ConcurrentSkipListMap<Short, Long>(
					dataMap.subMap((short) 0, true, (short) (data.size() / 2), false));
			NavigableMap<Short, Long> secondhalf = new ConcurrentSkipListMap<>(
					dataMap.subMap((short) (data.size() / 2), true, (short) data.size(), false));
			DataIndexGenrationTask searchIndex = new DataIndexGenrationTask(firsthalf, false);
			DataIndexGenrationTask searchIndex2 = new DataIndexGenrationTask(secondhalf, false);
			invokeAll(searchIndex, searchIndex2);
			Map<Long, List<Short>> index1 = new HashMap<>(searchIndex.join());
			Map<Long, List<Short>> index2 = new HashMap<>(searchIndex2.join());
			index1.entrySet().stream()
					.forEach(entry -> index2.merge(entry.getKey(), entry.getValue(), (listTwo, listOne) -> {
						listOne.addAll(listTwo);
						return listOne;
					}));
			index2.forEach(index1::putIfAbsent);
			return index1;
		}
		
		return generrateDataIndex();
	}

	private Map<Long, List<Short>> generrateDataIndex() {
		final ConcurrentNavigableMap<Long, List<Short>> index = new ConcurrentSkipListMap<>();
		data.forEach((k, v) -> {
			if (index.containsKey(v)) {
				index.get(v).add(k);
			} else {
				index.put(v, new ArrayList<Short>(Arrays.asList(k)));
			}
		});
		return index;
	}
}
