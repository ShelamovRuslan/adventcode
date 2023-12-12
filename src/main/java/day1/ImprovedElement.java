package day1;

import java.util.ArrayList;

public class ImprovedElement extends Element {

    protected ImprovedElement(){
        super();
    }

    @Override
    protected void variation(ArrayList<String> numbers) {

        for (int i = 0; i < numbers.size(); i++) {
            switch (numbers.get(i)){
                case "one": numbers.set(i, "1"); break;
                case "two": numbers.set(i, "2"); break;
                case "three": numbers.set(i, "3"); break;
                case "four": numbers.set(i, "4"); break;
                case "five": numbers.set(i, "5"); break;
                case "six": numbers.set(i, "6"); break;
                case "seven": numbers.set(i, "7"); break;
                case "eight": numbers.set(i, "8"); break;
                case "nine": numbers.set(i, "9"); break;
            }
        }
    }

    @Override
    protected String getPattern(){
        return "one|two|three|four|five|six|seven|eight|nine|1|2|3|4|5|6|7|8|9";
    }
}
