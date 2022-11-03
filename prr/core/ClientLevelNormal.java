package prr.core;

public class ClientLevelNormal extends ClientLevel {
    @Override
    public double textCost(int duration){
        if (duration < 50) {return 10;}
        else if (duration >= 50 && duration < 100) {return 16;}
        else {return 2*duration;}
    }

    @Override
    public double voiceCost(int duration){
        return 20 * duration;
    }

    @Override
    public double videoCost(int duration){
        return 30 * duration;
    }

    @Override
    public String toString(){
        return "NORMAL";
    }
}
