package kr.or.nextit.BaseBallGameProject;

import kr.or.nextit.BaseBallGameProject.H2DB;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;

import kr.or.nextit.BaseBallGameProject.RegisterListener;
import kr.or.nextit.rank.RankModel;
import kr.or.nextit.register.RegisterController;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class BaseBallGameController implements Initializable {

	@FXML
	Group numberGroup;
	@FXML
	Label s_result;
	@FXML
	Label b_result;
	@FXML
	Button resultBtn;
	@FXML
	Label selNum;
	@FXML
	Group labelGroup;
	@FXML
	Label countLabel;
	@FXML
	ImageView underlineImg;
	@FXML
	StackPane stackPane;
	private List<Integer> userSelect;
	private List<Integer> answers;
	private Button[] btn;
	private List<Label> labels;
	private int strikeCnt = 0;
	private int ballCnt = 0;
	private int cnt = 0;

	private void newGame() {
		answers = new ArrayList<Integer>();
		btn = new Button[10];

		strikeCnt = 0;
		ballCnt = 0;
		cnt = 0;
		countLabel.setText(cnt + "");
		b_result.setText(ballCnt + "");
		s_result.setText(strikeCnt + "");

		Random random = new Random();
		do {
			int v_num = random.nextInt(10);
			boolean v_check = false;
			for (int i = 0; i < answers.size(); i++) {
				if (answers.get(i) == v_num) {
					v_check = true;
					break;
				}
			}
			if (!v_check) {
				answers.add(v_num);
			}

			for (int i = 0; i <= 9; i++) {
				btn[i] = (Button) numberGroup.getChildren().get(i); // 버튼 배열에 넣기
			}

		} while (answers.size() < 3);

		System.out.println(answers);

		for (int delIndex = userSelect.size() - 1; delIndex >= 0; delIndex--) {

			int delNum = userSelect.get(delIndex);
			btn[delNum].setDisable(false); // 눌렀던 버튼 활성화
			userSelect.remove(delIndex);
			labels.get(delIndex).setText("");
		}

		for (int i = 0; i < 3; i++) {
			labels.add((Label) labelGroup.getChildren().get(i)); // 라벨 넣기
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		labels = new ArrayList<Label>();
		userSelect = new ArrayList<Integer>();
		newGame();
		labels.get(2).textProperty().addListener(new ChangeListener<String>() { // 3개 입력하면 결정 버튼 활성화
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (observable.getValue().isEmpty()) {
					resultBtn.setDisable(true);
				} else {
					resultBtn.setDisable(false);
				}
			}
		});

		underlineImg.setVisible(false);
		countLabel.setVisible(false);
	}

	@FXML
	public void removeAction() {
		int delIndex = userSelect.size() - 1;
		if (delIndex < 0) {
			new Alert(AlertType.WARNING, "선택된 숫자가 없습니다.", ButtonType.OK).show(); // 3개 초과 숫자 입력시 경고
		} else {
			int delNum = userSelect.get(delIndex);
			btn[delNum].setDisable(false); // 삭제 눌렀을 때 눌렀던 버튼 활성화
			userSelect.remove(delIndex);
			labels.get(delIndex).setText("");
		}
	}

	@FXML
	public void resultAction() throws Exception {
		for (int i = 0; i <= 2; i++) {
			if (userSelect.get(i) == answers.get(i)) { // strike 조건
				strikeCnt++;
			} else { // ball 조건
				if (userSelect.get(i) == answers.get((i + 1) % 3) || userSelect.get(i) == answers.get((i + 2) % 3)) {
					ballCnt++;
				}
			}
		}
		s_result.setText(strikeCnt + "");
		b_result.setText(ballCnt + "");
		countLabel.setText(++cnt + " 번");
		underlineImg.setVisible(true);
		countLabel.setVisible(true);

		if (strikeCnt == 3) { // 정답을 맞췄을 때
			String count = String.valueOf(cnt);
			new Alert(AlertType.INFORMATION, "성공하셨습니다.", ButtonType.FINISH).show();

			FXMLLoader loader = new FXMLLoader(getClass().getResource("/kr/or/nextit/register/Register.fxml")); // fxml
																												// 생성

			loader.setControllerFactory(new Callback<Class<?>, Object>() {
				@Override
				public Object call(Class<?> param) {
					return new RegisterController(new RegisterListener() {
						@Override
						public void register(String name) {
							try {
								new H2DB().insert(new RankModel(name, count));
							} catch (SQLException e) {
								e.printStackTrace();
							}
						}
					});
				}
			});
			Parent reg = loader.load();
			stackPane.getChildren().add(reg);
			reg.setTranslateX(((AnchorPane) reg).getPrefWidth()); // anchorpane이 login보다 위

			Timeline timeline = new Timeline();
			KeyValue keyValue = new KeyValue(reg.translateXProperty(), 0);
			KeyFrame keyFrame = new KeyFrame(Duration.millis(500), keyValue);
			timeline.getKeyFrames().add(keyFrame);
			timeline.play();

			newGame();
			countLabel.setText("");
			underlineImg.setVisible(false);
			countLabel.setVisible(false);
		}
		strikeCnt = 0;
		ballCnt = 0;
		selNum.setText(userSelect.toString()); // 이전 숫자 출력
		userSelect.clear();

		for (int i = 0; i < labels.size(); i++) {
			labels.get(i).setText("");
		}
		for (int i = 0; i <= 9; i++) {
			btn[i].setDisable(false);
		}

	}

	@FXML
	public void clickAction(ActionEvent event) {
		if (userSelect.size() == 3) {
			new Alert(AlertType.WARNING, "3개의 숫자만 선택하세요", ButtonType.OK).show(); // 3개 초과 숫자 입력시 경고
		} else {
			int num = Integer.parseInt(((Button) event.getTarget()).getText());
			userSelect.add(num);

			btn[num].setDisable(true);
			labels.get(userSelect.size() - 1).setText(num + "");
		}
	}

	@FXML
	public void showRankAction() throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("/kr/or/nextit/rank/Rank.fxml"));
		Scene scene = new Scene(root);

		Stage window = new Stage();
		window.setScene(scene);

		window.show();
	}

	@FXML
	public void restartAction() {
		newGame();
	}

}
