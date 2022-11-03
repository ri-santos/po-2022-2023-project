package prr.core;

public class ClientLevelGold extends ClientLevel {
    @Override
    public double textCost(int duration){
        if (duration < 100) {return 10;}
        else {return 2*duration;}
    }

    @Override
    public double voiceCost(int duration){
        return 10 * duration;
    }

    @Override
    public double videoCost(int duration){
        return 20 * duration;
    }

    @Override
    public String toString(){
        return "GOLD";
    }
}
