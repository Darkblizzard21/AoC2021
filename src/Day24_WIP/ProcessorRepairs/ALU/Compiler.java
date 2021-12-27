package Day24_WIP.ProcessorRepairs.ALU;

import java.util.List;

public class Compiler {
    public static List<String> shorten(List<String> programm){
        for (int i = 0; i < programm.size(); i++) {
            var command = programm.get(i).split(" ");
            if ("zro".equals(command[0])) {
                if (Integer.parseInt(command[2]) == 0)
                    programm.set(i, "zro " + command[1]);
            }

        }
        return programm;
    }
}
