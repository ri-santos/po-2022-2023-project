package prr.core;

import java.io.Serializable;
import java.util.HashSet;

import java.util.Collection;
import java.util.HashMap;
import java.io.IOException;

import prr.core.exception.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private HashMap<String, Client> _clients;
  private HashMap<String, Terminal> _terminals;
  private HashSet<TariffPlan> _tariffPlans;


  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  public Network(){
    _clients = new HashMap<String, Client>();
    _terminals = new HashMap<String, Terminal>();
    _tariffPlans = new HashSet<TariffPlan>();
  }

  /**
   * Read text input file and create corresponding domain entities.
   * 
   * @param filename name of the text input file
   * @throws UnrecognizedEntryException if some entry is not correct
   * @throws IOException if there is an IO erro while processing the text file
   */
  void importFile(String filename) throws UnrecognizedEntryException, IOException /* FIXME maybe other exceptions */  {
    //FIXME implement method
  }

  public void registerClient(String key, String name, int taxNumber) throws DuplicateKeyException{
    Client newClient = new Client(key, name, taxNumber);
    if (getClient(key) == null){
      _clients.put(key, newClient);
    }
    else throw new DuplicateKeyException(key);
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

  public void registerFancyTerminal(String id, String clientKey){
    Client owner = getClient(clientKey);
    Terminal newTerminal = new FancyTerminal(id, owner);
    _terminals.put(id, newTerminal);
    owner.addClientTerminal(newTerminal);
  }

  private Client getClient(String key){
    return _clients.get(key);
  }

  public Collection<Client> getClients(){
    return _clients.values();
  }

  public HashSet<String> showAllClients(){
    HashSet<String> clients = new HashSet<String>();
    for (Client client : getClients()){
      clients.add(client.toString());
    }
    return clients;
  }

  public String showClient(String key){
    return getClient(key).toString();
  }

  public Terminal getTerminal(String id){
    return _terminals.get(id);
  }

  public Collection<Terminal> getTerminals(){
    return _terminals.values();
  }

  public Collection<TariffPlan> getTariffPlans(){
    return _tariffPlans;
  }

  


}




