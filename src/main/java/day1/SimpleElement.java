package day1;

import java.util.ArrayList;

public class SimpleElement extends Element {

    protected SimpleElement(){
        super();
    }

    @Override
    protected void variation(ArrayList<String> numbers) {

    }

    @Override
    protected String getPattern(){
        return "1|2|3|4|5|6|7|8|9";
    }
}
