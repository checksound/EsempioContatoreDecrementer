package sequenzacritica;

public class MainWithThreadTermination {
    public static void main(String[] args) throws InterruptedException {
             Contatore contatore = new Contatore(100);

             DecrementerInterrompible decrementer1 =
                     new DecrementerInterrompible("Decrementer_1", contatore);
             DecrementerInterrompible decrementer2 =
                     new DecrementerInterrompible("Decrementer_2", contatore);

             decrementer1.start();
             decrementer2.start();

             decrementer1.join(1*1000);
             decrementer2.join(1*1000);

             if(decrementer1.isAlive()) {
                 System.out.println("Sending interrupt to Decrementer_1");
                 decrementer1.interrupt();
                 decrementer1.join();
             }

            if(decrementer2.isAlive()) {
                System.out.println("Sending interrupt to Decrementer_2");
                decrementer2.interrupt();
                decrementer2.join();
            }

            System.out.println("VALORE CONTATORE: " + contatore.getValue());

    }
}