package utils;

import java.util.Random;

public class PageSequenceGenerator {

    public static int[] generateRandomSequence(int length, int maxPageNumber){
        Random random = new Random();
        int[] sequence = new int[length];

        for(int i=0; i<length; i++){
            sequence[i] = random.nextInt(maxPageNumber);
        }
        return sequence;
    }

    public static int[] parseManualInput(String input){
        try{
            String[] parts = input.trim().split("\\s+");
            int[] sequence = new int[parts.length];

            for(int i=0; i<parts.length; i++){
                sequence[i] = Integer.parseInt(parts[i]);
            }
            return sequence;
        }catch(NumberFormatException e){
            return null;
        }
    }
}
