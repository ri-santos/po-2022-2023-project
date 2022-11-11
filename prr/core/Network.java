package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.IOException;

import prr.core.exception.*;
import prr.core.filters.ClientFilter;
import prr.core.filters.TerminalFilter;


/**
* Class Network stores clients and their associated terminals and communications.
*/

public class Network implements Serializable {

/** 
 * Serial number for serialization 
 */
  private static final long serialVersionUID = 202208091753L;

/**
 * Client HashMap that stores the registered Clients
 */
  private HashMap<String, Client> _clients;

/** 
 * Terminal TreeMap that stores the registered Terminals
 */
  private TreeMap<String, Terminal> _terminals;

/** 
 * Communication TreeMap that stores the registered Communications
 */
  private TreeMap<Integer, Communication> _communications;

/** 
 * _payments attribute stores all Clients payments(global payments)
 */
  private double _payments;

/** 
 * _debts attribute stores all Clients debts(global debts)
 */
  private double _debts;


/**
 * Constructor for class Network: initiates each atribute's Collection.
 */

  public Network(){
    _clients = new HashMap<String, Client>();
    _terminals = new TreeMap<String, Terminal>();
    _communications = new TreeMap<Integer, Communication>();
  }


/**
 * Read text input file and create corresponding domain entities.
 * 
 * @param filename name of the text input file
 * @throws UnrecognizedEntryException if some entry is not correct
 * @throws IOException if there is an IO erro while processing the text file
 */

  void importFile(String filename) throws UnrecognizedEntryException, IOException, ImportFileException  {
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }


/**
 * Gets the global payments.
 *
 * @return the global payments associated with the Network.
 */

  public double getGlobalPayments(){
    _payments = 0;
    _clients.values().forEach(c -> _payments += c.getPayments());
    return _payments;
  }


/**
 * Gets the global debts.
 *
 * @return the global debts associated with the Network.
 */

  public double getGlobalDebts(){
    _debts = 0;
    _clients.values().forEach(c -> _debts += c.getDebts());
    return _debts;
  }


/**
 * Gets the Client associated with a specific key.
 *
 * @param key key of the Client.
 * @return the Client associated with that key.
 */

  private Client getClient(String key){
    return _clients.get(key);
  }


/**
 * Gets all existing Clients.
 * 
 * @return unmodifiable collection of all the clients.
 */

  public Collection<Client> getClients(){
    return Collections.unmodifiableCollection(_clients.values());
  }


/**
 * Applies the selected filter on the clients list.
 * 
 * @return filtered list.
 */

  public List<Client> getClientsFiltered(ClientFilter f){
    return Collections.unmodifiableList(f.apply(_clients.values()));
  }


/**
 * Check if  the Network has a client associated with a key.
 * 
 * @param key key of the Client.
 * @throws UnknownKeyException if Client does not exist.
 * @return if Client exist return it.
 */

  public Client getExistingClient(String key) throws UnknownKeyException{
    Client client = getClient(key);
    if(client != null){
      return client;
    }else throw new UnknownKeyException(key);
  }


/**
 * Register a new Client in _clients.
 *
 * @param key key of the Client.
 * @param name name of the Client.
 * @param taxNumber tax number of the Client.
 * @throws DuplicateKeyException if there's already a Client with the same key code.
 */

  public void registerClient(String key, String name, int taxNumber) throws DuplicateKeyException{
    Client newClient = new Client(key, name, taxNumber);
    if (getClient(key) == null){
      _clients.put(key, newClient);
    }
    else throw new DuplicateKeyException(key);
  }


/**
 * Show Client associated with a key.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return show Client with that key. 
 */

  public String showClient(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return client.toString();
  }

/**
 * Show a Client's Notifications.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return show all Notifications related with that Client. 
 */

  public List<DeliveryType> showNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    List<DeliveryType> inbox = new ArrayList<>(client.getInbox());
    client.clearInbox();
    return Collections.unmodifiableList(inbox);
  }


/**
 * Turn Client's Notifications on.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return a boolean related with the Notifications state associated with that Client. 
 */

  public boolean enableClientNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    if (client.getRecieveNotificationsState() == true){
      return false;
    }
    client.turnNotificationsOn();
    return true;
  }


/**
 * Turn Client's Notifications off.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return a boolean related with the Notifications state associated with that Client. 
 */

  public boolean disableClientNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    if (client.getRecieveNotificationsState() == false){
      return false;
    }
    client.turnNotificationsOff();
    return true;
  }


/**
 * Show payments associated with that Client.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return show a payments related with that Client key. 
 */

  public double showClientPayments(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return client.getPayments();
  }


/**
 * Show debts associated with that Client.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return show a debts related with that Client key. 
 */

  public double showClientDebt(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return client.getDebts();
  }


/**
 * Show made Communications from Client associated with that key.
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return a unmodifiable List with all made Communications from that Client.
 */

  public List<Communication> showCommunicationsFromClient(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableList(client.getMadeCommunications());
  }


