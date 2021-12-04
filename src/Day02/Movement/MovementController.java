package Day02.Movement;

import Common.Tuple;

import java.util.List;

public class MovementController {
    public static int ApplyMovement(List<Tuple<InputType, Integer>> in) {
        int depth = 0;
        int horizontal = 0;
        for (var t : in) {
            switch (t.x){
                case up:
                    depth -= t.y;
                    break;
                case down:
                    depth += t.y;
                    break;
                case forward:
                    horizontal += t.y;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        return depth * horizontal;
    }

    public static int ApplyMovement_Aimed(List<Tuple<InputType, Integer>> in) {
        int depth = 0;
        int horizontal = 0;
        int aim = 0;
        for (var t : in) {
            switch (t.x){
                case up:
                    aim -= t.y;
                    break;
                case down:
                    aim += t.y;
                    break;
                case forward:
                    horizontal += t.y;
                    depth += aim * t.y;
                    break;
                default:
                    throw new IllegalStateException();
            }
        }
        return depth * horizontal;
    }
}
