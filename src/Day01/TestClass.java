package Day01;

import Day01.DepthMeasurement.DepthSampler;

public class TestClass {
    public static void main(String[] args) {
        DepthSampler ds = new DepthSampler();
        System.out.println("Simple Sampler Count: " + ds.SampleDepth());
        System.out.println("Sliding Sampler Count: " + ds.SampleDepth_Sliding());
    }
}
