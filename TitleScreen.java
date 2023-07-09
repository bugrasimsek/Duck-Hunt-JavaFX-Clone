import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.Scene;
import javafx.scene.image.Image;

/**
 *
 * TitleScreen class is responsible for displaying the title screen of the game.
 * There's a title screen music playing
 * If user presses enter,  game starts
 */
public class TitleScreen extends Scene {
    public TitleScreen() {
        super(new Pane());

        Image image = new Image("assets/welcome/1.png");

        Pane root = (Pane) this.getRoot();

        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE, false, false, false, false))));

        root.setPrefSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE);

        //set width and height of the scene
        DuckHunt.setHeight(image.getHeight() * DuckHunt.SCALE);
        DuckHunt.setWidth(image.getWidth() * DuckHunt.SCALE);


        FlashingText enter = new FlashingText("PRESS ENTER TO PLAY", 15);
        FlashingText exit = new FlashingText("PRESS ESC TO EXIT", 15);

        root.getChildren().add(enter.getLabel());
        root.getChildren().add(exit.getLabel());


        //bind the x and y position of the enter label to the width and height of the scene
        enter.getLabel().minWidthProperty().bind(root.widthProperty());
        enter.getLabel().minHeightProperty().bind(root.heightProperty());
        enter.getLabel().setAlignment(Pos.CENTER);


        //bind the x and y position of the exit label to the width and height of the scene
        exit.getLabel().minWidthProperty().bind(root.widthProperty());
        exit.getLabel().minHeightProperty().bind(root.heightProperty());
        exit.getLabel().setAlignment(Pos.CENTER);

        //move the exit and enter label down
        enter.getLabel().setTranslateY(50);
        exit.getLabel().setTranslateY(100);

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    DuckHunt.setScene(new SelectionScreen());
                    break;
                case ESCAPE:
                    System.exit(0);
                    break;
            }
        });


    }

}


