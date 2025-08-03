package dao;

import model.Board;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BoardDAO {

    public void createBoard(String nome) {
        String sql = "INSERT INTO boards (nome) VALUES (?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int boardId = rs.getInt(1);
                ColumnDAO colDao = new ColumnDAO();
                colDao.createDefaultColumns(boardId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Board> listBoards() {
        List<Board> boards = new ArrayList<>();
        String sql = "SELECT * FROM boards";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                boards.add(new Board(rs.getInt("id"), rs.getString("nome")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return boards;
    }

    public void deleteBoard(int id) {
        String sql = "DELETE FROM boards WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
