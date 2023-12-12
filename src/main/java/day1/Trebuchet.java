package day1;

import java.io.*;
import java.util.ArrayList;

public class Trebuchet {

    public static void main(String[] args) {
        String path = "src/main/java/day1/calibration_document.enc";
        System.out.println(new Trebuchet(path, "difficult").getResult());
    }

    private final ArrayList<String> original = new ArrayList<>();
    private final ArrayList<Element> elements = new ArrayList<>();
    private int result;

    /**
     * @param path to the file to be decrypted
     * @param difficulty encryption complexity, can be easy or difficult
     */
    public Trebuchet(String path, String difficulty){

        read(path);
        original.forEach(line -> {
            try {
                if (difficulty.equals("easy")){
                    elements.add(Element.createInstance(SimpleElement.class).setOriginal(line));
                } else if (difficulty.equals("difficult")){
                    elements.add(Element.createInstance(ImprovedElement.class).setOriginal(line));
                }
            } catch (IllegalAccessException | InstantiationException e) {
                throw new RuntimeException(e);
            }
        });
        elements.forEach(element -> result += element.getNumber());
    }

    private void read(String path) {

        try {

            FileReader fileReader = new FileReader(path);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                this.original.add(line);
            }

            bufferedReader.close();
            fileReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getResult() {
        return result;
    }
}
