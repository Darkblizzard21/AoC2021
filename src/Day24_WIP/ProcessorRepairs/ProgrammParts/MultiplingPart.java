package Day24_WIP.ProcessorRepairs.ProgrammParts;

public class MultiplingPart implements ProgrammPart {
    private final int offset;

    public MultiplingPart(int offset){
        this.offset = offset;
    }

    @Override
    public int evauluate(int inp, int z){
        return 26 * z + inp + offset;
    }

    @Override
    public boolean isDividing() {
        return false;
    }

    @Override
    public int toHit(int z) {
        throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return "{26z+inp+"+ offset + '}';
    }
}
