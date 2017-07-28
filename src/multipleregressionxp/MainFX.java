package multipleregressionxp;

import datasets.Datasets;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

/**
 *
 * @author Luis
 */
public class MainFX extends Application {

    @Override
    public void start(Stage primaryStage) {
        //BorderPane
        BorderPane root = new BorderPane();

        //Main HBox
        HBox main = new HBox(10);
        main.setAlignment(Pos.CENTER);

        //Button HBox
        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);

        //ChoiceBox
        ChoiceBox<String> datasetChoiceBox = new ChoiceBox<>(FXCollections.observableArrayList(
                Datasets.DATASET_1, Datasets.DATASET_2, Datasets.DATASET_3,
                Datasets.DATASET_4, Datasets.DATASET_5, Datasets.DATASET_10));
        datasetChoiceBox.getSelectionModel().select(0);

        //Button
        Button startBtn = new Button("Start");

        //Bar Chart
        Axis xAxisB = new CategoryAxis();
        xAxisB.setLabel("Range");
        Axis yAxisB = new NumberAxis();
        yAxisB.setLabel("Frequency");

        BarChart<String, Integer> histogram = new BarChart<>(xAxisB, yAxisB);
        histogram.setTitle("SSR Histogram");

        //Line Chart
        Axis xAxisL = new CategoryAxis();
        xAxisL.setLabel("OLS Function");
        Axis yAxisL = new NumberAxis(0, 1.0, 0.1);
        yAxisL.setLabel("SSR Range");

        LineChart<String, Double> lineChart = new LineChart<>(xAxisL, yAxisL);
        lineChart.setTitle("SSR x Function");

        //Children
        root.setCenter(main);
        root.setBottom(btnContainer);
        main.getChildren().addAll(histogram, lineChart);
        btnContainer.getChildren().addAll(startBtn, datasetChoiceBox);

        //Handlers
        startBtn.setOnAction(e -> {
            //Clear Previous Series
            histogram.getData().clear();
            lineChart.getData().clear();

            //Create Series
            XYChart.Series histoSeries = new XYChart.Series<>();
            histoSeries.setName("SSR Range Frequency");
            XYChart.Series lineSeries = new XYChart.Series<>();
            lineSeries.setName("SSR");

            //Run
            XP.run(datasetChoiceBox.getSelectionModel().getSelectedItem());

            //Get Data For Histogram
            XP.getHistogramForGraph().forEach((t, u) -> {
                if (u == null) {
                    histoSeries.getData().add(new XYChart.Data<>(t, 0));
                } else {
                    histoSeries.getData().add(new XYChart.Data<>(t, u));
                }
            });

            //Get Data for Line Graph
            XP.getLinegraphForGraph().forEach((t, u) -> {
                lineSeries.getData().add(new XYChart.Data<>(t.getFunction().getDependentVariable().toString(), u));
            });

            //Add New/Updated Series
            histogram.getData().add(histoSeries);
            lineChart.getData().add(lineSeries);
        });

        //Scene
        Scene scene = new Scene(root, 800, 600);

        //Stage
        primaryStage.setTitle("Multiple Regression");
        //primaryStage.setResizable(false);
        //primaryStage.getIcons().add();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
