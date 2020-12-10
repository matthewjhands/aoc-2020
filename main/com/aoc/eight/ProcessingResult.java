package main.com.aoc.eight;

import java.util.ArrayList;

public class ProcessingResult {
    public final int accumulator;
    public final ArrayList<Instruction> instructionSequence;
    public final boolean looped;

    public ProcessingResult(int accumulator, ArrayList<Instruction> instructionSequence, boolean looped) {
        this.accumulator = accumulator;
        this.instructionSequence = instructionSequence;
        this.looped = looped;
    }
    
}