/**
 * Show Communications that Client with that key received.        
 *
 * @param key key of the Client.
 * @throws UnknownKeyException if the Client does not exist.
 * @return a unmodifiable List with all Communications that Client received.
 */

  public Collection<Communication> showCommunicationsToClient(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableList(client.getReceivedCommunications());
  }


/**
 * Gets a Terminal from  _terminals.
 *
 * @param id id of the Terminal.
 * @return the Terminal associated with that id.
 */
  public Terminal getTerminal(String id){
    return _terminals.get(id);
  }


/**
 * Gets all Termials.
 * 
 * @return gets all Terminal of the _terminals.
 */

  public Collection<Terminal> getTerminals(){
    return _terminals.values();
  }
  

/**
 * Show all existing Terminals.
 * 
 * @return unmodifiable list of the Terminals registered in the Network.
 */

  public Collection<Terminal> showAllTerminals(){
    return Collections.unmodifiableCollection(getTerminals());
  }


/**
 * Register a new Terminal in _terminals.
 *
 * @param type type of the Terminal.
 * @param id id of the Terminal.
 * @param clientKey key of the Client.
 * @throws InvalidTerminalIdException if Terminal s id is not a valid code.
 * @throws UnknownKeyException if Client does not exist.
 * @throws DuplicateKeyException if there is a Terminal with the same key code.
 */

  public void registerTerminal(String type, String id, String clientKey) throws InvalidTerminalIdException, UnknownKeyException, DuplicateKeyException{
    Client owner = getExistingClient(clientKey);
    
    if (!id.matches("[0-9]+") | id.length() != 6){throw new InvalidTerminalIdException(id);}

    else if (getTerminal(id) == null){
      
      switch(type){
        case "BASIC":
        Terminal newBasicTerminal = new BasicTerminal(id, owner);
        addTerminal(id, newBasicTerminal, owner);
        break;
        
        case "FANCY":
        Terminal newFancyTerminal = new FancyTerminal(id, owner);
        addTerminal(id, newFancyTerminal, owner);
        break;
        }
        
      } else throw new DuplicateKeyException(id);
  }
   
  
/**
 * Add a new Terminal in _terminals.
 *
 * @param id id of the new Terminal.
 * @param newTerminal new Terminal.
 * @param owner Client who will be associated with that Terminal.
 */

  public void addTerminal(String id, Terminal newTerminal, Client owner){
    _terminals.put(id, newTerminal);
    owner.addClientTerminal(newTerminal, id);
  }


/**
 * Open a Terminal's console menu.
 *
 * @param id id of the Terminal.
 * @throws UnknownKeyException UnknownKeyException is thrown when that id of the Terminal does not exist.
 * @return if Terminal exists will return it.
 */

  public Terminal openTerminalMenu(String id) throws UnknownKeyException{
      return getExistingTerminal(id);
  }


/**
 * Gets an existing Terminal.
 *
 * @param id id of the Terminal.
 * @throws UnknownKeyException UnknownKeyException is thrown when that id of the Terminal does not exist.
 * @return if the Terminal exists will return it.
 */

  public Terminal getExistingTerminal(String id) throws UnknownKeyException{
    Terminal terminal = getTerminal(id);
    if(terminal != null){
      return terminal;
    }else throw new UnknownKeyException(id);
  }


/**
 * Apllies the selected filter on the terminals list.
 * 
 * @return the filtered list.
 */

  public Collection<Terminal> showTerminalsFiltered(TerminalFilter f){
    return Collections.unmodifiableCollection(f.apply(getTerminals()));
  }

  
/**
 * Show Terminal s balance.
 *
 * @return a Terminal balance.
 */

  public double showTerminalBalance(Terminal terminal){
    return terminal.getBalance();
  }


/**
 * Add a new friend for a Terminal.
 *
 * @param terminal id of the terminal.
 * @param friend id of the terminal friend.
 * @throws UnknownKeyException if a terminal with the specified id does not exist.
 */

  public void addFriend(Terminal terminal, String friend) throws UnknownKeyException{
    Terminal friendTerminal = getExistingTerminal(friend);
    if(friendTerminal != terminal){
      terminal.addNewFriend(friendTerminal, friend);
    }
  }


/**
 * Remove a friend form a Terminal s freinds.
 *
 * @param terminal id of the terminal.
 * @param friend id of the terminal friend.
 * @throws UnknownKeyException if a terminal with the specified id does not exist.
 */

  public void removeFriend(Terminal terminal, String friend) throws UnknownKeyException{
    getExistingTerminal(friend);
    terminal.removeFriend(friend);
  }


/**
 * Set Terminal a IDLE mode.
 *
 * @param terminal id of the terminal.
 * @return a boolean related with that Terminal mode.
 */

  public boolean setTerminalIdle(Terminal terminal){
    if(terminal.getMode().toString() == "IDLE"){
      return false;
    }
      terminal.setOnIdle();
    return true;
  }


/**
 * Set for Terminal a OFF mode.
 *
 * @param terminal id of the terminal.
 * @return a boolean related with that Terminal mode.
 */

  public boolean setTerminalOff(Terminal terminal){
    if(terminal.getMode().toString() == "OFF"){
      return false;
    }
      terminal.turnOff();
    return true;
  }


