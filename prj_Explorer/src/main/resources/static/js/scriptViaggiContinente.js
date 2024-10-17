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
                const viaggioElement = `
                    <div class="viaggio">
                        
                        <div class="viaggio-info">
                            <h3>${viaggio.paese}</h3>
                            <p>Partenza: ${viaggio.dataPartenza}</p>
                            <p>Prezzo: €${viaggio.prezzo}</p>
                            <button class="cta-button">Prenota</button>
                        </div>
                    </div>
                `;
                container.innerHTML += viaggioElement;
            });
        }
    }

    // Chiamata iniziale per caricare i viaggi
    caricaViaggi();