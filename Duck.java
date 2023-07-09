import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Duck {
    private ImageView[] duckFrames;
    private ImageView duckView;
    private AnimationTimer flyAnimation;
    private AnimationTimer fallingAnimation;

    public Duck() {
        duckFrames = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            String framePath = "assets/duck_black/" + (i + 4) + ".png";
            duckFrames[i] = new ImageView(new Image(framePath));
        }

        duckView = duckFrames[0]; // Initialize with the first frame
        duckView.setLayoutX(20);
        duckView.setLayoutY(75);
        duckView.setSmooth(true);
        duckView.setScaleX(DuckHunt.SCALE);
        duckView.setScaleY(DuckHunt.SCALE);
        duckView.setPreserveRatio(true);

        flyAnimation = new AnimationTimer() {
            private int frameCount = 0;
            private double duckX = 5;
            private double duckY = 5;
            private double duckSpeedX = 3;
            private double duckSpeedY = 1;

            private Image frame1 = duckFrames[0].getImage();
            private Image frame2 = duckFrames[1].getImage();
            private Image frame3 = duckFrames[2].getImage();

            @Override
            public void handle(long now) {
                double sceneWidth = duckView.getScene().getWidth();
                double sceneHeight = duckView.getScene().getHeight();

                // Change duck's direction when it hits the edge of the scene
                if (duckX + duckView.getImage().getWidth() >= sceneWidth || duckX <= 0) {
                    duckSpeedX *= -1;
                    // Mirror the duck's image
                    duckView.setScaleX(duckView.getScaleX() * -1);
                }

                // Update duck's position
                duckX += duckSpeedX;
                duckView.setLayoutX(duckX);

                // Animate duck's flight
                frameCount++;
                if (frameCount % 10 == 0) {
                    if (frameCount % 30 == 0) {
                        duckView.setImage(frame1);
                    } else if (frameCount % 20 == 0) {
                        duckView.setImage(frame2);
                    } else {
                        duckView.setImage(frame3);
                    }
                }
            }
        };

        // Create animation timer
        fallingAnimation = new AnimationTimer() {

            String framePath = "assets/duck_black/8.png";
            private Image fallingFrame = new Image(framePath);
            double duckY = duckView.getY();

            @Override
            public void handle(long now) {
                duckView.setImage(fallingFrame);
                duckY += 2;
                duckView.setLayoutY(duckY);
                //duckY = duckView.getLayoutY();
            }
        };

    }


    public ImageView getDuckView() {
        return duckView;
    }

    public void startFlyAnimation() {

        flyAnimation.start();
    }

    //start falling animation
    public void startFallingAnimation() {
        fallingAnimation.start();
    }
    public void stopFlyAnimation() {
        flyAnimation.stop();
    }



}