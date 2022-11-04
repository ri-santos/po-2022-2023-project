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
    public void upgradeLevel(Client c){
      if(c.getPayments() - c.getDebts() > 500){
        c.setLevel(new ClientLevelGold());
      }    
    }

    @Override
    public void downgradeLevel(Client c){}

    @Override
    public void setToNormal(Client c){}

    @Override
    public String toString(){
        return "NORMAL";
    }
}
