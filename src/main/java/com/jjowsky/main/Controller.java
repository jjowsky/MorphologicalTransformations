package com.jjowsky.main;

import com.jjowsky.transformations.Noise;
import com.jjowsky.transformations.Utils;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.Random;

public class Controller {

    @FXML
    private Pane displayPane;
    @FXML
    private ImageView mainImageView;
    @FXML
    private MenuItem loadFileMenuItem;
    @FXML
    private ComboBox optionsComboBox;
    @FXML
    private ComboBox noisesComboBox;

    private BufferedImage image;

    public void onActionLoadFileMenuItem() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image files", "*.png", "*jpg", "*bmp")
        );
        File selectedFile = fileChooser.showOpenDialog(displayPane.getScene().getWindow());
        if (selectedFile != null) {
            Image img = new Image(selectedFile.toURI().toString());
            ImageView iv = new ImageView(img);
            displayPane.getChildren().clear();
            displayPane.getChildren().add(iv);

            image = ImageIO.read(selectedFile);
        }
    }

    public void initialize() {
        optionsComboBox.getItems().clear();
        optionsComboBox.getItems().addAll("Noise");

        noisesComboBox.getItems().clear();
        noisesComboBox.getItems().addAll("Gaussian");
        noisesComboBox.setVisible(false);
    }

    public void transformOnAction() throws InterruptedException {
           int[][] pixels = Utils.convertTo2D(image);
           BufferedImage bufImg = Noise.addGaussianNoise(pixels);
           Image img = SwingFXUtils.toFXImage(bufImg, null);
           displayPane.getChildren().remove(0);
           ImageView iv = new ImageView(img);
           displayPane.getChildren().add(iv);
    }

    public void optionsOnAction() {
        switch (optionsComboBox.getValue().toString()) {
            case "Noise" : {
                noisesComboBox.setVisible(true);
            }
        }
    }

    public void noisesOnAction() {

    }
}

