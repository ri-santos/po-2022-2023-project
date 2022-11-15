package prr.app.lookup;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import prr.app.exception.UnknownClientKeyException;
import prr.core.Communication;
import prr.core.Network;
import prr.core.comparators.CompareByCommId;
import prr.core.exception.UnknownKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show communications to a client.
 */
class DoShowCommunicationsToClient extends Command<Network> {

  DoShowCommunicationsToClient(Network receiver) {
    super(Label.SHOW_COMMUNICATIONS_TO_CLIENT, receiver);
    addStringField("key", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException {
    String key = stringField("key");
    try{
        List<Communication> comms = new ArrayList<>(_receiver.showCommunicationsToClient(key));
        Collections.sort(comms, new CompareByCommId());
        _display.addAll(comms);
        _display.display();
    }catch (UnknownKeyException e){
        throw new UnknownClientKeyException(e.getKey());
    }
  }
}
