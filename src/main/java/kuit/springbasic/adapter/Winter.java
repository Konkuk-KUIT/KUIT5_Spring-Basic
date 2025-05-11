package kuit.springbasic.adapter;

public class Winter {

    public static void main(String[] args) {
        Ski ski=new Ski();
        SnowBoard snowBoard=new SnowBoard();
        ski.skiing();
        snowBoard.snowBoarding();

        // 스키가 스노보드도 탈 수 있다.
        SkiAdapter skiAdapter=new SkiAdapter(ski);
        skiAdapter.snowBoarding();
    }
}
