package Day01.DepthMeasurement;

import java.io.*;
import java.net.URL;

public class DepthSampler {
    public int SampleDepth(){
        URL url = getClass().getResource("input.txt");
        assert url != null;
        File file = new File(url.getPath());

        int depthCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line = br.readLine();
            int lastDepth = Integer.parseInt(line);
            while ((line = br.readLine()) != null) {
                int nextDepth = Integer.parseInt(line);
                if(nextDepth > lastDepth) depthCount++;
                lastDepth = nextDepth;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depthCount;
    }

    public int SampleDepth_Sliding(){
        URL url = getClass().getResource("input.txt");
        assert url != null;
        File file = new File(url.getPath());

        final int slidingSize = 3;
        SumRingBuffer sumRingBuffer = new SumRingBuffer(slidingSize);

        int depthCount = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            for (int i = 0; i < slidingSize; i++) {
                line = br.readLine();
                sumRingBuffer.push(Integer.parseInt(line));
            }

            int lastDepth = sumRingBuffer.getSum();

            while ((line = br.readLine()) != null) {
                int nextDepth = sumRingBuffer.push(Integer.parseInt(line));
                if(nextDepth > lastDepth) depthCount++;
                lastDepth = nextDepth;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return depthCount;
    }
}