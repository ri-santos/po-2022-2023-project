package prr.app.client;

import java.util.Collections;
import java.util.List;

import prr.core.Client;
import prr.core.CompareByKey;
import prr.core.Network;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show all clients.
 */
class DoShowAllClients extends Command<Network> {

  DoShowAllClients(Network receiver) {
    super(Label.SHOW_ALL_CLIENTS, receiver);
  }
  
  @Override
  protected final void execute() throws CommandException {
    List<Client> clients = _receiver.getClients();
    Collections.sort(clients, new CompareByKey());
    _display.addAll(clients);
    _display.display();
  }
}
