// Pacote: model
package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnotacaoDAO {

    public void adicionarAnotacao(Anotacao anotacao) throws SQLException {
        String sql = "INSERT INTO anotacoes (titulo, descricao, data_lembrete, hora_lembrete) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setString(2, anotacao.getDescricao());
            stmt.setDate(3, new java.sql.Date(anotacao.getDataLembrete().getTime()));
            stmt.setTime(4, new java.sql.Time(anotacao.getHoraLembrete().getTime()));
            stmt.executeUpdate();
        }
    }

    public List<Anotacao> listarAnotacoes() throws SQLException {
        List<Anotacao> lista = new ArrayList<>();
        String sql = "SELECT * FROM anotacoes";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Anotacao anotacao = new Anotacao();
                anotacao.setId(rs.getInt("id"));
                anotacao.setTitulo(rs.getString("titulo"));
                anotacao.setDescricao(rs.getString("descricao"));
                anotacao.setDataCriacao(rs.getTimestamp("data_criacao"));
                anotacao.setDataLembrete(rs.getDate("data_lembrete"));
                anotacao.setHoraLembrete(rs.getTime("hora_lembrete"));
                lista.add(anotacao);
            }
        }
        return lista;
    }

    public void atualizarAnotacao(Anotacao anotacao) throws SQLException {
        String sql = "UPDATE anotacoes SET titulo = ?, descricao = ?, data_lembrete = ?, hora_lembrete = ? WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, anotacao.getTitulo());
            stmt.setString(2, anotacao.getDescricao());
            stmt.setDate(3, new java.sql.Date(anotacao.getDataLembrete().getTime()));
            stmt.setTime(4, new java.sql.Time(anotacao.getHoraLembrete().getTime()));
            stmt.setInt(5, anotacao.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarAnotacao(int id) throws SQLException {
        String sql = "DELETE FROM anotacoes WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}

