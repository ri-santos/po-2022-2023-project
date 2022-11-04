package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.DestinationTerminalIsOffException;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command for sending a text communication.
 */
class DoSendTextCommunication extends TerminalCommand {

  DoSendTextCommunication(Network context, Terminal terminal) {
    super(Label.SEND_TEXT_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("id", Message.terminalKey());
    addStringField("message", Message.textMessage());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String id = stringField("id");
    String message = stringField("message");
    try{
      _network.sendTextCommunication(_receiver, id, message);
    }catch (UnknownKeyException e){
      throw new UnknownTerminalKeyException(e.getKey());
    }catch (DestinationTerminalIsOffException e){
      _network.addNotifyClient(_receiver, id);
      _display.popup(Message.destinationIsOff(id));
    }
  }
}
