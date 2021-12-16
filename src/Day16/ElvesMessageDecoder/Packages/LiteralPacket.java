package Day16.ElvesMessageDecoder.Packages;

import java.util.List;
import java.util.Optional;

public class LiteralPacket implements Packet {
    private final short version;
    private final short typeID;
    private final Long literalValue;

    public LiteralPacket(short version, short typeID, Long literalValue) {
        this.version = version;
        this.typeID = typeID;
        this.literalValue = literalValue;
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
        return literalValue;
    }

    @Override
    public Optional<List<Packet>> subPackages() {
        return Optional.empty();
    }
}
