package prr.app.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prr.core.Client;
import prr.core.Network;
import prr.core.comparators.CompareByDebts;
import prr.core.filters.WithDebtsFilter;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show clients with negative balance.
 */
class DoShowClientsWithDebts extends Command<Network> {

  DoShowClientsWithDebts(Network receiver) {
    super(Label.SHOW_CLIENTS_WITH_DEBTS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    List<Client> clients = new ArrayList<>(_receiver.getClientsFiltered(new WithDebtsFilter()));
    Collections.sort(clients, Collections.reverseOrder(new CompareByDebts()));
    _display.addAll(clients);
    _display.display();
  }
}
