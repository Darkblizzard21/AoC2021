package Day24.ProcessorRepairs.ALU.Exeptions;

public class UnsupportedInstructionExeption extends RuntimeException {
    public UnsupportedInstructionExeption(String instruction){
        super("Unsupported instruction: " + instruction);
    }
}
