package prr.core;

import java.io.Serializable;
import java.util.HashSet;


// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Abstract terminal.
 */
abstract public class Terminal implements Serializable /* FIXME maybe addd more interfaces */{

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private String _id;
  private Client _owner;
  private String _modeString;
  private double _debt;
  private double _payments;
  private TerminalMode _mode;
  private HashSet<Terminal> _friends;
  private HashSet<Client> _toNotify;
  
  
  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  public Terminal(String id, Client owner){
    _id = id;
    _owner = owner;
    _mode = TerminalMode.ON;
    _modeString = "Idle";
    _debt = 0;
    _payments = 0;
    _friends = new HashSet<Terminal>();
    _toNotify = new HashSet<Client>();
  }

  public String getId(){
    return _id;
  }

  /**
   * Checks if this terminal can end the current interactive communication.
   *
   * @return true if this terminal is busy (i.e., it has an active interactive communication) and
   *          it was the originator of this communication.
   **/
  public boolean canEndCurrentCommunication() {
    // FIXME add implementation code
    return false;
  }
  
  /**
   * Checks if this terminal can start a new communication.
   *
   * @return true if this terminal is neither off neither busy, false otherwise.
   **/
  public boolean canStartCommunication() {
    // FIXME add implementation code
    return false;
  }
}
