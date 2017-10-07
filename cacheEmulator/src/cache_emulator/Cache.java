
package cache_emulator;


public class Cache<Objeto> {
    private final Object buffer;
    private final int tamanhoBuff;
    
    public Cache(int buffer) {
        this.buffer = new Object[buffer];
        this.tamanhoBuff = buffer;
    }

    public Object[] getBuffer() {
        return (Object[]) buffer;
    }
    
    public int getTamanhoBuff() {
        return tamanhoBuff;
    }
}
