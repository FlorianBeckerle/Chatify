package chatify.application;

import java.util.TimerTask;
import chatify.application.ApplicationC;
import javafx.application.Platform;

public class chatifyTimerTask extends TimerTask{
    
    ApplicationC myApplication;
    boolean all;
    
    public chatifyTimerTask(ApplicationC myApplication, boolean onlyMessages){
        this.myApplication = myApplication;
        this.all = onlyMessages;
    }
    
    @Override
    public void run() {
           Platform.runLater(() -> {
                System.out.println("Refreshed");
                try{
                    myApplication.timerRefresh(all);
                }catch(Exception ex){
                    System.out.println("Timer fehler");
                }
           });
    }
    
}
