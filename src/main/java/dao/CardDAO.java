package dao;

import model.Card;
import util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CardDAO {

    public void createCard(int boardId, int colunaId, String titulo, String descricao) {
        String sql = "INSERT INTO cards (board_id, coluna_id, titulo, descricao) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, boardId);
            stmt.setInt(2, colunaId);
            stmt.setString(3, titulo);
            stmt.setString(4, descricao);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Card> listCardsByColumn(int colunaId) {
        List<Card> cards = new ArrayList<>();
        String sql = "SELECT * FROM cards WHERE coluna_id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, colunaId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                cards.add(new Card(
                        rs.getInt("id"),
                        rs.getInt("board_id"),
                        rs.getInt("coluna_id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getBoolean("bloqueado"),
                        rs.getString("motivo_bloqueio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public Card getCardById(int id) {
        String sql = "SELECT * FROM cards WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Card(
                        rs.getInt("id"),
                        rs.getInt("board_id"),
                        rs.getInt("coluna_id"),
                        rs.getString("titulo"),
                        rs.getString("descricao"),
                        rs.getTimestamp("data_criacao").toLocalDateTime(),
                        rs.getBoolean("bloqueado"),
                        rs.getString("motivo_bloqueio")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void moveCard(int cardId, int novaColunaId) {
        String sql = "UPDATE cards SET coluna_id = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, novaColunaId);
            stmt.setInt(2, cardId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void cancelCard(int cardId, int colunaCancelamentoId) {
        moveCard(cardId, colunaCancelamentoId);
    }

    public void blockCard(int cardId, String motivo) {
        String sql = "UPDATE cards SET bloqueado = 1, motivo_bloqueio = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, motivo);
            stmt.setInt(2, cardId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void unblockCard(int cardId, String motivo) {
        String sql = "UPDATE cards SET bloqueado = 0, motivo_bloqueio = ? WHERE id = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, motivo);
            stmt.setInt(2, cardId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
