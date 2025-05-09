package kuit.springbasic.adapter;

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
