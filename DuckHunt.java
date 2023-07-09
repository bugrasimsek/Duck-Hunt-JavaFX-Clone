import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * DuckHunt class is responsible for controlling the scene changes, keeping constants about game etc.
 */

public class DuckHunt extends Application {

    public static final double SCALE = 3;
    public static final double VOLUME = 0.5; // in range of 0 to 1
    private static double width;
    private static double height;
    public static int currentBackground = 1;
    public static int currentCursor = 1;
    public static Stage stage;


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = new Stage();

        //Title of game window border
        stage.setTitle("HUBBM Duck Hunt");

        //Favicon for game window
        Image icon = new Image("assets/favicon/1.png");
        stage.getIcons().add(icon);

        //Set the scene to the title screen
        stage.setScene(new TitleScreen());

        //Loop the title music
        playSound("Title", true);




        stage.show();

    }

    //set the scene to the selection screen
    public static void setScene(Scene scene) {
        stage.setScene(scene);
    }

    /***
     * Plays given sound effect
     * @param sound
     * For gun effect, use "Gunshot"
     * For duck falling, use "DuckFalls"
     */
    public void playSound(String sound, boolean shouldLoop) {
        Media media = new Media(getClass().getResource("assets/effects/" + sound + ".mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setVolume(DuckHunt.VOLUME);
        mediaPlayer.play();
    }


    //Change the background image
    public static void changeBackground() {
        Image image = new Image("assets/background/" + DuckHunt.getCurrentBackground() + ".png");

        Pane root = (Pane) stage.getScene().getRoot();

        root.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE, false, false, false, false))));

        root.setPrefSize(image.getWidth() * DuckHunt.SCALE, image.getHeight() * DuckHunt.SCALE);
    }

    //Change the cursor to a crosshair
    public static void changeCursor() {
        Image cursor = new Image("assets/crosshair/" + DuckHunt.getCurrentCursor() + ".png");
        stage.getScene().setCursor(new ImageCursor(cursor));
    }


    public static void setHeight(double height) {
        DuckHunt.height = height;
    }

    public static void setWidth(double width) {
        DuckHunt.width = width;
    }

    public static double getWidth() {
        return width;
    }

    public static double getHeight() {
        return height;
    }

    public static int getCurrentBackground() {
        return currentBackground;
    }

    public static int getCurrentCursor() {
        return currentCursor;
    }

    public void setCurrentBackground(int currentBackground) {
        this.currentBackground = currentBackground;
    }

    public void setCurrentCursor(int currentCursor) {
        this.currentCursor = currentCursor;
    }
}
