package test;

import test.Tile.Bag;

public class MainTrain {

    public static void testBag()
    {
        Bag b=Tile.Bag.getBag();
        Bag b1=Tile.Bag.getBag();
        if(b1!=b)
            System.out.println("your Bag in not a Singleton (-5)");

        int[] q0 = b.getQuantities();
        q0[0]+=1;
        int[] q1 = b.getQuantities();
        if(q0[0]!=q1[0] + 1)
            System.out.println("getQuantities did not return a clone (-5)");

        for(int k=0;k<9;k++) {
            int[] qs = b.getQuantities();
            Tile t = b.getRand();
            int i=t.letter-'A';
            int[] qs1 = b.getQuantities();
            if(qs1[i]!=qs[i]-1)
                System.out.println("problem with getRand (-1)");

            b.put(t);
            b.put(t);
            b.put(t);

            if(b.getQuantities()[i]!=qs[i])
                System.out.println("problem with put (-1)");
        }
        if(b.getTile('a')!=null || b.getTile('$')!=null || b.getTile('A')==null)
            System.out.println("your getTile is wrong (-2)");

    }



    private static Tile[] get(String s) {
        Tile[] ts=new Tile[s.length()];
        int i=0;
        for(char c: s.toCharArray()) {
            ts[i]=Bag.getBag().getTile(c);
            i++;
        }
        return ts;
    }

    public static void testBoard() {
        Board b = Board.getBoard();
        if(b!=Board.getBoard())
            System.out.println("board should be a Singleton (-5)");


        Bag bag = Bag.getBag();
        Tile[] ts=new Tile[10];
        for(int i=0;i<ts.length;i++)
            ts[i]=bag.getRand();

        Word w0=new Word(ts,0,6,true);
        Word w1=new Word(ts,7,6,false);
        Word w2=new Word(ts,6,7,true);
        Word w3=new Word(ts,-1,7,true);
        Word w4=new Word(ts,7,-1,false);
        Word w5=new Word(ts,0,7,true);
        Word w6=new Word(ts,7,0,false);

        if(b.boardLegal(w0) || b.boardLegal(w1) || b.boardLegal(w2) || b.boardLegal(w3) || b.boardLegal(w4) || !b.boardLegal(w5) || !b.boardLegal(w6))
            System.out.println("your boardLegal function is wrong (-10)");

        for(Tile t : ts)
            bag.put(t);

        //test -1

//        Word horn=new Word(get("HORN"), 7, 5, false);
//        if(b.tryPlaceWord(horn)!=14)
//            System.out.println("problem in placeWord for 1st word (-10)");
//
//        Word farm=new Word(get("FA_M"), 5, 7, true);
//        if(b.tryPlaceWord(farm)!=9)
//            System.out.println("problem in placeWord for 2ed word (-10)");
//
//        Word paste=new Word(get("PASTE"), 9, 5, false);
//        if(b.tryPlaceWord(paste)!=25)
//            System.out.println("problem in placeWord for 3ed word (-10)");
//
//        Word mob=new Word(get("_OB"), 8, 7, false);
//        int mobpoint = b.tryPlaceWord(mob);
//        if(mobpoint!=18)
//            System.out.println("mob point sould be 18");
//
//        Word bit=new Word(get("BIT"), 10, 4, false);
//        int bitpoint = b.tryPlaceWord(bit);
//        if(bitpoint!=22)
//         System.out.println("bitpoint should be 22 (-15)");
//

        //test -2

//        Word bit2=new Word(get("S_TA"), 9, 4, true);
//        if(b.tryPlaceWord(bit2)!=28)
//            System.out.println("SBTA should be 28 (-15)");
//        Word bit3=new Word(get("A_ONE"), 11, 3, false);
//        if(b.tryPlaceWord(bit3)!=26)
//            System.out.println("ATONE should be 26 (-15)");
//        Word bit6=new Word(get("ADE"),12,5,true);
//        if(b.tryPlaceWord(bit6)!=21)
//            System.out.println("ADE should be 21 (-15)");

        //test -3

//        Word ahi=new Word(get("A_I"), 6, 5, true);
//        if(b.tryPlaceWord(ahi)!=16)
//            System.out.println("AHI should be ahi (-15)");
//
//        Word al=new Word(get("AL"), 6, 9, true);
//        if(b.tryPlaceWord(al)!=16)
//            System.out.println("ALBE should be ahi (-15)");
//
//        Word ran=new Word(get("R_N"), 6, 6, false);
//        if(b.tryPlaceWord(ran)!=16)
//            System.out.println("RAN should be ahi (-15)");
//
        //test -4

//        Word ala=new Word(get("ALA"), 8, 4, false);
//        if(b.tryPlaceWord(ala)!=32)
//            System.out.println("ALA should be 32 (-15)");


        //test -5 <-> tests that suppose to return 0.

//        Word bit4=new Word(get("ABA"),0,12,false);
//        if(b.tryPlaceWord(bit4)!=0)//not adjust to any tile in graph
//            System.out.println("ABA should be 0 (-15)");
//        Word bit5=new Word(get("ADEL"),3,11,false);
//        if(b.tryPlaceWord(bit5)!=0)//Not legal.
//            System.out.println("ADEL should be 0 (-15)");
//        Word aaaa =new Word(get("aawe"), 5, 8, false);
//        if(b.tryPlaceWord(aaaa)!=0) //letters are not legal
//            System.out.println("aawe should be 0");
//        Word bot=new Word(get("___"), 0, 7, false);
//        if(b.tryPlaceWord(bot)!=0)
//            System.out.println("problem in placeWord for 1st word (-10)");


        //test 6
//        Word love=new Word(get("LO_E"), 0, 7, false);
//        if(b.tryPlaceWord(love)!=0)
//            System.out.println("problem in placeWord for 1st word (-10)");
//
//        Word horn=new Word(get("HORN"), 1, 7, true);
//        if(b.tryPlaceWord(horn)!=0)
//            System.out.println("problem in placeWord for 1st word (-10)");
//
//        Word tov=new Word(get("TOV"), 7, 8, false);
//        if(b.tryPlaceWord(tov)!=0)
//            System.out.println("problem in placeWord for 1st word (-10)");

        Word board=new Word(get("BOARD"), 4, 5, false);
        if(b.tryPlaceWord(board)!=0)
            System.out.println("problem in placeWord for 1st word (-10)");
        Word lol=new Word(get("LOL"), 6, 7, true);
        if(b.tryPlaceWord(lol)!=6)
            System.out.println("problem in placeWord for 2st word (-10)");
        Word beers=new Word(get("BEARS"), 8, 2, false);
        if(b.tryPlaceWord(beers)!=12)
            System.out.println("problem in placeWord for 3st word (-10)");
        Word okay=new Word(get("_KAY"), 7, 7, false);
        if(b.tryPlaceWord(okay)!=11)
            System.out.println("problem in placeWord for 4st word (-10)");
        Word abc=new Word(get("ABC"), 9, 8, false);
        if(b.tryPlaceWord(abc)!=0)
            System.out.println("problem in placeWord for 5st word (-10)");
        Word x=new Word(get("X"), 8, 8, true);
        if(b.tryPlaceWord(x)!=49)
            System.out.println("problem in placeWord for 7st word (-10)");
    }

    public static void main(String[] args) {
        testBag(); // 30 points
        testBoard(); // 70 points
        System.out.println("done");
    }

}
