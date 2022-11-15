package prr.core.comparators;

import java.util.Comparator;

import prr.core.Communication;

public class CompareByCommId implements Comparator<Communication> {
    public int compare(Communication c1, Communication c2){
        return c1.getCommunicationId() - c2.getCommunicationId();
    }
}
