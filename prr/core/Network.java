package prr.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.exception.*;


/**
* Class Network implements a store.
*/

public class Network implements Serializable {

/** 
 * Serial number for serialization 
 */
  private static final long serialVersionUID = 202208091753L;

/**
 *  Description of the Client TreeMap
 */
  private TreeMap<String, Client> _clients;

/** 
 * Description of the Terminal TreeMap
 */
  private TreeMap<String, Terminal> _terminals;

/** 
 * Description of TariffPlan TreeMap 
 */
  private HashSet<TariffPlan> _tariffPlans;


/**
 * Constructor for class Network: create TreeMaps for each attribute.
 */

  public Network(){
    _clients = new TreeMap<String, Client>();
    _terminals = new TreeMap<String, Terminal>();
    _tariffPlans = new HashSet<TariffPlan>();
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
 * Gets all existing Client.
 * 
 * @return unmodifiable collection of all the clients.
 */

  public Collection<Client> getClients(){
    return Collections.unmodifiableCollection(_clients.values());
  }


/**
 * Check if has client associated with that key.
 * 
 * @param key key of the Client.
 * @throws ClientDoesNotExistException ClientDoesNotExistException is thrown when Client does not exist.
 * @return if Client exist return it.
 */

  public Client getExistingClient(String key) throws ClientDoesNotExistException{
    Client client = getClient(key);
    if(client != null){
      return client;
    }else throw new ClientDoesNotExistException();
  }


/**
 * Register a new Client in _clients.
 *
 * @param key key of the Client.
 * @param name name of the Client.
 * @param taxNumber tax number of the Client.
 * @throws DuplicateKeyException  DuplicateKeyException is thrown when has Client with the same key code.
 */

  public void registerClient(String key, String name, int taxNumber) throws DuplicateKeyException{
    Client newClient = new Client(key, name, taxNumber);
    if (getClient(key) == null){
      _clients.put(key, newClient);
    }
    else throw new DuplicateKeyException();
  }


/**
 * Show Client associated with that key.
 *
 * @param key key of the Client.
 * @throws ClientDoesNotExistException  ClientDoesNotExistException is thrown when Client does not exist.
 * @return show Client which has that key. 
 */

 public String showClient(String key) throws ClientDoesNotExistException{
    Client client = getExistingClient(key);
    return client.toString();
  }


/**
 * Show Client's Notifications.
 *
 * @param key key of the Client.
 * @throws ClientDoesNotExistException ClientDoesNotExistException is thrown when Client does not exist.
 * @return show all Notifications related with that Client. 
 */

  public Collection<Notification> showNotifications(String key) throws ClientDoesNotExistException{
    Client client = getExistingClient(key);
    return Collections.unmodifiableCollection(client.getNotifications());
  }


/**
 * Gets the Terminal of the _terminals.
 *
 * @param id id of the Terminal.
 * @return the Terminal associated with that id.
 */

  public Terminal getTerminal(String id){
    return _terminals.get(id);
  }


/**
 * Gets all Termial.
 * 
 * @return gets all Terminal of the _terminals.
 */

  public Collection<Terminal> getTerminals(){
    return _terminals.values();
  }
  

/**
 * Show all existing Terminal.
 * 
 * @return show all Terminal of the _terminal.
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
 * @throws InvalidTerminalIdException InvalidTerminalIdException is thrown when Terminal s id is not a valid code.
 * @throws ClientDoesNotExistException ClientDoesNotExistException is thrown when Client does not exist.
 * @throws DuplicateKeyException DuplicateKeyException is thrown when has Terminal with the same key code.
 */

  public void registerTerminal(String type, String id, String clientKey) throws InvalidTerminalIdException, ClientDoesNotExistException, DuplicateKeyException{
    Client owner = getExistingClient(clientKey);
    
    if (id.length()!= 6){throw new InvalidTerminalIdException();}

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
        
      } else throw new DuplicateKeyException();
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
 * Open a Terminals menu console.
 *
 * @param id id of the Terminal.
 * @throws TerminalDoesNotExistException TerminalDoesNotExistException is thrown when that id of the Terminal does not exist.
 * @return if Terminal exists will return it.
 */

  public Terminal openTerminalMenu(String id) throws TerminalDoesNotExistException{
      return getExistingTerminal(id);
  }


/**
 * Gets existing Terminal.
 *
 * @param id id of the Terminal.
 * @throws TerminalDoesNotExistException TerminalDoesNotExistException is thrown when that id of the Terminal does not exist.
 * @return if the Terminal exists will return it.
 */

  public Terminal getExistingTerminal(String id) throws TerminalDoesNotExistException{
    Terminal terminal = getTerminal(id);
    if(terminal != null){
      return terminal;
    }else throw new TerminalDoesNotExistException(id);
  }


/**
 * Show all unused Terminal.
 *
 * @return show all Terminal witch doesnt realized any communications.
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


/**
 * Add a new friend for the Terminal.
 *
 *@param terminal id of the terminal.
 *@param friend id of the terminal friend.
 *@throws TerminalDoesNotExistException if a terminal with the specified id does not exist.
 */

  public void addFriend(String terminal, String friend) throws TerminalDoesNotExistException{
    Terminal addToTerminal = getExistingTerminal(terminal);
    Terminal friendTerminal = getExistingTerminal(friend);
    addToTerminal.addNewFriend(friendTerminal, friend);
  }


/**
 * Gets tariff plans.
 *
 * @return return all tariff plans from _tariffPlans.
 */
  
  public Collection<TariffPlan> getTariffPlans(){
    return _tariffPlans;
  }
  
}

