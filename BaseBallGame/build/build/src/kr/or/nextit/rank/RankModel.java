package kr.or.nextit.rank;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class RankModel {
	private IntegerProperty ranking;
	private StringProperty name;
	private StringProperty count;

	public RankModel(int ranking, String name, String count) {
		this.ranking = new SimpleIntegerProperty(ranking);
		this.name = new SimpleStringProperty(name);
		this.count = new SimpleStringProperty(count);
	}

	public RankModel(String name, String count) {
		this.name = new SimpleStringProperty(name);
		this.count = new SimpleStringProperty(count);
	}

	public String getName() {
		return name.get();
	}

	public void setName(String name) {
		this.name.set(name);
	}

	public StringProperty nameProperty() {
		return name;
	}

	public String getCount() {
		return count.get();
	}

	public void setCount(String count) {
		this.count.set(count);
	}

	public StringProperty countProperty() {
		return count;
	}

	public Integer getRanking() {
		return ranking.get();
	}

	public void setRanking(Integer ranking) {
		this.ranking.set(ranking);
	}

	public IntegerProperty rankingProperty() {
		return ranking;
	}

	@Override
	public String toString() {
		return "RankModel [ranking=" + ranking.get() + ", name=" + name.get() + ", count=" + count.get() + "]";
	}

}
