package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.Display;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Enable client notifications.
 */
class DoEnableClientNotifications extends Command<Network> {

  DoEnableClientNotifications(Network receiver) {
    super(Label.ENABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException {
    String key = stringField("key");
    try{
      if (_receiver.enableClientNotifications(key) == false){
        _display.popup(Message.clientNotificationsAlreadyEnabled());
      }
    } catch(UnknownKeyException e){
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
