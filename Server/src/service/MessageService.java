package service;

import dao.MessageDao;
import model.Message;

import java.util.ArrayList;

public class MessageService {
    public void addMessage(Message message){
        MessageDao dao = new MessageDao();
        dao.addMessage(message);
    }

    public ArrayList<Message> getMessages(long account1, long account2){
        MessageDao dao = new MessageDao();
        return dao.getMessages(account1, account2);
    }
}
