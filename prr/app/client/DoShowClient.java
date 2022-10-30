package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show specific client: also show previous notifications.
 */
class DoShowClient extends Command<Network> {

  DoShowClient(Network receiver) {
    super(Label.SHOW_CLIENT, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException,UnknownClientKeyException {
    String key = stringField("key");
    try{
      _display.addLine(_receiver.showClient(key));
      _display.addAll(_receiver.showNotifications(key));
    } catch(UnknownKeyException e){
      throw new UnknownClientKeyException(e.getKey());
    }
    _display.display();
  }
}
