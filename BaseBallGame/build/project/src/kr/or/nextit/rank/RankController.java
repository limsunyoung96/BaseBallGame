package kr.or.nextit.rank;

import java.net.ConnectException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.stage.Stage;
import javafx.util.Callback;
import kr.or.nextit.BaseBallGameProject.H2DB;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;

public class RankController implements Initializable {
	@FXML
	TableColumn<RankModel, String> count;
	@FXML
	TableColumn<RankModel, String> name;
	@FXML
	TableColumn<RankModel, Integer> ranking;
	@FXML
	TableView<RankModel> tableView;
	@FXML
	Button closeBtn;
	private ObservableList<RankModel> rankList;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
//		rankList = FXCollections.observableArrayList();
//		tableView.setItems(rankList);
//		name.setCellValueFactory(
//				new Callback<TableColumn.CellDataFeatures<RankModel, String>, ObservableValue<String>>() {
//					@Override
//					public ObservableValue<String> call(CellDataFeatures<RankModel, String> param) {
//						return param.getValue().nameProperty();
//					}
//				});
//		count.setCellValueFactory(
//				new Callback<TableColumn.CellDataFeatures<RankModel, String>, ObservableValue<String>>() {
//
//					@Override
//					public ObservableValue<String> call(CellDataFeatures<RankModel, String> param) {
//						return param.getValue().countProperty();
//					}
//				});

		List<RankModel> list = new H2DB().selectList(); // 만들어둔 selectList 호출
		rankList = FXCollections.observableArrayList(list);
		tableView.setItems(rankList);
//		ranking.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<RankModel,Integer>, ObservableValue<Integer>>() {
//			
//			@Override
//			public ObservableValue<Integer> call(CellDataFeatures<RankModel, Integer> param) {
//				return param.getValue();
//			}
//		}); 

		// param -> param.getValue().rankingProperty().asObject()
		ranking.setCellValueFactory(param -> param.getValue().rankingProperty().asObject());
		name.setCellValueFactory(param -> param.getValue().nameProperty());
		count.setCellValueFactory(param -> param.getValue().countProperty());
	}

	@FXML
	public void closeAction() {
		Stage stage = (Stage) closeBtn.getScene().getWindow();
		stage.close();

	}

}
