package cache_emulator;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

public class CacheController implements Initializable {

    private Cache<String> cache;
    private UltimoRecenteUsado_LRU lru;
    @FXML
    private VBox vBoxPilha;
    @FXML
    private TextField txtEntradaAntiga, txtEntradaAtual;
    @FXML
    private Button btnEmpilhar;

    @FXML
    private Label lblMiss, lblHit;

    private Label[] niveisPilha;
    private Object[] buff;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String resposta = JOptionPane.showInputDialog(null, "informe o tamalho da \"pilha/cache\"");

            cache = new Cache<>(Integer.parseInt(resposta));
            lru = new UltimoRecenteUsado_LRU(cache);

            niveisPilha = new Label[cache.getTamanhoBuff()];
            for (int i = 0; i < niveisPilha.length; i++) {
                niveisPilha[i] = new Label("NULL");
                niveisPilha[i].getStyleClass().add("pilha");
            }
            vBoxPilha.getChildren().addAll(niveisPilha);
            lblMiss.setStyle("-fx-text-fill:red;");
            lblHit.setStyle("-fx-text-fill:blue;");

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O valor informado deve ser numérico e menor que " + Integer.MAX_VALUE);
            Platform.exit();
        }
    }

    @FXML
    private void empilhar() {
        lru.atualizarCache(txtEntradaAtual.getText());
        buff = cache.getBuffer();
        for (int i = 0; i < cache.getTamanhoBuff(); i++) {
            setText(niveisPilha[i], (String) buff[i]);
        }
        setText(lblHit, "Hit: " + lru.getHit());
        setText(lblMiss, "Miss: " + lru.getMiss());
        setText(txtEntradaAntiga, txtEntradaAntiga.getText() + " " + txtEntradaAtual.getText());
        setText(txtEntradaAtual, "");
    }

    private void setText(TextField campoTexto, String valor) {
        campoTexto.textProperty().setValue(valor);
    }

    private void setText(Label label, String valor) {
        label.textProperty().setValue(valor);
    }

}
