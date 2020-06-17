# GestoreProgetti

Gestore progetti è un sistema software di tipo client-server, usato per la gestione dei progetti e condivisione dei task tra i vari utenti all'interno della stessa azienda.

Il sistema consente di gestire e rappresentare:
- I propri utenti, registrandosi e in seguito autenticandosi nel sistema.
- I propri progetti, creandone di nuovi e fornendo la visibilità ad altri utenti.
- I task relativi a quel progetto, inserendone di nuovi con titoli e  descrizioni e assegnandoli ai soli utenti che hanno la visibilità del progetto.
- I commenti legati ai task, nei quali ogni utente che dispone della visibilità del progetto può effettuare.
- I tag impostati ai progetti o ai task, creandone di nuovi e associando il proprio colore ad ognuno di essi.

L'uso del sistema in questione è descritto principalmente dai seguenti casi d'uso:

**Caso d'uso UC1:** *Inserimento nuovo progetto - Attore Primario: un Utente*
*Scenario principale di successo*
1. Un utente vuole inserire un nuovo progetto nel sistema.
2. L'utente inserisce il suo username e la sua password. Il sistema verifica la correttezza dei dati immessi, e autentica l'Utente. Il sistema mostra quindi la Home Page con tutti i possibili comandi da poter effettuare.
3. L'utente sceglie l'attività "Crea nuovo progetto"
4. L'utente inserisce titolo e descrizione del progetto. Il sistema registra queste informazioni sul progetto, a cui associa anche un identificatore univoco.
5. Il sistema mostra la pagina del progetto con tutti i possibili comandi da poter effettuare.
6. Da questo momento, l'utente del sistema potrà vedere nella lista dei suoi progetti, il nuovo progetto appena creato.

*Estensioni*
2a.Credenziali utente non convalidate. Il sistema richiede l'inserimento dei dati.
3-4a.L'utente annulla l'operazione di inserimento. Il sistema ***non*** registra nessuna informazione sul progetto.

**Caso d'uso UC2:** *Inserimento nuovo task - Attore Primario: un Utente*
*Scenario principale di successo*
1. L'utente si trova nella pagina del progetto
2. L'utente sceglie l'attività "Crea nuovo task"
3. L'utente inserisce nome e descrizione del task, il sistema mostra la lista degli utenti che dispongono della visibilità del progetto, l'utente quindi sceglie tra questi la persona al quale associare il task.
4. Il sistema registra le informazioni sul task, a cui associa un identificatore univoco.
5. Il sistema mostra nella pagina del progetto il nuovo task appena creato con le relative descrizioni e l'utente al quale è stato associato.

*Estensioni*
3a.L'utente inserisce dei dati non validi. Il sistema riporta l'errore all'utente e chiede nuovamente l'inserimento.
3b.L'utente annulla l'operazione. Il sistema ***non*** registra nessuna informazione sul task.

**Caso d'uso UC3:** *Aggiunta visibilità progetto - Attore Primario: un Utente*
*Scenario principale di successo*
1. L'utente si trova nella pagina del progetto
2. L'utente sceglie l'attività "Aggiungi visibilità progetto"
3. Il sistema mostra la lista di tutti gli utenti del sistema che non hanno ancora la visibilità del progetto.
4. L'utente sceglie l'utente al quale fornire la visibilità del progetto. Il sistema registra la scelta dell'utente.
- L'utente **ripete il passo 4** fino a che non indica che ha terminato
5. Il sistema mostra nella pagina del progetto tutti gli utenti che dispongono della visibilità del progetto.

*Estensioni*
3-4a. L'utente annulla l'operazione. Il sistema ***non*** registra nessuna informazione sulla visibilità.

**Caso d'uso UC4:** *Inserimento nuovo tag - Attore Primario: un Utente*
*Scenario principale di successo*
1. L'utente si trova nella Home Page.
2. L'utente sceglie l'attività "Crea nuovo tag".
3. L'utente inserisce Nome e Descrizione del tag. Il sistema mostra la lista di colori disponibili. L'utente seleziona uno tra questi.
4. Il sistema verifica la correttezza dei dati e registra le informazioni del tag, a cui associa un identificatore univoco.
5. Da questo momento, l'utente potrà visualizzare il tag appena creato nella lista dei tag da assocciare al progetto.

*Estensioni*
3a.L'utente annulla l'operazione. Il sistema ***non*** registra nessuna informazione sul tag.
3b.L'utente inserisce dei dati non validi. Il sistema riporta l'errore all'utente e chiede nuovamente l'inserimento.

**Caso d'uso UC5:** *Inserimento nuovo commento realativo ad un task - Attore Primario: un Utente*

*Scenario principale di successo:*

1. L'utente si trova nella pagina del progetto
2. L'utente sceglie l'attività "COMMENTA" relativa ad un task di progetto
3. Il sistema mostra la lista di tutti i commenti relativi a quel task.
4. L'utente inserisce un commento nell'apposita fotm. Il sistema mostra il commento in coda alla lista.
- L'utente **ripete il passo 4** fino a che non indica che ha terminato
5. Da questo momento tutti gli utenti che hanno visibilità del progetto potranno vedere i nuovi commenti inseriti.

*Estensioni*
4a. L'utente annulla l'operazione. Il sistema ***non*** registra il commento.
