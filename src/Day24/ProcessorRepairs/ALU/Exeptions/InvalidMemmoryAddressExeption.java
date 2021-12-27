package Day24.ProcessorRepairs.ALU.Exeptions;

public class InvalidMemmoryAddressExeption extends RuntimeException {
    public InvalidMemmoryAddressExeption(char address){
        super("Unsupported address: " + address);
    }

}
