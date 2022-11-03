package prr.core.comparators;

import java.util.Comparator;

import prr.core.Client;

public class CompareByDebts implements Comparator<Client>{
    public int compare(Client c1, Client c2){
        if (c1.getDebts() > c2.getDebts()) return 1;

        else if (c1.getDebts() < c2.getDebts()) return -1;
        
        else return new CompareByKey().compare(c1, c2);
    }
}
