package prr.core;

public class Notification extends DeliveryType{
  private NotificationType _type;
  private Terminal _notifyingTerminal;

  public Notification(String type, Terminal from){
    super();
    switch(type){
      case "OFFtoIDLE":
        _type = NotificationType.O2I;
        break;
      case "SILENCEtoIDLE":
        _type = NotificationType.S2I;
        break;
      case "OFFtoSILENCE":
        _type = NotificationType.O2S;
        break;
      case "BUSYtoIDLE":
        _type = NotificationType.B2I;
        break;
    }
    _notifyingTerminal = from;
  }

  public String toString(){
      return (_type + "|" + _notifyingTerminal.getId());
  }
}