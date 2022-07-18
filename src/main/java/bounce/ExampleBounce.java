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

/**
 * An example class, separate from the basic application, that manages the game interactions.
 * 
 * @author Robert C. Duvall
 */

public class ExampleBounce {
    // many resources may be in the same shared folder
    // note, leading slash means automatically start in "src/main/resources" folder
    // note, Java always uses forward slash, "/", (even for Windows)
    public static final String RESOURCE_PATH = "/";
    public static final String BOUNCER_IMAGE = RESOURCE_PATH + "duke-seal-logo.png";
    public static final int BOUNCER_SIZE = 40;
    public static final int BOUNCER_SPEED = 16;
    public static final Paint MOVER_COLOR = Color.PLUM;
    public static final int MOVER_SIZE = 50;
    public static final int MOVER_SPEED = 8;
    public static final Paint GROWER_COLOR = Color.BISQUE;
    public static final double GROWER_RATE = 1.1;
    public static final int GROWER_SIZE = 50;
    public static final Paint HIGHLIGHT = Color.OLIVEDRAB;

    // things needed to remember during the game
    int directionToggle2 = 1;
    int layoutWidth = 0;
    int layoutHeight = 0;
    // need to keep track of these in order to manage their movement and intersections
    private Bouncer myBouncer;
    private Bouncer myBouncer2;
    private Bouncer myBouncer3;
    private Bouncer myBouncer4;
    private Rectangle myMover;
    private Rectangle myGrower;


    // Create the game's "scene": what shapes will be in the game and their starting properties
    public Scene setupGame (int width, int height, Paint background) {
        layoutWidth = width;
        layoutHeight = height;
        // create one top level collection to organize the things in the scene
        Group root = new Group();
        // make an image based on a file "resource" found using Java's classpath

        myBouncer = new Bouncer(width/2, height/2, 1, 1, root);
        myBouncer2 = new Bouncer(150, 300, -1, 1, root);
        myBouncer3 = new Bouncer(300, 200, 3, -1, root);
        myBouncer4 = new Bouncer(100, 150, -5, -5, root);

        // x and y represent the top left corner, so center it in window by offsetting by the width nd height

        // make some shapes and set their properties
        myMover = new Rectangle(width / 2 - MOVER_SIZE / 2, height / 2 - 100, MOVER_SIZE, MOVER_SIZE);
        myMover.setFill(MOVER_COLOR);
        myGrower = new Rectangle(width / 2 - GROWER_SIZE / 2, height / 2 + 50, GROWER_SIZE, GROWER_SIZE);
        myGrower.setFill(GROWER_COLOR);
        // order added to the group is the order in which they are drawn
        root.getChildren().add(myMover);
        root.getChildren().add(myGrower);
        // create a place to see the shapes
        Scene scene = new Scene(root, width, height, background);
        // respond to input
        scene.setOnKeyPressed(e -> handleKeyInput(e.getCode()));
        scene.setOnMouseClicked(e -> handleMouseInput(e.getX(), e.getY()));
        return scene;
    }

    // Change properties of shapes in small ways to animate them over time
    // Note, there are more sophisticated ways to animate shapes, but these simple ways work fine to start
    public void step (double elapsedTime) {

        // update "actors" attributes

        myBouncer.move(layoutWidth, layoutHeight, elapsedTime);
        myBouncer2.move(layoutWidth, layoutHeight, elapsedTime);
        myBouncer3.move(layoutWidth, layoutHeight, elapsedTime);
        myBouncer4.move(layoutWidth, layoutHeight, elapsedTime);

        // bouncer moves at a "constant" rate now matter how many frames are drawn per second
        // shapes move at a "variable" rate, faster for higher frame rate
        myMover.setRotate(myMover.getRotate() - 1);
        myGrower.setRotate(myGrower.getRotate() + 1);


        // check for collisions

//        if (isIntersecting(myBouncer2, myMover)) {
//            myMover.setFill(HIGHLIGHT);
//        }
//        else {
//            myMover.setFill(MOVER_COLOR);
//        }
//        if (isIntersecting(myBouncer2, myGrower)) {
//            myGrower.setScaleX(1);
//            myGrower.setScaleY(1);
//        }

    }

    // What to do each time a key is pressed
    private void handleKeyInput (KeyCode code) {
        // NOTE new Java syntax that some prefer (but watch out for the many special cases!)
        //   https://blog.jetbrains.com/idea/2019/02/java-12-and-intellij-idea/
        switch (code) {
            case RIGHT -> myMover.setX(myMover.getX() + MOVER_SPEED);
            case LEFT -> myMover.setX(myMover.getX() - MOVER_SPEED);
            case UP -> myMover.setY(myMover.getY() - MOVER_SPEED);
            case DOWN -> myMover.setY(myMover.getY() + MOVER_SPEED);
        }
        // TYPICAL way to do it, definitely more readable for longer actions
//        if (code == KeyCode.RIGHT) {
//            myMover.setX(myMover.getX() + MOVER_SPEED);
//        }
//        else if (code == KeyCode.LEFT) {
//            myMover.setX(myMover.getX() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.UP) {
//            myMover.setY(myMover.getY() - MOVER_SPEED);
//        }
//        else if (code == KeyCode.DOWN) {
//            myMover.setY(myMover.getY() + MOVER_SPEED);
//        }
    }

    // What to do each time a key is pressed
    private void handleMouseInput (double x, double y) {
        if (myGrower.contains(x, y)) {
            myGrower.setScaleX(myGrower.getScaleX() * GROWER_RATE);
            myGrower.setScaleY(myGrower.getScaleY() * GROWER_RATE);
        }
    }

    // Name for a potentially complex comparison to make code more readable
    private boolean isIntersecting (ImageView a, Rectangle b) {
        // with images can only check bounding box (as it is calculated in container with other objects)
        return b.getBoundsInParent().intersects(a.getBoundsInParent());
        // with shapes, can check precisely (in this case, it is easy because the image is circular)
//        Shape bouncerBounds = new Circle(a.getX() + a.getFitWidth() / 2,
//                                       a.getY() + a.getFitHeight() / 2,
//                                       a.getFitWidth() / 2 - BOUNCER_SIZE / 20);
//        return ! Shape.intersect(bouncerBounds, b).getBoundsInLocal().isEmpty();
    }
}
