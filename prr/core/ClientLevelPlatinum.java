package prr.core;

public class ClientLevelPlatinum extends ClientLevel {
    @Override
    public double textCost(int duration){
        if (duration < 50) {return 0;}
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
    public void upgradeLevel(Client c){}

    @Override
    public void downgradeLevel(Client c){
        if(c.verifyLast2Text()){c.setLevel(new ClientLevelGold());}
    }
    @Override
    public void setToNormal(Client c){
        if(c.getPayments() - c.getDebts() < 0){ c.setLevel(new ClientLevelNormal());}
    }

    @Override
    public String toString(){
        return "PLATINUM";
    }
}
