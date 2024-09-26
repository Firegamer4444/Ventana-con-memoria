package dad;

import javafx.application.Application;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class VentanaConMemoriaApp extends Application {

    // view

    private Slider redSlider;
    private Slider greenSlider;
    private Slider blueSlider;

    private Label redLabel;
    private Label greenLabel;
    private Label blueLabel;

    // model

    private DoubleProperty x = new SimpleDoubleProperty();
    private DoubleProperty y = new SimpleDoubleProperty();
    private DoubleProperty width = new SimpleDoubleProperty();
    private DoubleProperty height = new SimpleDoubleProperty();

    private IntegerProperty red = new SimpleIntegerProperty();
    private IntegerProperty blue = new SimpleIntegerProperty();
    private IntegerProperty green = new SimpleIntegerProperty();


    @Override
    public void init() throws Exception {
        super.init();

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder , ".ventanaConMemoria");
        File configFile = new File(configFolder , "config.properties");

        if (configFile.exists()){
            FileInputStream fis = new FileInputStream(configFile);

            Properties props = new Properties();
            props.load(fis);

            width.set(Double.parseDouble(props.getProperty("Size.width")));
            height.set(Double.parseDouble(props.getProperty("Size.height")));
            x.set(Double.parseDouble(props.getProperty("Location.x")));
            y.set(Double.parseDouble(props.getProperty("Location.y")));
            red.set(Integer.parseInt(props.getProperty("Background.red")));
            green.set(Integer.parseInt(props.getProperty("Background.green")));
            blue.set(Integer.parseInt(props.getProperty("Background.blue")));
        }
        else {
            width.set(320);
            height.set(200);
            x.set(0);
            y.set(0);
            red.set(0);
            green.set(0);
            blue.set(0);
        }

    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        redSlider = new Slider();
        redSlider.setMin(0);
        redSlider.setMax(255);
        redSlider.setShowTickLabels(true);
        redSlider.setShowTickMarks(true);
        redSlider.setMajorTickUnit(255);
        redSlider.setMinorTickCount(5);

        redLabel = new Label("red");


        greenSlider = new Slider();
        greenSlider.setMin(0);
        greenSlider.setMax(255);
        greenSlider.setShowTickLabels(true);
        greenSlider.setShowTickMarks(true);
        greenSlider.setMajorTickUnit(255);
        greenSlider.setMinorTickCount(5);

        blueSlider = new Slider();
        blueSlider.setMin(0);
        blueSlider.setMax(255);
        blueSlider.setShowTickLabels(true);
        blueSlider.setShowTickMarks(true);
        blueSlider.setMajorTickUnit(255);
        blueSlider.setMinorTickCount(5);

        VBox root = new VBox(5 , redSlider , greenSlider , blueSlider);
        root.setFillWidth(false);
        root.setAlignment(Pos.CENTER);
        root.setBackground(Background.fill(Color.rgb(red.getValue() , green.getValue() , blue.getValue())));

        Scene scene = new Scene(root , width.getValue() , height.getValue());

        primaryStage.setX(x.getValue());
        primaryStage.setY(y.getValue());
        primaryStage.setTitle("Ventana con memoria");
        primaryStage.setScene(scene);
        primaryStage.show();

        // bindings
        x.bind(primaryStage.xProperty());
        y.bind(primaryStage.yProperty());
        width.bind(primaryStage.widthProperty());
        height.bind(primaryStage.heightProperty());

        redSlider.valueProperty().bindBidirectional(red);
        greenSlider.valueProperty().bindBidirectional(green);
        blueSlider.valueProperty().bindBidirectional(blue);

        red.addListener((o , ov , nv) -> {
            root.setBackground(Background.fill(Color.rgb(nv.intValue() , green.getValue() , blue.getValue())));
        } );

        green.addListener((o , ov , nv) -> {
            root.setBackground(Background.fill(Color.rgb(red.getValue() , nv.intValue() , blue.getValue())));
        });

        blue.addListener((o , ov , nv) -> {
            root.setBackground(Background.fill(Color.rgb(red.getValue() , green.getValue() , nv.intValue())));
        });

    }

    @Override
    public void stop() throws Exception {

        File profileFolder = new File(System.getProperty("user.home"));
        File configFolder = new File(profileFolder , ".ventanaConMemoria");
        File configFile = new File(configFolder , "config.properties");

        if (!configFolder.exists()){
            configFolder.mkdir();
        }

        FileOutputStream fos = new FileOutputStream(configFile);

        Properties prop = new Properties();
        prop.setProperty("Size.width" , "" + width.getValue());
        prop.setProperty("Size.height" , "" + height.getValue());
        prop.setProperty("Location.x" , "" + x.getValue());
        prop.setProperty("Location.y" , "" + y.getValue());
        prop.setProperty("Background.red" , "" + red.getValue());
        prop.setProperty("Background.green" , "" + green.getValue());
        prop.setProperty("Background.blue" , "" + blue.getValue());
        prop.store(fos , "Estado de la ventana");
    }

}
