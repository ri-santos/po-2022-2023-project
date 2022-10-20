package prr.app.client;

import prr.core.Network;

import prr.core.exception.*;

import javax.sound.midi.Receiver;

import prr.app.exception.DuplicateClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register new client.
 */
class DoRegisterClient extends Command<Network> {

  DoRegisterClient(Network receiver) {
    super(Label.REGISTER_CLIENT, receiver);
    addStringField("key", Message.key());
    addStringField("name", Message.name());
    addIntegerField("taxNumber", Message.taxId());
  }
  
  @Override
  protected final void execute() throws CommandException, DuplicateClientKeyException{
    String key = stringField("key");
    String name = stringField("name");
    int taxNum = integerField("taxNumber");

    try{
    _receiver.registerClient(key, name, taxNum);
    }catch (DuplicateKeyException e){
      throw new DuplicateClientKeyException(key);
    }
  }
}
