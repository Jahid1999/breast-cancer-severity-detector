package sample;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import mainPackage.Knn;
import mainPackage.SingleData;

public class Main extends Application {


    public void startApp (Stage primaryStage)
    {
        GridPane root = new GridPane();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(11.5, 12.5, 13.5, 14.5));
        root.setHgap(5.5);
        root.setVgap(5.5);

        root.add(new Label("BI-RADS Assessment (1-5):"), 0, 0);
        TextField bi_rads = new TextField();
        root.add(bi_rads, 1, 0);

        root.add(new Label("Age:"), 0, 1);
        TextField age = new TextField();
        root.add(age, 1, 1);

        root.add(new Label("Shape:"), 0, 2);
        ToggleGroup shapeGroup = new ToggleGroup();
        RadioButton round = new RadioButton("Round");
        RadioButton oval = new RadioButton("Oval");
        RadioButton lobular = new RadioButton("Lobular");
        RadioButton irregular = new RadioButton("Irregular");
        round.setToggleGroup(shapeGroup);
        oval.setToggleGroup(shapeGroup);
        lobular.setToggleGroup(shapeGroup);
        irregular.setToggleGroup(shapeGroup);
        root.add(round, 1, 2);
        root.add(oval, 2, 2);
        root.add(lobular, 1, 3);
        root.add(irregular, 2, 3);

        root.add(new Label("Margin:"), 0, 5);
        ToggleGroup marginGroup = new ToggleGroup();
        RadioButton circumscribed = new RadioButton("Circumscribed");
        RadioButton microlobulated = new RadioButton("Microlobulated");
        RadioButton obscured = new RadioButton("Obscured");
        RadioButton ill_defined = new RadioButton("Ill-Defined");
        RadioButton spiculated = new RadioButton("Spiculated");
        circumscribed.setToggleGroup(marginGroup);
        microlobulated.setToggleGroup(marginGroup);
        obscured.setToggleGroup(marginGroup);
        ill_defined.setToggleGroup(marginGroup);
        spiculated.setToggleGroup(marginGroup);
        root.add(circumscribed, 1, 5);
        root.add(microlobulated, 2, 5);
        root.add(obscured, 1, 6);
        root.add(ill_defined, 2, 6);
        root.add(spiculated, 1, 7);


        root.add(new Label("Density:"), 0, 9);
        ToggleGroup densityGroup = new ToggleGroup();
        RadioButton high = new RadioButton("High");
        RadioButton iso = new RadioButton("ISO");
        RadioButton low = new RadioButton("Low");
        RadioButton fat_containing = new RadioButton("Fat-Containing");
        high.setToggleGroup(densityGroup);
        iso.setToggleGroup(densityGroup);
        low.setToggleGroup(densityGroup);
        fat_containing.setToggleGroup(densityGroup);
        root.add(high, 1, 9);
        root.add(iso, 2, 9);
        root.add(low, 1, 10);
        root.add(fat_containing, 2, 10);


        Button submit = new Button("Submit");

        root.add(submit, 1, 15);
        GridPane.setHalignment(submit, HPos.CENTER);

        Knn knn = new Knn();
        knn.run();

        SingleData dataPoint = new SingleData();
        submit.setOnAction(event -> {

            //age
            dataPoint.setAge(Integer.parseInt(age.getText()));
            //bdrads
            dataPoint.setBdRads(Integer.parseInt(bi_rads.getText()));

            //shape
            if (round.isSelected())
                dataPoint.setShape(1);
            else if (oval.isSelected())
                dataPoint.setShape(2);
            else if (lobular.isSelected())
                dataPoint.setShape(3);
            else if (irregular.isSelected())
                dataPoint.setShape(4);
            else
                dataPoint.setShape(120);

            //margin
            if (circumscribed.isSelected())
                dataPoint.setMargin(1);
            else if (microlobulated.isSelected())
                dataPoint.setMargin(2);
            else if (obscured.isSelected())
                dataPoint.setMargin(3);
            else if (ill_defined.isSelected())
                dataPoint.setMargin(4);
            else if (spiculated.isSelected())
                dataPoint.setMargin(5);
            else
                dataPoint.setMargin(120);

            //density
            if (high.isSelected())
                dataPoint.setDensity(1);
            else if (iso.isSelected())
                dataPoint.setDensity(2);
            else if (low.isSelected())
                dataPoint.setDensity(3);
            else if (fat_containing.isSelected())
                dataPoint.setDensity(4);
            else
                dataPoint.setDensity(120);

            int res = knn.testSeverity(dataPoint);
            if(res == 0) {
                Alert alert =  new Alert(Alert.AlertType.INFORMATION, "Relax! It's in benign stage. No need to biopsy.", ButtonType.OK);
                alert.setHeaderText("Benign");
                alert.setTitle("Severity Level");
                alert.show();
            }
            else {
                Alert alert =  new Alert(Alert.AlertType.WARNING, "It's in malignant stage. Need to do biopsy. \nSee a Doctor as soon as possible. !", ButtonType.OK);
                alert.setHeaderText("Malignant");
                alert.setTitle("Severity Level");
                alert.show();
            }

        });

        Button measure = new Button("Show Measures");
        root.add(measure, 0, 20);
        GridPane.setHalignment(submit, HPos.RIGHT);

        measure.setOnAction(event -> {
            Alert alert =  new Alert(Alert.AlertType.INFORMATION, "Accuracy = " + String.format("%.2f", knn.getAccuracy()) + " %\n\n" +
                    "Precision   = " + String.format("%.2f", knn.getPrecision()) + " %\n\n" +
                    "Recall = " + String.format("%.2f", knn.getRecall()) + " %\n\n" +
                    "F1 Score = " + String.format("%.2f", knn.getF1Score()) + " %\n" , ButtonType.OK);
            alert.setHeaderText("Different Type of Measures");
            alert.setTitle("Measures");
            alert.show();
        });

        primaryStage.setTitle("Breast Cancer Severity Detector");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setResizable(false);
        primaryStage.show();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {

        startApp(primaryStage);
    }

    public static void main(String[] args) {

        launch(args);
    }
}