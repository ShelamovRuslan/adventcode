package day22;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class SandSlabs {

    public static void main(String[] args) {

        String path = "src/main/java/day1/snapshot_bricks";
        SandSlabs sandSlabs = new SandSlabs(path);
    }

    public SandSlabs(String path){
        ArrayList<String> input = read(path);
        Brick[][][] bricks = convertToModel(input);
    }

    private Brick[][][] convertToModel(ArrayList<String> input) {

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
