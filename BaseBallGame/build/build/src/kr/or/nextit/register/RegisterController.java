package kr.or.nextit.register;

import kr.or.nextit.BaseBallGameProject.RegisterListener;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import javafx.scene.control.TextField;

public class RegisterController {

	@FXML
	TextField nameField;
	@FXML
	AnchorPane registerPane;
	private RegisterListener listener;

	public RegisterController() {

	}

	public RegisterController(RegisterListener listener) {
		this.listener = listener;
	}

	@FXML
	public void registerAction() {
		listener.register(nameField.getText());
		dismissScene();
	}

	@FXML
	public void cancelAction() {
		dismissScene();
	}

	private void dismissScene() {
		StackPane stackPane = (StackPane) registerPane.getParent();
		// 등록 화면 초기값
		registerPane.setTranslateY(0);
		Timeline timeline = new Timeline();
		KeyValue keyValue = new KeyValue(registerPane.translateYProperty(), registerPane.getPrefHeight());
		KeyFrame keyFrame = new KeyFrame(Duration.millis(500), new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				// 애니메이션이 종료된 후 실행
				stackPane.getChildren().remove(registerPane);
			}
		}, keyValue);
		timeline.getKeyFrames().add(keyFrame);
		timeline.play();
	}
}