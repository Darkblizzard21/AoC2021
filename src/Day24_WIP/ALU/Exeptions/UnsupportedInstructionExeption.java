package Day24_WIP.ALU.Exeptions;

public class UnsupportedInstructionExeption extends RuntimeException {
    public UnsupportedInstructionExeption(String instruction){
        super("Unsupported instruction: " + instruction);
    }
}
