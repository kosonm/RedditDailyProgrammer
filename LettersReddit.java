package app;

import java.util.HashMap;

public class LettersReddit {

    public boolean solution(String s) {
        char[] word = s.toCharArray();
        HashMap<Character, Integer> map = new HashMap<Character, Integer>();

        for (Character c : word) {
            if (!map.containsKey(c)) {
                map.put(c, 1);
            } else if (map.containsKey(c)) {
                map.replace(c, map.get(c) + 1);
            }
        }

        if (map.size() != 2 && map.size() > 0) {
            char[] temp = map.values().toString().toCharArray();
            char[] values;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < temp.length; i++){
                if(Character.isDigit(temp[i])){
                    sb.append(temp[i]);
                }
            }
            values = sb.toString().toCharArray();

            for(int i = 0; i< values.length - 1; i ++){
                if(values[i] != values[i+1]){
                    return false;
                }
            }
            return true;
        }

        else if(map.size() == 2) {
            char[] temp = map.values().toString().toCharArray();
            char[] values;
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < temp.length; i++){
                if(Character.isDigit(temp[i])){
                    sb.append(temp[i]);
                }
            }
            values = sb.toString().toCharArray();


            if(values[0] == values[1]){
                return true;
            }
            else if(values[0] != values[1]){
                return false;
            }
        }
        return true;

    }
    public static void main(String[] args) {
        LettersReddit tester = new LettersReddit();
        boolean result = tester.solution("a");
        System.out.println(result);
    }
}