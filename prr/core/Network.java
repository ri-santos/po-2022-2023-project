package prr.core;

import java.io.Serializable;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;
import java.io.IOException;

import prr.core.exception.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private TreeMap<String, Client> _clients;
  private TreeMap<String, Terminal> _terminals;
  private HashSet<TariffPlan> _tariffPlans;


  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
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
    //FIXME implement method
    Parser parser = new Parser(this);
    parser.parseFile(filename);
  }

  public void registerClient(String key, String name, int taxNumber) throws DuplicateKeyException{
    Client newClient = new Client(key, name, taxNumber);
    if (getClient(key) == null){
      _clients.put(key, newClient);
    }
    else throw new DuplicateKeyException(key);
  }

  public void registerTerminal(String type, String id, String clientKey) throws InvalidTerminalIdException, ClientDoesNotExistException, DuplicateKeyException{
    switch(type){
      case "BASIC":
      registerBasicTerminal(id, clientKey);
      break;
      
      case "FANCY":
      registerFancyTerminal(id, clientKey);
      break;
    }
  }
  
  public void registerBasicTerminal(String id, String clientKey) throws InvalidTerminalIdException, ClientDoesNotExistException, DuplicateKeyException{
    Client owner = getClient(clientKey);
    if (owner == null){
      throw new ClientDoesNotExistException(clientKey);
    }
    else{
      if (id.length()!= 6){
        throw new InvalidTerminalIdException(id);
      }
      else{
        if (getTerminal(id) == null){
          Terminal newTerminal = new BasicTerminal(id, owner);
          _terminals.put(id, newTerminal);
          owner.addClientTerminal(newTerminal);
        }
        else throw new DuplicateKeyException(id);
      }
    }
  }

  public void registerFancyTerminal(String id, String clientKey) throws InvalidTerminalIdException, ClientDoesNotExistException, DuplicateKeyException{
    Client owner = getClient(clientKey);
     if (owner == null){
      throw new ClientDoesNotExistException(clientKey);
    }
    else{
      if (id.length()!= 6){
        throw new InvalidTerminalIdException(id);
      }
      else{
        if (getTerminal(id) == null){
          Terminal newTerminal = new FancyTerminal(id, owner);
          _terminals.put(id, newTerminal);
          owner.addClientTerminal(newTerminal);
        }
        else throw new DuplicateKeyException(id);
      }
    }
  }

  private Client getClient(String key){
    return _clients.get(key);
  }

  public Collection<Client> getClients(){
    Collection<Client> client = new ArrayList<Client>(_clients.values());
    return client;
  }

  public HashSet<String> showAllClients(){
    HashSet<String> clients = new HashSet<String>();
    for (Client client : getClients()){
      clients.add(client.toString());
    }
    return clients;
  }

  public String showClient(String key) throws ClientDoesNotExistException{
    Client client = getClient(key);
    if (client != null){
      return client.toString();
    }
    else throw new ClientDoesNotExistException(key);
  }

  public Terminal getTerminal(String id){
    return _terminals.get(id);
  }

  public Collection<Terminal> getTerminals(){
    Collection<Terminal> terminals = new ArrayList<Terminal>(_terminals.values());
    return terminals;
  }

  public Terminal openTerminalMenu(String id) throws TerminalDoesNotExistException{
      return getExistingTerminal(id);
  }

  public HashSet<String> showAllTerminals(){
    HashSet<String> terminals = new HashSet<String>();
    for (Terminal terminal : getTerminals()){
      terminals.add(terminal.toString());
    }
    return terminals;
  }

  public Collection<TariffPlan> getTariffPlans(){
    return _tariffPlans;
  }

  public Collection<Terminal> showAllUnusedTerminals(){
    Collection<Terminal> unusedTerminals = new ArrayList<Terminal>();
    for (Terminal terminal : getTerminals()){
      if (terminal.numberOfCommunications() == 0){
        unusedTerminals.add(terminal);
      }
    }
    return unusedTerminals;
  }

  public Terminal getExistingTerminal(String id) throws TerminalDoesNotExistException{
    Terminal terminal = getTerminal(id);
    if(terminal != null){
      return terminal;
    }else throw new TerminalDoesNotExistException(id);
  }

  public void addFriend(String terminal, String friend) throws TerminalDoesNotExistException{
    Terminal addToTerminal = getExistingTerminal(terminal);
    Terminal friendTerminal = getExistingTerminal(friend);
    addToTerminal.addNewFriend(friendTerminal, friend);
  }

}




