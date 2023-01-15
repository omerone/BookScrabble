package test;

import java.util.ArrayList;

public class Board {

    Tile [][] t ;

    static boolean middleCheck ;
    int [] letterScore = {1,3,3,2,1,4,2,4,1,8,5,1,3,1,1,3,10,1,1,1,1,4,4,8,4,10}; //ascii start at 65=A -> 90=Z

    public static char [][] boardScore =
    {
            {'R','N','N','T','N','N','N','R','N','N','N','T','N','N','R'},
            {'N','Y','N','N','N','B','N','N','N','B','N','N','N','Y','N'},
            {'N','N','Y','N','N','N','T','N','T','N','N','N','Y','N','N'},
            {'T','N','N','Y','N','N','N','T','N','N','N','Y','N','N','T'},
            {'N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N'},
            {'N','B','N','N','N','B','N','N','N','B','N','N','N','B','N'},
            {'N','N','T','N','N','N','T','N','T','N','N','N','T','N','N'},
            {'R','N','N','T','N','N','N','C','N','N','N','T','N','N','R'},//8
            {'N','N','T','N','N','N','T','N','T','N','N','N','T','N','N'},
            {'N','B','N','N','N','B','N','N','N','B','N','N','N','B','N'},
            {'N','N','N','N','Y','N','N','N','N','N','Y','N','N','N','N'},
            {'T','N','N','Y','N','N','N','T','N','N','N','Y','N','N','T'},
            {'N','N','Y','N','N','N','T','N','T','N','N','N','Y','N','N'},
            {'N','Y','N','N','N','B','N','N','N','B','N','N','N','Y','N'},
            {'R','N','N','T','N','N','N','R','N','N','N','T','N','N','R'},
    };

    private static Board board; //singltone

    private Board () {
        t = new Tile[15][15];
        middleCheck = false;
    }

    public static Board getBoard() {
        if(board == null) {
            synchronized (Board.class) {
                board = new Board();
            }
        }
        return board;
    }

    public Tile [][] getTiles() {
        return board.t;
    }


    public boolean boardLegal(Word w) {
         boolean check; int x ;
         check = overBorders(w);
         if (!check) return false;
         x = firstToStart(w);
         if(x == -1)
             return false;
         if(x == 1)
             return true;
         check = overLen(w);
         if (!check) return false;
         check = letterIsNotLegal(w);
         if (!check) return false;
         check = wordOnTopOfAnotherWord(w);
         if (!check) return false;

        if(t[w.row][w.col] == null) {
            return directions(w);
        }
        if(t[w.row][w.col] != null && w.tiles[0] == null) {
            return directions(w);
        }
        if(t[w.row][w.col].letter == w.tiles[0].letter) {
            return directions(w);
        }
        return false;
    }

