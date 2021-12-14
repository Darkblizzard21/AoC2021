package Day14.PolymerizationExtention;

import Common.Tuple;
import Common.Util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class PolymerBuilder extends PolymerBuilderInputProvider {
    private long lookUps;
    private Map<String, Character> mappings;
    private Map<Integer, Map<String, Map<Integer, Long>>> knowResults;

    public int scoreAfterXSteps_FullMemory(int steps) {
        return scoreAfterXSteps_FullMemory(steps, false);
    }

    public int scoreAfterXSteps_FullMemory(int steps, boolean useExample) {
        var in = load(useExample);
        String polymer = in.x;
        Map<String, Character> mappings = in.y;

        System.out.println("Template:\t\t" + polymer);
        for (int i = 0; i < steps; i++) {
            StringBuilder nextPolymer = new StringBuilder();
            nextPolymer.append(polymer.charAt(0));

            List<Character> chars = polymer.chars().mapToObj(y -> (char) y).collect(Collectors.toList());
            Util.PairwiseSliding(chars).forEach(t -> {
                char c = mappings.get("" + t.x + t.y);
                nextPolymer.append(c);
                nextPolymer.append(t.y);
            });

            polymer = nextPolymer.toString();

            System.out.println("After step " + (i + 1) + ":\t" + ((polymer.length() < 150) ? polymer : ("Size: " + polymer.length())));

            System.out.println(getScore(polymer));
        }

        return getScore(polymer);
    }

    public long scoreAfterXSteps_Recursive(int steps) {
        return scoreAfterXSteps_Recursive(steps, false);
    }

    public long scoreAfterXSteps_Recursive(int steps, boolean useExample) {
        var in = load(useExample);
        String polymer = in.x;
        mappings = in.y;
        knowResults = new HashMap<>();
        lookUps = 0;

        var charCount = interpret(polymer, steps);

        var sortedCounts = charCount.values().stream().sorted().collect(Collectors.toList());
        long result = sortedCounts.get(sortedCounts.size() - 1) - sortedCounts.get(0);

        System.out.println("lookups used: " + lookUps);
        mappings = null;
        knowResults = null;
        return result;
    }

    private Map<Integer, Long> interpret(String s, int step) {
        if (s.length() <= 0)
            return new HashMap<>();
        if (step <= 0)
            return count(s);

        // Try to get something from cache
        if (knowResults.containsKey(step)) {
            var results = knowResults.get(step);
            var longestContain = results.keySet().stream()
                    .sorted(Comparator.comparing(String::length).reversed())
                    .filter(s::contains)
                    .findFirst();
            if (longestContain.isPresent()) {
                lookUps++;
                var str = longestContain.get();
                var idx = s.indexOf(str);

                var cached = results.get(str);
                if (str.length() == s.length()) return cached;

                var preCached = interpret(s.substring(0, idx + 1), step);
                var postCached = interpret(s.substring(idx + str.length()), step);

                var result = merge(preCached, merge(cached, postCached));

                // Remove duplicated count
                Integer preChar = (int) s.charAt(idx);
                result.put(preChar, result.get(preChar) - 1);

                Integer postChar = (int) s.charAt(idx + str.length());
                result.put(postChar, result.get(postChar) - 1);

                results.put(str, result);
                return result;
            }
        }

        StringBuilder nextPolymer = new StringBuilder();
        nextPolymer.append(s.charAt(0));

        List<Character> chars = s.chars().mapToObj(y -> (char) y).collect(Collectors.toList());
        Util.PairwiseSliding(chars).forEach(t -> {
            char c = mappings.get("" + t.x + t.y);
            nextPolymer.append(c);
            nextPolymer.append(t.y);
        });

        var newPolymer = nextPolymer.toString();

        var result1 = interpret(newPolymer.substring(0, (newPolymer.length() / 2) + 1), step - 1);
        var result2 = interpret(newPolymer.substring(newPolymer.length() / 2), step - 1);
        var result = merge(result1, result2);

        Integer overlap = (int) newPolymer.charAt((newPolymer.length() / 2));
        result.put(overlap, result.get(overlap) - 1);

        saveMap(step, s, result);

        return result;
    }

    private Map<Integer, Long> count(String in) {
        return in.chars()
                .boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
    }

    private Map<Integer, Long> merge(Map<Integer, Long> a, Map<Integer, Long> b) {
        return Stream.concat(a.entrySet().stream(), b.entrySet().stream())
                .collect(Collectors.groupingBy(Map.Entry::getKey,
                        Collectors.summingLong(Map.Entry::getValue)));
    }

    private void saveMap(int step, String sToSave, Map<Integer, Long> mToSave) {
        if (knowResults.containsKey(step)) {
            var map = knowResults.get(step);
            map.put(sToSave, mToSave);
        } else {
            Map<String, Map<Integer, Long>> map = new HashMap<>();
            map.put(sToSave, mToSave);
            knowResults.put(step, map);
        }
    }

    private Tuple<Tuple<Character, Integer>, Tuple<Character, Integer>> getMostAndLeastCommon(String in) {
        var map = in.chars()
                .boxed()
                .collect(Collectors.groupingBy(c -> c, Collectors.counting()));
        long min = Long.MAX_VALUE;
        char minC = ' ';
        long max = Long.MIN_VALUE;
        char maxC = ' ';
        for (int key : map.keySet()) {
            var count = map.get(key);
            if (count < min) {
                min = count;
                minC = (char) key;
            }
            if (count > max) {
                max = count;
                maxC = (char) key;
            }
        }

        return new Tuple<>(
                new Tuple<>(maxC, (int) max),
                new Tuple<>(minC, (int) min)
        );
    }

    private int getScore(String s) {
        var t = getMostAndLeastCommon(s);
        return t.x.y - t.y.y;
    }
}
