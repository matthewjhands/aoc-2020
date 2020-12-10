package main.com.aoc.eight;

import java.util.Arrays;
import java.util.HashSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Instruction {
    public final String opComponent;
    public final int numberComponent;
    public final int index;
    private static Pattern instructionRegex = Pattern.compile("^(\\w{3}) ([0-9+-]+)$");
    public static final HashSet<String> permittedOps = new HashSet<>(Arrays.asList("nop","acc","jmp"));

    public Instruction(int index, String inst) {
        Matcher m = instructionRegex.matcher(inst);
        m.find();
        this.index = index;
        this.opComponent = m.group(1);
        this.numberComponent = Integer.parseInt(m.group(2));
    }

    public Instruction(String opComponent, int numberComponent, int index) {
        this.opComponent = opComponent;
        this.numberComponent = numberComponent;
        this.index = index;
    }

    @Override
    public String toString() {
        return "Instruction [index=" + index + ", numberComponent=" + numberComponent + ", opComponent=" + opComponent
                + "]";
    }

}
