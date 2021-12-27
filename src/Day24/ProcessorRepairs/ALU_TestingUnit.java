package Day24.ProcessorRepairs;

import Day24.ProcessorRepairs.ALU.ALU;
import Day24.ProcessorRepairs.ProgrammParts.DividingPart;
import Day24.ProcessorRepairs.ProgrammParts.MultiplingPart;
import Day24.ProcessorRepairs.ProgrammParts.ProgrammPart;

import java.util.LinkedList;
import java.util.List;

public class ALU_TestingUnit extends ALU_TestingUnitInputProvider {
    public String findLargestNumber() {
        ALU alu = new ALU();
        var programm = load(0);

        var programmParts = toParts(programm);

        var res = solve(programmParts,false);

        alu.run(programm,res);

        return res;
    }

    public String findSmallestNumber() {
        ALU alu = new ALU();
        var programm = load(0);

        var programmParts = toParts(programm);

        var res = solve(programmParts);

        alu.run(programm,res);

        return res;
    }

    private String solve(List<ProgrammPart> programmParts) {
        return solve(programmParts, true);
    }

    private String solve(List<ProgrammPart> programmParts, boolean findSmallest) {
        return solve(0, 0, programmParts, findSmallest);
    }

    private String solve(int z, int digit, List<ProgrammPart> programmParts, boolean forward) {
        if (digit >= programmParts.size()) return z == 0 ? "" : "!";
        var programmPart = programmParts.get(digit);
        if (programmPart.isDividing()) {
            var toHit = programmPart.toHit(z);
            if (0 < toHit && toHit < 10) {
                return toHit + solve(programmPart.evauluate(toHit, z), digit + 1, programmParts, forward);
            } else {
                return "!";
            }
        }

        for (int i = forward ? 1 : 9; forward ? i < 10 : i > 0; i = i + (forward ? 1 : -1)) {
            var res = solve(programmPart.evauluate(i, z), digit + 1, programmParts, forward);
            if(!res.endsWith("!"))
                return i + res;
        }
        return "!";
    }

    private List<ProgrammPart> toParts(List<String> program) {
        List<String> programFragment = new LinkedList<>();
        List<ProgrammPart> res = new LinkedList<>();
        for (var line : program) {
            if (line.equals("inp w") && programFragment.size() != 0) {
                res.add(toPart(programFragment));
                programFragment.clear();
            }
            programFragment.add(line);
        }
        res.add(toPart(programFragment));
        return res;
    }

    private ProgrammPart toPart(List<String> programFragment) {
        String[] strings = programFragment.toArray(String[]::new);

        int offsetLine = -1;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("add y w")) {
                offsetLine = i + 1;
                break;
            }
        }
        int offset = Integer.parseInt(strings[offsetLine].substring(6));

        int evalLine = -1;
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals("eql x w")) {
                evalLine = i;
                break;
            }
        }
        String addLine = strings[evalLine - 1];
        int addNumber = Integer.parseInt(addLine.substring(6));


        if (0 < addNumber && addNumber < 10) throw new IllegalStateException();
        if (0 < addNumber) {
            return new MultiplingPart(offset);
        } else {
            return new DividingPart(offset, addNumber);
        }
    }
}
