package day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SandSlabs {

    public static void main(String[] args) {

        String path = "src/main/java/day22/snapshot_bricks";
        SandSlabs sandSlabs = new SandSlabs(path);
    }

    public SandSlabs(String path) {
        ArrayList<String> input = read(path);
        Brick[][][] bricksModel = new Brick
                [20]
                [20]
                [1000];
        ArrayList<Brick> bricks = createBricks(input);

        bricksModel = convertToModel(bricks, bricksModel);

        for (int i = 0; i < 1000; i++) {


            for (Brick brick : bricks) {

                if (brick.firstZ == 1) {
                    continue;
                }

                if (brick.getZ().size() > 1) {
                    int x = brick.getX().get(0);
                    int y = brick.getY().get(0);
                    while (bricksModel[x][y][brick.firstZ - 1] == null && brick.firstZ - 1 > 1){
                        bricksModel[x][y][brick.firstZ - 1] = brick;
                        bricksModel[x][y][brick.secondZ] = null;
                        brick.secondZ = brick.secondZ - 1;
                        brick.firstZ = brick.firstZ - 1;
                    }
                } else if (brick.getY().size() > 1) {
                    int z = brick.getZ().get(0);
                    int x = brick.getX().get(0);
                    while (checkYZ(brick, bricksModel, x, z) && brick.firstZ > 1){
                        for (Integer y : brick.getY()) {
                            bricksModel[x][y][z] = null;
                            bricksModel[x][y][z - 1] = brick;
                        }
                        brick.firstZ = brick.firstZ - 1;
                        brick.secondZ = brick.secondZ - 1;
                        z = z - 1;
                    }
                } else if (brick.getX().size() > 1) {
                    int z = brick.getZ().get(0);
                    int y = brick.getY().get(0);

                    while (checkXZ(brick, bricksModel, y, z) && brick.firstZ > 1){
                        for (Integer x : brick.getX()) {
                            bricksModel[x][y][z] = null;
                            bricksModel[x][y][z - 1] = brick;
                        }
                        brick.firstZ = brick.firstZ - 1;
                        brick.secondZ = brick.secondZ - 1;
                        z = z - 1;
                    }
                } else {
                    int z = brick.getZ().get(0);
                    int y = brick.getY().get(0);
                    int x = brick.getX().get(0);

                    if (z == 1) {
                        continue;
                    }
                    while (bricksModel[x][y][z - 1] == null && z > 1){
                        bricksModel[x][y][z] = null;
                        bricksModel[x][y][z - 1] = brick;
                        brick.firstZ = brick.firstZ - 1;
                        brick.secondZ = brick.secondZ - 1;
                        z = z - 1;
                    }
                }
            }
        }

        HashSet<Brick> result = new HashSet<>();


        for (int z = 1; z < 900; z++) {
            for (int x = 0; x < 17; x++) {
                for (int y = 0; y < 17; y++) {

                    Brick brick = bricksModel[x][y][z];


                    if (brick == null) {
                        continue;
                    }

                    ArrayList<Brick> secondLvl = new ArrayList<>();
                    if (brick.getZ().size() > 1) {
                        if (bricksModel[x][y][brick.secondZ + 1] == null) {
                            result.add(brick);
                        } else {
                           secondLvl.add(bricksModel[x][y][brick.secondZ + 1]);
                           if(checkLowerLvl(secondLvl, brick, bricksModel)){
                               result.add(brick);
                               bricksModel[x][y][brick.secondZ + 1].help = true;
                           }
                        }

                    } else if (brick.getY().size() > 1) {

                        for (Integer iY : brick.getY()) {
                            Brick brick1 = bricksModel[x][iY][z+1];
                            if (brick1 == null) {
                                continue;
                            }
                            secondLvl.add(brick1);
                        }

                        if (checkLowerLvl(secondLvl, brick, bricksModel)) {
                            result.add(brick);
                        }
                    } else if (brick.getX().size() > 1) {
                        for (Integer iX : brick.getX()) {
                            Brick brick1 = bricksModel[iX][y][z+1];
                            if (brick1 == null) {
                                continue;
                            }
                            secondLvl.add(bricksModel[iX][y][z+1]);
                        }

                        if (checkLowerLvl(secondLvl, brick, bricksModel)) {
                            result.add(brick);
                        }
                    } else {

                        Brick brick1 = bricksModel[x][y][z+1];
                        if (brick1 != null) {
                            secondLvl.add(bricksModel[x][y][z+1]);
                        }

                        if (checkLowerLvl(secondLvl, brick, bricksModel)) {
                            result.add(brick);
                        }
                    }
                }
            }
        }

        System.out.println(result.size());

    }

    private boolean checkXZ(Brick brick, Brick[][][] bricksModel, int y, int z){

        for (Integer x : brick.getX()) {
            if (bricksModel[x][y][z - 1] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkYZ(Brick brick, Brick[][][] bricksModel, int x, int z){

        for (Integer y : brick.getY()) {
            if (bricksModel[x][y][z - 1] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkLowerLvl(ArrayList<Brick> secondLvl, Brick brick, Brick[][][] bricksModel) {


        ArrayList<Boolean> result = new ArrayList<>();

        if (secondLvl.isEmpty()) {
            return true;
        }

        for (Brick secondBrick : secondLvl) {

            if (secondBrick.getZ().size() > 1) {
                return false;
            }

            if (secondBrick.getX().size() > 1) {
                for (Integer x : secondBrick.getX()) {
                    int testX = x;
                    int testY = secondBrick.getY().get(0);
                    int testZ = secondBrick.getFirstZ() - 1;
                    Brick temp = bricksModel[testX][testY][testZ];
                    if (temp != brick && temp != null){
                        return true;
                    }
                }
            } else
            if (secondBrick.getY().size() > 1) {
                for (Integer y : secondBrick.getX()) {

                    int xTe = secondBrick.getX().get(0);
                    int yTe = y;
                    int zTe = secondBrick.getFirstZ()-1;

                    Brick temp = bricksModel[xTe][yTe][zTe];
                    if (temp != brick && temp != null){
                        return true;
                    }
                }
            } else {
                return false;
            }
        }

        for (Boolean aBoolean : result) {
            if (aBoolean) {
                return true;
            }
        }
        return false;
    }


    private ArrayList<Brick> createBricks(ArrayList<String> input) {
        ArrayList<Brick> result = new ArrayList<>();
        input.forEach(line -> result.add(new Brick(line)));
        return result;
    }

    private Brick[][][] convertToModel(ArrayList<Brick> bricks, Brick[][][] bricksModel) {
        for (Brick brick : bricks) {
            if (brick.getZ().size() > 1) {
                for (Integer integer : brick.getZ()) {
                    bricksModel[brick.getX().get(0)][brick.getY().get(0)][integer] = brick;
                }
            } else if (brick.getX().size() > 1) {
                for (Integer integer : brick.getX()) {
                    bricksModel[integer][brick.getY().get(0)][brick.getZ().get(0)] = brick;
                }
            } else if (brick.getY().size() > 1) {
                for (Integer integer : brick.getY()) {
                    bricksModel[brick.getX().get(0)][integer][brick.getZ().get(0)] = brick;
                }
            } else {
                bricksModel[brick.getX().get(0)][brick.getY().get(0)][brick.getZ().get(0)] = brick;
            }
        }
        return bricksModel;
    }

    private ArrayList<String> read(String path) {
        ArrayList<String> result = new ArrayList<>();
        try {

            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                result.add(line);
            }

            bufferedReader.close();
            fileReader.close();
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
