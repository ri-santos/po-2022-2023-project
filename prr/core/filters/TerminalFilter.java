package prr.core.filters;

import java.util.Collection;

import prr.core.Terminal;

public interface TerminalFilter {
    public Collection<Terminal> apply(Collection<Terminal> terminals);
}
