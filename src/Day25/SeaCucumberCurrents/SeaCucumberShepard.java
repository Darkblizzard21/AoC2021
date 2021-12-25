package Day25.SeaCucumberCurrents;

public class SeaCucumberShepard extends SeaCucumberShepardInputProvider{
    public int stepsToHold(int loadId){
        var layer = load(loadId);

        int i = 1;
        System.out.println(layer);
        while (layer.nextStep()) {
            System.out.println(i++ + "\n" +layer);
        }

        return i;
    }
}
