import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class SelectionScreen extends Scene {


    public SelectionScreen() {
        super(new Pane());

        changeBackground();
        changeCursor();

        Pane root = (Pane) this.getRoot();

        playSound("Intro");

        //Add labels
        Label label1 = new Label("USE ARROW KEYS TO NAVIGATE");
        label1.setTextFill(Color.ORANGE);
        label1.setFont(Font.font("Arial Bold", FontWeight.BOLD, 7 * DuckHunt.SCALE));
        label1.minWidthProperty().bind(root.widthProperty());
        label1.minHeightProperty().bind(root.heightProperty());
        label1.setAlignment(Pos.TOP_CENTER);
        label1.setTranslateY(10 * DuckHunt.SCALE);

        Label label2 = new Label("PRESS ENTER TO START");
        label2.setTextFill(Color.ORANGE);
        label2.setFont(Font.font("Arial Bold", FontWeight.BOLD, 7 * DuckHunt.SCALE));
        label2.minWidthProperty().bind(root.widthProperty());
        label2.minHeightProperty().bind(root.heightProperty());
        label2.setAlignment(Pos.TOP_CENTER);
        label2.setTranslateY(20 * DuckHunt.SCALE);


        //Add labels to root
        root.getChildren().add(label1);
        root.getChildren().add(label2);

        setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case ENTER:
                    DuckHunt.setScene(new Level1());
                    break;

                case ESCAPE:
                    DuckHunt.setScene(new TitleScreen());
                    break;

                case LEFT:
                    if (DuckHunt.currentBackground == 1)
                        DuckHunt.currentBackground = 6;
                    else
                        DuckHunt.currentBackground--;
                    break;
                case RIGHT:
                    if (DuckHunt.currentBackground == 6)
                        DuckHunt.currentBackground = 1;
                    else
                        DuckHunt.currentBackground++;
                    break;
                case DOWN:
                    if (DuckHunt.currentCursor == 1)
                        DuckHunt.currentCursor = 7;
                    else
                        DuckHunt.currentCursor--;
                    break;
                case UP:
                    if (DuckHunt.currentCursor == 7)
                        DuckHunt.currentCursor = 1;
                    else
                        DuckHunt.currentCursor++;
                    break;
            }

            //if key is left or right, change background
            if (event.getCode() == KeyCode.LEFT || event.getCode() == KeyCode.RIGHT)
                changeBackground();

                //if key is up or down, change cursor
            else if (event.getCode() == KeyCode.UP || event.getCode() == KeyCode.DOWN)
                changeCursor();

        });


    }

    /***
     * Plays given sound effect
     * @param sound
     * For gun effect, use "Gunshot"
     * For duck falling, use "DuckFalls"
     */
    public void playSound(String sound) {
        Media media = new Media(getClass().getResource("assets/effects/" + sound + ".mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(DuckHunt.VOLUME);
        mediaPlayer.play();

    }

    //Change the background image
    public void changeBackground() {
        Image image = new Image("assets/background/" + DuckHunt.getCurrentBackground() + ".png");

        Pane root = (Pane) this.getRoot();

        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE, false, false, false, false))));

        root.setPrefSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE);
    }

    //Change the cursor to a crosshair
    public void changeCursor() {
        Image cursor = new Image("assets/crosshair/" + DuckHunt.getCurrentCursor() + ".png");
        this.setCursor(new ImageCursor(cursor));
    }


}