/**
 * Set for Terminal a SILENCE mode.
 *
 * @param terminal id of the terminal.
 * @return a boolean related with that Terminal mode.
 */

  public boolean setTerminalSilence(Terminal terminal){
    if(terminal.getMode().toString() == "SILENCE"){
      return false;
    }
      terminal.setOnSilent();
    return true;
  }


/**
 * Gets all existing Communication.
 * 
 * @return collection of all the Communication.
 */

  public Collection<Communication> getCommunications(){
    return _communications.values();
  }


/**
 * Show all existing Communication.
 * 
 * @return unmodifiable list of the Communication registered in the Network.
 */

  public Collection<Communication> showAllCommunications(){
    return Collections.unmodifiableCollection(getCommunications());
  }



  /*public boolean isCommunicationFrom(int id , Terminal from){


  }*/


/**
 * Send a Text Communication between two Terminals.
 *
 * @param from Terminal that sends the Communication.
 * @param to id of the Terminal that will received that Communication.
 * @throws UnknownKeyException if a Terminal with the specified id does not exist.
 * @throws DestinationTerminalIsOffException if a destination Terminal is in a OFF mode,its not enable to receive that Communication.
 */

  public void sendTextCommunication(Terminal from, String to, String message) throws UnknownKeyException, DestinationTerminalIsOffException{
    Terminal destTerminal = getExistingTerminal(to);
    int id = _communications.size() + 1;
    Communication textComm = new TextCommunication(message, id, from, destTerminal);
    from.makeSMS(destTerminal, textComm);
    _communications.put(id, textComm);
  }


/**
 * Make a Interactive Communication between two Terminals.
 *
 * @param from Terminal that sends the Communication.
 * @param to id of the Terminal that will received that Communication.
 * @param type type of the Interactive Communication.
 * @throws UnknownKeyException if a Terminal with the specified id does not exist.
 * @throws UnsupportedAtDestinationException if a destination Terminal doesnt support that type of Communication.
 * @throws UnsupportedAtOriginException if a origin Terminal doesnt support that type of Communication.
 * @throws DestinationTerminalIsBusyException if a destination Terminal in a BUSY mode.
 * @throws DestinationTerminalIsOffException if a destination Terminal in a OFF mode.
 * @throws DestinationTerminalisSilentException if a destination Terminal in a SILENCE mode.
 */

  public void makeInteractiveCommunication (Terminal from, String to, String type) throws UnknownKeyException, UnsupportedAtDestinationException, UnsupportedAtOriginException,
  DestinationTerminalIsBusyException, DestinationTerminalIsOffException, DestinationTerminalisSilentException{
    Terminal destTerminal = getExistingTerminal(to);
    if (destTerminal != from){
        Communication newComm;
        int id = _communications.size() + 1;
        destTerminal.getMode().acceptInteractive();
        switch(type){
            case "VIDEO":
            newComm = new VideoCommunication(id, from, destTerminal);
            from.makeVideoCall(destTerminal, newComm);
            _communications.put(id, newComm);
            break;
            
            case "VOICE":
            newComm = new VoiceCommunication(id, from, destTerminal);
            from.makeVoiceCall(destTerminal, newComm);
            _communications.put(id, newComm);
            break;
        }
    }
    else{throw new DestinationTerminalIsBusyException();}
  }


/**
 * Show Terminals ongoing Communication.
 *
 * @param from Terminal that made the Communication.
 * @return ongoing Communication associated with that Terminal.
 */

  public Communication showOngoingCommunication(Terminal from){
		return from.getOngoingCommunication();
  }


/**
 * Check if the Terminal starts the ongoing Communication.
 *
 * @param from Terminal.
 * @return if Terminal starts that Communication return true.
 */

	public boolean isTerminalFrom(Terminal terminal){
		return (terminal.getOngoingCommunication().getIdSender() == terminal.getId()
		&& terminal.hasOngoingCommunication());
	}


/**
 * End an Interactive Communication ,safe that duration and calculate that cost.
 *
 * @param terminal Terminal that starts a Interactive Communication.
 * @return a Communication s cost.
 */

	public double endInteractiveCommunication(Terminal terminal, int duration){
		Communication communication = terminal.getOngoingCommunication();
		String destTerminalId = communication.getIdReceiver();
		Terminal destTerminal = getTerminal(destTerminalId);
		communication.setSize(duration);
		destTerminal.endOngoingCommunicationTo();
		terminal.endOngoingCommunicationFrom();
		communication.setnotOngoing();
		return communication.computeCost();
	}


/**
 * Add a Client to notify.
 *
 * @param from Terminal that wants start a Interactive Communication.
 * @param id id of the Terminal that will received a Interactive Communication from Terminal from if had sucess.
 */

	public void addNotifyClient(Terminal from, String id){
		Terminal destTerminal = getTerminal(id);
		destTerminal.addNotifyClient(from.getOwner());
	}

}