    boolean overBorders(Word w){
        if(w.row<0 || w.row>=15 || w.col<0 || w.col>=15) {
            return false;
        }
        else
            return true;
    }
    int firstToStart(Word w){
        if(t[7][7] == null) {
            for(int i=0; i<w.tiles.length; i++) {
                if(w.tiles[i] == null) {
                    return -1;
                }
            }
            if (w.col == 7 && w.row == 7)
                if (w.row <= 7 && w.row + w.tiles.length >= 7 && w.row + w.tiles.length < 15 && w.col <= 7 && w.col + w.tiles.length >= 7 && w.col + w.tiles.length < 15)
                    return 1;
            //up down
            if (w.col == 7) {
                if (w.vertical)
                    if (w.row <= 7 && w.row + w.tiles.length - 1 >= 7 && w.row + w.tiles.length - 1 < 15)
                        return 1;
                else return -1;
            }
            //up down
            if (w.row == 7) {
                if (!w.vertical)
                    if (w.col <= 7 && w.col + w.tiles.length - 1 >= 7 && w.col + w.tiles.length - 1 < 15)
                        return 1;
                else
                    return -1;
            }
        }
        return 0;
    }
    boolean overLen(Word w){
        if(w.vertical) {
            if(w.tiles.length > 15-(w.row)) {
                return false;
            }
        }
        if(!w.vertical) {
            if(w.tiles.length > 15-(w.col)) {
                return false;
            }
        }
        return true;
    }
    boolean letterIsNotLegal(Word w){
        int flag =0;
        for(int i =0; i<w.tiles.length; i++) {
            if(w.tiles[i] != null)
                if(w.tiles[i].letter < 'A' || w.tiles[i].letter > 'Z')
                    return false;
            if(w.tiles[i] != null)
                flag = 1;
        }
        if(flag == 0) return false;
        else return true;
    }
      boolean wordOnTopOfAnotherWord(Word w) {
        for(int i =0; i<w.tiles.length; i++) {
            if(w.tiles[i] != null)
                break;
            if(i+1 == 15)
                return false;
        }
        return true;
    }
    public boolean directions(Word w) {
        //up to down
        if (w.vertical) {
            for(int i =0; i<w.tiles.length; i++) {
                if(t[w.row+i][w.col] == null) {
                    continue;
                }
                if(t[w.row+i][w.col] != null && w.tiles[i] != null) {
                    if(t[w.row+i][w.col].letter != w.tiles[i].letter) {
                        return false;
                    }
                }
            }
        }
        //left to right
        else {
            for(int i =0; i<w.tiles.length; i++) {
                if(t[w.row][w.col+i] == null) {
                    continue;
                }
                if(t[w.row][w.col+i] != null && w.tiles[i] != null) {
                    if(t[w.row][w.col+i].letter != w.tiles[i].letter) {
                        return false;
                    }
                }
            }
        }

        //test to see if the word is lay to any other words and if the word is on the border + laying on other words.
        int flag =0;
        if(w.vertical) {
            int i=0;
            //left and right
            while(i<w.tiles.length) {
                if(w.col-1 > 0) {
                    if(t[w.row+i][w.col-1] != null) {
                        flag = 1;
                    }
                }
                if(w.col + 1 < 14) {
                    if(t[w.row+i][w.col+1] != null) {
                        flag =1;
                    }
                }
                i++;
            }
            //up word
            if(w.row -1 > 0) {
                if(t[w.row-1][w.col] != null) {
                    flag =1;
                }
            }
            //down word
            if( w.row +w.tiles.length < 14) {
                if(t[w.row+w.tiles.length][w.col] != null) {
                    flag =1;
                }
            }
        }
        //!vertical
        else {
            int i=0;
            while(i<w.tiles.length) {
                if(w.row-1 >0) {
                    if(t[w.row-1][w.col+i] != null) {
                        flag =1;
                    }
                }
                if(w.row+1 <14) {
                    if(t[w.row+1][w.col+i] != null) {
                        flag =1;
                    }
                }
                i++;
            }
            //1 index left
            if(w.col-1 > 0) {
                if(t[w.row][w.col-1] != null) {
                    flag =1;
                }
            }
            //1 index right
            if(w.col+w.tiles.length < 14) {
                if(t[w.row][w.col+w.tiles.length] != null) { //*****//
                    flag =1;
                }
            }
        }
        if(flag == 1) {
            return true;
        }
        return false;
    }

    public boolean dictionaryLegal(Word word) {
        return true;
    }

