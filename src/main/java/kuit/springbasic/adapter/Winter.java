package kuit.springbasic.adapter;

public class Winter {

    public static void main(String[] args) {
        Ski ski=new Ski();
        SnowBoard snowBoard=new SnowBoard();
        ski.skiing();
        snowBoard.snowBoarding();

        SkiAdapter skiAdapter=new SkiAdapter(ski);
        skiAdapter.snowBoarding();
    }
}
