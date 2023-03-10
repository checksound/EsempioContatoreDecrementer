# Esempio Contatore Decrementer

Abbiamo una variabile di tipo `Contatore` con un metodo per decrementare
il valore, `decrement()` e un metodo che restituisce il valore `getValue()`;

Qui sotto l'esempio di come si deve comportare un oggetto di tipo `Contatore`.

```java

Contatore contatore = new Contatore(5);

contatore.decrement();
int valore = contatore.getValue();  // ritorna valore=4

contatore.decrement();
valore = contatore.getValue();  // ritorna valore=3

```

Implementare la classe `Contatore` scrivendo il codice, in modo che l'accesso da parte di più thread non crei 
una sequenza critica nel caso due thread eseguano contemporaneamente
l'operazione di decrement.

Data la classe `Decrementer` di tipo `Thread` che agisce su una variabile di tipo `Contatore` e decrementa il contatore finché non arriva a 0 e
quindi termina l'esecuzione del thread (codice qui sotto)

```java

public class Decrementer extends Thread {

		private Contatore contatore;

		public Decrementer(Contatore contatore) {
			this.contatore = contatore;
		}

		public void run() {
				
				while(true) {
						
						boolean isZero = (0 == contatore.getValue());  // step 1
						if(isZero)  // CONDIZIONE D'USCITA step 2
							return;
						else
							contatore.decrement();  // step 3
				
				}  // fine while
			
		}  // fine run

}

```

Immaginate due thread t1 e t2 di tipo `Decrementer` vengono mandati in 
esecuzione contemporaneamente passandogli una stessa variabile di 
tipo `Contatore`.

Con quale flusso di esecuzione potrebbe succedere che i due thread, t1 e t2, 
non terminino non arrivando mai alla condizione d'uscita?

Come si potrebbe cambiare il codice per far si che invece i due 
thread terminino?

_______________________________________________________________

Implementazione di `Counter`, [sequenzacritica.Contatore](./src/sequenzacritica/Contatore.java),
il thread che esegue il decremento [sequenzacritica.Decrementer](./src/sequenzacritica/Decrementer.java)
e l'applicazione [sequenzacritica.Main](./src/sequenzacritica/Main.java).

Nonostante [sequenzacritica.Contatore](./src/sequenzacritica/Contatore.java) abbia tutti
i metodi `synchronized` c'è comunque il problema della sezione critica in [sequenzacritica.Decrementer](./src/sequenzacritica/Decrementer.java).

```java

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
            if(isZero)  // CONDIZIONE D'USCITA  // step 2
                return;
            else
                contatore.decrement();  // step 3

        }
    }
}

```

Il problema può nascere ad esempio da questa sequenza di esecuzione:
se `contatore.getValue()` vale 1 ed eseguiamo questa sequenza dei due thread:
`thread1.1 < thread2.1 < thread1.2 < thread2.2 < thread3.3 < thread3.3`, al termine della sequenza
il valore di `contatore.getValue()` vale -1 e quindi i due thread che decrementano non raggiungeranno
mai la condizione d'uscita (controllo se `contatore.getValue() == 0`).

Mentre con la sequenza `thread1.1 < thread1.2 < thread1.3 < thread2.1 < thread2.2 < thread2.3` 
il problema non si verifica.

Basta provare ad eseguire qualche volta l'applicazione [sequenzacritica.Main](./src/sequenzacritica/Main.java) 
per riscontrare il problema. Il programma in alcuni casi non termina mai e va fermato dall'esterno perché i due thread
non escono mai dal metodo run() e quindi l'applicazione non termina.

L'implementazione [sequenzacritica.MainWithThreadTermination](./src/sequenzacritica/MainWithThreadTermination.java)
forza la terminazione inviando una `interrupt()` ad ogni thread se ancora alive dopo 1 secondo di
esecuzione 
(Utilizza un decrementer che gestisce la situazione in cui è [sequenzacritica.DecrementerInterrompible](./src/sequenzacritica/DecrementerInterrompible.java)).


La versione [sequenzacriticafixed.Decrementer](./src/sequenzacriticafixed/Decrementer.java) con l'aggiunta
del blocco `synchronized` per far si che un thread solo alla volta possa accedere alla
sequenza critica.

```java

package sequenzacriticafixed;

public class Decrementer extends Thread {

    private Contatore contatore;
    public Decrementer(String name, Contatore contatore) {
        super(name);
        this.contatore = contatore;
    }

    public void run() {
        while(true) {

            synchronized(contatore) {
                boolean isZero = (0 == contatore.getValue());  // step 1
                if (isZero)  // CONDIZIONE D'USCITA  // step 2
                    return;
                else
                    contatore.decrement();  // step 3
            }
        }
    }
}

```

Ora il programma [sequenzacriticafixed.Main](./src/sequenzacriticafixed/Main.java)
termina correttamente.