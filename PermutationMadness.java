package app;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PermutationMadness {
    /*
     * Description
     * 
     * Permutation madness
     * 
     * A group of programs has gotten together in a line and have started dancing
     * 
     * They appear to have 3 dance moves.
     * 
     * Try Spin that's a good trick, the right end swaps up most left keeing their
     * order
     * 
     * Exchange Two programs appear to swap depending on the numbers given
     * 
     * Partner Two programs that know eachother swaps
     * 
     * Input description
     * 
     * a list of programs in their initial order
     * 
     * First you will be given a string of characters, each character is an
     * individual program
     * 
     * On the next line you will get a list of moves split by ,
     * 
     * The moves work as following:
     * 
     * Spin is given as SN where N is a positive integer. This moves N programs from
     * the right up front, keeping their order
     * 
     * Exchange is given as xA/B where A and B are the positions of two programs
     * that will swap positions
     * 
     * Partner is given as pA/B where A and B refer to the original positions of the
     * programs and swaps them whereever they currently are
     * 
     * Output description
     * 
     * The output is the final order the list of programs stand in after they are
     * done dancing
     */

    List<Program> programList;

    public PermutationMadness(String s) {
        programList = new ArrayList<Program>();
        initialize(s);
    }

    private void initialize(String s) {
        for (int i = 0; i < s.length(); i++) {
            char letter = s.charAt(i);
            Program program = new Program(letter, i);
            programList.add(program);
        }
    }

    private void spin(int i) {
        Collections.rotate(programList, i);
    }

    private void exchange(int pos1, int pos2) {
        Program temp = programList.get(pos1);
        programList.set(pos1, programList.get(pos2));
        programList.set(pos2, temp);
    }

    private void partner(int pos1, int pos2) {
        int currentPos1 = getCurrentPos(pos1);
        int currentPos2 = getCurrentPos(pos2);
        if (currentPos1 == -1 || currentPos2 == -1) {
            System.out.println("Index out of Bounds");
            return;
        }
        exchange(currentPos1, currentPos2);
    }

    private int getCurrentPos(int originalPos) {
        int currentPos = -1;
        for (int i = 0; i < programList.size(); i++) {
            if (programList.get(i).getIndex() == originalPos) {
                currentPos = i;
            }
        }
        return currentPos;
    }

    private void parseCommand(String command) {
        if (command.charAt(0) == 's') {
            int num = Integer.parseInt(command.substring(1));
            spin(num);
            return;
        }
        String[] pos = command.split("[xp/]");
        int pos1 = Integer.parseInt(pos[1]);
        int pos2 = Integer.parseInt(pos[2]);
        if (command.charAt(0) == 'x') {
            exchange(pos1, pos2);
        } else {
            partner(pos1, pos2);
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(programList.size());
        for (Program program : programList) {
            sb.append(program.getLetter());
        }
        return sb.toString();
    }

    public String permute(String[] commands) {
        for (String command : commands) {
            if (command != null) {
                parseCommand(command);
            }
        }
        return this.toString();
    }
}

class Program {

    char letter;
    int index;

    public Program(char letter, int index) {
        this.letter = letter;
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public char getLetter() {
        return letter;
    }
}