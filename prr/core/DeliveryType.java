package prr.core;

import java.io.Serializable;

public abstract class DeliveryType implements Serializable{

    private static final long serialVersionUID = 202208091753L;

    public DeliveryType(){}
    public abstract String toString();
}
