import java.util.Arrays;
import java.util.Collections;
import java.util.List;


class testLambdas {

	List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");


	Collections.sort(names,(a,b)-> b.compareTo(a));


}
