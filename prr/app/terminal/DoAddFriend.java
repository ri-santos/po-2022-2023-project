package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.menus.CommandException;
/**
 * Add a friend.
 */
class DoAddFriend extends TerminalCommand{

  DoAddFriend(Network context, Terminal terminal) {
    super(Label.ADD_FRIEND, context, terminal);
    addStringField("friendId", Message.terminalKey());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException{
    String friendId = stringField("friendId");
    try{
        _network.addFriend(_receiver, friendId);
    } catch (UnknownKeyException e){
        throw new UnknownTerminalKeyException(e.getKey());
    }
  }
}
