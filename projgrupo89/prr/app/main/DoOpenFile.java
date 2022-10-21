package prr.app.main;

import java.io.IOException;

import prr.core.NetworkManager;
import prr.app.exception.FileOpenFailedException;
import prr.core.exception.UnavailableFileException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * Command to open a file.
 */
class DoOpenFile extends Command<NetworkManager> {

  DoOpenFile(NetworkManager receiver) {
    super(Label.OPEN_FILE, receiver);
    addStringField("filename", Message.openFile());
  }
  
  @Override
  protected final void execute() throws CommandException {
   String filename = stringField("filename");
    try {
    _receiver.load(filename);
    }catch(ClassNotFoundException|IOException|UnavailableFileException e){
      throw new FileOpenFailedException(e);
    }
  }
}
