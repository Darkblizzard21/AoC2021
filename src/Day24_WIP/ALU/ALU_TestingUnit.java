package Day24_WIP.ALU;

public class ALU_TestingUnit extends ALU_TestingUnitInputProvider {
    public String findLargestNumber(int loadId) {
        ALU alu = new ALU();
        var programm = load(1);

        long count = 0;
        alu.run(Compiler.shorten(programm),"1");
        alu.run(Compiler.shorten(programm),"2");
        alu.run(Compiler.shorten(programm),"3");
        alu.run(Compiler.shorten(programm),"4");
        alu.run(Compiler.shorten(programm),"5");
        alu.run(Compiler.shorten(programm),"6");
        alu.run(Compiler.shorten(programm),"8");
        var valid =  alu.getZ();


        return "13579246899999";
    }
}
