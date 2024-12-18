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
        e.preventDefault(); // Impedisce l'invio standard del form
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
    .then(response => {
        if (!response.ok) {
            throw new Error('Errore nel caricamento dei paesi');
        }
        return response.json();
    })
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

/*function creaViaggio() {
    const formData = new FormData(document.getElementById('creaViaggioForm'));
    const viaggioData = Object.fromEntries(formData.entries());

    // Verifica le date di partenza e arrivo
    const dataPartenza = new Date(viaggioData.data_Partenza);
    const dataArrivo = new Date(viaggioData.data_Arrivo);
    
    if (dataArrivo <= dataPartenza) {
        alert("La data di arrivo deve essere successiva alla data di partenza.");
        return; // Blocca l'invio se le date non sono valide
    }

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
        if(response.status === 409){
            alert('Sei già in un viaggio in queste date');
            throw new Error('Sei già in un altro viaggio');
        }
        else if (!response.ok) {
            throw new Error('Errore nella creazione del viaggio');
        }
        
        return response.json();
    })
    .then(viaggio => {
        alert('Viaggio creato con successo!');
        // Reindirizza alla pagina dei dettagli del viaggio appena creato
        window.location.href = `/profilo`; 
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Si è verificato un errore durante la creazione del viaggio.');
    });
}*/

document.getElementById('creaViaggioForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Previene l'invio predefinito del form

    const submitButton = document.querySelector('.btn-submit');
    submitButton.disabled = true; // Disabilita il pulsante

    const formData = new FormData(this);
    const viaggioData = Object.fromEntries(formData.entries());

    // Verifica le date di partenza e arrivo
    const dataPartenza = new Date(viaggioData.data_Partenza);
    const dataArrivo = new Date(viaggioData.data_Arrivo);
    
    if (dataArrivo <= dataPartenza) {
        alert("La data di arrivo deve essere successiva alla data di partenza.");
        submitButton.disabled = false; // Riabilita il pulsante
        return; // Blocca l'invio se le date non sono valide
    }
	
	if (dataPartenza <= new Date()) {
	        alert("Errore: La data di partenza deve essere successiva alla data odierna.");
	        submitButton.disabled = false; // Riabilita il pulsante
	        return; // Blocca l'invio se la data di partenza non è valida
	    }

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
        if(response.status === 409){
            alert('Sei già in un viaggio in queste date');
            throw new Error('Sei già in un altro viaggio');
        }
        else if (!response.ok) {
            throw new Error('Errore nella creazione del viaggio');
        }
        
        return response.json();
    })
    .then(viaggio => {
        alert('Viaggio creato con successo!');
        // Reindirizza alla pagina dei dettagli del viaggio appena creato
        window.location.href = `/profilo`; 
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Si è verificato un errore durante la creazione del viaggio.');
    })
    .finally(() => {
        submitButton.disabled = false; // Riabilita il pulsante alla fine
    });
});
