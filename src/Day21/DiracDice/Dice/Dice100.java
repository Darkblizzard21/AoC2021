package Day21.DiracDice.Dice;

public class Dice100 implements Dice{
    private long count = 0;
    private int nextNum = 0;
    @Override
    public int roll() {
        int res = nextNum + 1;
        nextNum = res % 100;
        count++;
        return res;
    }

    @Override
    public long rollCount() {
        return count;
    }
}
