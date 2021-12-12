import Common.CastTests;
import Day01.DepthMeasurement.DepthSampler;
import Day02.Movement.MovementController;
import Day02.Movement.MovementInputSampler;
import Day03.PowerConsumtion.RateCalculator;
import Day04.Bingo.BingoSolver;
import Day05.HydrothermalVenture.LinePlotter;
import Day06.Lanternfish.FishGrowthCalculator;
import Day07.CrabAlignment.CrabAligner;
import Day08.SevenSegmentSearch.EncryptionSolver;
import Day08.SevenSegmentSearch.NumberCounter;
import Day09.LavaTubes.SmokeMapScanner_WithDebug;
import Day10.SyntaxScoring.SyntaxChecker;
import Day11.OctopusDisco.FlashPredictor_WithDebug;
import Day12.GraphPassagePathing.PassageExplorer;

public class TestClass {
    public static void main(String[] args) {
        int day = 12;
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
            case 5:
                LinePlotter lp = new LinePlotter();
                long crossings = lp.findNumberOfCrossings();
                System.out.println("Crossings with of at least 2 lines (excluding Diagonal): " + crossings);
                long allCrossings = lp.findNumberOfCrossings_IncDiagonals();
                System.out.println("Crossings with of at least 2 lines: " + allCrossings);
                break;
            case 6:
                FishGrowthCalculator fgc = new FishGrowthCalculator();
                long fs18 = fgc.countFishAfterDay(18);
                long fs80 = fgc.countFishAfterDay(80);
                long fs256 = fgc.countFishAfterDay(256);
                System.out.println("Count day018: " + fs18 + "\nCount day080: " + fs80 + "\nCount day256: " + fs256);
                break;
            case 7:
                CrabAligner crabAligner = new CrabAligner();
                System.out.println("Median: " + crabAligner.CrabAlignCost_Median()
                        + "\nAverage: " + crabAligner.CrabAlignCost_Average());
                break;
            case 8:
                // Part 1
                NumberCounter counter = new NumberCounter();
                System.out.println(counter.count1478());
                // Part 2
                EncryptionSolver solver = new EncryptionSolver();
                // Test Identical
                var identical = EncryptionSolver.decrypt(new String[]{
                        "abcefg", "cf", "acdeg", "acdfg", "bcdf", "abdfg", "abdefg", "acf", "abcdefg", "abcdfg"
                });
                // Test Example
                var example = EncryptionSolver.decrypt(new String[]{
                        "acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"
                });
                System.out.println("" + EncryptionSolver.interpret(EncryptionSolver.decryptString("cdfeb", example))
                        + EncryptionSolver.interpret(EncryptionSolver.decryptString("fcadb", example))
                        + EncryptionSolver.interpret(EncryptionSolver.decryptString("cdfeb", example))
                        + EncryptionSolver.interpret(EncryptionSolver.decryptString("cdbaf", example)));
                // Solve Problem
                System.out.println(solver.Solve());
                break;
            case 9:
                SmokeMapScanner_WithDebug sms = new SmokeMapScanner_WithDebug();
                sms.debugPrintBasin_Map();
                sms.debugPrintBasin_Singles();
                System.out.println("\nLowPoint Score: " + sms.findLowPointScore());
                var basins = sms.findBasinScore();
                System.out.println("Baisns: " + basins);
                break;
            case 10:
                SyntaxChecker sc = new SyntaxChecker();
                System.out.println("Corruption Score: " + sc.getCorruptionScore());
                System.out.println("Completion Score: " + sc.getCompletionScore());
                break;
            case 11:
                FlashPredictor_WithDebug fp = new FlashPredictor_WithDebug();
                int steps = 100;
                fp.Flashes_PrintLastBoard(steps);
                System.out.println("Flash Count: " + fp.Flashes(steps));

                System.out.println("First Synced Flash: " + fp.FirstSyncedFlash());
                break;
            case 12:
                PassageExplorer pe = new PassageExplorer();

                System.out.println("Number of paths for Example 1:\t" + pe.calculatePathCount(1) + " (==10)");
                System.out.println("Number of paths for Example 2:\t" + pe.calculatePathCount(2) + " (==19)");
                System.out.println("Number of paths for Example 3:\t" + pe.calculatePathCount(3) + " (==226)");
                System.out.println("\nNumber of paths for Input:\t\t" + pe.calculatePathCount(0));

                System.out.println("With doubleVisitAllowed:");
                System.out.println("\nNumber of paths for Example 1:\t"
                        + pe.calculatePathCount(1, true) + " (==36)");
                System.out.println("Number of paths for Example 2:\t"
                        + pe.calculatePathCount(2, true) + " (==103)");
                System.out.println("Number of paths for Example 3:\t"
                        + pe.calculatePathCount(3, true) + " (==3509)");
                System.out.println("\nNumber of paths for Input:\t\t" + pe.calculatePathCount(0, true));
                break;
        }
    }
}
