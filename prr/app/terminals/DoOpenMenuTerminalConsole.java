package prr.app.terminals;

import prr.core.Network;
import prr.core.exception.InvalidTerminalIdException;
import prr.core.exception.TerminalDoesNotExistException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add mode import if needed

/**
 * Open a specific terminal's menu.
 */
class DoOpenMenuTerminalConsole extends Command<Network> {

  DoOpenMenuTerminalConsole(Network receiver) {
    super(Label.OPEN_MENU_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    //FIXME implement command
    // create an instance of prr.app.terminal.Menu with the
    // selected Terminal and open it
    String id = stringField("id");
    try{
      (new prr.app.terminal.Menu(_receiver, _receiver.openTerminalMenu(id))).open();
    } catch(TerminalDoesNotExistException e){
      throw new UnknownTerminalKeyException(e.getId());
    }
  }
}
