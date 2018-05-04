package ie.edwin.lessons.search.framework.forkjoin.index;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.RecursiveTask;

public class DataIndexGenrationTask extends RecursiveTask<Map<Long, List<Short>>> {

	private HashMap<Short, Long> data;
	public final int dataThreshold = 10000;
	// This flag is to prevent too much recursive call on the task
	private boolean intialsplit;
	//Identify the depth of recursive call
	private int depth;
	
	public DataIndexGenrationTask(Map<Short, Long> dataMap, boolean intialspilt,int depth) {
		//Just a simple hash map ,
		this.data = new HashMap<>(dataMap);
		this.intialsplit = intialspilt;
		this.depth=depth;
	}

	@Override
	protected Map<Long, List<Short>> compute() {
		if (data.size() > dataThreshold && intialsplit) {
			int total=data.size(),intial=0, mid=total/2 ;
			SortedMap<Short, Long> dataMap = new TreeMap<>(data);
			SortedMap<Short, Long> firsthalf=	dataMap.subMap((short)intial, (short)mid);
			SortedMap<Short, Long> secondhalf= dataMap.subMap((short)(mid+1),(short)total);
			DataIndexGenrationTask searchIndex = new DataIndexGenrationTask(firsthalf, false,depth++);
			DataIndexGenrationTask searchIndex2 = new DataIndexGenrationTask(secondhalf, false,depth++);
			invokeAll(searchIndex, searchIndex2);
			Map<Long, List<Short>> index1 = new HashMap<>(searchIndex.join());
			Map<Long, List<Short>> index2 = new HashMap<>(searchIndex2.join());
			
			//Merging  Indexes , for duplicate data
			index1.entrySet().stream()
					.forEach(entry -> index2.merge(entry.getKey(), entry.getValue(), (listTwo, listOne) -> {
						listOne.addAll(listTwo);
						return listOne;
					}));
			
			//Union of Index
			index2.forEach(index1::putIfAbsent);
			return index1;
		}
		
		return generrateDataIndex();
	}

	private Map<Long, List<Short>> generrateDataIndex() {
		final SortedMap<Long, List<Short>> index = new TreeMap<>();
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
