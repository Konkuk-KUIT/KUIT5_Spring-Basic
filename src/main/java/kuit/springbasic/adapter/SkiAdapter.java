package kuit.springbasic.adapter;

// 복잡한 두 개의 레거시 코드를 합칠 때 Adapter Pattern을 사용할 수 있다.
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
