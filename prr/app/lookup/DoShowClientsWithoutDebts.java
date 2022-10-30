package prr.app.lookup;

import java.util.Collections;
import java.util.List;

import prr.core.Client;
import prr.core.CompareByKey;
import prr.core.Network;
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
    List<Client> clients = _receiver.getClientsWithoutDebts();
    Collections.sort(clients, new CompareByKey());
    _display.addAll(clients);
    _display.display();
  }
}
