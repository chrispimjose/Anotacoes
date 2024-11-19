package controller;
// Pacote: controller

import model.Anotacao;
import model.AnotacaoDAO;
import java.sql.SQLException;
import java.util.List;

public class AnotacaoController {
    private AnotacaoDAO anotacaoDAO = new AnotacaoDAO();

    public void adicionarAnotacao(Anotacao anotacao) throws SQLException {
        anotacaoDAO.adicionarAnotacao(anotacao);
    }

    public List<Anotacao> listarAnotacoes() throws SQLException {
        return anotacaoDAO.listarAnotacoes();
    }

    public void atualizarAnotacao(Anotacao anotacao) throws SQLException {
        anotacaoDAO.atualizarAnotacao(anotacao);
    }

    public void deletarAnotacao(int id) throws SQLException {
        anotacaoDAO.deletarAnotacao(id);
    }
}

