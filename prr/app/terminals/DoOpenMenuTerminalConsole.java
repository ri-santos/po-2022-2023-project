package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
  }

  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String id = stringField("id");
    try{
      (new prr.app.terminal.Menu(_receiver, _receiver.openTerminalMenu(id))).open();
    } catch(UnknownKeyException e){
      throw new UnknownTerminalKeyException(e.getKey());
    }
  }
}
