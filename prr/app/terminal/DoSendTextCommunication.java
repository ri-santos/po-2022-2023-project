package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

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
    //FIXME implement command
    String id = stringField("id");
    String message = stringField("message");
    try{
      if(!_network.sendTextCommunication(_receiver, id, message)){
        _display.popup(Message.destinationIsOff(message));       
      }
    }catch (UnknownKeyException e){
      throw new UnknownTerminalKeyException(e.getKey());
    }
  }
}