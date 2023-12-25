package day22;


import java.util.HashSet;

public abstract class Brick {
    protected abstract char getName();
    protected abstract boolean move();
    protected abstract boolean disintegrated();
    protected abstract boolean checkDisintegratedBottomBrick();
    protected abstract boolean isDisintegrated();
    protected abstract void check();
}
