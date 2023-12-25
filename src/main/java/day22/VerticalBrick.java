package day22;

import java.util.ArrayList;

public class VerticalBrick extends Brick{
    char name = SandSlabs.getAlphabet();

    @Override
    protected char getName() {
        return name;
    }
    private final ArrayList<Integer> z;
    private int firstZ;
    private int lastZ;
    private int x;
    private int y;
    private boolean disintegrated;
    protected VerticalBrick(int x, int y, int firstZ, int lastZ){
        this.z = new ArrayList<>();
        for (int i = firstZ; i <= lastZ; i++) {
            this.z.add(i);
        }
        this.firstZ = firstZ;
        this.lastZ = lastZ;
        this.x = x;
        this.y = y;
        for (Integer z : this.z) {
            SandSlabs.bricksModel[this.x][this.y][z] = this;
        }
    }

    protected boolean move() {
        if (checkMove()){

            while (checkMove()){

                SandSlabs.bricksModel[this.x][this.y][this.firstZ - 1] = this;
                SandSlabs.bricksModel[this.x][this.y][this.lastZ] = null;

                z.replaceAll(integer -> integer - 1);
                this.firstZ--;
                this.lastZ--;
            }

            return true;
        }
        return false;
    }

    private boolean checkMove(){

        if (this.firstZ == 1){
            return false;
        }

        if (SandSlabs.bricksModel[x][y][this.firstZ - 1] != null){
            return SandSlabs.bricksModel[x][y][this.firstZ - 1].move();
        }

        return true;
    }

    @Override
    protected boolean disintegrated() {
        return this.disintegrated = checkDisintegrated();
    }

    private boolean checkDisintegrated() {

        if (SandSlabs.bricksModel[x][y][this.lastZ + 1] == null){
            return true;
        }

        return SandSlabs.bricksModel[x][y][this.lastZ + 1].checkDisintegratedBottomBrick();
    }

    @Override
    protected boolean checkDisintegratedBottomBrick() {
        return false;
    }
    @Override
    protected void check() {
        SandSlabs.disintegration.add(this);
        if (SandSlabs.bricksModel[x][y][lastZ + 1] != null && SandSlabs.bricksModel[x][y][lastZ + 1].isDisintegrated()){
            SandSlabs.bricksModel[x][y][lastZ + 1].check();
        }
    }
    @Override
    public boolean isDisintegrated() {
        if (firstZ == 1){
            return false;
        }

        if (SandSlabs.bricksModel[x][y][firstZ - 1] == null){
            return true;
        }

        if (SandSlabs.disintegration.contains(SandSlabs.bricksModel[x][y][firstZ - 1])){
            return true;
        }

        return false;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        VerticalBrick guest = (VerticalBrick) obj;
        return this.name == guest.name;
    }
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * name;
        return result;
    }
}
