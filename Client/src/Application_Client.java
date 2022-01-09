import net.Client;
import view.FriendListFrame;
import view.LoginFrame;

public class Application_Client {
    public static void main(String[] args){
        //new LoginFrame();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginFrame();
            }
        });
    }
}
