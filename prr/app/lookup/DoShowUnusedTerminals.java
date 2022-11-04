package prr.app.lookup;

import prr.core.Network;
import prr.core.filters.UnusedTerminals;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show unused terminals (without communications).
 */
class DoShowUnusedTerminals extends Command<Network> {

  DoShowUnusedTerminals(Network receiver) {
    super(Label.SHOW_UNUSED_TERMINALS, receiver);
  }

  @Override
  protected final void execute() throws CommandException {
    _display.addAll(_receiver.showTerminalsFiltered(new UnusedTerminals()));
    _display.display();
  }
}
