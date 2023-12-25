package day22;

import trash.Day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicInteger;

public class SandSlabs {

    public static void main(String[] args) {

        SandSlabs sandSlabs = new SandSlabs();
    }
    private static char alphabet = '1';

    public static char getAlphabet() {
        char temp = alphabet;
        alphabet++;
        return temp;
    }

    protected static Brick[][][] bricksModel = new Brick
            [10]
            [10]
            [1000];

    public static ArrayList<String> input = read("src/main/java/day22/snapshot_bricks");
    static HashSet<Brick> disintegration = new HashSet<>();
    public SandSlabs() {

        Brick[][][] bricksModel = SandSlabs.bricksModel;

        ArrayList<Brick> bricks = createBrick(input);
        for (int i = 0; i < 1000; i++) {
            bricks.forEach(Brick::move);
        }
        AtomicInteger result = new AtomicInteger();
        bricks.forEach(brick -> {
            if (brick.disintegrated()){
                result.getAndIncrement();
            }
        });
        System.out.println(result.get());


        int resultSecond = 0;

        for (int i = 0; i < bricks.size(); i++) {
            Brick brick = bricks.get(i);
            System.out.println("id " + i);
            disintegration = new HashSet<>();
            brick.check();
            disintegration.remove(brick);
            resultSecond += disintegration.size();
        }

        System.out.println(resultSecond);
    }



    private ArrayList<Brick> createBrick(ArrayList<String> input){
        ArrayList<Brick> result = new ArrayList<>();
        for (String line : input) {
            String[] split = line.split("~");
            String[] first = split[0].split(",");
            int firstX = Integer.parseInt(first[0]);
            int firstY = Integer.parseInt(first[1]);
            int firstZ = Integer.parseInt(first[2]);
            String[] second = split[1].split(",");
            int secondX = Integer.parseInt(second[0]);
            int secondY = Integer.parseInt(second[1]);
            int secondZ = Integer.parseInt(second[2]);

            if (firstX != secondX){
                result.add(new XAxisBrick(firstX, secondX, firstY, firstZ));
            } else if (firstY != secondY){
                result.add(new YAxisxBrick(firstX, firstY, secondY, firstZ));
            } else if (firstZ != secondZ){
                result.add(new VerticalBrick(firstX, firstY, firstZ, secondZ));
            } else {
                result.add(new MiniBrick(firstX, firstY, firstZ));
            }
        }
        return result;
    }

    private static ArrayList<String> read(String path) {
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
