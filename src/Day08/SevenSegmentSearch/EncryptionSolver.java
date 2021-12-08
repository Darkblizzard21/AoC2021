package Day08.SevenSegmentSearch;

import Common.Tuple;

import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EncryptionSolver {
    private static final int OFFSET = 97;

    private static final String[] digits = new String[]{
            "abcefg",
            "cf",
            "acdeg",
            "acdfg",
            "bcdf",
            "abdfg",
            "abdefg",
            "acf",
            "abcdefg",
            "abcdfg"
    };

    public static Character[] decrypt(String[] strings) {
        assert strings.length == 10 : "Input Mismatch";

        var lengthMap = Arrays.stream(strings)
                .map(s -> s.chars()
                        .sorted()
                        .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                        .toString())
                .collect(Collectors.groupingBy(String::length, Collectors.toList()));

        // c f Query
        String cfQuery = lengthMap.get(2).get(0);
        assert cfQuery.length() == 2 : "Query Failed";
        int[] cfIdx = buildIndex(cfQuery);

        // a Query
        String aQuery = lengthMap.get(3).get(0)
                .replace("" + cfQuery.charAt(0), "")
                .replace("" + cfQuery.charAt(1), "");
        assert aQuery.length() == 1;
        final char A = aQuery.charAt(0);

        // b d Query
        String bdQuery = lengthMap.get(4).get(0)
                .replace("" + cfQuery.charAt(0), "")
                .replace("" + cfQuery.charAt(1), "");
        assert bdQuery.length() == 2 : "Query Failed";

        // c d e Query
        String cdeQuery;
        {
            var sixLong = lengthMap.get(6);         // 0 6 9
            var sevenLong = lengthMap.get(7).get(0);
            StringBuilder cdeList = new StringBuilder();
            for (var six : sixLong) {
                var x = (six + sevenLong).chars().distinct().toArray();
                cdeList.append((char) x[6]);
            }
            cdeQuery = cdeList.toString();
            assert cdeQuery.length() == 3 : "Query Failed";
        }

        // bce Query
        String bceQuery;
        {
            var sixLong = lengthMap.get(6);         // 0 6 9
            var fiveLong = lengthMap.get(5);         // 3 3 5
            StringBuilder bceList = new StringBuilder();
            for (var six : sixLong) {
                for (var five : fiveLong) {
                    var x = (five + six).chars().distinct().toArray();
                    if (x.length == 6)
                        bceList.append((char) x[5]);
                }
            }
            bceQuery = bceList.toString();
            assert bceQuery.length() == 3 : "Query Failed";
        }


        // ce Query
        var ceQuery = (cdeQuery + bceQuery).chars()
                .distinct()
                .mapToObj(i -> "" + (char) i)
                .reduce("", (p, c) -> p + c)
                .replace("" + bdQuery.charAt(0), "")
                .replace("" + bdQuery.charAt(1), "");
        assert ceQuery.length() == 2 : "Query Failed";
        int[] ceIdx = buildIndex(ceQuery);

        // d Query
        final char D = cdeQuery
                .replace("" + ceQuery.charAt(0), "")
                .replace("" + ceQuery.charAt(1), "")
                .charAt(0);

        // b Query
        final char B = bdQuery
                .replace("" + D, "")
                .charAt(0);

        // c Query
        final char C = (char) (cfIdx[cfIdx[0] == ceIdx[0] || cfIdx[0] == ceIdx[1] ? 0 : 1] + OFFSET);

        // f Query
        final char F = cfQuery.replace("" + C, "").charAt(0);

        // E Query
        final char E = ceQuery.replace("" + C, "").charAt(0);

        // G Query
        final char G = lengthMap.get(7).get(0)
                .replace("" + A, "")
                .replace("" + B, "")
                .replace("" + C, "")
                .replace("" + D, "")
                .replace("" + E, "")
                .replace("" + F, "").charAt(0);

        // Build Final result as array
        final Character[] result = new Character[7];
        result[A - OFFSET] = 'a';
        result[B - OFFSET] = 'b';
        result[C - OFFSET] = 'c';
        result[D - OFFSET] = 'd';
        result[E - OFFSET] = 'e';
        result[F - OFFSET] = 'f';
        result[G - OFFSET] = 'g';

        return result;
    }

    private static int[] buildIndex(String in) {
        int[] res = new int[in.length()];
        for (int i = 0; i < res.length; i++) {
            res[i] = in.charAt(i) - OFFSET;
        }
        return res;
    }

    public static String decryptString(String toDecrypt, Character[] mapping) {
        StringBuilder r = new StringBuilder();
        for (int i = 0; i < toDecrypt.length(); i++) {
            var x = toDecrypt.charAt(i) - OFFSET;
            r.append(mapping[x]);
        }
        return r.toString();
    }

    public static int interpret(String in) {
        var sorted = in.chars()
                .sorted()
                .mapToObj(i -> "" + (char) i)
                .reduce("", (p, c) -> p + c);
        for (int i = 0; i < digits.length; i++) {
            if (digits[i].equals(sorted)) {
                return i;
            }
        }
        throw new IllegalArgumentException(sorted);
    }

    public long Solve() {
        return loadFullInput()
                .map(t -> new Tuple<>(decrypt(t.x), t.y))
                .map(t -> Arrays.stream(t.y)
                        .map(s -> decryptString(s, t.x))
                        .map(EncryptionSolver::interpret)
                        .map(i -> "" + i)
                        .reduce("", (p, c) -> p + c)
                )
                .map(Long::parseLong)
                .reduce(Long::sum)
                .orElseThrow();
    }

    private Stream<Tuple<String[], String[]>> loadFullInput() {
        URL url = getClass().getResource("input.txt");
        assert url != null;

        try {
            return Files
                    .lines(Paths.get(url.getPath().substring(1)))
                    .map(s -> new Tuple<>(
                            s.substring(0, 58).split(" "),
                            s.substring(61).split(" ")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new IllegalStateException();
    }
}
