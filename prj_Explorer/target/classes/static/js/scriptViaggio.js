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


        // Chiamata iniziale per caricare i dettagli del viaggio
        caricaDettagliViaggio();