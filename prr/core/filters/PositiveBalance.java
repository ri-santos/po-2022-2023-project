package prr.core.filters;

import java.util.ArrayList;
import java.util.Collection;

import prr.core.Terminal;

public class PositiveBalance implements TerminalFilter {
    @Override
    public Collection<Terminal> apply(Collection<Terminal> terminals) {
        Collection<Terminal> positiveTerminals = new ArrayList<Terminal>();
        for (Terminal terminal : terminals){
            if (terminal.getBalance() > 0){
                positiveTerminals.add(terminal);
            }
        }
        return positiveTerminals;
    }
}
