package prr.app.terminal;

import prr.core.Network;
import prr.core.Terminal;
import prr.core.exception.DestinationTerminalIsBusyException;
import prr.core.exception.DestinationTerminalIsOffException;
import prr.core.exception.DestinationTerminalisSilentException;
import prr.core.exception.UnknownKeyException;
import prr.core.exception.UnsupportedAtDestinationException;
import prr.core.exception.UnsupportedAtOriginException;
import prr.app.exception.UnknownTerminalKeyException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.CommandException;
//FIXME add more imports if needed

/**
 * Command for starting communication.
 */
class DoStartInteractiveCommunication extends TerminalCommand {

  DoStartInteractiveCommunication(Network context, Terminal terminal) {
    super(Label.START_INTERACTIVE_COMMUNICATION, context, terminal, receiver -> receiver.canStartCommunication());
    addStringField("id", Message.terminalKey());
    addOptionField("type", Message.commType(), "VIDEO", "VOICE");
  }
  
  @Override
  protected final void execute() throws CommandException, UnknownTerminalKeyException {
    String toId = stringField("id");
    String type = optionField("type");
    try{
        _network.makeInteractiveCommunication(_receiver, toId, type);
      }catch (UnknownKeyException e){
        throw new UnknownTerminalKeyException(e.getKey());
      }catch (UnsupportedAtOriginException e){
        _display.popup(Message.unsupportedAtOrigin(_receiver.getId(), type));
      }catch (UnsupportedAtDestinationException e){
        _display.popup(Message.unsupportedAtDestination(toId, type));
      }catch (DestinationTerminalIsOffException e){
        _network.addNotifyClient(_receiver, toId);
        _display.popup(Message.destinationIsOff(toId));
      }catch (DestinationTerminalIsBusyException e){
        _network.addNotifyClient(_receiver, toId);
        _display.popup(Message.destinationIsBusy(toId));
      }catch (DestinationTerminalisSilentException e){
        _network.addNotifyClient(_receiver, toId);
        _display.popup(Message.destinationIsSilent(toId));
      }
  }
}