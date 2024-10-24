// Funzione per verificare se il token JWT è presente nei cookie
function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    const loginLink = document.getElementById('loginLink');
    const registerLink = document.getElementById('registerLink');
    const logoutButton = document.getElementById('logoutButton');
    const creaViaggioLink = document.getElementById('creaViaggioLink');

    if (token) {
        loginLink.style.display = 'none';
        registerLink.style.display = 'none';
        logoutButton.style.display = 'block';
        creaViaggioLink.style.display = 'inline-block';

        let username = document.createElement('a');
        username.textContent = "Ciao, " + JSON.parse(localStorage.getItem('username'));
        username.style.marginRight = '10px';
        username.href = '/profilo';
        logoutButton.parentNode.insertBefore(username, logoutButton);
    } else {
        loginLink.style.display = 'block';
        registerLink.style.display = 'block';
        logoutButton.style.display = 'none';
        creaViaggioLink.style.display = 'none';
    }
}

// Funzione per gestire il logout
function handleLogout() {
    document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    localStorage.removeItem("username");
    window.location.href = '/';
}

// Funzione per reindirizzare gli utenti non autenticati
function redirectIfNotAuth() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    if (!token) {
        window.location.href = '/login';
    }
}

// Funzione per reindirizzare l'utente alla pagina dei risultati con i parametri di ricerca
function cercaViaggi() {
    const destinazione = document.getElementById('destinazione').value;
    const checkInDate = document.getElementById('checkInDate').value;
    const tipologiaViaggio = document.getElementById('tipologiaViaggio').value; // Ottieni la tipologia selezionata

    // Controlliamo se almeno uno dei campi è stato inserito
    if (!destinazione && !checkInDate && !tipologiaViaggio) {
        alert('Per favore inserisci almeno una destinazione, una data o una tipologia di viaggio.');
        return;
    }

    // Inizializziamo l'URL della ricerca
    let url = 'risultati?'; // Cominciamo con il punto di partenza dell'URL
    
    // Aggiungiamo i parametri solo se sono presenti
    if (destinazione) {
        url += `stato=${encodeURIComponent(destinazione)}`; // Aggiungi lo stato (destinazione)
    }

    if (checkInDate) {
        if (url.includes('stato=')) {
            url += `&dataPartenza=${checkInDate}`; // Aggiungi la data se lo stato è già presente
        } else {
            url += `dataPartenza=${checkInDate}`; // Aggiungi solo la data se lo stato non c'è
        }
    }

    if (tipologiaViaggio) {
        if (url.includes('stato=') || url.includes('dataPartenza=')) {
            url += `&tipologia=${encodeURIComponent(tipologiaViaggio)}`; // Aggiungi tipologia se c'è un altro parametro
        } else {
            url += `tipologia=${encodeURIComponent(tipologiaViaggio)}`; // Aggiungi solo tipologia
        }
    }

    window.location.href = url; // Reindirizza l'utente alla pagina con i risultati
}

// Funzione per mostrare i suggerimenti in base all'input dell'utente
function showSuggestions(query) {
    const suggestionsBox = document.getElementById('suggestions');
    suggestionsBox.innerHTML = ''; // Pulisci i suggerimenti precedenti

    if (query.length < 2) {
        suggestionsBox.classList.remove('active'); // Nascondi la casella se la lunghezza è minore di 2
        return;
    }

    // Chiamata API per i suggerimenti
    fetch(`/api/suggestions?query=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            if (data.length === 0) {
                suggestionsBox.classList.remove('active'); // Nessun suggerimento
                return;
            }

            data.forEach(suggestion => {
                const suggestionItem = document.createElement('div');
                suggestionItem.className = 'suggestion-item';
                suggestionItem.innerHTML = `<span>${suggestion}</span>`;

                suggestionItem.addEventListener('click', function() {
                    document.getElementById('destinazione').value = suggestion;
                    suggestionsBox.classList.remove('active'); // Chiudi i suggerimenti dopo la selezione
                });

                suggestionsBox.appendChild(suggestionItem);
            });

            suggestionsBox.classList.add('active'); // Mostra i suggerimenti
        })
        .catch(error => {
            console.error('Errore nel recupero dei suggerimenti:', error);
        });
}

// Event listener per l'input del campo destinazione
document.getElementById('destinazione').addEventListener('input', function(event) {
    const query = event.target.value;
    showSuggestions(query); // Mostra i suggerimenti mentre l'utente digita
});

document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();

    const logoutButton = document.getElementById('logoutButton');
    logoutButton.addEventListener('click', handleLogout);

    const cercaButton = document.getElementById('cercaButton');
    cercaButton.addEventListener('click', cercaViaggi);
    
    // Carica i viaggi in partenza quando la pagina è caricata
    caricaViaggiInPartenza();
});

// Funzione per caricare i viaggi in partenza dall'API
function caricaViaggiInPartenza() {
    fetch('/api/viaggi/in-partenza')
        .then(response => {
            if (!response.ok) {
                throw new Error('Errore nella chiamata API');
            }
            return response.json();
        })
        .then(viaggi => {
            mostraViaggiInPartenza(viaggi); // Funzione per mostrare i viaggi
        })
        .catch(error => {
            console.error('Errore nel caricamento dei viaggi in partenza:', error);
            document.getElementById('viaggiProssimi').innerHTML = '<p>Nessun viaggio disponibile al momento.</p>';
        });
}

// Funzione per mostrare i viaggi in partenza nella sezione HTML
function mostraViaggiInPartenza(viaggi) {
    const container = document.getElementById('viaggiProssimi');
    container.innerHTML = ''; // Pulisci il contenitore prima di inserire nuovi viaggi

    if (viaggi.length === 0) {
        container.innerHTML = '<p>Nessun viaggio disponibile al momento.</p>';
    } else {
        viaggi.forEach(viaggio => {
            const paese = viaggio.paese.stato; // Ottieni il nome del paese
            const dataPartenza = viaggio.data_Partenza; // Data di partenza
            const prezzo = viaggio.prezzo;
            const img = viaggio.paese.img; // Immagine del paese

            // Costruisci l'elemento HTML per ogni viaggio
            const viaggioElement = `
                <div class="viaggio">
                    <img src="${img}" alt="Viaggio a ${paese}">
                    <div class="viaggio-info">
                        <h3>${paese}</h3>
                        <p>Partenza: ${dataPartenza}</p>
                        <p>Prezzo: €${prezzo}</p>
                        <button class="cta-button">Prenota</button>
                    </div>
                </div>
            `;
            container.innerHTML += viaggioElement;
        });
    }
}
