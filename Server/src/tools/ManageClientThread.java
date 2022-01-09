package tools;

import java.util.HashMap;
import java.util.HashSet;

public class ManageClientThread {

    public static HashMap<Long, ServerThread> threads = new HashMap<>();

    public static HashMap<Long, ServerThread> getClientThreads(){
        return threads;
    }

    public static void addClientThread(long account, ServerThread thread){
        threads.put(account, thread);
    }

    public static ServerThread getClientThread(long account){
        return threads.get(account);
    }

    public static void removeClientThread(long account){
        threads.remove(account);
    }
}
