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
    public void upgradeLevel(Client c){
        if(c.verifyLast5Video()){c.setLevel(new ClientLevelPlatinum());}
    }

    @Override
    public void downgradeLevel(Client c){setToNormal(c);}

    @Override
    public void setToNormal(Client c){
        if(c.getPayments() - c.getDebts() < 0){c.setLevel(new ClientLevelNormal());}
    }

    @Override
    public String toString(){
        return "GOLD";
    }
}
