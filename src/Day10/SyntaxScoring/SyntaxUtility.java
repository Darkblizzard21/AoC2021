package Day10.SyntaxScoring;

import java.util.InputMismatchException;
import java.util.Stack;

public class SyntaxUtility {
    private SyntaxUtility(){}

    public static char mirrorBracket(char in) {
        switch (in) {
            case '(':
                return ')';
            case ')':
                return '(';
            case '[':
                return ']';
            case ']':
                return '[';
            case '{':
                return '}';
            case '}':
                return '{';
            case '<':
                return '>';
            case '>':
                return '<';
            default:
                throw new InputMismatchException();
        }
    }
    public static long scoreForStack(Stack<Character> in){
        long out = 0;
        while (!in.empty())
            out = out * 5 + scoreForCompletingChar(in.pop());
        return out;
    }

    private static long scoreForCompletingChar(char in){
        switch (in){
            case '(':
                return 1;
            case '[':
                return 2;
            case '{':
                return 3;
            case '<':
                return 4;
            default:
                throw new InputMismatchException();
        }
    }

    public static long scoreForChar(char in){
            switch (in){
                case ')':
                    return 3;
                case ']':
                    return 57;
                case '}':
                    return 1197;
                case '>':
                    return 25137;
                default:
                    throw new InputMismatchException();
            }
    }
}
