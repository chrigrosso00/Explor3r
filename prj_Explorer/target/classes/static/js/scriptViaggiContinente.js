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
            // Accedi correttamente ai dati del JSON
            const id_viaggio = viaggio.id_viaggio;
            const paese = viaggio.paese.stato; // Ottieni il nome del paese
            const dataPartenza = viaggio.data_Partenza; // Data di partenza
            const dataArrivo = viaggio.data_Arrivo; // Data di arrivo
            const prezzo = viaggio.prezzo;
            const descrizione = viaggio.descrizione;

            // Costruisci l'elemento HTML per ogni viaggio
            const viaggioElement = `
                <div class="viaggio">
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

// Chiamata iniziale per caricare i viaggi
caricaViaggi();