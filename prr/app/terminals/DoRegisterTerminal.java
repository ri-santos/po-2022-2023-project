package prr.app.terminals;

import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("idClient", Message.clientKey());

  //invalidar   idTer invalido ou ja esta a ser utilizado


    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException {
    //FIXME implement command
    String id = stringField("id");
    String type = optionField("type");
    String idClient = stringField("idClient");

//??????????????
    try{

    

    }
    if(receiver.findClient(idClient)){
      switch(type){
        case "BASIC":
        _register.registerBasicTerminal(id,)
      }
    

  }
}
