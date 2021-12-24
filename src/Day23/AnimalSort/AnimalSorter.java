package Day23.AnimalSort;

public class AnimalSorter extends AnimalSorterInputProvider {
    public int MinSortPoints(int loadId) {
        var pods = new PodState(loadAsArray(loadId));
        int cost = 0;
        return 0;
    }

    public int solveExampleHardCoded(){
        var pods = new PodState(loadAsArray(1));

        pods.move(6,0,3,0);

        pods.move(4,0,6,0);

        pods.move(4,1,5,0);
        pods.move(3,0,4,1);

        pods.move(2,0,4,0);

        pods.move(8,0,7,0);
        pods.move(8,1,9,0);

        pods.move(7,0,8,1);
        pods.move(5,0,8,0);

        pods.move(9,0,2,0);
        return pods.cost;
    }

    public int solveFirstHardCoded(){
        var pods = new PodState(loadAsArray(0));

        pods.move(8,0,10,0);
        pods.move(8,1,9,0);
        pods.print();

        pods.move(2,0,8,1);
        pods.move(4,0,8,0);
        pods.print();

        pods.move(4,1,1,0);
        pods.print();

        pods.move(6,0,4,1);
        pods.move(6,1,4,0);
        pods.print();

        pods.move(2,1,6,1);
        pods.move(9,0,6,0);
        pods.print();

        pods.move(1,0,2,1);
        pods.move(10,0,2,0);
        pods.print();

        return pods.cost;
    }
}
