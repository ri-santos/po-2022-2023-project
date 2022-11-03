package prr.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.IOException;

import prr.core.TerminalState.TerminalMode;
import prr.core.exception.*;


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


  private TreeMap<Integer, Communication> _communications;

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

  public List<Client> getClients(){
    return new ArrayList<>(_clients.values());
  }

  public List<Client> getClientsWithDebts(){
    List<Client> clientsDebts = new ArrayList<>();
    for (Client client : _clients.values()){
      if(client.getDebts() != 0){clientsDebts.add(client);}
    }
    return clientsDebts;
  }

  public List<Client> getClientsWithoutDebts(){
    List<Client> clientsDebts = new ArrayList<>();
    for (Client client : _clients.values()){
      if(client.getDebts() == 0){clientsDebts.add(client);}
    }
    return clientsDebts;
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

  public Collection<Notification> showNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableCollection(client.getNotifications());
  }

  public boolean enableClientNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    if (client.getRecieveNotificationsState() == true){
      return false;
    }
    client.turnNotificationsOn();
    return true;
  }

  public boolean disableClientNotifications(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    if (client.getRecieveNotificationsState() == false){
      return false;
    }
    client.turnNotificationsOff();
    return true;
  }

  public void showClientPaymentsAndDebts(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
  }

  public Collection<Communication> showCommunicationsFromClient(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableCollection(client.getMadeCommunications());
  }

  public Collection<Communication> showCommunicationsToClient(String key) throws UnknownKeyException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableCollection(client.getReceivedCommunications());
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
 * Show all unused Terminal.
 *
 * @return string of the Terminals that haven't made or recieved any communications.
 */

  public Collection<String> showAllUnusedTerminals(){
    Collection<String> unusedTerminals = new ArrayList<String>();
    for (Terminal terminal : getTerminals()){
      if (terminal.numberOfCommunications() == 0){
        unusedTerminals.add(terminal.toString());
      }
    }
    return unusedTerminals;
  }

  public Collection<String> showTerminalsPositiveBalance(){
    Collection<String> positiveTerminals = new ArrayList<String>();
    for (Terminal terminal : getTerminals()){
      if (terminal.getBalance() > 0){
        positiveTerminals.add(terminal.toString());
      }
    }
    return positiveTerminals;
  }

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
    terminal.addNewFriend(friendTerminal, friend);
  }


  public void removeFriend(Terminal terminal, String friend) throws UnknownKeyException{
    getExistingTerminal(friend);
    terminal.removeFriend(friend);
  }


  public boolean setTerminalIdle(Terminal terminal){
    if(terminal.getMode() == "IDLE"){
      return false;
    }
      terminal.setOnIdle();
    return true;
  }


  public boolean setTerminalOff(Terminal terminal){
    if(terminal.getMode() == "OFF"){
      return false;
    }
      terminal.turnOff();
    return true;
  }


  public boolean setTerminalSilence(Terminal terminal){
    if(terminal.getMode() == "SILENCE"){
      return false;
    }
      terminal.setOnSilent();
    return true;
  }

  public Collection<Communication> getCommunications(){
    return _communications.values();
  }

  public Collection<Communication> showAllCommunications(){
    return Collections.unmodifiableCollection(getCommunications());
  }

  /*public boolean isCommunicationFrom(int id , Terminal from){


  }*/
  public void sendTextCommunication(Terminal from, String to, String message) throws UnknownKeyException, DestinationTerminalIsOffException{
    Terminal destTerminal = getExistingTerminal(to);
    int id = _communications.size() + 1;
    Communication textComm = new TextCommunication(message, id, from, destTerminal);
    from.makeSMS(destTerminal, textComm);
    _communications.put(id, textComm);
  }

  public void makeInteractiveCommunication (Terminal from, String to, String type) throws UnknownKeyException, UnsupportedAtDestinationException, UnsupportedAtOriginException,
  DestinationTerminalIsBusyException, DestinationTerminalIsOffException, DestinationTerminalisSilentException{
    Terminal destTerminal = getExistingTerminal(to);
    Communication newComm;
    int id = _communications.size() + 1;
    switch(type){
      case "VIDEO":
      newComm = new VideoCommunication(id, from, destTerminal);
      from.makeVideoCall(destTerminal, newComm);
      
      case "VOICE":
      newComm = new VoiceCommunication(id, from, destTerminal);
      from.makeVoiceCall(destTerminal, newComm);
    }
  }

  public String showOngoingCommunication(Terminal from){
		return from.getOngoingCommunication().toString();
  }

	public boolean isTerminalFrom(Terminal terminal){
		return (terminal.getOngoingCommunication().getIdSender() == terminal.getId()
		&& terminal.hasOngoingCommunication());
	}

	public double endInteractiveCommunication(Terminal terminal, int duration){
    Communication communication = terminal.getOngoingCommunication();
    String destTerminalId = communication.getIdReceiver();
    Terminal destTerminal = getTerminal(destTerminalId);
    destTerminal.endOngoingCommunication();
    terminal.endOngoingCommunication();
    communication.setSize(duration);
    return communication.computeCost();
	}
}
