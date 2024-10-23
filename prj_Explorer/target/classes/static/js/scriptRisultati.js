// Funzione per ottenere il parametro dall'URL
function getQueryParameter(param) {
    const urlParams = new URLSearchParams(window.location.search);
    return urlParams.get(param);
}

// Ottieni i parametri dallo stato e dalla data di partenza dall'URL
const stato = getQueryParameter('stato');
const dataPartenza = getQueryParameter('dataPartenza');

// Funzione per chiamare l'API Spring Boot e ottenere i viaggi per lo stato e la data di partenza
function caricaViaggi() {
    // Costruisci l'URL dell'API
    let apiUrl = `/api/viaggi/search?stato=${encodeURIComponent(stato)}`;
    
    if (dataPartenza) {
        apiUrl += `&dataPartenza=${dataPartenza}`;
    }

    fetch(apiUrl)
        .then(response => {
            if (response.ok) {
                return response.json(); // Parse JSON
            } else if (response.status === 204) {
                // Se non ci sono viaggi, mostra un messaggio vuoto
                document.getElementById('lista-viaggi').innerHTML = '<p>Nessun viaggio disponibile per i criteri di ricerca selezionati.</p>';
                throw new Error('Nessun contenuto');
            } else {
                throw new Error('Errore nella chiamata API');
            }
        })
        .then(viaggi => {
            mostraViaggi(viaggi); // Mostra i viaggi nella pagina
        })
        .catch(error => {
            console.error('Errore:', error);
            document.getElementById('lista-viaggi').innerHTML = '<p>Errore nel recuperare i viaggi. Riprova più tardi.</p>';
        });
}

// Funzione per mostrare i viaggi nella pagina
function mostraViaggi(viaggi) {
    const container = document.getElementById('lista-viaggi');
    container.innerHTML = ''; // Svuota il contenitore prima di inserire nuovi viaggi

    if (viaggi.length === 0) {
        container.innerHTML = '<p>Nessun viaggio disponibile per i criteri di ricerca selezionati.</p>';
    } else {
        viaggi.forEach(viaggio => {
            // Accedi ai dati del viaggio
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
    window.location.href = `viaggio?id=${id_viaggio}`; // Reindirizza alla pagina del viaggio specifico
}

// Chiamata iniziale per caricare i viaggi in base ai parametri della query string
caricaViaggi();
