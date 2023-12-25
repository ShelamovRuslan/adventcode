package day22;

import java.util.ArrayList;
import java.util.HashSet;

public class XAxisBrick extends Brick {
    char name = SandSlabs.getAlphabet();
    @Override
    protected char getName() {
        return name;
    }
    private final ArrayList<Integer> x;
    private int firstX;
    private int lastX;
    private int z;
    private int y;
    private boolean disintegrated;

    protected XAxisBrick(int firstX, int lastX, int y, int z) {

        this.x = new ArrayList<>();
        for (int i = firstX; i <= lastX; i++) {
            x.add(i);
        }
        this.firstX = firstX;
        this.lastX = lastX;
        this.z = z;
        this.y = y;
        for (Integer x : this.x) {
            SandSlabs.bricksModel[x][this.y][this.z] = this;
        }
    }

    protected boolean move() {

        if (checkMove()){
            for (Integer x : this.x) {
                SandSlabs.bricksModel[x][this.y][this.z] = null;
                SandSlabs.bricksModel[x][this.y][this.z - 1] = this;
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

        for (Integer x : this.x) {
            if (SandSlabs.bricksModel[x][this.y][this.z - 1] != null) {
                return false;
            }
        }

        return true;
    }

    @Override
    protected boolean disintegrated() {
        for (Integer x : this.x) {
            if (SandSlabs.bricksModel[x][this.y][this.z + 1] != null) {
                if (!SandSlabs.bricksModel[x][this.y][this.z + 1].checkDisintegratedBottomBrick()) {
                    return this.disintegrated = false;
                }
            }
        }
        return this.disintegrated = true;
    }

    @Override
    protected boolean checkDisintegratedBottomBrick() {
        HashSet<Brick> result = new HashSet<>();
        for (Integer x : this.x) {
            if (SandSlabs.bricksModel[x][this.y][this.z - 1] != null) {
                result.add(SandSlabs.bricksModel[x][this.y][this.z - 1]);
            }
        }
        return result.size() > 1;
    }

    @Override
    protected void check() {
        SandSlabs.disintegration.add(this);
        ArrayList<Brick> des = new ArrayList<>();
        for (Integer x : this.x) {
            if (SandSlabs.bricksModel[x][this.y][this.z + 1] != null &&
                SandSlabs.bricksModel[x][this.y][this.z + 1].isDisintegrated()) {
                SandSlabs.disintegration.add(SandSlabs.bricksModel[x][this.y][this.z + 1]);
                des.add(SandSlabs.bricksModel[x][this.y][this.z + 1]);
            }
        }
        for (Brick de : des) {
            de.check();
        }
    }

    @Override
    public boolean isDisintegrated() {
        HashSet<Brick> result = new HashSet<>();

        for (Integer x : this.x) {

            if (SandSlabs.bricksModel[x][this.y][this.z - 1] == null){
                continue;
            }

            if (SandSlabs.disintegration.contains(SandSlabs.bricksModel[x][this.y][this.z - 1])){
                continue;
            }

            result.add(SandSlabs.bricksModel[x][this.y][this.z - 1]);
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

        XAxisBrick guest = (XAxisBrick) obj;
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
