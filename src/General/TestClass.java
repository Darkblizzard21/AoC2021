package General;

import Day01.DepthMeasurement.DepthSampler;
import Day02.Movement.MovementController;
import Day02.Movement.MovementInputSampler;
import Day03.PowerConsumtion.RateCalculator;
import Day04.Bingo.BingoSolver;

public class TestClass {
    public static void main(String[] args) {
        int day = 4;
        if (args.length > 0 && CastTests.isNumeric(args[0])) {
            day = Integer.parseInt(args[0]);
        }

        switch (day) {
            case 1:
                DepthSampler ds = new DepthSampler();
                System.out.println("Simple Sampler Count: " + ds.SampleDepth());
                System.out.println("Sliding Sampler Count: " + ds.SampleDepth_Sliding());
                break;
            case 2:
                MovementInputSampler inputSampler = new MovementInputSampler();
                var list = inputSampler.SampleInput();
                System.out.println("Horizontal * Depth: " + MovementController.ApplyMovement(list));
                System.out.println("Horizontal * Depth (Aimed): " + MovementController.ApplyMovement_Aimed(list));
                break;
            case 3:
                RateCalculator rateCalculator = new RateCalculator();
                var input = rateCalculator.sampleInput();
                int gamma = rateCalculator.CalculateRate(input);
                int epsilon = RateCalculator.OtherRate(gamma);
                System.out.println("Gamma: " + gamma + " Epsilon: " + epsilon + " PowerConsumption: " + gamma * epsilon);
                int oxygen = rateCalculator.calculateOxygenRating(input);
                int co2 = rateCalculator.calculateCO2Rating(input);
                System.out.println("Oxygen: " + oxygen + " CO2: " + co2 + " oxygen generator rating: " + oxygen * co2);
                break;
            case 4:
                BingoSolver bs = new BingoSolver();
                int score = bs.SolveBingo();
                System.out.println("Winning Score: " + score);
                int scoreL = bs.SolveBingo_FindLooser();
                System.out.println("Loosing Score: " + scoreL);
                break;
        }
    }
}
