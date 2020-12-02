package main.com.aoc.two;

import java.util.regex.*;

public class Password {
    public int policyMin;
    public int policyMax;
    public char policyChar;
    public String value;

    public boolean isValidPartOne() {
        Pattern pattern = Pattern.compile(Character.toString(policyChar));
        Matcher matcher = pattern.matcher(value);
        int count = (int) matcher.results().count();
        return (count >= policyMin) && (count <= policyMax);
    }

    public boolean isValidPartTwo() {
        return ((value.charAt(policyMin-1) == policyChar) ^ (value.charAt(policyMax-1) == policyChar));
    }

    @Override
	public String toString() {
		return "Password [policyChar=" + policyChar + ", policyMax=" + policyMax + ", policyMin=" + policyMin
				+ ", value=" + value + ", isValidPartOne()=" + isValidPartOne() + ", isValidPartTwo()=" + isValidPartTwo() + "]";
	}

}
