import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

public class FlashingText {

    private Label label;

    //Constructor
    public FlashingText(String text, int size) {
        label = new Label(text);
        label.setTextFill(Color.ORANGE);
        label.setFont(Font.font("Arial Bold", FontWeight.BOLD, size * DuckHunt.SCALE));
        label.setOpacity(1);

        // Flashing animation
        EventHandler<ActionEvent> actionEvent = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                //if the opacity of the enter label is 1, set it to 0
                //if the opacity of the enter label is 0, set it to 1
                if (label.getOpacity() == 1) {
                    label.setOpacity(0);
                } else {
                    label.setOpacity(1);
                }
            }
        };

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(500), actionEvent));
        animation.setCycleCount(Animation.INDEFINITE);
        animation.play();

        // Flashing animation alternative 2
       /*FadeTransition animation = new FadeTransition(Duration.millis(500), label);
        animation.setFromValue(1.0);
        animation.setToValue(0.0);
        animation.setCycleCount(Animation.INDEFINITE);
        animation.setAutoReverse(true);
        animation.play();*/
    }

    public Label getLabel() {
        return label;
    }


}
