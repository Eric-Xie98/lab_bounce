package bounce;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

public class Bouncer {

    public static final int BOUNCER_SIZE = 20;
    public static final int BOUNCER_SPEED = 16;

    double myX;
    double myY;
    int dirToggleX;
    int dirToggleY;
    Circle ball;


    public Bouncer(int x, int y, int dx, int dy, Group root){

        ball = new Circle(x, y, BOUNCER_SIZE, Color.DEEPSKYBLUE);

        dirToggleX = dx;
        dirToggleY = dy;

        myX = ball.getCenterX();
        myY = ball.getCenterY();

        root.getChildren().add(ball);


    }

    public void move(int width, int height, double elapsedTime){

        // if bound detected, bounce in said direction, flip corresponding toggle
        if((ball.getCenterX() >= width - BOUNCER_SIZE) || (ball.getCenterX() <= 0 + BOUNCER_SIZE)){
            dirToggleX *= -1;
        }
        if((ball.getCenterY() >= height - BOUNCER_SIZE) || (ball.getCenterY() <= 0 + BOUNCER_SIZE)){
            dirToggleY *= -1;
        }

        ball.setCenterX(ball.getCenterX() + BOUNCER_SPEED * elapsedTime * dirToggleX);
        ball.setCenterY(ball.getCenterY() + BOUNCER_SPEED * elapsedTime * dirToggleY);


    }


}
