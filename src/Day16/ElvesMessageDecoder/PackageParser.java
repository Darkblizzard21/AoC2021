package Day16.ElvesMessageDecoder;

import Day16.ElvesMessageDecoder.Packages.LiteralPacket;
import Day16.ElvesMessageDecoder.Packages.OperatorPacket;
import Day16.ElvesMessageDecoder.Packages.Packet;

import java.util.LinkedList;
import java.util.List;

public class PackageParser extends PackageParserInputProvider {
    public int sumOfIds(int loadId) {
        var bytes = load(loadId);

        var packet = interpret(bytes);

        return packet.typeIdSum();
    }

    public int sumOfVersions(int loadId) {
        var bytes = load(loadId);

        var packet = interpret(bytes);

        return packet.versionSum();
    }

    public Packet loadPacket(int loadId){
        return interpret(load(loadId));
    }

    private Packet interpret(BitQueue bitQ) {
        short version = (short) bitQ.getNext(3);
        short id = (short) bitQ.getNext(3);
        if (id == 4) {
            List<Integer> ints = new LinkedList<>();

            int next;
            do {
                next = bitQ.getNext(1);
                ints.add(bitQ.getNext(4));
            } while (next == 1);

            long number = 0;
            Integer[] intArr = ints.toArray(Integer[]::new);
            for (int i = 0; i < intArr.length; i++) {
                number += ((long) intArr[i]) << ((intArr.length - 1 - i) * 4);
            }

            return new LiteralPacket(version, id, number);
        } else {
            boolean lengthTypeId = bitQ.getNext(1) == 1;
            if (lengthTypeId) {//the next 11 bits are a number that represents the number of sub-packets immediately contained by this packet.
                int numberOfSubPackets = bitQ.getNext(11);
                List<Packet> packets = new LinkedList<>();
                for (int i = 0; i < numberOfSubPackets; i++) {
                    packets.add(interpret(bitQ));
                }
                return new OperatorPacket(version, id, packets);
            } else {//the next 15 bits are a number that represents the total length in bits of the sub-packets contained by this packet.
                int subPacketsLength = bitQ.getNext(15);
                long startBitCount = bitQ.getConsumedBitsCount();
                List<Packet> packets = new LinkedList<>();
                do {
                   packets.add(interpret(bitQ));
                }while (bitQ.getConsumedBitsCount() - startBitCount < subPacketsLength);
                return new OperatorPacket(version, id, packets);
            }
        }
    }
}
