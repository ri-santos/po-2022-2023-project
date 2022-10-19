package prr.app.main;

import java.io.IOException;

import prr.core.NetworkManager;
import prr.core.exception.MissingFileAssociationException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
//FIXME add more imports if needed

/**
 * Command to save a file.
 */
class DoSaveFile extends Command<NetworkManager> {

  DoSaveFile(NetworkManager receiver) {
    super(Label.SAVE_FILE, receiver);
    if (!_receiver.hasFile()){
      addStringField("newfilename", Message.newSaveAs());
    }
  }
  
  @Override
  protected final void execute() {
    //FIXME implement command and create a local Form
    
    if (!_receiver.hasFile()){
      String filename = stringField("newfilename");
      try{
        _receiver.saveAs(filename);
      }catch(IOException|MissingFileAssociationException e){
        _display.popup(Message.fileNotFound(filename));
      }
    }
    else{
      try{
        _receiver.save();

      }catch(IOException|MissingFileAssociationException e){
        _display.popup(Message.fileNotFound());
      }
    }
  }
}
