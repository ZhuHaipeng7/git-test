package controller;

import dao.MessageDao;
import model.Message;
import service.MessageService;

import java.util.ArrayList;

public class MessageController {
    public void addMessage(Message message){
        MessageService service = new MessageService();
        service.addMessage(message);
    }

    public ArrayList<Message> getMessages(long account1, long account2){
        MessageService service = new MessageService();
        return service.getMessages(account1, account2);
    }
}
