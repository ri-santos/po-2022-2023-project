package prr.core;
import java.io.Serializable;

public abstract class ClientLevel implements Serializable {
    
    private static final long serialVersionUID = 202208091753L;


    public abstract double textCost(int duration);
    public abstract double voiceCost(int duration);
    public abstract double videoCost(int duration);
    public abstract void upgradeLevel(Client c);
    public abstract void downgradeLevel(Client c);
    public abstract void setToNormal(Client c);
    public abstract String toString();
}
