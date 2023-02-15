# Esempio Contatore Decrementer

Abbiamo una variabile di tipo `Contatore` con un metodo per decrementare
il valore, `decrement()` e un metodo che restituisce il valore `getValue()`;

Qui sotto l'esempio di come si deve comportare un oggetto di tipo `Contatore`.

```
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
						if(isZero)  // CONDIZIONE D'USCITA
							return;
						else
							contatore.decrement();  // step 2
				
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
i metodi `synchronized` c'è comunque il problema della sezione critica.

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
                if (isZero)  // CONDIZIONE D'USCITA
                    return;
                else
                    contatore.decrement();  // step 2
            }
        }
    }
}

```
