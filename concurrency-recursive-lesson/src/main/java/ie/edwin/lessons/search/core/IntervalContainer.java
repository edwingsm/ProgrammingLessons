package ie.edwin.lessons.search.core;

/**
 * a specialized container of records optimized for efficient range queries on
 * an attribute of the data.
 */
public interface IntervalContainer {
	/**
	 * 
	 * @param fromValue
	 * @param toValue
	 * @param fromInclusive
	 * @param toInclusive
	 * @return the Ids of all instances found in the container that have data value
	 *         between fromValue and toValue with optional inclusivity.
	 */
	IdHolder findIdsInRange(long fromValue, long toValue, boolean fromInclusive, boolean toInclusive);

}
