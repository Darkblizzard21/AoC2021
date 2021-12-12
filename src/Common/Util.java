package Common;

public class Util {
    private Util(){}

    public static boolean isStringLowerCase(String str){
        return str.chars()
                .mapToObj(Character::isLowerCase)
                .allMatch(s->s);
    }
}
