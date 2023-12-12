package day1;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Element {
    private int number;
    public static Element createInstance(Class<? extends Element> clazz) throws IllegalAccessException, InstantiationException {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        }
    }

    public Element setOriginal(String original) {
        ArrayList<String> numbers = this.searchNumber(original, getPattern());
        variation(numbers);
        this.number = setNumber(numbers);
        return this;
    }

    protected abstract void variation(ArrayList<String> numbers);

    protected ArrayList<String> searchNumber(String original, String pattern) {

        ArrayList<String> result = new ArrayList<>();
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(original);

        while(m.find()) {
            result.add(original.substring(m.start(), m.end()));
            m.region(m.start() + 1, original.length());
        }
        return result;
    }

    protected Integer setNumber(ArrayList<String> numbers) {

        if (numbers.size() == 0){
            return 0;
        }

        if (numbers.size() == 1){
            return Integer.parseInt(numbers.get(0) + numbers.get(0));
        } else {
            return Integer.parseInt(numbers.get(0) + "" +
                    numbers.get(numbers.size() - 1));
        }
    }

    protected abstract String getPattern();

    protected int getNumber() {
        return number;
    }
}
