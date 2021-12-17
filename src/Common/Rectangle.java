package Common;

public class Rectangle {
    private final int upperX;
    private final int lowerX;
    private final int upperY;
    private final int lowerY;

    public Rectangle(int x1, int y1, int x2, int y2){
        upperX = Math.max(x1,x2);
        lowerX = Math.min(x1,x2);
        upperY = Math.max(y1,y2);
        lowerY = Math.min(y1,y2);
    }

    public boolean isInRect(Tuple<Integer,Integer> point){
        return isInRect(point.x,point.y);
    }

    public boolean isInRect(int x, int y){
        return x <= upperX && x >= lowerX
                && y <= upperY && y >= lowerY;
    }

    public int getUpperX() {
        return upperX;
    }

    public int getLowerX() {
        return lowerX;
    }

    public int getUpperY() {
        return upperY;
    }

    public int getLowerY() {
        return lowerY;
    }
}
