package ie.edwin.lessons.search.framework;

import java.util.Collection;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.concurrent.ConcurrentSkipListSet;

import ie.edwin.lessons.search.core.IdHolder;

public class ResultSetIdHolder implements IdHolder {
	
	/**
	 * ConcurrentSkipListSet are Fail-safe and Thread-safe
	 * Ensure  order of ids
	 */
	private NavigableSet<Short> idList = null;
	private Iterator<Short> idIterator = null;


	public ResultSetIdHolder(Collection<Short> inputs)  {
		if(inputs == null || inputs.isEmpty())
			idList = new ConcurrentSkipListSet<>();
		
		this.idList = new ConcurrentSkipListSet<Short>(inputs);
		this.idIterator = idList.iterator();
	}

	@Override
	public short nextId() {
		if (idIterator.hasNext()) {
			return idIterator.next();
		} else {
			return -1;
		}
	}

}
