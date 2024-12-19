package com.java.fx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class Main extends Application {
	public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		launch();
	}

	@Override
	public void start(Stage stage) throws Exception{
		context = SpringApplication.run(Main.class);
		FXMLLoader fxml = new FXMLLoader(getClass().getResource
				("/com/java/fx/Main.fxml"));
		fxml.setControllerFactory(context::getBean);
		Scene scene = new Scene(fxml.load());
		stage.initStyle(StageStyle.TRANSPARENT);
		stage.setScene(scene);
		stage.show();

	}

}
