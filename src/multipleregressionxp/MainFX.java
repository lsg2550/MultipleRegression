package multipleregressionxp;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.operators.OLS;

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

        //Button StackPane
        StackPane btnContainer = new StackPane();
        btnContainer.setAlignment(Pos.CENTER);

        //Button
        Button startBtn = new Button("Start");

        //Bar Chart
        Axis xAxisB = new CategoryAxis();
        xAxisB.setLabel("Range");
        Axis yAxisB = new NumberAxis();
        yAxisB.setLabel("Frequency");

        BarChart<String, Integer> histogram = new BarChart<>(xAxisB, yAxisB);
        histogram.setTitle("SSR Histogram");

        XYChart.Series histoSeries = new XYChart.Series<>();
        histoSeries.setName("SSR Range Frequency");

        //Line Chart
        Axis xAxisL = new CategoryAxis();
        xAxisL.setLabel("OLS Function");
        Axis yAxisL = new NumberAxis();
        yAxisL.setLabel("SSR Range");

        LineChart<OLS, Double> lineChart = new LineChart<>(xAxisL, yAxisL);
        lineChart.setTitle("SSR X Function");

        XYChart.Series lineSeries = new XYChart.Series<>();
        lineSeries.setName("SSR");

        //Children
        root.setCenter(main);
        root.setBottom(btnContainer);
        main.getChildren().addAll(histogram, lineChart);
        btnContainer.getChildren().add(startBtn);

        //Handlers
        startBtn.setOnAction(e -> {
            XP.run();

            //Get Data For Histogram
            XP.getHistogramForGraph().forEach((t, u) -> {
                if (u == null) {
                    histoSeries.getData().add(new XYChart.Data<>(t, 0));
                } else {
                    histoSeries.getData().add(new XYChart.Data<>(t, u));
                }
            });
            histogram.getData().clear();
            histogram.getData().add(histoSeries);

            //Get Data for Line Graph
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
