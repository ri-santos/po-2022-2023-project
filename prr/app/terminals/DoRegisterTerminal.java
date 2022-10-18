package prr.app.terminals;

import prr.core.exception.InvalidTerminalIdException;
import prr.core.exception.ClientDoesNotExistException;
import prr.core.exception.DuplicateKeyException;
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
    addStringField("terminalId", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("keyClient", Message.clientKey());

  //invalidar   idTer invalido ou ja esta a ser utilizado


    //FIXME add command fields
  }

  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException, InvalidTerminalKeyException, DuplicateTerminalKeyException {
    //FIXME implement command
    String terminalId = stringField("terminalId");
    String type = optionField("type");
    String keyClient = stringField("keyClient");
    
    try{
      switch(type){
        case "BASIC":
          _receiver.registerBasicTerminal(terminalId, keyClient);
          break;
        case "FANCY":
          _receiver.registerFancyTerminal(terminalId, keyClient);
          break;
      }
    } catch(DuplicateKeyException e){
      throw new DuplicateTerminalKeyException(e.getKey());

    } catch(ClientDoesNotExistException e){
      throw new UnknownClientKeyException(e.getKey());

    } catch(InvalidTerminalIdException e){
      throw new InvalidTerminalKeyException(e.getId());
    }
  }
}

