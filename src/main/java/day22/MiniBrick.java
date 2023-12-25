package day22;

import java.util.HashSet;

public class MiniBrick extends Brick{
    char name = SandSlabs.getAlphabet();
    private int x;
    private int y;
    private int z;
    private boolean disintegrated;

    protected MiniBrick(int x, int y, int z){

        this.x = x;
        this.z = z;
        this.y = y;
        SandSlabs.bricksModel[this.x][this.y][this.z] = this;
    }

    @Override
    protected char getName() {
        return name;
    }

    protected boolean move() {
        if (checkMove()){
            while (checkMove()){

                SandSlabs.bricksModel[this.x][this.y][this.z - 1] = this;
                SandSlabs.bricksModel[this.x][this.y][this.z] = null;

                this.z = this.z - 1;
            }

            return true;
        }
        return false;
    }

    private boolean checkMove(){

        if (z == 1){
            return false;
        }

        if (SandSlabs.bricksModel[x][y][z - 1] != null){
            return SandSlabs.bricksModel[x][y][z - 1].move();
        }

        return true;
    }

    @Override
    protected boolean disintegrated() {
        return this.disintegrated = checkDisintegrated();
    }

    private boolean checkDisintegrated() {

        if (SandSlabs.bricksModel[x][y][z + 1] == null){
            return true;
        }

        return SandSlabs.bricksModel[x][y][z + 1].checkDisintegratedBottomBrick();
    }

    @Override
    protected boolean checkDisintegratedBottomBrick() {
        return false;
    }
    @Override
    public boolean isDisintegrated() {

        if (z == 1){
            return false;
        }

        if (SandSlabs.bricksModel[x][y][z - 1] == null){
            return true;
        }

        if (SandSlabs.disintegration.contains(SandSlabs.bricksModel[x][y][z - 1])){
            return true;
        }

        return false;
    }

    @Override
    protected void check() {
        SandSlabs.disintegration.add(this);
        if (SandSlabs.bricksModel[x][y][z + 1] != null && SandSlabs.bricksModel[x][y][z + 1].isDisintegrated()){
            SandSlabs.bricksModel[x][y][z + 1].check();
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        MiniBrick guest = (MiniBrick) obj;
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
