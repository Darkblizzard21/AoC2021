package Day02;

import Day02.Movement.InputSampler;
import Day02.Movement.MovementController;

public class TestClass {
    public static void main(String[] args) {
        InputSampler inputSampler = new InputSampler();
        var list = inputSampler.SampleInput();
        System.out.println("Horizontal * Depth: " + MovementController.ApplyMovement(list));
        System.out.println("Horizontal * Depth (Aimed): " + MovementController.ApplyMovement_Aimed(list));
    }
}
