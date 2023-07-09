import javafx.animation.*;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.ImageCursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;


public class Level1 extends Scene {

    private int currentBackground = 1;
    private int currentCursor = 1;
    private int ammoCount;
    private int duckCount;
    private int levelNumber;
    private boolean isSoundPlaying = false;
    private boolean isGameOver = false;
    private static double width = 0;
    private static double height = 0;
    private double w = 0;
    private double h = 0;

    public Level1() {
        super(new Pane());

        Pane root = (Pane) this.getRoot();

        changeBackground();
        changeCursor();




        levelNumber = 1;
        duckCount = 1;
        ammoCount = 3 * duckCount;

        Duck duck = new Duck();
        ((Pane) getRoot()).getChildren().add(duck.getDuckView());

        //place duck in the center of the screen
        duck.getDuckView().setTranslateX(10);
        duck.getDuckView().setTranslateY(30);

        duck.startFlyAnimation();

        //Change foreground image
        Image image = new Image("assets/foreground/" + DuckHunt.currentBackground + ".png");
        BackgroundImage background = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, true, true));

        // Create a region to hold the background image
        Region backgroundRegion = new Region();
        backgroundRegion.setBackground(new Background(background));

        // Bind the size of the background region to the size of the root pane
        backgroundRegion.prefWidthProperty().bind(DuckHunt.stage.widthProperty());
        backgroundRegion.prefHeightProperty().bind(DuckHunt.stage.heightProperty());

        backgroundRegion.toFront();

        root.getChildren().add(backgroundRegion);




        //Add ammo count label to the right top corner of the scene
        Label ammoText = new Label("Ammo Left: " + ammoCount);
        ammoText.setFont(Font.font("Arial Bold", FontWeight.BOLD, 7 * DuckHunt.SCALE));
        ammoText.setTextFill(Color.ORANGE);
        ammoText.minWidthProperty().bind(root.widthProperty());
        ammoText.minHeightProperty().bind(root.heightProperty());
        ammoText.setAlignment(Pos.TOP_RIGHT);

        //Add level number text to the center top  of the scene
        Label levelText = new Label("Level " + levelNumber + "/6");
        levelText.setFont(Font.font("Arial Bold", FontWeight.BOLD, 7 * DuckHunt.SCALE));
        levelText.setTextFill(Color.ORANGE);
        levelText.minWidthProperty().bind(root.widthProperty());
        levelText.minHeightProperty().bind(root.heightProperty());
        levelText.setAlignment(Pos.TOP_CENTER);


        root.getChildren().add(ammoText);
        root.getChildren().add(levelText);

        this.setOnMouseClicked(event -> {
                    //Don't shoot before the gunshot sound is done playing or if there is no ammo left or if the ducks are already shot
                    if (ammoCount == 0 || duckCount == 0)
                        return;

                    playSound("Gunshot");

                    //Reduce ammo count
                    ammoCount--;

                    //update ammo count
                    ammoText.setText("Ammo Left: " + ammoCount);


                    double mouseX = event.getX();
                    double mouseY = event.getY();


                    double w = duck.getDuckView().getImage().getWidth();
                    double h = duck.getDuckView().getImage().getHeight();



                    //If the mouse is in the same position as the duck, the duck is shot
                    if (mouseX >= duck.getDuckView().getLayoutX() - w && mouseX <= duck.getDuckView().getLayoutX() + w && mouseY >= duck.getDuckView().getLayoutY() - h && mouseY <= duck.getDuckView().getLayoutY() + h) {
                        duck.stopFlyAnimation();
                        duck.getDuckView().setImage(new Image("assets/duck_black/7.png"));

                        playSound("DuckFalls");
                        TranslateTransition transition = new TranslateTransition(Duration.seconds(2), duck.getDuckView());
                        transition.setToY(this.getHeight()); // Set the target Y position (the bottom of the screen)
                        transition.play(); // Start the animation
                        duck.getDuckView().setImage(new Image("assets/duck_black/8.png"));
                        //decrement duck count
                        duckCount--;

                        //If there are no more ducks, level complete
                        if (duckCount == 0) {
                            playSound("LevelCompleted");
                            levelNumber++;

                            Font textFont = Font.font("Arial Bold", FontWeight.BOLD, 15 * DuckHunt.SCALE);

                            Text gameOverText = new Text("YOU WIN!");
                            gameOverText.setFont(textFont);
                            gameOverText.setFill(Color.ORANGE);

                            //Center the label
                            gameOverText.setLayoutX(this.getWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2);
                            gameOverText.setLayoutY(this.getHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2);

                            root.getChildren().add(gameOverText);

                            //Add "Play Again" text to the scene
                            Text nextLevel = new Text("Press ENTER to play next level");
                            nextLevel.setFont(textFont);
                            nextLevel.setFill(Color.ORANGE);

                            //Center the label
                            nextLevel.setLayoutX(this.getWidth() / 2 - nextLevel.getLayoutBounds().getWidth() / 2);
                            nextLevel.setLayoutY(this.getHeight() / 2 - nextLevel.getLayoutBounds().getHeight() / 2 + 20 * DuckHunt.SCALE);

                            root.getChildren().add(nextLevel);


                            //Make the text blink
                            EventHandler<ActionEvent> blinkEvent = new EventHandler<ActionEvent>() {
                                public void handle(ActionEvent e) {
                                    //if the opacity of the enter label is 1, set it to 0
                                    //if the opacity of the enter label is 0, set it to 1
                                    if (nextLevel.getOpacity() == 1) {
                                        nextLevel.setOpacity(0);
                                    } else {
                                        nextLevel.setOpacity(1);
                                    }
                                }
                            };

                            Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), blinkEvent));
                            animation.setCycleCount(Animation.INDEFINITE);
                            animation.play();

                        }


                        //fallingAnimation.start();
                    }
            //If ammo count is 0 while there are still ducks, game over
            if (ammoCount == 0 && duckCount > 0) {

                playSound("GameOver");

                Font textFont = Font.font("Arial Bold", FontWeight.BOLD, 15 * DuckHunt.SCALE);

                Text gameOverText = new Text("GAME OVER!");
                gameOverText.setFont(textFont);
                gameOverText.setFill(Color.ORANGE);

                //Center the label
                gameOverText.setLayoutX(this.getWidth() / 2 - gameOverText.getLayoutBounds().getWidth() / 2);
                gameOverText.setLayoutY(this.getHeight() / 2 - gameOverText.getLayoutBounds().getHeight() / 2);

                root.getChildren().add(gameOverText);

                //Add "Play Again" text to the scene
                Text playAgain = new Text("Press ENTER to play again");
                playAgain.setFont(textFont);
                playAgain.setFill(Color.ORANGE);

                //Center the label
                playAgain.setLayoutX(this.getWidth() / 2 - playAgain.getLayoutBounds().getWidth() / 2);
                playAgain.setLayoutY(this.getHeight() / 2 - playAgain.getLayoutBounds().getHeight() / 2 + 20* DuckHunt.SCALE);

                root.getChildren().add(playAgain);

                //Add "Quit" text to the scene
                Text quit = new Text("Press ESC to exit");
                quit.setFont(textFont);
                quit.setFill(Color.ORANGE);

                //Center the label
                quit.setLayoutX(this.getWidth() / 2 - quit.getLayoutBounds().getWidth() / 2);
                quit.setLayoutY(this.getHeight() / 2 - quit.getLayoutBounds().getHeight() / 2 + 40 * DuckHunt.SCALE);

                root.getChildren().add(quit);


                //Make the text blink
                EventHandler<ActionEvent> blinkEvent = new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent e)
                    {
                        //if the opacity of the enter label is 1, set it to 0
                        //if the opacity of the enter label is 0, set it to 1
                        if (playAgain.getOpacity() == 1) {
                            playAgain.setOpacity(0);
                            quit.setOpacity(0);
                        } else {
                            playAgain.setOpacity(1);
                            quit.setOpacity(1);
                        }
                    }
                };

                Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), blinkEvent));
                animation.setCycleCount(Animation.INDEFINITE);
                animation.play();

                isGameOver = true;

            }

        });

        //If game is over, pressing ENTER will restart the game from first level
        this.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ESCAPE) {
                //Stop the application
                System.exit(0);
            }
            else if (e.getCode() == KeyCode.ENTER) {
                //Stop the application
                DuckHunt.stage.setScene(new Level1());
            }
        });

               

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


    //Create a new Text object
    public Text createText(String text) {
        Text newText = new Text(text);
        newText.setFont(Font.font("Arial Bold", FontWeight.BOLD, 15 * DuckHunt.SCALE));
        newText.setFill(Color.ORANGE);
        return newText;
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

        isSoundPlaying = mediaPlayer.getStatus().equals(MediaPlayer.Status.PLAYING);


    }


}
