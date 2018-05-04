package ie.edwin.lessons.search.framework;

import java.util.Collection;
import java.util.PriorityQueue;

import ie.edwin.lessons.search.core.IdHolder;

public class ResultQueueIds implements IdHolder {
	
	/**
	 * PriorityQueue uses less memory
	 * Ensure  order of ids
	 */
	private PriorityQueue<Short> idQueue;

	public ResultQueueIds(Collection<Short> inputs) {
		if (inputs == null || inputs.isEmpty())
			idQueue = new PriorityQueue<>();

		this.idQueue = new PriorityQueue<Short>(inputs);
	}

	@Override
	public short nextId() {
		return  idQueue.size() > 0 ? idQueue.remove() : IdHolder.END_OF_IDS;
	}

}
