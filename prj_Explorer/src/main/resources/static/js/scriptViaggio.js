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

        // Chiamata iniziale per caricare i dettagli del viaggio
        caricaDettagliViaggio();