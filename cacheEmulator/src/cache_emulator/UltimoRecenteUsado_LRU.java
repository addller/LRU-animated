package cache_emulator;

public class UltimoRecenteUsado_LRU<ObjetoEmCache> {

    private static final int ANTERIOR = 0,
            PROXIMO = 1,
            NULL = -1;

    private final ObjetoEmCache[] buffer;
    private final int tamanhoBuffer;
    private int index, acerto, erro, ultimoDaFila;
    private final int[][] fila;

    public UltimoRecenteUsado_LRU(Cache<ObjetoEmCache> cache) {
        buffer = (ObjetoEmCache[]) cache.getBuffer();
        tamanhoBuffer = buffer.length;
        ultimoDaFila = buffer.length - 1;
        fila = new int[tamanhoBuffer][];
    }
    
    public void atualizarCache(ObjetoEmCache novoObjeto) {

        for (index = 0; index < tamanhoBuffer; index++) {

            if (buffer[index] == null) {
                buffer[index] = novoObjeto;
                fila[index] = new int[]{-1, -1, index};
                if (index > 0) {
                    fila[index][ANTERIOR] = index - 1;
                    fila[index - 1][PROXIMO] = index;
                }
                erro++;
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
    

    public int getMiss() {
        return erro;
    }

    public int getHit() {
        return acerto;
    }
}
