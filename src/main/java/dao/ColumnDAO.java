package dao;

import model.BoardColumn;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColumnDAO {

    public void createDefaultColumns(int boardId) {
        createColumn(boardId, "Backlog", 1, "INICIAL");
        createColumn(boardId, "In Progress", 2, "PENDENTE");
        createColumn(boardId, "Done", 3, "FINAL");
        createColumn(boardId, "Canceled", 4, "CANCELAMENTO");
    }

    public void createColumn(int boardId, String nome, int ordem, String tipo) {
        String sql = "INSERT INTO board_columns (board_id, nome, ordem, tipo) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            stmt.setString(2, nome);
            stmt.setInt(3, ordem);
            stmt.setString(4, tipo);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<BoardColumn> listColumnsByBoard(int boardId) {
        List<BoardColumn> columns = new ArrayList<>();
        String sql = "SELECT * FROM board_columns WHERE board_id = ? ORDER BY ordem";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                columns.add(new BoardColumn(
                        rs.getInt("id"),
                        rs.getInt("board_id"),
                        rs.getString("nome"),
                        rs.getInt("ordem"),
                        rs.getString("tipo")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return columns;
    }
}
