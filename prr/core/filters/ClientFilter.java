package prr.core.filters;

import java.util.Collection;
import java.util.List;

import prr.core.Client;

public interface ClientFilter {
    public List<Client> apply(Collection<Client> clients);
}
