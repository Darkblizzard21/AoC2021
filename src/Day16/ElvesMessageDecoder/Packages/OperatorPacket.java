package Day16.ElvesMessageDecoder.Packages;

import java.util.List;
import java.util.Optional;
import java.util.function.LongBinaryOperator;

public class OperatorPacket implements Packet {
    private final short version;
    private final short typeID;
    private final List<Packet> subPackages;

    public OperatorPacket(short version, short typeID, List<Packet>  subPackages) {
        this.version = version;
        this.typeID = typeID;
        this.subPackages = subPackages;
    }

    @Override
    public short version() {
        return version;
    }

    @Override
    public short typeID() {
        return typeID;
    }

    @Override
    public long literalValue() {
        LongBinaryOperator operator;
        switch (typeID){
            case 0:
                operator = Long::sum;
                break;
            case 1:
                operator = (long a, long b) -> a * b;
                break;
            case 2:
                operator = Math::min;
                break;
            case 3:
                operator = Math::max;
                break;
            case 5:
                operator = (long a, long b) -> a > b ? 1 : 0;
                break;
            case 6:
                operator = (long a, long b) -> a < b ? 1 : 0;
                break;
            case 7:
                operator = (long a, long b) -> a == b ? 1 : 0;
                break;
            default:
                throw new IllegalStateException("Id invalid");
        }

        return subPackages.stream().
                map(Packet::literalValue)
                .mapToLong(l -> l)
                .reduce(operator)
                .getAsLong();
    }

    @Override
    public Optional<List<Packet>> subPackages() {
        return Optional.of(subPackages);
    }
}
