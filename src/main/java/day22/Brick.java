package day22;

import java.util.ArrayList;

public class Brick {

    public static void main(String[] args) {
        new Brick("");
    }

    static char alphabet = 'a';

    char name;
    boolean help = false;
    private final String original;
    private final ArrayList<Integer> x = new ArrayList<>();
    private final ArrayList<Integer> y = new ArrayList<>();
    private final ArrayList<Integer> z = new ArrayList<>();

    int firstX;
    int firstY;
    int firstZ;

    int secondX;
    int secondY;
    int secondZ;

    public Brick(String original){

        name = alphabet;
        alphabet++;

        this.original = original;

        String[] temp = original.split("~");

        String[] first = temp[0].split(",");
        String[] second = temp[1].split(",");



        firstX = Integer.parseInt(first[0]);
        firstY = Integer.parseInt(first[1]);
        firstZ = Integer.parseInt(first[2]);

        secondX = Integer.parseInt(second[0]);
        secondY = Integer.parseInt(second[1]);
        secondZ = Integer.parseInt(second[2]);


        for (int i = firstX; i <= secondX; i++) {
            x.add(i);
        }

        for (int i = firstY; i <= secondY; i++) {
            y.add(i);
        }

        for (int i = firstZ; i <= secondZ; i++) {
            z.add(i);
        }
    }

    public ArrayList<Integer> getX() {
        return x;
    }

    public ArrayList<Integer> getY() {
        return y;
    }

    public ArrayList<Integer> getZ() {
        return z;
    }

    public int getFirstX() {
        return firstX;
    }

    public int getFirstY() {
        return firstY;
    }

    public int getFirstZ() {
        return firstZ;
    }

    public int getSecondX() {
        return secondX;
    }

    public int getSecondY() {
        return secondY;
    }

    public int getSecondZ() {
        return secondZ;
    }
}
