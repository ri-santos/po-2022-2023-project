package prr.core;

import java.io.Serializable;
import java.util.HashSet;
import java.io.IOException;
import prr.core.exception.UnrecognizedEntryException;
import prr.app.exception.*;

// FIXME add more import if needed (cannot import from pt.tecnico or prr.app)

/**
 * Class Store implements a store.
 */
public class Network implements Serializable {

  /** Serial number for serialization. */
  private static final long serialVersionUID = 202208091753L;
  private HashSet<Client> _clients;
  private HashSet<Terminal> _terminals;
  private HashSet<TariffPlan> _tariffPlans;


  // FIXME define attributes
  // FIXME define contructor(s)
  // FIXME define methods
  
  public Network(){
    _clients = new HashSet<Client>();
    _terminals = new HashSet<Terminal>();
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

  public void registerClient(String key, String name, int taxNumber) throws DuplicateClientKeyException{
    Client newClient = new Client(key, name, taxNumber);
    if (!this.findClient(key)){
      System.out.println("added");
      _clients.add(newClient);
    }
    else{throw new DuplicateClientKeyException(key);}
  }

  public void registerBasicTerminal(String id, Client owner){
    Terminal newTerminal = new BasicTerminal(id, owner);
    _terminals.add(newTerminal);
    owner.addClientTerminal(newTerminal);
  }

  public void registerFancyTerminal(String id, Client owner){
    Terminal newTerminal = new FancyTerminal(id, owner);
    _terminals.add(newTerminal);
    owner.addClientTerminal(newTerminal);
  }

  public HashSet<Client> getClients(){
    return _clients;
  }

  public boolean hasClient(Client client){
    return _clients.contains(client);
  }

  public boolean findClient(String key){
    for (Client client : _clients){
      System.out.println(client.getKey());
      if (client.getKey().equals(key)){
        System.out.println("same key");
        return true;
      }
    }
    return false;
  }
/*
  public Client returnClient(String key){
      for (Client client : _clients){
        if (client.getKey() == key){
          return client;
      }
    }
  }

*/

  public HashSet<Terminal> getTerminals(){
    return _terminals;
  }

  public HashSet<TariffPlan> getTariffPlans(){
    return _tariffPlans;
  }

}



