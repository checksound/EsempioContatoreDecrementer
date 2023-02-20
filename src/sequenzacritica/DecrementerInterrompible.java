package sequenzacritica;

public class DecrementerInterrompible extends Thread {

    private Contatore contatore;
    public DecrementerInterrompible(String name, Contatore contatore) {
        super(name);
        this.contatore = contatore;
    }

    public void run() {
        while(true) {
            if (isInterrupted()) {
                return;
            }
            boolean isZero = (0 == contatore.getValue());  // step 1
            if(isZero)  // CONDIZIONE D'USCITA step 2
                return;
            else
                contatore.decrement();  // step 3

        }
    }
}
