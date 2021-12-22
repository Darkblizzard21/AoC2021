package Common;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class F_Rectangle3D {
    Float3 lowerBound;
    Float3 upperBound;

    public F_Rectangle3D(float x1, float y1, float z1, float x2, float y2, float z2) {
        lowerBound = new Float3(Math.min(x1, x2), Math.min(y1, y2), Math.min(z1, z2));
        upperBound = new Float3(Math.max(x1, x2), Math.max(y1, y2), Math.max(z1, z2));
    }

    public F_Rectangle3D(Float3 v1, Float3 v2) {
        lowerBound = new Float3(Math.min(v1.x(), v2.x()), Math.min(v1.y(), v2.y()), Math.min(v1.z(), v2.z()));
        upperBound = new Float3(Math.max(v1.x(), v2.x()), Math.max(v1.y(), v2.y()), Math.max(v1.z(), v2.z()));
    }

    public static F_Rectangle3D wrap(Rectangle3D rectangle3D) {
        var diff = new Float3(0.5f);
        return new F_Rectangle3D((new Float3(rectangle3D.lowerBound)).sub(diff), (new Float3(rectangle3D.upperBound)).add(diff));
    }

    public Float3 getLowerBound() {
        return lowerBound;
    }

    public Float3 getUpperBound() {
        return upperBound;
    }

    public boolean isInRect(Float3 f3) {
        return f3.x() <= upperBound.x() && f3.x() >= lowerBound.x()
                && f3.y() <= upperBound.y() && f3.y() >= lowerBound.y()
                && f3.z() <= upperBound.z() && f3.z() >= lowerBound.z();
    }

    public long wrappingIntegerCount() {
        return ((long) Math.floor(upperBound.x()) - (long) Math.ceil(lowerBound.x()) + 1)
                * ((long) Math.floor(upperBound.y()) - (long) Math.ceil(lowerBound.y()) + 1)
                * ((long) Math.floor(upperBound.z()) - (long) Math.ceil(lowerBound.z()) + 1);
    }

    public Optional<List<F_Rectangle3D>> subtract(F_Rectangle3D sub) {
        if (!overlaps(sub)) return Optional.of(this.warpAsList());
        var intersection = intersection(sub);
        if(equals(intersection))
            return Optional.empty();
        var iLower = intersection.getLowerBound();
        var iUpper = intersection.getUpperBound();

        List<F_Rectangle3D> shards = new LinkedList<>();

        // Lower front row
        var LowerLowAnchor = lowerBound;
        var LowerUpAnchor = iLower.withX(upperBound.x());
        generateRow(shards,
                LowerLowAnchor, LowerUpAnchor,
                iLower.x(), iUpper.x());

        // Lower middle row
        generateRow(shards,
                LowerLowAnchor.withY(iLower.y()), LowerUpAnchor.withY(iUpper.y()),
                iLower.x(), iUpper.x());

        // Lower back row
        generateRow(shards,
                LowerLowAnchor.withY(iUpper.y()), LowerUpAnchor.withY(upperBound.y()),
                iLower.x(), iUpper.x());

        // Middle front row
        var MiddleLowAnchor = lowerBound.withZ(iLower.z());
        var MiddleUpAnchor = iUpper.withX(upperBound.x()).withY(iLower.y());
        generateRow(shards,
                MiddleLowAnchor, MiddleUpAnchor,
                iLower.x(), iUpper.x());

        // Middle center left and right
        // middle center left
        shards.add(new F_Rectangle3D(
                iLower.withX(lowerBound.x()),
                iUpper.withX(iLower.x())
        ));
        // middle center right
        shards.add(new F_Rectangle3D(
                iLower.withX(iUpper.x()),
                iUpper.withX(upperBound.x())
        ));

        // Middle back row
        generateRow(shards,
                MiddleLowAnchor.withY(iUpper.y()), MiddleUpAnchor.withY(upperBound.y()),
                iLower.x(), iUpper.x());


        // Upper front row
        var UpperLowAnchor = lowerBound.withZ(iUpper.z());
        var UpperUpAnchor = upperBound.withY(iLower.y());
        generateRow(shards,
                UpperLowAnchor, UpperUpAnchor,
                iLower.x(), iUpper.x());

        // Upper middle row
        generateRow(shards,
                UpperLowAnchor.withY(iLower.y()), UpperUpAnchor.withY(iUpper.y()),
                iLower.x(), iUpper.x());

        // Upper back row
        generateRow(shards,
                UpperLowAnchor.withY(iUpper.y()), UpperUpAnchor.withY(upperBound.y()),
                iLower.x(), iUpper.x());

        return Optional.of(shards.stream().filter(this::overlaps).collect(Collectors.toList()));
    }

    private void generateRow(List<F_Rectangle3D> appendTo,
                             Float3 lowerAnchor, Float3 upperAnchor,
                             float firstIntersection, float secondIntersection) {
        //Left entry
        appendTo.add(new F_Rectangle3D(
                lowerAnchor,
                upperAnchor.withX(firstIntersection)
        ));
        //Middle entry
        appendTo.add(new F_Rectangle3D(
                lowerAnchor.withX(firstIntersection),
                upperAnchor.withX(secondIntersection)
        ));
        //Right entry
        appendTo.add(new F_Rectangle3D(
                lowerAnchor.withX(secondIntersection),
                upperAnchor
        ));
    }

    public F_Rectangle3D intersection(F_Rectangle3D rect) {
        float xmin = Math.max(this.lowerBound.x(), rect.lowerBound.x());
        float xmax = Math.min(this.upperBound.x(), rect.upperBound.x());
        float ymin = Math.max(this.lowerBound.y(), rect.lowerBound.y());
        float ymax = Math.min(this.upperBound.y(), rect.upperBound.y());
        float zmin = Math.max(this.lowerBound.z(), rect.lowerBound.z());
        float zmax = Math.min(this.upperBound.z(), rect.upperBound.z());
        return new F_Rectangle3D(xmin, ymin, zmin, xmax, ymax, zmax);
    }

    public boolean overlaps(F_Rectangle3D rect) {
        return !(rect.upperBound.x() <= lowerBound.x()
                || rect.upperBound.y() <= lowerBound.y()
                || rect.upperBound.z() <= lowerBound.z()
                || rect.lowerBound.x() >= upperBound.x()
                || rect.lowerBound.y() >= upperBound.y()
                || rect.lowerBound.z() >= upperBound.z());
    }

    @Override
    public String toString() {
        return "F_[]3D{ wrapping: " + wrappingIntegerCount()+
                ", v1:"+ lowerBound +
                ", v2:" + upperBound +
                '}';
    }

    public List<F_Rectangle3D> warpAsList(){
        var out = new LinkedList<F_Rectangle3D>();
        out.add(this);
        return out;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        F_Rectangle3D that = (F_Rectangle3D) o;
        return Objects.equals(lowerBound, that.lowerBound) && Objects.equals(upperBound, that.upperBound);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowerBound, upperBound);
    }
}
