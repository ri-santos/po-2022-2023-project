package prr.core.filters;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import prr.core.Client;

public class WithoutDebtsFilter implements ClientFilter {
    
    @Override
    public List<Client> apply(Collection<Client> clients) {
        List<Client> clientsDebts = new ArrayList<Client>();
        for (Client client : clients){
            if(client.getDebts() == 0){clientsDebts.add(client);}
        }
        return clientsDebts;
    }
}