package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Disable client notifications.
 */
class DoDisableClientNotifications extends Command<Network> {

  DoDisableClientNotifications(Network receiver) {
    super(Label.DISABLE_CLIENT_NOTIFICATIONS, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException,UnknownClientKeyException {
    String key = stringField("key");
    try{
        if (_receiver.disableClientNotifications(key) == false){
            _display.popup(Message.clientNotificationsAlreadyDisabled());
          }
    } catch(UnknownKeyException e){
      throw new UnknownClientKeyException(e.getKey());
    }
  }
}
