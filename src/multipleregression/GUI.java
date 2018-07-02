package multipleregression;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.chart.Axis;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import utils.io.Read;

/**
 *
 * @author Luis
 */
public class GUI extends Application {

    @Override
    public void start(Stage primaryStage) {
        gui(primaryStage);
    }

    private void gui(Stage primaryStage) {
        //BorderPane
        BorderPane root = new BorderPane();

        //Main HBox
        HBox main = new HBox(10);
        main.setAlignment(Pos.CENTER);

        //Button HBox
        HBox btnContainer = new HBox();
        btnContainer.setAlignment(Pos.CENTER);

        //Dataset Path Location TextField
        TextField datasetTextField = new TextField();

        //Button
        Button startBtn = new Button("Start");

        //Bar Chart
        Axis xAxisB = new CategoryAxis();
        xAxisB.setLabel("SSR Range");
        Axis yAxisB = new NumberAxis();
        yAxisB.setLabel("Frequency");

        BarChart<String, Double> histogram = new BarChart<>(xAxisB, yAxisB);
        histogram.setTitle("SSR Histogram");

        //Line Chart
        Axis xAxisL = new CategoryAxis();
        xAxisL.setLabel("Function");
        Axis yAxisL = new NumberAxis(0, 1.0, 0.1);
        yAxisL.setLabel("SSR Range");

        LineChart<String, Double> lineChart = new LineChart<>(xAxisL, yAxisL);
        lineChart.setTitle("SSR x Function");

        //Children
        root.setCenter(main);
        root.setBottom(btnContainer);
        main.getChildren().addAll(histogram, lineChart);
        btnContainer.getChildren().addAll(startBtn, datasetTextField);

        //Handlers
        startBtn.setOnAction(e -> {
            new Thread(() -> {
                //Test if path input is valid before doing anything
                if (!Read.isPathValid(datasetTextField.getText())) {
                    return;
                }

                //Create Series
                XYChart.Series histoSeries = new XYChart.Series<>();
                histoSeries.setName("SSR Range Frequency");
                XYChart.Series lineSeries = new XYChart.Series<>();
                lineSeries.setName("SSR");

                //Run
                MLR.run(datasetTextField.getText());

                //Get Data For Histogram
                MLR.histogramForGUI().forEach((t, u) -> {
                    XYChart.Data<String, Double> histogramChartData;
                    if (u == null) {
                        histoSeries.getData().add(new XYChart.Data<>(t, 0));
                    } else {
                        histogramChartData = new XYChart.Data<>(t, u);
                        //histogramChartData.setNode(new HoveredThresholdNode(u));
                        histoSeries.getData().add(histogramChartData);
                    }
                });

                //Get Data for Line Graph
                MLR.linegraphForGUI().forEach((t, u) -> {
                    XYChart.Data<String, Double> lineChartData = new XYChart.Data<>(t.getFunction().getDependentVariable().toString(), u);
                    //xyD.setNode(new HoveredThresholdNode(u));
                    lineSeries.getData().add(lineChartData);
                });

                //Clear Previous Series & Add New/Updated Series
                Platform.runLater(() -> {
                    histogram.getData().clear();
                    lineChart.getData().clear();
                    histogram.getData().add(histoSeries);
                    lineChart.getData().add(lineSeries);
                });

            }).start();
        });

        //Scene
        Scene scene = new Scene(root, 800, 600);

        //Stage
        primaryStage.setTitle("Multiple Linear Regression");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> {
            System.exit(0);
        });
    }

    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Modified Version of https://gist.github.com/jewelsea/4681797 a node which
     * displays a value on click, but is otherwise empty
     *
     * @author jewelsea
     */
    private class HoveredThresholdNode extends StackPane {

        HoveredThresholdNode(Double value) {
            setPrefSize(15, 15);

            final DecimalFormat df = new DecimalFormat("#.###");
            df.setRoundingMode(RoundingMode.CEILING);

            final Label label = new Label(df.format(value));
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

            setOnMouseClicked((MouseEvent mouseEvent) -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited((MouseEvent mouseEvent) -> {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
                toBack();
            });
        }

        HoveredThresholdNode(Integer value) {
            setPrefSize(15, 15);

            final Label label = new Label(value + "");
            label.getStyleClass().addAll("default-color0", "chart-line-symbol", "chart-series-line");
            label.setStyle("-fx-font-size: 20; -fx-font-weight: bold;");
            label.setMinSize(Label.USE_PREF_SIZE, Label.USE_PREF_SIZE);

            setOnMouseClicked((MouseEvent mouseEvent) -> {
                getChildren().setAll(label);
                setCursor(Cursor.NONE);
                toFront();
            });
            setOnMouseExited((MouseEvent mouseEvent) -> {
                getChildren().clear();
                setCursor(Cursor.CROSSHAIR);
            });
        }
    }
}