    public ArrayList<Word> getWords(Word word) {
        boolean checking = false;
        ArrayList arrayList = new ArrayList();
        arrayList = layingWords(word);
        Tile [] tile = new Tile[word.tiles.length];

        if(arrayList.size() > 0) {
            boolean contain = contain(arrayList, word);
            if(contain) {
                return arrayList;
            }
        }

        //puting 'word' into the array . if we have null in 'word' complete the word by t[][]
        for(int i=0; i<word.tiles.length; i++) {
            if(word.tiles[i] == null) {
                checking = true;
                if(word.vertical) {
                    tile[i] = t[word.row+i][word.col];
                }
                else {
                    tile[i] = t[word.row][word.col+i];
                }
            }
            else {
                tile[i] = word.tiles[i];
            }
        }
        if(checking) {
            Word newWordToAdd;
            if(word.vertical) {
                newWordToAdd = new Word(tile ,word.row  ,word.col  ,true);
            }
            else {
                newWordToAdd = new Word(tile ,word.row  ,word.col ,false);
            }
            //the original word
            arrayList.add(newWordToAdd);
            return arrayList;
        }
        else {
            //the original word
            arrayList.add(word);
            return arrayList;
        }

    }
    public boolean contain(ArrayList<Word> arrayList , Word word) {
        if(word.vertical) {
            if(arrayList.get(0).vertical && arrayList.get(0).col == word.col) {
                if(arrayList.get(0).row == word.row) {
                    if(arrayList.get(0).tiles.length > word.tiles.length) {
                        return true;
                    }
                }
                if(arrayList.get(0).row < word.row) {
                    if(arrayList.get(0).tiles.length-1 + arrayList.get(0).row >= word.row + word.tiles.length-1) {
                        return true;
                    }
                }
            }
        }
        else {
            if(!arrayList.get(0).vertical && arrayList.get(0).row == word.row) {
                if(arrayList.get(0).col == word.col) {
                    if(arrayList.get(0).tiles.length > word.tiles.length) {
                        return true;
                    }
                }
                if(arrayList.get(0).col < word.col) {
                    if(arrayList.get(0).tiles.length-1 + arrayList.get(0).col >= word.col + word.tiles.length-1) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    Tile [] removingNullsFromArray(Tile [] tiles) {
        int i =0;
        while (tiles[i]!= null) {
            i++;
        }
        Tile [] newTile = new Tile[i];
        for(int s=0; s<i; s++) {
            newTile[s] = tiles[s];
        }
        return newTile;
    }

    /* one option to check - one word close to another word */
    public ArrayList<Word> layingWords (Word word) //the words that surond the word on the board.
    {
        ArrayList arrayList = new ArrayList();
        int i =0;
        //vertical = up to down
        if(word.vertical) {
            int visit=0;
            // most up - check if the first letter is null and the above is letter - if yes we need to get that word
            if(word.row-1 >0) {
                if(t[word.row-1][word.col] != null && word.tiles[0] != null) {
                    Tile [] tileForNewWord = new Tile[15];
                    int j=word.row-1 ;
                    int firstRealLetter;
                    int s =0;
                    while(t[j][word.col] != null) {
                        j--;
                    }
                    j++;
                    firstRealLetter = j;
                    while(t[firstRealLetter][word.col] != null) {
                        tileForNewWord[s] = t[firstRealLetter][word.col]; //check this
                        s++;
                        firstRealLetter++;
                    }
                    for(int x=0; x<word.tiles.length; x++,s++) {
                        tileForNewWord[s] = word.tiles[x];
                    }
                    visit=1;
                    Tile [] tiles = removingNullsFromArray(tileForNewWord);
                    Word newWordToAdd = new Word(tiles , j , word.col ,true);
                    arrayList.add(newWordToAdd);
                }
            }
            //most down
            if(word.row+word.tiles.length < 14 && visit ==0) {
                if(t[word.row+word.tiles.length][word.col] != null && word.tiles[word.tiles.length-1] != null) {
                    Tile [] tileForNewWord = new Tile[15];
                    int j=word.row;
                    int x;
                    for(x =0; x<word.tiles.length; x++ , j++) {
                        if(word.tiles[x] == null && t[word.row+x][word.col] != null) {
                            tileForNewWord[x] = t[word.row+x][word.col];
                            continue;
                        }
                        tileForNewWord[x] = word.tiles[x];
                    }
                    while(t[j][word.col] != null) {
                        tileForNewWord[x] = t[j][word.col];
                        x++;
                        j++;
                    }
                    Tile [] tiles = removingNullsFromArray(tileForNewWord);
                    Word newWordToAdd = new Word(tiles , word.row , word.col ,true);
                    arrayList.add(newWordToAdd);
                }
            }
            //loop the word itself
            while(i != word.tiles.length) {
                if(word.tiles[i] == null) {
                    i++;
                    continue;
                }
                //right but not left
                if(word.col+1 < 14) {
                    if(word.tiles[i] != null && t[word.row+i][word.col-1] == null && t[word.row+i][word.col+1] != null) {
                        int j= word.col+1;
                        int s=1;
                        Tile [] tileForNewWord = new Tile[15];
                        tileForNewWord[0] = word.tiles[i];
                        while(t[word.row+i][j] != null) {
                            tileForNewWord[s] = t[word.row+i][j];
                            j++;
                            s++;
                        }
                        Tile [] tiles = removingNullsFromArray(tileForNewWord);
                        Word newWordToAdd = new Word(tiles , word.row+i , word.col ,false);
                        arrayList.add(newWordToAdd);
                    }
                }
                //left with or without right
                if(word.col-1 >0) {
                    if(word.tiles[i] != null && t[word.row+i][word.col-1] != null) {
                        int j= word.col-1;
                        int s=0;
                        int firstWordLocate;
                        Tile [] tileForNewWord = new Tile[15];
                        while(t[word.row+i][j] != null) {
                            if(j==0)
                            {
                                break;
                            }
                            j--;
                        }
                        j++;
                        firstWordLocate = j;
                        while(t[word.row+i][firstWordLocate] != null) {
                            tileForNewWord[s] = t[word.row+i][firstWordLocate];
                            s++;
                            firstWordLocate++;
                        }
                        if(t[word.row][word.col+1] ==null) {
                            tileForNewWord[s] = word.tiles[i];
                        }
                        Tile [] tiles = removingNullsFromArray(tileForNewWord);
                        Word newWordToAdd = new Word(tiles , word.row+i , j ,false);
                        arrayList.add(newWordToAdd);
                    }
                }
                i++;
            }
        }
        // !vertical = left to right
        else {
            // THE LEFTEST
            int visit=0;
            if(word.col-1 > 0) {
                if(word.tiles[0] != null && t[word.row][word.col-1] != null) {
                    int j = word.col - 1;
                    int s = 0;
                    int firstRealLetter;
                    Tile[] tileForNewWord = new Tile[15];
                    while (t[word.row][j] != null) {
                        if(j == 0){
                            break;
                        }
                        j--;
                    }
                    j++;
                    firstRealLetter = j;
                    int index = 0;
                    int cal = word.tiles.length;
                    while (t[word.row][firstRealLetter] != null || index < cal) {
                        //when we have on the board tile != null
                        if (t[word.row][firstRealLetter] != null)
                            tileForNewWord[s] = t[word.row][firstRealLetter];
                        //when we have in the word tile != null
                        else if (word.tiles[index] != null)
                            tileForNewWord[s] = word.tiles[index];
                        //start add only when first is came to word.col
                        if (firstRealLetter >= word.col )
                            index++;
                        firstRealLetter++;
                        s++;
                    }
                    visit = 1;
                    Tile [] tiles = removingNullsFromArray(tileForNewWord);
                    Word newWordToAdd = new Word(tiles, word.row, j, false);
                    arrayList.add(newWordToAdd);
                }
            }
            //the rightest
            if(word.col+word.tiles.length-1 < 14 && visit == 0) {
                if(word.tiles[word.tiles.length-1] != null && t[word.row][word.col+word.tiles.length] != null) {
                    int j= word.col;
                    int x =0;
                    Tile [] tileForNewWord = new Tile[15];
                    //put inside the word itself
                    for(x =0; x<word.tiles.length; x++,j++) {
                        tileForNewWord[x] = word.tiles[x];
                    }
                    //put what i have on the board
                    while (t[word.row][j] != null) {
                        tileForNewWord[x] = t[word.row][j];
                        x++;
                        j++;
                    }
                    Tile [] tiles = removingNullsFromArray(tileForNewWord);
                    Word newWordToAdd = new Word(tiles , word.row , word.col ,false);
                    arrayList.add(newWordToAdd);
                }
            }
            //loop the word itself
            while(i != word.tiles.length) {
                // up with or without down
                if(word.tiles[i] == null) {
                    i++;
                    continue;
                }
                if(word.row-1 > 0) {
                    if(word.tiles[i] != null && t[word.row-1][word.col+i] != null ) {
                        int j= word.row;
                        int s=0;
                        int firstWordLocate;
                        Tile [] tileForNewWord = new Tile[15];
                        while(t[j-1][word.col+i] != null && j >=0) {
                            if(j==0) {
                                break;
                            }
                            j--;
                        }
                        firstWordLocate = j;
                        while(t[firstWordLocate][word.col+i] != null ) {
                            tileForNewWord[s] = t[firstWordLocate][word.col+i];
                            if(firstWordLocate == 14) {
                                break;
                            }
                            s++;
                            firstWordLocate++;
                        }
                        int x=1;
                        tileForNewWord[s] = word.tiles[i];
                        while(t[word.row+x][word.col+i] != null ) {
                            tileForNewWord[s+1] = t[word.row+x][word.col+i];
                            s++;
                            x++;
                        }
                        Tile [] tiles = removingNullsFromArray(tileForNewWord);
                        Word newWordToAdd = new Word(tiles , j , word.col+i ,true);
                        arrayList.add(newWordToAdd);
                    }
                }
                // down but not up
                if(word.row+i >14) {
                    break;
                }
                if(word.row+1 < 14) {
                    if(word.tiles[i] != null && t[word.row+1][word.col+i] != null && t[word.row-1][word.col+i] == null) {
                        int j= word.row+1;
                        int s=1;
                        Tile [] tileForNewWord = new Tile[15];
                        tileForNewWord[0] = word.tiles[i];
                        while(t[j][word.col+i] != null) {
                            tileForNewWord[s] = t[j][word.col+i];
                            if(j==14) {
                                break;
                            }
                            s++;
                            j++;
                        }
                        Tile [] tiles = removingNullsFromArray(tileForNewWord);
                        Word newWordToAdd = new Word(tiles , word.row , word.col+i ,true);
                        arrayList.add(newWordToAdd);
                    }
                }
                i++;
            }
        }
        return arrayList;
    }

    public int getScore(Word word)
    {
        int sum=0;
        int temp=0;
        //suming letters
        for(int i =0; i<word.tiles.length; i++) {
            if(word.tiles[i] == null) {
                if(word.vertical) {
                    char w = t[word.row+i][word.col].letter;
                    temp = letterScore[(w - 65)];
                }
                else {
                    char w = t[word.row][word.col+i].letter;
                    temp = letterScore[(w - 65)];
                }
            }
            else {
                temp = letterScore[(word.tiles[i].letter - 65)];
            }
            if(word.vertical) {
                //when double or triple the letters
                if(boardScore[word.row + i][word.col] == 'B') {
                    temp *= 3;
                }
                if(boardScore[word.row + i][word.col] == 'T') {
                    temp *= 2;
                }
            }
            else {
                if(boardScore[word.row][word.col+i] == 'B') {
                    temp *= 3;
                }
                if(boardScore[word.row][word.col+ i] == 'T') {
                    temp *= 2;
                }
            }
            sum += temp;
        }
        // suming words
        for (int i =0; i<word.tiles.length; i++) {
            if (word.vertical) {
                // first step in the game
                if (boardScore[word.row + i][word.col] == 'C' && !middleCheck) {
                    middleCheck = true;
                    sum *= 2;
                }
                if (boardScore[word.row + i][word.col] == 'R') {
                    sum *= 3;
                }
                if (boardScore[word.row + i][word.col] == 'Y') {
                    sum *= 2;
                }
            } else {
                // first step in the game
                if (boardScore[word.row][word.col + i] == 'C' && !middleCheck) {
                    middleCheck = true;
                    sum *= 2;
                }
                if (boardScore[word.row][word.col + i] == 'R') {
                    sum *= 3;
                }
                if (boardScore[word.row][word.col + i] == 'Y') {
                    sum *= 2;
                }
            }
        }
        return sum;
    }

    public int tryPlaceWord(Word word)
    {
        //check if 'word' is legal
        if(!boardLegal(word)) {
            return 0;
        }

        //get and put in list all the words that lay on 'word'
        ArrayList<Word> array = new ArrayList<>();
        array = getWords(word);

        //check all the words that lay on this word if 'legal'
        for(int w = 0; w<array.size(); w++) {
            if(!boardLegal(array.get(w))) {
                return 0;
            }
        }

        //put the word into the board   -> t[][]
        for(int i =0; i<word.tiles.length; i++) {
            if(word.tiles[i] == null) {
             continue;
            }
            if (word.vertical) {
                if(word.row + i <= 14 || word.row + i >= 0)
                    t[word.row + i][word.col] = word.getTiles()[i];
            }
            else {
                if(word.col + i <= 14 || word.col + i >= 0)
                    t[word.row][word.col + i] = word.getTiles()[i];
            }
        }

        //sum all the score that lying
        int sum =0;
        for(int w = 0; w<array.size(); w++) {
            //sum each word that lay on 'word'
            sum += getScore(array.get(w));
            //check if it is the first word on the board if yes it suppose to locate on the middle star -> double word score/
            for(int i =0; i<array.get(w).tiles.length; i++) {
                if(word.vertical) {
                    if(array.get(w).row+i == 7 && array.get(w).col ==7 && !middleCheck) {
                        middleCheck = true;
                        sum*=2;
                    }
                }
                else {
                    if(array.get(w).row == 7 && array.get(w).col +i ==7 && !middleCheck) {
                        middleCheck = true;
                        sum*=2;
                    }
                }
            }
        }
        return sum;
    }
}
