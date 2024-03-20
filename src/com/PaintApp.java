package com;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import javafx.geometry.Pos;

/**
 * This class represents a simple painting application that allows users to draw
 * circles and squares on a canvas. Users can choose the shape, location, size,
 * and color of the shapes to be drawn. It provides options to draw and remove
 * shapes from the canvas.
 *
 * @author Your Name
 * @version 1.0
 */
public class PaintApp extends Application {

    private Canvas canvas;
    private GraphicsContext gc;
    private ArrayList<GeometricObject> shapes = new ArrayList<>();
    private Color currentColor = Color.BLACK;
    private boolean drawCircle = true;

    private double startX, startY;
    private double prevX, prevY;

    private TextField locationXField;
    private TextField locationYField;
    private TextField sizeField;
    private ColorPicker colorPicker;
    private Label errorLabel;

    @Override
    public void start(Stage primaryStage) {
        canvas = new Canvas(800, 600);
        gc = canvas.getGraphicsContext2D();
        BorderPane root = new BorderPane();
        root.setCenter(canvas);

        RadioButton circleButton = new RadioButton("Circle");
        RadioButton squareButton = new RadioButton("Square");

        ToggleGroup shapeToggleGroup = new ToggleGroup();
        circleButton.setToggleGroup(shapeToggleGroup);
        squareButton.setToggleGroup(shapeToggleGroup);
        circleButton.setSelected(true);
        shapeToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue == circleButton) {
                drawCircle = true;
            } else if (newValue == squareButton) {
                drawCircle = false;
            }
        });

        locationXField = new TextField();
        locationXField.setPromptText("X");
        locationXField.setPrefWidth(50);

        locationYField = new TextField();
        locationYField.setPromptText("Y");
        locationYField.setPrefWidth(50);

        sizeField = new TextField();
        sizeField.setPromptText("Size");
        sizeField.setPrefWidth(50);

        colorPicker = new ColorPicker();
        colorPicker.setPrefWidth(100);
        Label locationLabel = new Label("Location:");
        Label sizeLabel = new Label("Size:");
        Label colorLabel = new Label("Color:");
        Button drawButton = new Button("Draw");
        drawButton.setOnAction(event -> drawShape());

        Button unDrawButton = new Button("UnDraw");
        unDrawButton.setOnAction(event -> UnDraw());

        HBox controlBox = new HBox(10);
        controlBox.setStyle("-fx-background-color: lightgray;");
        controlBox.getChildren().addAll(
                circleButton, squareButton,
                locationLabel, locationXField, locationYField,
                sizeLabel, sizeField, colorLabel, colorPicker, drawButton, unDrawButton
        );
        controlBox.setPadding(new Insets(10));

        errorLabel = new Label("No error");
        errorLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
        HBox errorBox = new HBox(errorLabel);
        errorBox.setStyle("-fx-background-color: gray;");
        errorBox.setPadding(new Insets(10, 0, 0, 0));
        errorBox.setAlignment(Pos.CENTER);

        BorderPane mainLayout = new BorderPane();
        mainLayout.setBottom(errorBox);
        mainLayout.setCenter(controlBox);
        root.setBottom(mainLayout);

        Scene scene = new Scene(root, 800, 700);
        primaryStage.setTitle("Assignment 8");
        primaryStage.setScene(scene);
        primaryStage.show();

        canvas.setOnMousePressed(event -> {
            startX = event.getX();
            startY = event.getY();
            prevX = startX;
            prevY = startY;
        });

        canvas.setOnMouseDragged(event -> {
            try {
                double currentX = event.getX();
                double currentY = event.getY();

                double size = Double.parseDouble(sizeField.getText());

                if (size <= 0) {
                    throw new NumberFormatException("Size must be positive");
                }

                if (currentX < 0 || currentX > canvas.getWidth() || currentY < 0 || currentY > canvas.getHeight()) {
                    throw new IllegalArgumentException("Location out of range");
                }
                currentColor = colorPicker.getValue();
                if (drawCircle) {
                    double radius = size / 2;
                    double centerX = (currentX + prevX) / 2;
                    double centerY = (currentY + prevY) / 2;
                    addShape(new Circle(radius, centerX, centerY, currentColor));
                } else {
                    double minX = Math.min(prevX, currentX);
                    double minY = Math.min(prevY, currentY);
                    addShape(new Square(size, minX, minY, currentColor));
                }

                prevX = currentX;
                prevY = currentY;
                errorLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
                errorLabel.setText("No error");
            } catch (NumberFormatException e) {
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                errorLabel.setText("Invalid input: " + e.getMessage());
            } catch (IllegalArgumentException e) {
                errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                errorLabel.setText("Error: " + e.getMessage());
            }
        });

        canvas.setOnMouseReleased(event -> {
            prevX = 0;
            prevY = 0;
        });
    }

    /**
     * Adds a GeometricObject to the list of shapes and redraws the canvas.
     *
     * @param shape The GeometricObject to add
     */
    private void addShape(GeometricObject shape) {
        shapes.add(shape);
        redrawCanvas();
    }

    /**
     * Removes the last added shape from the list and redraws the canvas.
     */
    private void UnDraw() {
        if (!shapes.isEmpty()) {
            shapes.remove(shapes.size() - 1);
            redrawCanvas();
        }
    }

    /**
     * Redraws the canvas by clearing it and drawing all shapes in the list.
     */
    private void redrawCanvas() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (GeometricObject shape : shapes) {
            shape.draw(gc);
        }
    }

    /**
     * Draws a shape on the canvas based on user input, handles error cases, and
     * updates the error label accordingly.
     */
    private void drawShape() {
        String error = null;
        try {
            double size = Double.parseDouble(sizeField.getText());
            double x, y;

            // Check if the fields are empty before parsing
            if (locationXField.getText().isEmpty() || locationYField.getText().isEmpty()) {
                throw new NumberFormatException("Empty field");
            }

            x = Double.parseDouble(locationXField.getText());
            y = Double.parseDouble(locationYField.getText());

            if (x < 0 || x > canvas.getWidth() || y < 0 || y > canvas.getHeight()) {
                error = "Location out of range";
            }
            currentColor = colorPicker.getValue();
            if (drawCircle) {
                addShape(new Circle(size, x, y, currentColor));
            } else {
                addShape(new Square(size, x, y, currentColor));
            }
        } catch (NumberFormatException e) {
            error = "Invalid input";
        }
        if (error != null) {
            errorLabel.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
            errorLabel.setText("Error: " + error + " in " + (locationXField.isEditable() ? "X" : "Y") + " field");
        } else {
            errorLabel.setStyle("-fx-text-fill: green; -fx-font-weight: bold;");
            errorLabel.setText("No error");
        }
    }

    /**
     * Launches the JavaFX application.
     *
     * @param args Command-line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
