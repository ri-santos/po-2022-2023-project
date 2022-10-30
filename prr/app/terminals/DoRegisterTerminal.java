package prr.app.terminals;

import prr.core.exception.InvalidTerminalIdException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.DuplicateKeyException;
import prr.core.Network;
import prr.app.exception.DuplicateTerminalKeyException;
import prr.app.exception.InvalidTerminalKeyException;
import prr.app.exception.UnknownClientKeyException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Register terminal.
 */
class DoRegisterTerminal extends Command<Network> {

  DoRegisterTerminal(Network receiver) {
    super(Label.REGISTER_TERMINAL, receiver);
    addStringField("terminalId", Message.terminalKey());
    addOptionField("type", Message.terminalType(), "BASIC", "FANCY");
    addStringField("keyClient", Message.clientKey());
  }

  @Override
  protected final void execute() throws CommandException, UnknownClientKeyException, InvalidTerminalKeyException, DuplicateTerminalKeyException {

    String terminalId = stringField("terminalId");
    String type = optionField("type");
    String keyClient = stringField("keyClient");
    
    try{
      _receiver.registerTerminal(type, terminalId, keyClient);
    } catch(DuplicateKeyException e){
      throw new DuplicateTerminalKeyException(e.getKey());

    } catch(UnknownKeyException e){
      throw new UnknownClientKeyException(e.getKey());

    } catch(InvalidTerminalIdException e){
      throw new InvalidTerminalKeyException(e.getId());
    }
  }
}

