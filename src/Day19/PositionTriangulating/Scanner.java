package Day19.PositionTriangulating;

import Common.Int3;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Scanner {
    private Int3 position = new Int3(0);
    private Int3 rotation = new Int3(0);
    private Scanner alignedTo = null;
    private List<Int3> relativeBeacons;

    public Scanner(List<Int3> relativeBeacons) {
        this.relativeBeacons = relativeBeacons;
    }

    public void alignTo(Scanner other) {
        alignTo:
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                for (int k = 0; k < 4; k++) {

                    // Check if alligned
                    var x = relativeBeacons.stream()
                            .map(i3 -> other.relativeBeacons.stream().map(o3 -> o3.sub(i3)).collect(Collectors.toList()))
                            .flatMap(Collection::stream)
                            .collect(Collectors.groupingBy(t -> t, Collectors.counting()));
                    for (var entryLong : x.entrySet()) {
                        if (entryLong.getValue() >= 12) {
                            setPosition(entryLong.getKey());
                            alignedTo = other;
                            break alignTo;
                        }
                    }
                    rotateZ90();
                }
                rotateY90();
            }
            rotateX90();
        }
    }

    public int beaconCount() {
        return relativeBeacons.size();
    }

    public Int3 getBeacon(int i) {
        return position.add(relativeBeacons.get(i));
    }

    public List<Int3> getLocalBeacons() {
        return relativeBeacons.stream().map(Int3::new).collect(Collectors.toList());
    }

    public List<Int3> getBeacons() {
        return relativeBeacons.stream().map(int3 -> int3.add(getPosition())).collect(Collectors.toList());
    }

    public List<Int3> getAlignedBeacons() {
        return relativeBeacons.stream().map(int3 -> int3.add(getAlignedPosition())).collect(Collectors.toList());
    }

    public Int3 getPosition() {
        return new Int3(position);
    }

    public Int3 getAlignedPosition() {
        return isAligned() ? alignedTo.getAlignedPosition().add(position) : new Int3(position);
    }

    public void setPosition(Int3 position) {
        this.position = position;
    }

    public Int3 getRotation() {
        return new Int3(rotation);
    }

    public boolean isAligned(){
        return alignedTo != null;
    }

    public Scanner getAlignedTo(){
        return alignedTo;
    }

    public void rotateX90() {
        relativeBeacons = relativeBeacons.stream().map(Int3::rotX90).collect(Collectors.toList());
        rotation.setX((rotation.x() + 90) % 360);
    }

    public void rotateY90() {
        relativeBeacons = relativeBeacons.stream().map(Int3::rotY90).collect(Collectors.toList());
        rotation.setY((rotation.y() + 90) % 360);
    }

    public void rotateZ90() {
        relativeBeacons = relativeBeacons.stream().map(Int3::rotZ90).collect(Collectors.toList());
        rotation.setZ((rotation.z() + 90) % 360);
    }

    @Override
    public String toString() {
        return "Scanner{" +
                "position=" + position + ", " +
                "alignedPosition=" + getAlignedPosition() + ", " +
                "rotation=" + rotation + ", " +
                "elements=" + relativeBeacons.size() +
                '}';
    }
}
