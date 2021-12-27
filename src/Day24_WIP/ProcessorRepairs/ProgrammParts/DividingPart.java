package Day24_WIP.ProcessorRepairs.ProgrammParts;

public class DividingPart implements ProgrammPart {
    private final int offset;
    private final int subtract;

    public DividingPart(int offset, int subtract) {
        this.offset = offset;
        this.subtract = subtract;
    }

    @Override
    public int evauluate(int inp, int z) {
        if ((z % 26 + subtract) == inp) {
            return z / 26;
        } else {
            return 26 * (z / 26) + inp + offset;
        }
    }

    @Override
    public boolean isDividing() {
        return true;
    }

    @Override
    public int toHit(int z) {
        return (z % 26 + subtract);
    }

    @Override
    public String toString() {
        return "{z/26||z+inp+" + offset + ", sub: " + subtract + '}';
    }
}
