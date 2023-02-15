package sequenzacriticafixed;

public class Contatore {
    private int value;
    public Contatore(int value) {
        this.value = value;
    }

    public synchronized void decrement() {
        value--;
    }

    public synchronized int getValue() {
        return value;
    }

}
