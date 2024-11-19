import javax.swing.SwingUtilities;

import view.AnotacaoView;

public class App {
    public static void main(String[] args) {
        // Inicializa a aplicação na Event Dispatch Thread para o Swing
        SwingUtilities.invokeLater(() -> {
            // Cria uma instância da AnotacaoView e a torna visível
            AnotacaoView view = new AnotacaoView();
            view.setVisible(true);
        });
    }
}
