package Common;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Util {
    private Util(){}

    public static boolean isStringLowerCase(String str){
        return str.chars()
                .mapToObj(Character::isLowerCase)
                .allMatch(s->s);
    }

    public static <X> Stream<Tuple<X,X>> PairwiseSliding(List<X> in){
        return IntStream.range(1, in.size())
                .mapToObj(i -> new Tuple<>(in.get(i-1), in.get(i)));
    }
}
