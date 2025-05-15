package kuit.springbasic.adapter;

// 두 개의 레거시 코드를 만들때 adapter 패턴을 만들 수 있다.
public class SkiAdapter extends SnowBoard{
    private Ski ski;

    public SkiAdapter(Ski ski) {
        this.ski = ski;
    }

    @Override
    public void snowBoarding() {
        ski.skiing();
    }
}
