/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatifyanimation;

import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author fb
 */
public class chatifyAnimationV2 extends Application {
    
    private int width = 420;
    private int height = 570;
    
    
/*    @FXML
    private VBox registerPane;
*/  
    @Override
    public void start(Stage primaryStage) {
        
        
        //Vbox root = registerPane;
        VBox root = new VBox();
        //root.getChildren().add();
        
        
        
        
        //Background animations
        
        //LINES
        Line li1 = new Line(width, 110, 0, height-45);
        li1.setStroke(Color.rgb(255, 69, 0));
        
        PathTransition path1 = new PathTransition();
        path1.setPath(new Line(width/2, height/2+126, width/2, height/2+100));
        path1.setNode(li1);
        path1.setDuration(Duration.millis(7000));
        path1.setCycleCount(Timeline.INDEFINITE);
        path1.setAutoReverse(true);
        path1.play(); 
        
        root.getChildren().add(li1);
        
        
        Line li2 = new Line(width, 100, 0, height-50);
        li2.setStroke(Color.rgb(255, 69, 0));
        
        PathTransition path2 = new PathTransition();
        path2.setPath(new Line(width/2, height/2+100, width/2, height/2+120));
        path2.setNode(li2);
        path2.setDuration(Duration.millis(8000));
        path2.setCycleCount(Timeline.INDEFINITE);
        path2.setAutoReverse(true);
        path2.play(); 
        
        root.getChildren().add(li2);
        
        
        //BEAMS
        Rectangle rtBeam1 = new Rectangle(width/2, height/2, width*4, 10);
        rtBeam1.setFill(Color.rgb(255, 69, 0));
        rtBeam1.setRotate(-45);
        
        PathTransition rtPath1 = new PathTransition();
        rtPath1.setPath(new Line(3*width, -3*width+200, -3*width, 3*width+200));
        rtPath1.setNode(rtBeam1);
        rtPath1.setDuration(Duration.millis(10000));
        rtPath1.setDelay(Duration.millis(Math.random()*1000+4000*4));
        rtPath1.setCycleCount(Timeline.INDEFINITE);
        rtPath1.play(); 
        
        root.getChildren().add(rtBeam1);
        
        
        Rectangle rtBeam2 = new Rectangle(width/2, height/2, width*4, 20);
        rtBeam2.setFill(Color.rgb(255, 69, 0));
        rtBeam2.setRotate(-45);
        
        PathTransition rtPath2 = new PathTransition();
        rtPath2.setPath(new Line(-3*width, 3*width+240, 3*width, -3*width+240));
        rtPath2.setNode(rtBeam2);
        rtPath2.setDuration(Duration.millis(10000));
        rtPath2.setDelay(Duration.millis(Math.random()*1000+3000*4*7));
        rtPath2.setCycleCount(Timeline.INDEFINITE);
        rtPath2.play(); 
        
        root.getChildren().add(rtBeam2);
        
        
        Rectangle rtBeam3 = new Rectangle(width/2, height/2, width*4, 15);
        rtBeam3.setFill(Color.rgb(255, 69, 0));
        rtBeam3.setRotate(-45);
        
        PathTransition rtPath3 = new PathTransition();
        rtPath3.setPath(new Line(-3*width, 3*width+270, 3*width, -3*width+270));
        rtPath3.setNode(rtBeam3);
        rtPath3.setDuration(Duration.millis(10000));
        rtPath3.setDelay(Duration.millis(Math.random()*1000+2500*4*10));
        rtPath3.setCycleCount(Timeline.INDEFINITE);
        rtPath3.play(); 
        
        root.getChildren().add(rtBeam3);
        
        
        Rectangle rtBeam4 = new Rectangle(width/2, height/2, width*4, 30);
        rtBeam4.setFill(Color.rgb(255, 69, 0));
        rtBeam4.setRotate(-45);
        
        PathTransition rtPath4 = new PathTransition();
        rtPath4.setPath(new Line(3*width, -3*width+310, -3*width, 3*width+310));
        rtPath4.setNode(rtBeam4);
        rtPath4.setDuration(Duration.millis(10000));
        rtPath4.setDelay(Duration.millis(Math.random()*1000+4080*4*8));
        rtPath4.setCycleCount(Timeline.INDEFINITE);
        rtPath4.play(); 
        
        root.getChildren().add(rtBeam4);
        
        
        Rectangle rtBeam5 = new Rectangle(width/2, height/2, width*4, 10);
        rtBeam5.setFill(Color.rgb(255, 69, 0));
        rtBeam5.setRotate(-45);
        
        PathTransition rtPath5 = new PathTransition();
        rtPath5.setPath(new Line(-3*width, 3*width+345, 3*width, -3*width+345));
        rtPath5.setNode(rtBeam5);
        rtPath5.setDuration(Duration.millis(10000));
        rtPath5.setDelay(Duration.millis(Math.random()*1000+1480*4));
        rtPath5.setCycleCount(Timeline.INDEFINITE);
        rtPath5.play(); 
        
        root.getChildren().add(rtBeam5);
        
        Rectangle rtBeam6 = new Rectangle(width/2, height/2, width*4, 17);
        rtBeam6.setFill(Color.rgb(255, 69, 0));
        rtBeam6.setRotate(-45);
        
        PathTransition rtPath6 = new PathTransition();
        rtPath6.setPath(new Line(3*width, -3*width+375, -3*width, 3*width+375));
        rtPath6.setNode(rtBeam6);
        rtPath6.setDuration(Duration.millis(10000));
        rtPath6.setDelay(Duration.millis(Math.random()*1000+5780*4*6));
        rtPath6.setCycleCount(Timeline.INDEFINITE);
        rtPath6.play(); 
        
        root.getChildren().add(rtBeam6);
        
        Rectangle rtBeam7 = new Rectangle(width/2, height/2, width*4, 24);
        rtBeam7.setFill(Color.rgb(255, 69, 0));
        rtBeam7.setRotate(-45);
        
        PathTransition rtPath7 = new PathTransition();
        rtPath7.setPath(new Line(3*width, -3*width+415, -3*width, 3*width+415));
        rtPath7.setNode(rtBeam7);
        rtPath7.setDuration(Duration.millis(10000));
        rtPath7.setDelay(Duration.millis(Math.random()*1000+4780*4*4));
        rtPath7.setCycleCount(Timeline.INDEFINITE);
        rtPath7.play(); 
        
        root.getChildren().add(rtBeam7);
        
        
        
        //Alles Anzeigen
        Scene scene = new Scene(root, width, height);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
