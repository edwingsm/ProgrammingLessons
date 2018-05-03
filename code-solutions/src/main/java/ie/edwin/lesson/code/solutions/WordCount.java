package ie.edwin.lesson.code.solutions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WordCount {
	
	public static void main(String[] args) {
		String s="But I must explain to you how all this mistaken idea of denouncing pleasure and praising pain was born and I will give you a complete account "
				+ "of the system, and expound the actual teachings of the great explorer of the truth, the master-builder of human happiness. No one rejects, dislikes, "
				+ "or avoids pleasure itself, because it is pleasure,"
				+ " but because those who do not know how to pursue pleasure rationally encounter consequences that are extremely painful."
				+ " Nor again is there anyone who loves or pursues or desires to obtain pain of itself, because it is pain, but occasionally circumstances occur"
				+ " in which toil and pain can procure him some great pleasure. "
				+ "To take a trivial example, which of us ever undertakes laborious physical exercise, except to obtain some advantage from it?"
				+ " But who has any right to find fault with a man who chooses to enjoy a pleasure that has no annoying consequences, or one who avoids a pain that produces no resultant pleasure?"
				+"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, "
				+ "so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty "
				+ "through weakness of will, which is the same as saying through shrinking from toil and pain."
				+ " These cases are perfectly simple and easy to distinguish."
				+ " In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best,"
				+ " every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty "
				+ "or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always "
				+ "holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.";
		
		String noComa= s.replace(",", " ");
		String noFullstops= noComa.replace(".", " ").toLowerCase();
		List<String> wordsList= Arrays.asList(noFullstops.split("\\s+"));
		Map<String, Long> collect = 
		        wordsList.stream().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
		
	//	collect.forEach((k, v) -> System.out.println((k + ":" + v)));
		
		LinkedHashMap<String, Long> countByWordSorted = collect.entrySet()
	            .stream()
	            .sorted(Map.Entry.comparingByValue(Comparator.naturalOrder()))
	            .collect(Collectors.toMap(
	                    Map.Entry::getKey,
	                    Map.Entry::getValue,
	                    (v1, v2) -> {
	                        throw new IllegalStateException();
	                    },
	                    LinkedHashMap::new
	            ));
		countByWordSorted.forEach((k, v) -> System.out.println((k + ":" + v)));
		Map<String, Integer> counts = wordsList.parallelStream().
	            collect(Collectors.toConcurrentMap(
	                w -> w, w -> 1, Integer::sum));
		
	}

}
