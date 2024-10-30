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
    document.getElementById('maxPartecipanti').textContent = viaggio.maxPartecipanti;

    // Aggiorna la descrizione, itinerario e difficoltà
    document.getElementById('descrizione').textContent = viaggio.descrizione;
    document.getElementById('itinerario').textContent = viaggio.itinerario;
    document.getElementById('tipologia').textContent = viaggio.tipologia;
    document.getElementById('creatore').textContent = viaggio.utente.username;
    
}

// Funzione per verificare se il token JWT è presente nei cookie
function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    const loginLink = document.getElementById('loginLink');
    const registerLink = document.getElementById('registerLink');
    const logoutButton = document.getElementById('logoutButton');

    // Verifica se l'utente è loggato
    if (token) {
        // Nascondi i link di login e registrazione, mostra il bottone di logout
        loginLink.style.display = 'none';
        registerLink.style.display = 'none';
        logoutButton.style.display = 'block';

        // Verifica se l'elemento del saluto esiste già per evitare duplicati
        let usernameElement = document.getElementById('usernameSaluto');
        if (!usernameElement) {
            // Se non esiste, creane uno
            let username = document.createElement('a');
            username.textContent = "Ciao, " + JSON.parse(localStorage.getItem('username'));
            username.style.marginRight = '10px';
            username.href = '/profilo';
            username.id = 'usernameSaluto'; // Aggiungi un ID per identificare l'elemento
            logoutButton.parentNode.insertBefore(username, logoutButton);
        }
    } else {
        // Se il token non è presente, mostra i link di login e registrazione, nascondi il bottone di logout
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
        }else if (response.status === 418) {
            alert('Sei gia in viaggio in queste date');
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

function deleteButton(){
    let utenteId;
    fetch(`/api/viaggi/${id_viaggio}`)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else {
                throw new Error('Errore nella chiamata API dettagli');
            }
        })
        .then(viaggio => {
            utenteId = viaggio.utente.id_utente
        fetch(`api/utente/nominativo/${user}`)
            .then(response => {
                if (response.ok) {
                    return response.json(); // Parse JSON
                } else {
                    throw new Error('Errore nella chiamata API dettagli');
                }
            })
            .then(utente => {
                if(utenteId === utente.id_utente){
                    const cancellaButton = document.getElementById('cancella');
                    cancellaButton.removeAttribute('hidden');
                    cancellaButton.addEventListener('click', cancellaViaggio)
                }
            })
            .catch(error => {
                console.error('Errore:', error);
                document.getElementById('dettagli-viaggio').innerHTML = '<p>Errore nel caricamento dei dettagli del viaggio.</p>';
            });
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('dettagli-viaggio').innerHTML = '<p>Errore nel caricamento dei dettagli del viaggio.</p>';
        });

        let user = JSON.parse(localStorage.getItem('username'));
        
        
}

function cancellaViaggio(){
    fetch(`/api/viaggi/${id_viaggio}`)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else {
                throw new Error('Errore nella chiamata API dettagli');
            }
        })
        .then(viaggio => {
            fetch(`/api/viaggi/delete`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(viaggio)  // Invia l'oggetto Viaggio direttamente, non come proprietà
            })
            .then(response => {
                if (response.status === 200) {  // Stato corretto: 200 OK
                    alert('Cancellazione riuscita');
                } else if (response.status === 403) {
                    alert('Non puoi iscriverti al tuo viaggio.');
                } else if (response.status === 404) {
                    alert('Viaggio non trovato.');
                } else if (response.status === 400) {
                    alert('Errore: Dati di richiesta non validi. Controlla i dettagli e riprova.');
                } else if (response.status === 500) {
                    alert('Errore interno del server. Riprova più tardi.');
                } else {
                    alert('Errore durante la cancellazione. Riprova più tardi.');
                }
                window.location.href = `/profilo`
            })
            .catch(error => {
                console.error('Errore:', error);
                alert('Errore durante la cancellazione.');
            });
            
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('dettagli-viaggio').innerHTML = '<p>Errore nel caricamento dei dettagli del viaggio.</p>';
        });
}

function discriviViaggio() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));

    if (!token) {
        alert("Devi essere loggato per disiscriverti da un viaggio.");
        return;
    }

    const id_viaggio = getQueryParameter('id');
    const username = JSON.parse(localStorage.getItem('username'));

    fetch(`/api/prenotazione/delete/${username}/${id_viaggio}`, {
        method: 'DELETE',
        headers: {
            'Authorization': 'Bearer ' + token.split('=')[1]
        }
    })
    .then(response => {
        if (response.status === 200) {
            alert('Disiscrizione effettuata con successo!');
        } else if (response.status === 404) {
            alert('Prenotazione non trovata.');
        } else if (response.status === 500) {
            alert('Errore interno del server. Riprova più tardi.');
        } else {
            alert('Errore durante la disiscrizione. Riprova più tardi.');
        }
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Errore durante la disiscrizione.');
    });
}


document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();
    caricaDettagliViaggio();
    caricaPartecipanti();
    deleteButton();

    const prenotaButton = document.getElementById('prenotaButton');
    const disiscrivitiButton = document.getElementById('discrivitiButton');
    const id_viaggio = getQueryParameter('id');

    fetch(`/api/prenotazioni/viaggi/limite/${id_viaggio}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        }
    })
    .then(response => response.json())
    .then(data => {
        if (!data) {
            prenotaButton.style.display = 'none';
        }
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Errore durante la verifica del limite di partecipanti.');
    });
    
    prenotaButton.addEventListener('click', prenotaViaggio);
    disiscrivitiButton.addEventListener('click', discriviViaggio);
});