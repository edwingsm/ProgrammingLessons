package ie.edwin.lessons.search.core;

public interface IntervalContainerFactory {
	
	/**
	 * Builds an immutable container optimized for range queries. Data is expected
	 * to be 32k items or less. The position in the ÒdataÓ array represents the ÒidÓ
	 * for that instance in question. For the ÒPayrollResultÓ example before, the
	 * ÒidÓ might be the workerÕs employee number, the data value is the
	 * corresponding net pay. E.g. data[5]=2000 means that employee #6 has net pay
	 * of . 2000.
	 */
	IntervalContainer createContainer(long[] data);

}
