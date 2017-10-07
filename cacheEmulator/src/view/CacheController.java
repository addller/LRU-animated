package view;

import cache_emulator.Cache;
import cache_emulator.UltimoRecenteUsado_LRU;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javax.swing.JOptionPane;

public class CacheController implements Initializable {

    private Cache<String> cache;
    private UltimoRecenteUsado_LRU lru;
    @FXML
    private VBox vBoxPilha;
    @FXML
    private TextField txtEntradaAtual;
    @FXML
    private Button btnEmpilhar;
    @FXML
    private TextArea txtEntradaAntiga;
    @FXML
    private Label lblMiss, lblHit;

    private Label[] niveisPilha;
    private Object[] buff;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            String resposta = JOptionPane.showInputDialog(null, "informe o tamalho da \"pilha/cache\"");
            criarCache(Integer.parseInt(resposta));
            txtEntradaAntiga.setWrapText(true);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "O valor informado deve ser numérico e menor que " + Integer.MAX_VALUE);
            Platform.exit();
        }
    }

    private void criarCache(int tamanhoCache) {
        cache = new Cache<>(tamanhoCache);
        lru = new UltimoRecenteUsado_LRU(cache);

        niveisPilha = new Label[cache.getTamanhoBuff()];
        for (int i = 0; i < niveisPilha.length; i++) {
            niveisPilha[i] = new Label("NULL");
            niveisPilha[i].getStyleClass().add("pilha");
        }
        vBoxPilha.getChildren().addAll(niveisPilha);
        addClassTo(lblMiss, "txtRed", "txtBold");
        addClassTo(lblHit, "txtBlue", "txtBold");
    }

    private static void addClassTo(Node node, String... classCSS) {
        Arrays.stream(classCSS).forEach(node.getStyleClass()::add);
    }

    @FXML
    private void reiniciar() {
        Arrays.stream(niveisPilha).forEach(nivel -> nivel.setText("NULL"));
        lru.limparCache();
        txtValueTo(txtEntradaAtual, "");
        txtValueTo(txtEntradaAntiga, "");
        txtValueTo(lblHit, "Hit: 0");
        txtValueTo(lblMiss, "Miss: 0");
    }

    private static void txtValueTo(TextArea areaTexto, String texto) {
        areaTexto.textProperty().setValue(texto);
    }

    private static void txtValueTo(Label label, String texto) {
        label.textProperty().setValue(texto);
    }

    private static void txtValueTo(TextField campoDeTexto, String texto) {
        campoDeTexto.textProperty().setValue(texto);
    }

    @FXML
    private void empilhar() {
        lru.atualizarCache(txtEntradaAtual.getText());
        buff = cache.getBuffer();
        for (int i = 0; i < cache.getTamanhoBuff(); i++) {
            txtValueTo(niveisPilha[i], (String) buff[i]);
        }
        atualizar();
    }

    private void atualizar() {
        txtValueTo(lblHit, "Hit: " + lru.getHit());
        txtValueTo(lblMiss, "Miss: " + lru.getMiss());
        txtValueTo(txtEntradaAntiga, (txtEntradaAntiga.getText() + " " + txtEntradaAtual.getText()));
        if (txtEntradaAntiga.getText().charAt(0) == ' ') {
            txtValueTo(txtEntradaAntiga, txtEntradaAntiga.getText().replaceFirst(" ", ""));
        }
        txtValueTo(txtEntradaAtual, "");
        txtEntradaAntiga.positionCaret(txtEntradaAntiga.getText().length());
    }

}
