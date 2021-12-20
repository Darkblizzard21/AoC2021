package Day20.ImageGeneration;

public class ImageEnhancer extends ImageEnhancerInputProvider {
    public InfiniteImage getImage(int loadId, int steps){
        InfiniteImage ii = load(loadId);

        for (int i = 0; i < steps; i++) {
            //System.out.println("AfterStep: " + i +":");
            //System.out.println(ii);
            ii = ii.enhance();
        }

        return ii;
    }
}
