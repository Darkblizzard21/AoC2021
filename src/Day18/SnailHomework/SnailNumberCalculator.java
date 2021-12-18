package Day18.SnailHomework;

import Common.Tuple;

public class SnailNumberCalculator extends SnailNumberCalculatorInputProvider{
    public SnailNumber addSnailNumbers(int loadId){
        var numbers = load(loadId).toArray(SnailNumber[]::new);
        var result = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            var next = numbers[i];
            result = result.add(next);
        }
        return result;
    }

    public Tuple<SnailNumber,SnailNumber> getHighestDualCombo(int loadId){
        var numbers = load(loadId).toArray(SnailNumber[]::new);
        long bestMag = 0;
        SnailNumber bestFist = new SnailNumber(0,null);
        SnailNumber bestSecond = new SnailNumber(0,null);
        for (int i = 0; i < numbers.length; i++) {
            for (int j = 0; j < numbers.length; j++) {
                if(i==j) continue;
                var nextMag = numbers[i].add(numbers[j]).magnitude();
                if(nextMag > bestMag){
                    bestMag = nextMag;
                    bestFist = numbers[i];
                    bestSecond = numbers[j];
                }
            }
        }
        return new Tuple<>(bestFist,bestSecond);
    }

    public long getHighestDualComboMag(int loadId){
        var x = getHighestDualCombo(loadId);
        return x.x.add(x.y).magnitude();
    }
}
