package prr.core.filters;

import java.util.ArrayList;
import java.util.Collection;

import prr.core.Terminal;

public class UnusedTerminals implements TerminalFilter{
    @Override
    public Collection<Terminal> apply(Collection<Terminal> terminals) {
        Collection<Terminal> unusedTerminals = new ArrayList<Terminal>();
        for (Terminal terminal : terminals){
            if (terminal.numberOfCommunications() == 0){
                unusedTerminals.add(terminal);
            }
        }
        return unusedTerminals;
    }
}
