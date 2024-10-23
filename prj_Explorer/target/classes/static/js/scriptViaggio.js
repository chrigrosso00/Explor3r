// Funzione per ottenere il parametro ID dall'URL
function getQueryParameter(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Ottieni l'ID del viaggio dall'URL
const id_viaggio = getQueryParameter('id');

// Funzione per caricare i dettagli del viaggio dall'API
function caricaDettagliViaggio() {
    fetch(`/api/viaggi/${id_viaggio}`)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else {
                throw new Error('Errore nella chiamata API dettagli');
            }
        })
        .then(viaggio => {
            mostraDettagliViaggio(viaggio); // Mostra i dettagli del viaggio
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('dettagli-viaggio').innerHTML = '<p>Errore nel caricamento dei dettagli del viaggio.</p>';
        });
}

function caricaPartecipanti(){
    fetch(`/api/viaggi/partecipanti/${id_viaggio}`)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else {
                throw new Error('Errore nella chiamata API partecipanti');
            }
        })
        .then(partecipanti => {
            mostraPartecipantiViaggio(partecipanti)
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('dettagli-viaggio').innerHTML = '<p>Errore nel caricamento dei partecipanti del viaggio.</p>';
        });
}

function mostraPartecipantiViaggio(partecipanti){
    const dettagliViaggio = document.getElementById('partecipanti-viaggio');
    const listaPartecipanti = document.createElement('ul');

    partecipanti.forEach(partecipante => {
        const listItem = document.createElement('li');
        listItem.textContent = partecipante.username;
        listaPartecipanti.appendChild(listItem);
    });

    dettagliViaggio.innerHTML = '<h3>Partecipanti al Viaggio:</h3>';
    dettagliViaggio.appendChild(listaPartecipanti);
}

// Funzione per mostrare i dettagli del viaggio nella pagina
function mostraDettagliViaggio(viaggio) {
    // Aggiorna l'immagine del viaggio
    const imgSection = document.getElementById('immagine-viaggio');
    imgSection.style.backgroundImage = `url('images/${viaggio.paese.stato}.jpg')`;

    // Aggiorna il nome del paese
    const nomePaese = document.getElementById('nome-paese');
    nomePaese.textContent = `Viaggio in ${viaggio.paese.stato}`;

    // Aggiorna la data di partenza, arrivo e prezzo
    document.getElementById('data-partenza').textContent = viaggio.data_Partenza;
    document.getElementById('data-arrivo').textContent = viaggio.data_Arrivo;
    document.getElementById('prezzo').textContent = viaggio.prezzo;

    // Aggiorna la descrizione, itinerario e difficoltà
    document.getElementById('descrizione').textContent = viaggio.descrizione;
    document.getElementById('itinerario').textContent = viaggio.itinerario;
    document.getElementById('tipologia').textContent = viaggio.tipologia;
}

// Funzione per verificare se il token JWT è presente nei cookie
function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    const loginLink = document.getElementById('loginLink');
    const registerLink = document.getElementById('registerLink');
    const logoutButton = document.getElementById('logoutButton');

    if (token) {
        // Se il token è presente, nascondi i link di login/registrazione e mostra il bottone di logout
        loginLink.style.display = 'none';
        registerLink.style.display = 'none';
        logoutButton.style.display = 'block';

        let username = document.createElement('a');
        username.textContent = "Ciao, " + JSON.parse(localStorage.getItem('username'));
        username.style.marginRight = '10px';
        username.href = '/profilo';
        logoutButton.parentNode.insertBefore(username, logoutButton);
    } else {
        // Se il token non è presente, mostra i link di login/registrazione e nascondi il bottone di logout
        loginLink.style.display = 'block';
        registerLink.style.display = 'block';
        logoutButton.style.display = 'none';
    }
}

// Funzione per gestire il logout
function handleLogout() {
    // Cancella il token JWT dal cookie
    document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    localStorage.removeItem("username");

    // Reindirizza alla pagina index.html
    window.location.href = '/';
}

// Event listener per il caricamento del DOM
document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();

    // Aggiungi event listener per il pulsante di logout
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', handleLogout);
    }

    // Carica i dettagli del viaggio alla fine
    caricaDettagliViaggio();
});

function prenotaViaggio() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));

    if (!token) {
        alert("Devi essere loggato per prenotare un viaggio.");
        return;
    }

    const id_viaggio = getQueryParameter('id');

    fetch(`/api/prenotazione`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + token.split('=')[1]
        },
        body: JSON.stringify({ id_viaggio: id_viaggio })
    })
    .then(response => {
        if (response.status === 201) {
            alert('Prenotazione effettuata con successo!');
        } else if (response.status === 403) {
            alert('Non puoi iscriverti al tuo viaggio.');
        } else if (response.status === 409) {
            alert('Sei già iscritto a questo viaggio.');
        } else if (response.status === 400) {
            alert('Errore: Dati di richiesta non validi. Controlla i dettagli e riprova.');
        } else if (response.status === 500) {
            alert('Errore interno del server. Riprova più tardi.');
        } else {
            alert('Errore durante la prenotazione. Riprova più tardi.');
        }
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Errore durante la prenotazione.');
    });
}


document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();
    caricaDettagliViaggio();
    caricaPartecipanti();

    // Aggiungi event listener per il pulsante di prenotazione
    const prenotaButton = document.getElementById('prenotaButton');
    prenotaButton.addEventListener('click', prenotaViaggio);
});