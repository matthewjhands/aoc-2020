package main.com.aoc.two;

import java.util.regex.*;

public class Password {
    public int policyMin;
    public int policyMax;
    public String policyChar;
    public String value;

    public boolean isValid() {
        Pattern pattern = Pattern.compile(policyChar);
        Matcher matcher = pattern.matcher(value);
        int count = (int) matcher.results().count();
        return (count >= policyMin) && (count <= policyMax);
    }

    @Override
	public String toString() {
		return "Password [policyChar=" + policyChar + ", policyMax=" + policyMax + ", policyMin=" + policyMin
				+ ", value=" + value + ", isValid()=" + isValid() + "]";
	}

}
