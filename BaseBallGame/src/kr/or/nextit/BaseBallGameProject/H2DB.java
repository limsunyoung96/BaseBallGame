package kr.or.nextit.BaseBallGameProject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import kr.or.nextit.rank.RankModel;

//SELECT ROWNUM() AS RANKING, NAME, COUNT 
//FROM 
//(SELECT NAME, COUNT 
//FROM RANK 
//ORDER BY CONVERT(COUNT, INT))
public class H2DB {
	private static final String DB_DRIVER = "org.h2.Driver";
	private static final String DB_CONNECTION = "jdbc:h2:~/BaseBallGame"; // database name
	private static final String DB_USER = "pc44"; // user id
	private static final String DB_PASSWORD = "pc44"; // passward

	public List<RankModel> selectList() {
		List<RankModel> list = null;
		Connection connection = null;
		Statement statement = null;
		ResultSet resultSet = null;
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			statement = connection.createStatement();
//			resultSet = statement.executeQuery("SELECT ROWNUM() AS RANKING, NAME, COUNT FROM "
//					+ "SELECT NAME, COUNT FROM RANK ORDER BY CONVERT(COUNT, INT))");
			resultSet = statement.executeQuery(
					"SELECT RANK() OVER (ORDER BY CONVERT(COUNT,INT)) RANKING, NAME, COUNT FROM RANK ORDER BY 1");
//			resultSet = statement.executeQuery("SELECT NAME, COUNT FROM RANK ORDER BY CONVERT(COUNT, INT))");
			list = new ArrayList<RankModel>(); // 이게 try 안에 들어와야 메모리적으로도 안전하다.

			while (resultSet.next()) {
				int ranking = resultSet.getInt("ranking");
				String nm = resultSet.getString("NAME");
				String count = resultSet.getString("COUNT");
				list.add(new RankModel(ranking, nm, count));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}

//	public static void main(String[] args) {
//		try {
////			DeleteDbFiles.execute("~", "testdb", true); // drop db if exist 'testdb'
//			initDB();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}

	private static Connection getDBConnection() {
		Connection dbConnection = null;
		try {
			Class.forName(DB_DRIVER);
		} catch (ClassNotFoundException e) {
			System.out.println(e.getMessage());
		}
		try {
			dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			return dbConnection;
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
		return dbConnection;
	}

	/*
	 * private static void initDB() throws SQLException { Connection connection =
	 * getDBConnection(); Statement stmt = null; try {
	 * connection.setAutoCommit(false); stmt = connection.createStatement();
	 * 
	 * // create TEST_TABLE for example stmt.
	 * execute("CREATE TABLE RANK(IDX INT PRIMARY KEY, NAME VARCHAR(100) ,COUNT VARCHAR(100) );"
	 * );
	 * 
	 * // insert some values into TEST_TABLE stmt.
	 * execute("INSERT INTO RANK VALUES(1, 'Martin.Park', '3'), (2, 'OskarDevelopers', '2');"
	 * );
	 * 
	 * // get result by using SELECT query ResultSet rs =
	 * stmt.executeQuery("SELECT * FROM RANK;"); while (rs.next()) {
	 * System.out.println("idx : " + rs.getString("idx") + " / " + "name : " +
	 * rs.getString("name")); }
	 * 
	 * stmt.close(); connection.commit();
	 * 
	 * } catch (SQLException e) { System.out.println("Exception Message " +
	 * e.getLocalizedMessage()); } catch (Exception e) { e.printStackTrace(); }
	 * finally { connection.close(); } }
	 */

//	void createTable() throws SQLException {
//		Connection connection = getDBConnection();
//		Statement stmt = null;
//
//		connection.setAutoCommit(false);
//		stmt = connection.createStatement();
//
//		// create TEST_TABLE for example
//		stmt.execute("CREATE TABLE RANK(IDX INT PRIMARY KEY, NAME VARCHAR(100) ,CNT INT );");
//
//		stmt.close();
//		connection.commit();
//		connection.close();
//	}

	void insert(RankModel model) throws SQLException {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
			statement = connection.createStatement();
			String sql = "INSERT INTO RANK(IDX, NAME, COUNT) VALUES(RANK_SEQ.NEXTVAL,'" + model.getName() + "', '"
					+ model.getCount() + "')";
			statement.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			if (statement != null) {
				statement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

//	public void select() throws SQLException {
//		Connection connection = getDBConnection();
//		Statement stmt = null;
//
//		connection.setAutoCommit(false);
//		stmt = connection.createStatement();
//
//		// get result by using SELECT query
//		ResultSet rs = stmt.executeQuery("SELECT * FROM TEST_TABLE;");
//		while (rs.next()) {
//			System.out.println("idx : " + rs.getString("idx") + " / " + "name : " + rs.getString("name"));
//		}
//		stmt.close();
//		connection.commit();
//		connection.close();
//	}
}
