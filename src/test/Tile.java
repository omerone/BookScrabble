package test;

import java.util.Objects;
import java.util.Random;

public class Tile {
    public final char letter;
    final int score;


    private Tile(char letter, int score)
    {
        this.letter = letter;
        this.score = score;
    }


    private Tile(Tile tile)
    {
        this.letter = tile.letter;
        this.score = tile.score;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tile tile = (Tile) o;
        return letter == tile.letter && score == tile.score;
    }

    @Override
    public int hashCode() {
        return Objects.hash(letter, score);
    }

    public static class Bag {
        private static Bag Sbag;
        int bagCapacity = 98;

        int[] letterCapacityOriganl= {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1}; //מערך הכמויות האורגינלי
        int[] letterCapacity = {9,2,2,4,12,2,3,2,9,1,1,4,2,6,8,2,1,6,4,6,4,2,2,1,2,1}; //מערך הכמויות
        char [] lettersArray = {'A','B','C','D','E','F','G','H','I','G','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        Tile[] tileArrayScore = {new Tile('A', 1), new Tile('B', 3), new Tile('C', 3), new Tile('D', 2),
                new Tile('E', 1), new Tile('F', 4), new Tile('G', 2), new Tile('H', 4),
                new Tile('I', 1), new Tile('J', 8), new Tile('K', 5), new Tile('L', 1),
                new Tile('M', 3), new Tile('N', 1), new Tile('O', 1), new Tile('P', 3),
                new Tile('Q', 10), new Tile('R', 1), new Tile('S', 1), new Tile('T', 1),
                new Tile('U', 1), new Tile('V', 4), new Tile('W', 4), new Tile('X', 8),
                new Tile('Y', 4), new Tile('Z', 10)}; //מערך האריחים

        public static Bag getBag()
        {
            if (Sbag == null)
            {
                Sbag = new Bag();
            }
            return Sbag;
        }

        public int size()
        {
            return bagCapacity;
        }
        public Tile getRand()
        {
            //checking if there is any tile left
            boolean empty = false;
            int flag = 0;
            if(bagCapacity==0)
            {
                return null;
            }

                // Generate random integers in range 0 to 25
                Random random = new Random();
                int randomNumber = random.nextInt(25);
                while (letterCapacity[randomNumber] == 0)
                {
                    randomNumber = random.nextInt(25);
                }
                //removing one tile from the arrayCapacity
                letterCapacity[randomNumber]--;
                bagCapacity--;

                return tileArrayScore[randomNumber];


        }
        public Tile getTile(char letter)
        {
            if(letter < 'A' || letter > 'Z') {return null;}
            if(bagCapacity <= 0) {return null;}

            int i = 0;
            while(letter != lettersArray[i] && i<25)
            {
                i++;
            }
            if(letterCapacity[i] > 0)
            {
                letterCapacity[i]--;
                bagCapacity--;
                return tileArrayScore[i];
            }
            return null;
        }

        public int[] getQuantities()
        {
            int[] arr = new int [26];
            System.arraycopy(letterCapacity,0,arr,0,26);
            return arr;
        }

        public void put(Tile t)
        {

            if(bagCapacity < 98)
            {
                if(letterCapacity[t.letter-'A'] < letterCapacityOriganl[t.letter-'A'])
                {
                    bagCapacity++;
                    letterCapacity[t.letter-'A']++;
                }
            }
        }
    }
}
