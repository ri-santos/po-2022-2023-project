package prr.core;

public class Notification {
    private NotificationType _type;
    private Terminal _notifyingTerminal;

    public Notification(NotificationType type){
        _type = type;
    }

    public String toString(){}
}
