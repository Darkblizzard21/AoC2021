package Day16.ElvesMessageDecoder.Packages;

import java.util.List;
import java.util.Optional;

public interface Packet {
    short version();
    short typeID();

    long literalValue();
    Optional<List<Packet>> subPackages();

    default int typeIdSum() {
        if (this.subPackages().isPresent()) {
            return this.typeID() + this.subPackages().get().stream().mapToInt(Packet::typeIdSum).sum();
        } else {
            return this.typeID();
        }
    }
    default int versionSum() {
        if (this.subPackages().isPresent()) {
            int versionSum = this.version();
            for (var p: subPackages().get()) {
                versionSum += p.versionSum();
            }
            return versionSum;
        } else {
            return this.version();
        }
    }
}
