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
import Day13.Origami.PaperFolder;
import Day13.Origami.PaperFolder_WithDebug;
import Day14.PolymerizationExtention.PolymerBuilder;
import Day15.ShellPathfinder.Pathfinder;
import Day16.ElvesMessageDecoder.PackageParser;
import Day17.TargetTrickShots.ProbeLauncher;
import Day18.SnailHomework.SnailNumberCalculator;
import Day19.PositionTriangulating.ScannerAligner;

public class TestClass {
    public static void main(String[] args) {
        int day = 19;
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
            case 13:
                PaperFolder pf_wd = new PaperFolder();
                System.out.println("Points After 1. Fold (example): " + pf_wd.pointCountAfterFolds(true, 1));
                System.out.println("Points After 1. Fold (input): " + pf_wd.pointCountAfterFolds(false, 1));

                System.out.println("\nCode (example):");
                PaperFolder_WithDebug.visualize(pf_wd.pointsAfterFolds(true));
                System.out.println("Code (input):");
                PaperFolder_WithDebug.visualize(pf_wd.pointsAfterFolds(false));
                break;
            case 14:
                PolymerBuilder pb = new PolymerBuilder();
                System.out.println("Part 1:\n");
                System.out.println("Score (example): " + pb.scoreAfterXSteps_Recursive(10, true));
                System.out.println("Score (input): " + pb.scoreAfterXSteps_Recursive(10));

                System.out.println("\nPart 2:\n");
                System.out.println("Score (example): " + pb.scoreAfterXSteps_Recursive(40, true));
                System.out.println("Starting To Calculate result for 40 steps recursive ... this may take awhile ...");
                long startTime = System.nanoTime();
                System.out.println("Score (input): " + pb.scoreAfterXSteps_Recursive(40));
                long stopTime = System.nanoTime();
                System.out.println("Time Neede: " + (stopTime - startTime) / 1000000000d + "s");
                break;
            case 15:
                Pathfinder pf = new Pathfinder();
                System.out.println("Lowest Risk Total (example): " + pf.minimalPathRisk(true));
                System.out.println("Lowest Risk Total (input): " + pf.minimalPathRisk(false));

                System.out.println("Lowest Risk Total 5 (example): " + pf.minimalPathRisk5(true));
                System.out.println("Lowest Risk Total 5 (input): " + pf.minimalPathRisk5(false));
                break;
            case 16:
                PackageParser pp = new PackageParser();
                System.out.println("Task1: VersionSums:\n");
                System.out.println("Example 4 VersionSum: " + pp.sumOfVersions(4) + " == 16");
                System.out.println("Example 5 VersionSum: " + pp.sumOfVersions(5) + " == 12");
                System.out.println("Example 6 VersionSum: " + pp.sumOfVersions(6) + " == 23");
                System.out.println("Example 7 VersionSum: " + pp.sumOfVersions(7) + " == 31");

                System.out.println("\nInput VersionSum: " + pp.sumOfVersions(0));

                System.out.println("\nTask2: Packet Evaluation:\n");
                System.out.println("Example  8 Evaluated: " + pp.loadPacket(8).literalValue() + " == 3");
                System.out.println("Example  9 Evaluated: " + pp.loadPacket(9).literalValue() + " == 54");
                System.out.println("Example 10 Evaluated: " + pp.loadPacket(10).literalValue() + " == 7");
                System.out.println("Example 11 Evaluated: " + pp.loadPacket(11).literalValue() + " == 9");
                System.out.println("Example 12 Evaluated: " + pp.loadPacket(12).literalValue() + " == 1");
                System.out.println("Example 13 Evaluated: " + pp.loadPacket(13).literalValue() + " == 0");
                System.out.println("Example 14 Evaluated: " + pp.loadPacket(14).literalValue() + " == 0");
                System.out.println("Example 15 Evaluated: " + pp.loadPacket(15).literalValue() + " == 1");

                System.out.println("\nInput Evaluated: " + pp.loadPacket(0).literalValue());

                System.out.println();
                break;
            case 17:
                ProbeLauncher pl = new ProbeLauncher();
                System.out.println("Highest Y (example): " + pl.highestY(1) + " == 45");
                System.out.println("Highest Y (input): " + pl.highestY(0));

                System.out.println("Options (example): " + pl.numberOfOptions(1) + " == 112");
                System.out.println("Options (input): " + pl.numberOfOptions(0));
                break;
            case 18:
                SnailNumberCalculator snc = new SnailNumberCalculator();
                var snailExample1 = snc.addSnailNumbers(1);
                System.out.println("\nFist Part:\n");
                System.out.println("Magnitude (example1): " + snailExample1.magnitude() + "==3488");
                var snailExample2 = snc.addSnailNumbers(2);
                System.out.println("Magnitude (example2): " + snailExample2.magnitude() + "==4140");
                System.out.println("Magnitude (input): " + snc.addSnailNumbers(20).magnitude());

                System.out.println("\nSecond Part:\n");
                System.out.println("BestMag: (example2) " + snc.getHighestDualComboMag(2) + "==3993");
                System.out.println("BestMag: (input) " + snc.getHighestDualComboMag(0) );
                break;
            case 19:
                ScannerAligner sa = new ScannerAligner();
                System.out.println("\n" + sa.getNumberOfBeacons(1) +"==79");
                System.out.println("\n" + sa.getNumberOfBeacons(0));


                System.out.println("\nManhattans distance (example):" + sa.biggestManhattansDistance(1) +"==3621");
                System.out.println("\nManhattans distance (input):" + sa.biggestManhattansDistance(0));
                break;
        }
    }
}
