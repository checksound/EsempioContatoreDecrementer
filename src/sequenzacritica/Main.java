package sequenzacritica;

public class Main {
    public static void main(String[] args) throws InterruptedException {
             Contatore contatore = new Contatore(100);

             Decrementer decrementer1 = new Decrementer("Decrementer_1", contatore);
             Decrementer decrementer2 = new Decrementer("Decrementer_2", contatore);

             decrementer1.start();
             decrementer2.start();

             decrementer1.join(1*1000);
             decrementer2.join(1*1000);

            System.out.println("VALORE CONTATORE: " + contatore.getValue());

    }
}