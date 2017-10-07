package cache_emulator;

import java.util.Arrays;

public class UltimoRecenteUsado_LRU<ObjetoEmCache> {

    private static final int ANTERIOR = 0,
            PROXIMO = 1,
            NULL = -1;

    private final ObjetoEmCache[] buffer;
    private final int tamanhoBuffer;
    private int index, acerto, erro, ultimoDaFila = -1;
    private final int[][] fila;

    public UltimoRecenteUsado_LRU(Cache<ObjetoEmCache> cache) {
        buffer = (ObjetoEmCache[]) cache.getBuffer();
        tamanhoBuffer = buffer.length;
        fila = new int[tamanhoBuffer][];
    }

    public void atualizarCache(ObjetoEmCache novoObjeto) {

        for (index = 0; index < tamanhoBuffer; index++) {
            if (isNovoObjeto(index, novoObjeto)) {
                return;
            }
            if (buffer[index].equals(novoObjeto)) {
                acerto++;
                ordenar();
                return;
            }
        }
        erro++;
        inserir(novoObjeto);
    }

    private boolean isNovoObjeto(int index, ObjetoEmCache novoObjeto) {
        if (buffer[index] == null) {
            buffer[index] = novoObjeto;
            fila[index] = new int[]{-1, -1, index};
            if (index > 0) {
                fila[index][ANTERIOR] = index - 1;
                fila[index - 1][PROXIMO] = index;
            }
            erro++;
            ultimoDaFila++;
            return true;
        }
        return false;
    }

    private void ordenar() {
        if (index == ultimoDaFila) {
            return;
        }
        fila[proximo(index)][ANTERIOR] = anterior(index);
        if (anterior(index) != NULL) {
            fila[anterior(index)][PROXIMO] = proximo(index);
        }
        fila[ultimoDaFila][PROXIMO] = index;
        fila[index][PROXIMO] = NULL;
        fila[index][ANTERIOR] = ultimoDaFila;
        ultimoDaFila = index;
    }

    private int proximo(int index) {
        return fila[index][PROXIMO];
    }

    private int anterior(int index) {
        return fila[index][ANTERIOR];
    }

    private void inserir(ObjetoEmCache novoObjeto) {
        for (index = 0; index < tamanhoBuffer; index++) {
            if (anterior(index) == NULL) {
                buffer[index] = novoObjeto;
                ordenar();
                break;
            }
        }
    }
    
    public void limparCache(){
        for (index = 0; index < buffer.length; index++) {
            buffer[index] = null;
        }
        index = erro = acerto = 0;
        ultimoDaFila = -1;
    }

    public int getMiss() {
        return erro;
    }

    public int getHit() {
        return acerto;
    }
}
