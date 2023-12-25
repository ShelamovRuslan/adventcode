package day22;

import java.util.ArrayList;
import java.util.HashSet;

public class YAxisxBrick extends Brick {
    char name = SandSlabs.getAlphabet();
    @Override
    protected char getName() {
        return name;
    }
    private final ArrayList<Integer> y;
    private int firstY;
    private int lastY;
    private int z;
    private int x;
    private boolean disintegrated;

    protected YAxisxBrick(int x, int firstY, int lastY, int z) {
        this.y = new ArrayList<>();
        for (int i = firstY; i <= lastY; i++) {
            y.add(i);
        }
        this.firstY = firstY;
        this.lastY = lastY;
        this.z = z;
        this.x = x;
        for (Integer y : this.y) {
            SandSlabs.bricksModel[this.x][y][this.z] = this;
        }
    }

    protected boolean move() {
        if (checkMove()) {

            for (Integer y : this.y) {
                SandSlabs.bricksModel[this.x][y][this.z] = null;
                SandSlabs.bricksModel[this.x][y][this.z - 1] = this;
            }
            this.z = this.z - 1;

            return true;
        }
        return false;
    }

    private boolean checkMove() {

        if (this.z == 1) {
            return false;
        }

        for (Integer y : this.y) {
            if (SandSlabs.bricksModel[this.x][y][this.z - 1] != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean disintegrated() {
        for (Integer y : this.y) {
            if (SandSlabs.bricksModel[this.x][y][this.z + 1] != null) {
                if (!SandSlabs.bricksModel[this.x][y][this.z + 1].checkDisintegratedBottomBrick()) {
                    return this.disintegrated = false;
                }
            }
        }
        return this.disintegrated = true;
    }

    @Override
    protected boolean checkDisintegratedBottomBrick() {
        HashSet<Brick> result = new HashSet<>();
        for (Integer y : this.y) {
            if (SandSlabs.bricksModel[this.x][y][this.z - 1] != null){
                result.add(SandSlabs.bricksModel[this.x][y][this.z - 1]);
            }
        }
        return result.size() > 1;
    }

    @Override
    protected void check() {
        SandSlabs.disintegration.add(this);
        ArrayList<Brick> des = new ArrayList<>();
        for (Integer y : this.y) {
            if (SandSlabs.bricksModel[this.x][y][this.z + 1] != null &&
                    SandSlabs.bricksModel[this.x][y][this.z + 1].isDisintegrated()) {
                SandSlabs.disintegration.add(SandSlabs.bricksModel[this.x][y][this.z + 1]);
                des.add(SandSlabs.bricksModel[this.x][y][this.z + 1]);
            }
        }
        for (Brick de : des) {
            de.check();
        }
    }

    @Override
    public boolean isDisintegrated() {
        HashSet<Brick> result = new HashSet<>();

        for (Integer y : this.y) {

            if (SandSlabs.bricksModel[this.x][y][this.z - 1] == null){
                continue;
            }

            if (SandSlabs.disintegration.contains(SandSlabs.bricksModel[this.x][y][this.z - 1])){
                continue;
            }

            result.add(SandSlabs.bricksModel[this.x][y][this.z - 1]);
        }
        return result.isEmpty();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        YAxisxBrick guest = (YAxisxBrick) obj;
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
