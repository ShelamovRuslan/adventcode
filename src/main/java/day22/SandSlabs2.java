package day22;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class SandSlabs2 {
    private Brick[][][] bricksModel;

    public static void main(String[] args) {
        String path = "src/main/java/day22/snapshot_bricks";
        SandSlabs2 sandSlabs = new SandSlabs2(path);
        sandSlabs.run();
    }

    public SandSlabs2(String path) {
        ArrayList<String> input = read(path);
        bricksModel = new Brick[20][20][1000];
        ArrayList<Brick> bricks = createBricks(input);
        convertToModel(bricks);
    }

    public void run() {
        handleBrickFalling();
        HashSet<Brick> supportedBricks = checkSupporting();
        System.out.println("Number of supported bricks: " + supportedBricks.size());
    }

    private void handleBrickFalling() {
        for (int i = 0; i < 1000; i++) {
            for (Brick brick : getAllBricks()) {
                if (brick.firstZ == 1) {
                    continue;
                }
                moveBrickDown(brick);
            }
        }
    }

    private void moveBrickDown(Brick brick) {
        if (brick.getZ().size() > 1) {
            int x = brick.getX().get(0);
            int y = brick.getY().get(0);
            while (bricksModel[x][y][brick.firstZ - 1] == null && brick.firstZ - 1 > 1) {
                bricksModel[x][y][brick.firstZ - 1] = brick;
                bricksModel[x][y][brick.secondZ] = null;
                brick.secondZ = brick.secondZ - 1;
                brick.firstZ = brick.firstZ - 1;
            }
        } else if (brick.getY().size() > 1) {
            int z = brick.getZ().get(0);
            int x = brick.getX().get(0);
            while (checkYZ(brick, x, z) && brick.firstZ > 1) {
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

            while (checkXZ(brick, y, z) && brick.firstZ > 1) {
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
                return;
            }
            while (bricksModel[x][y][z - 1] == null && z > 1) {
                bricksModel[x][y][z] = null;
                bricksModel[x][y][z - 1] = brick;
                brick.firstZ = brick.firstZ - 1;
                brick.secondZ = brick.secondZ - 1;
                z = z - 1;
            }
        }
    }

    private boolean checkXZ(Brick brick, int y, int z) {
        for (Integer x : brick.getX()) {
            if (bricksModel[x][y][z - 1] != null) {
                return false;
            }
        }
        return true;
    }

    private boolean checkYZ(Brick brick, int x, int z) {
        for (Integer y : brick.getY()) {
            if (bricksModel[x][y][z - 1] != null) {
                return false;
            }
        }
        return true;
    }

    private HashSet<Brick> checkSupporting() {
        HashSet<Brick> supportedBricks = new HashSet<>();
        for (int z = 1; z < 900; z++) {
            for (int x = 0; x < 17; x++) {
                for (int y = 0; y < 17; y++) {
                    Brick brick = bricksModel[x][y][z];
                    if (brick == null) {
                        continue;
                    }
                    if (isBrickSupported(brick)) {
                        supportedBricks.add(brick);
                    }
                }
            }
        }
        return supportedBricks;
    }

    private boolean isBrickSupported(Brick brick) {
        int x = brick.getX().get(0);
        int y = brick.getY().get(0);
        int z = brick.firstZ;

        if (brick.getZ().size() > 1) {
            return bricksModel[x][y][brick.secondZ + 1] == null;
        } else if (brick.getY().size() > 1) {
            for (Integer iY : brick.getY()) {
                if (bricksModel[x][iY][z + 1] == null) {
                    return false;
                }
            }
            return true;
        } else if (brick.getX().size() > 1) {
            for (Integer iX : brick.getX()) {
                if (bricksModel[iX][y][z + 1] == null) {
                    return false;
                }
            }
            return true;
        } else {
            return bricksModel[x][y][z + 1] != null;
        }
    }

    private ArrayList<Brick> getAllBricks() {
        ArrayList<Brick> allBricks = new ArrayList<>();
        for (Brick[][] brickRow : bricksModel) {
            for (Brick[] brickColumn : brickRow) {
                for (Brick brick : brickColumn) {
                    if (brick != null) {
                        allBricks.add(brick);
                    }
                }
            }
        }
        return allBricks;
    }

    private ArrayList<Brick> createBricks(ArrayList<String> input) {
        ArrayList<Brick> result = new ArrayList<>();
        input.forEach(line -> result.add(new Brick(line)));
        return result;
    }

    private void convertToModel(ArrayList<Brick> bricks) {
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
