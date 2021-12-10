package Day10.SyntaxScoring;

import Common.Tuple;


import java.util.InputMismatchException;
import java.util.stream.Collectors;
import java.util.Optional;
import java.util.Stack;

public class SyntaxChecker extends SyntaxCheckerInputProvider {
    public long getCorruptionScore() {
        return loadInput()
                .map(this::isCorruptedOrIncomplete)
                .filter(t -> t.x.isPresent())
                .map(t -> t.x.get())
                .mapToLong(SyntaxUtility::scoreForChar)
                .sum();
    }

    public long getCompletionScore() {
        var scores = loadInput()
                .map(this::isCorruptedOrIncomplete)
                .filter(t -> t.y.isPresent())
                .map(t -> t.y.get())
                .map(SyntaxUtility::scoreForStack)
                .sorted()
                .collect(Collectors.toList());
        int count = scores.size();
        if (count % 2 == 0) { // even number
            return (scores.get(count / 2 - 1) + scores.get(count / 2)) / 2;
        } else { // odd number
            return scores.get(count / 2);
        }
    }

    private Tuple<Optional<Character>, Optional<Stack<Character>>> isCorruptedOrIncomplete(String line) {
        return isCorruptedOrIncomplete(line, new Stack<>());
    }

    private Tuple<Optional<Character>, Optional<Stack<Character>>> isCorruptedOrIncomplete(
            String line,
            Stack<Character> openChunks) {
        if (line.length() <= 0)
            return new Tuple<>(
                    Optional.empty(),
                    openChunks.empty() ? Optional.empty() : Optional.of(openChunks)
            );
        char value = line.charAt(0);
        switch (value) {
            case '(':
            case '[':
            case '{':
            case '<':
                openChunks.push(value);
                return isCorruptedOrIncomplete(line.substring(1), openChunks);
            case ')':
            case ']':
            case '}':
            case '>':
                char toClose = openChunks.pop();
                if (toClose == SyntaxUtility.mirrorBracket(value))
                    return isCorruptedOrIncomplete(line.substring(1), openChunks);
                return new Tuple<>(Optional.of(value), Optional.empty());
            default:
                throw new InputMismatchException();
        }
    }
}
