document.addEventListener('DOMContentLoaded', function() {
    // Controlla l'autenticazione prima di caricare la pagina
    if (!checkAuthStatus()) {
        window.location.href = '/login';
        return;
    }

    // Carica la lista dei paesi
    caricaPaesi();

    // Gestisci l'invio del form
    document.getElementById('creaViaggioForm').addEventListener('submit', function(e) {
        e.preventDefault();
        creaViaggio();
    });
});

function checkAuthStatus() {
    return document.cookie.split('; ').find(row => row.startsWith('token=')) !== undefined;
}

function caricaPaesi() {
    fetch('/api/paesi', {
        headers: {
            'Authorization': 'Bearer ' + document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        }
    })
    .then(response => response.json())
    .then(paesi => {
        const select = document.getElementById('paese');
        paesi.forEach(paese => {
            const option = document.createElement('option');
            option.value = paese.id_paese;
            option.textContent = paese.stato;
            select.appendChild(option);
        });
    })
    .catch(error => console.error('Errore nel caricamento dei paesi:', error));
}

function creaViaggio() {
    const formData = new FormData(document.getElementById('creaViaggioForm'));
    const viaggioData = Object.fromEntries(formData.entries());

    // Converti l'ID del paese in un oggetto
    viaggioData.paese = { id_paese: parseInt(viaggioData['paese.id_paese']) };
    delete viaggioData['paese.id_paese'];

    fetch('/api/viaggi', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': 'Bearer ' + document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1]
        },
        body: JSON.stringify(viaggioData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nella creazione del viaggio');
        }
        return response.json();
    })
    .then(viaggio => {
        alert('Viaggio creato con successo!');
        // Reindirizza alla pagina dei dettagli del viaggio appena creato
        window.location.href = '';
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Si è verificato un errore durante la creazione del viaggio.');
    });
}