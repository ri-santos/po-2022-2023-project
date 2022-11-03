package prr.core;

public class ClientLevelPlatinum extends ClientLevel {
    @Override
    public double textCost(int duration){
        if (duration < 50) {return 10;}
        else {return 4;}
    }

    @Override
    public double voiceCost(int duration){
        return 10 * duration;
    }

    @Override
    public double videoCost(int duration){
        return 10 * duration;
    }

    @Override
    public String toString(){
        return "PLATINUM";
    }
}
