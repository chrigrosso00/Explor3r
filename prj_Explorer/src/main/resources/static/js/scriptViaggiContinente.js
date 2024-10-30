// Funzione per ottenere il parametro dall'URL
function getQueryParameter(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Ottieni il continente dall'URL
const continente = getQueryParameter('continente');
document.getElementById('titolo-continente').innerText = 'Viaggi in ' + continente;

// Funzione per chiamare l'API Spring Boot e ottenere i viaggi per il continente
function caricaViaggi() {
    fetch(`/api/viaggi/continente?continente=${continente}`)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else {
                throw new Error('Errore nella chiamata API');
            }
        })
        .then(viaggi => {
            mostraViaggi(viaggi); // Mostra i viaggi nella pagina
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('lista-viaggi').innerHTML = '<p>Nessun viaggio disponibile per il continente selezionato.</p>';
        });
}

// Funzione per mostrare i viaggi nella pagina
function mostraViaggi(viaggi) {
    const container = document.getElementById('lista-viaggi');
    container.innerHTML = ''; // Svuota il contenitore prima di inserire nuovi viaggi

    if (viaggi.length === 0) {
        container.innerHTML = '<p>Nessun viaggio disponibile per il continente selezionato.</p>';
    } else {
        viaggi.forEach(viaggio => {
            const id_viaggio = viaggio.id_viaggio;
            const paese = viaggio.paese.stato; // Ottieni il nome del paese
            const dataPartenza = viaggio.data_Partenza; // Data di partenza
            const dataArrivo = viaggio.data_Arrivo; // Data di arrivo
            const prezzo = viaggio.prezzo;
            const descrizione = viaggio.descrizione;
            const immagine = viaggio.paese.img;
            
            // Costruisci l'elemento HTML per ogni viaggio
             const viaggioElement = `
                <div class="viaggio">
                    <div class="immagine-viaggio">
                        <img src="${immagine}" alt="Immagine di ${paese}">
                    </div>
                    <div class="viaggio-info">
                        <h3>Paese: ${paese}</h3>
                        <p>Descrizione: ${descrizione}</p>
                        <p>Partenza: ${dataPartenza}</p>
                        <p>Arrivo: ${dataArrivo}</p>              
                        <p>Prezzo: €${prezzo}</p>
                        <button class="cta-button" onclick="apriPaginaViaggio(${id_viaggio})">Info</button>
                    </div>
                </div>
            `;
            container.innerHTML += viaggioElement;
        });
    }
}

// Funzione che apre la pagina del viaggio specifico con l'ID passato
function apriPaginaViaggio(id_viaggio) {
    window.location.href = `viaggio?id=${id_viaggio}`; // Reindirizza alla pagina con il parametro ID
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

    // Carica i viaggi alla fine
    caricaViaggi();
});
