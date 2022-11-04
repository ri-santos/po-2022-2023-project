package prr.app.client;

import prr.core.Network;
import prr.core.exception.UnknownKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Show the payments and debts of a client.
 */
class DoShowClientPaymentsAndDebts extends Command<Network> {

  DoShowClientPaymentsAndDebts(Network receiver) {
    super(Label.SHOW_CLIENT_BALANCE, receiver);
    addStringField("key", Message.key());
  }
  
  @Override
  protected final void execute() throws CommandException{ 
    try{
        String key = stringField("key");
        _display.popup(Message.clientPaymentsAndDebts(key, 
        Math.round(_receiver.showClientPayments(key)), Math.round(_receiver.showClientDebt(key))));
        
    }catch(UnknownKeyException e){
      throw new UnknownClientKeyException(e.getKey());}
  }
}

