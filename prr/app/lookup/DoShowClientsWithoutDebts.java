package prr.app.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prr.core.Client;
import prr.core.Network;
import prr.core.comparators.CompareByKey;
import prr.core.filters.WithoutDebtsFilter;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with positive balance.
 */
class DoShowClientsWithoutDebts extends Command<Network> {

  DoShowClientsWithoutDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITHOUT_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Client> clients = new ArrayList<Client>(_receiver.getClientsFiltered(new WithoutDebtsFilter()));
    Collections.sort(clients, new CompareByKey());
    _display.addAll(clients);
    _display.display();
  }
}
