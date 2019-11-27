import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Main extends Application
{

    public static ObservableList<Node> child;
    //
    private static final String title = "JellyBeanci";
    public static final int width = 800;
    public static final int height = 800;
    private static Color backcolor = Color.rgb(51, 51, 51);
    //
    static GraphicsContext gc;
    static double angle = 0;
    static double length = width * 0.25;
    static double hue = 0;
    static int multiplier = 2;
    static Color color;
    static Point2D center = new Point2D(width / 2, height / 2);

    //
    private static Timeline update;

    @Override
    public void start(Stage stage) throws Exception
    {
        Pane root = new Pane();
        child = root.getChildren();
        Circle center = new Circle(width / 2, height / 2, 5, Color.RED);
        Canvas canvas = new Canvas(width, height);
        gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1.4);
        canvas.setLayoutX(0);
        canvas.setLayoutY(0);

        child.addAll(canvas, center);
        //
        root.setOnKeyPressed(e -> {
            switch (e.getCode())
            {
                case F1:
                {
                    //PLAY
                    update.play();
                    break;
                }
                case F2:
                {
                    //PAUSE
                    update.pause();
                    break;
                }
                case F3:
                {
                    //Show Child Count
                    System.out.println("Child Count: " + child.size());
                    break;
                }
            }
        });
        update = new Timeline(new KeyFrame(Duration.millis(16), e -> {
            //60 fps
            //System.out.println("loop test");
            drawFrame();
        }));
        update.setCycleCount(Timeline.INDEFINITE);
        update.setRate(1);
        update.setAutoReverse(false);
        //update.play(); //uncomment for play when start
        //
        stage.setTitle(title);
        stage.setResizable(false);
        stage.setScene(new Scene(root, width - 10, height - 10, backcolor));
        stage.show();
        root.requestFocus();
    }

    private static void drawFrame()
    {
        hue += 0.1;
        color = Color.hsb(hue, 1, 1);
        gc.setStroke(color);
        Point2D p1 = Utils.endPoint(center.getX(), center.getY(), angle, length);
        gc.strokeLine(center.getX(), center.getY(), p1.getX(), p1.getY());
        gc.strokeOval(p1.getX() - 3, p1.getY() - 3, 6, 6);
        Point2D p2 = Utils.endPoint(p1.getX(), p1.getY(), angle * multiplier, length * 0.5);
        gc.strokeLine(p1.getX(), p1.getY(), p2.getX(), p2.getY());
        gc.strokeOval(p2.getX() - 5, p2.getY() - 5, 10, 10);
        angle += 1;
        multiplier = (int) (angle / 360) - 3;
        if (angle > angle * 10)
        {
            angle = 0;
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}