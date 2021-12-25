package Day24_WIP.ALU.Exeptions;

public class InvalidMemmoryAddressExeption extends RuntimeException {
    public InvalidMemmoryAddressExeption(char address){
        super("Unsupported address: " + address);
    }

}
