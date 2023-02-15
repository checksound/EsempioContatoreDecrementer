package sequenzacritica;

public class Decrementer extends Thread {

    private Contatore contatore;
    public Decrementer(String name, Contatore contatore) {
        super(name);
        this.contatore = contatore;
    }

    public void run() {
        while(true) {

            boolean isZero = (0 == contatore.getValue());  // step 1
            if(isZero)  // CONDIZIONE D'USCITA
                return;
            else
                contatore.decrement();  // step 2

        }
    }
}
