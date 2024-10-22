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
                throw new Error('Errore nella chiamata API');
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

<<<<<<< Updated upstream
        // Funzione per mostrare i dettagli del viaggio nella pagina
        function mostraDettagliViaggio(viaggio) {
    // Aggiorna l'immagine del viaggio
    const imgSection = document.getElementById('immagine-viaggio');
    imgSection.style.backgroundImage = `url('img/${viaggio.paese.stato}.jpg')`;

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

=======
// Funzione per mostrare i dettagli del viaggio nella pagina
function mostraDettagliViaggio(viaggio) {
    const container = document.getElementById('dettagli-viaggio');
    container.innerHTML = `
        <h2>Paese: ${viaggio.paese.stato}</h2>
        <p>Descrizione: ${viaggio.descrizione}</p>
        <p>Arrivo: ${viaggio.data_Arrivo}</p>
        <p>Partenza: ${viaggio.data_Partenza}</p>
        <p>Prezzo: €${viaggio.prezzo}</p>
        <p>Itinerario: ${viaggio.itinerario}</p>
        <p>Difficoltà: ${viaggio.difficolta}</p>
    `;
}
>>>>>>> Stashed changes

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
