package prr.core;

import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;

import prr.core.exception.*;


public class Parser {
  private Network _network;

  Parser(Network network) {
    _network = network;
  }

  void parseFile(String filename) throws IOException, UnrecognizedEntryException {
    try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
      String line;
      
      while ((line = reader.readLine()) != null)
        parseLine(line);
    }
  }
  
  private void parseLine(String line) throws UnrecognizedEntryException {
    String[] components = line.split("\\|");

    switch(components[0]) {
      case "CLIENT" -> parseClient(components, line);
      case "BASIC", "FANCY" -> parseTerminal(components, line);
      case "FRIENDS" -> parseFriends(components, line);
      default -> throw new UnrecognizedEntryException("Line with wong type: " + components[0]);
    }
  }

  private void checkComponentsLength(String[] components, int expectedSize, String line) throws UnrecognizedEntryException {
    if (components.length != expectedSize)
      throw new UnrecognizedEntryException("Invalid number of fields in line: " + line);
  }
  
  // parse a client with format CLIENT|id|nome|taxId
  private void parseClient(String[] components, String line) throws UnrecognizedEntryException {
    checkComponentsLength(components, 4, line);

    try {
      int taxNumber = Integer.parseInt(components[3]);
      _network.registerClient(components[1], components[2], taxNumber);
    } catch (NumberFormatException nfe) {
      throw new UnrecognizedEntryException("Invalid number in line " + line, nfe);
    } catch (DuplicateKeyException e) {
      throw new UnrecognizedEntryException("Invalid specification in line: " + line, e);
    }
  }

  // parse a line with format terminal-type|idTerminal|idClient|state
  private void parseTerminal(String[] components, String line) throws UnrecognizedEntryException {
    checkComponentsLength(components, 4, line);
    try {
      _network.registerTerminal(components[0], components[1], components[2]);
      switch(components[3]) {
        case "SILENCE" -> (_network.getTerminal(components[1])).setOnSilent();
        case "OFF" -> (_network.getTerminal(components[1])).turnOff();
        default -> {
         if (!components[3].equals("ON"))
           throw new UnrecognizedEntryException("Invalid specification in line: " + line);
        } 
      }
    } catch (InvalidTerminalIdException e) {
      throw new UnrecognizedEntryException("Invalid specification: " + line, e);
    } catch (UnknownKeyException e) {
      throw new UnrecognizedEntryException("Invalid specification: " + line, e);
    } catch (DuplicateKeyException e) {
      throw new UnrecognizedEntryException("Invalid specification: " + line, e);
    }
  }

  //Parse a line with format FRIENDS|idTerminal|idTerminal1,...,idTerminalN
  private void parseFriends(String[] components, String line) throws UnrecognizedEntryException {
    checkComponentsLength(components, 3, line);
      
    try {
      String[] friends = components[2].split(",");
      Terminal terminalToAdd = _network.getExistingTerminal(components[1]);
      
      for (String friend : friends)
        _network.addFriend(terminalToAdd, friend);
    } catch (UnknownKeyException e) {
      throw new UnrecognizedEntryException("Some message error in line:  " + line, e);
    }
  }
}
