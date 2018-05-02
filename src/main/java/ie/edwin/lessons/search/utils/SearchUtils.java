package ie.edwin.lessons.search.utils;

import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SearchUtils {

	public static Map<Short, Long> searchParallel(Map<Short, Long> dataMap, final long fromValue, final long toValue,
			final boolean fromInclusive, final boolean toInclusive) {

		final Map<Short, Long> result = dataMap.entrySet().parallelStream()
				.filter(serchPredicate(fromValue, toValue, fromInclusive, toInclusive))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
		return result;
	}
	public static Map<Short, Long> search(Map<Short, Long> dataMap, final long fromValue, final long toValue,
			final boolean fromInclusive, final boolean toInclusive) {

		final Map<Short, Long> result = dataMap.entrySet().stream()
				.filter(serchPredicate(fromValue, toValue, fromInclusive, toInclusive))
				.collect(Collectors.toMap(p -> p.getKey(), p -> p.getValue()));
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
