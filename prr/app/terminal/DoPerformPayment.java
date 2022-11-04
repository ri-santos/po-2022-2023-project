package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import pt.tecnico.uilib.menus.CommandException;
// Add more imports if needed

/**
 * Perform payment.
 */
class DoPerformPayment extends TerminalCommand {

  DoPerformPayment(Network context, Terminal terminal) {
    super(Label.PERFORM_PAYMENT, context, terminal);
    addIntegerField("key", Message.commKey());
  }
  
  @Override
  protected final void execute() throws CommandException {
    if(!_receiver.doPayment(integerField("key"))){
      _display.popup(Message.invalidCommunication());
    }
  }
}
