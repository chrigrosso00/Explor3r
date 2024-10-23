// Funzione per verificare se il token JWT è presente nei cookie
function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    const loginLink = document.getElementById('loginLink');
    const registerLink = document.getElementById('registerLink');
    const logoutButton = document.getElementById('logoutButton');
    const creaViaggioLink = document.getElementById('creaViaggioLink');

    if (token) {
        loginLink.style.display = 'none';
        registerLink.style.display = 'none';
        logoutButton.style.display = 'block';
        creaViaggioLink.style.display = 'inline-block';

        let username = document.createElement('a');
        username.textContent = "Ciao, " + JSON.parse(localStorage.getItem('username'));
        username.style.marginRight = '10px';
        username.href = '/profilo';
        logoutButton.parentNode.insertBefore(username, logoutButton);
    } else {
        loginLink.style.display = 'block';
        registerLink.style.display = 'block';
        logoutButton.style.display = 'none';
        creaViaggioLink.style.display = 'none';
    }
}

// Funzione per gestire il logout
function handleLogout() {
    document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    localStorage.removeItem("username");
    window.location.href = '/';
}

// Funzione per reindirizzare gli utenti non autenticati
function redirectIfNotAuth() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    if (!token) {
        window.location.href = '/login';
    }
}

// Funzione per reindirizzare l'utente alla pagina dei risultati con i parametri di ricerca
function cercaViaggi() {
    const destinazione = document.getElementById('destinazione').value;
    const checkInDate = document.getElementById('checkInDate').value;
    const tipologiaViaggio = document.getElementById('tipologiaViaggio').value; // Ottieni la tipologia selezionata

    if (!destinazione) {
        alert('Per favore inserisci una destinazione.');
        return;
    }

    let url = `risultati?stato=${encodeURIComponent(destinazione)}`;
    if (checkInDate) {
        url += `&dataPartenza=${checkInDate}`;
    }
    if (tipologiaViaggio) {
        url += `&tipologia=${tipologiaViaggio}`; 
    }

    window.location.href = url;
}


// Funzione per mostrare i suggerimenti in base all'input dell'utente
function showSuggestions(query) {
    const suggestionsBox = document.getElementById('suggestions');
    const searchSpinner = document.getElementById('searchSpinner');

    if (query.length < 2) {
        suggestionsBox.classList.remove('active');
        return;
    }

    // Mostra lo spinner
    searchSpinner.classList.add('active');

    // Chiamata API per i suggerimenti
    fetch(`/api/suggestions?query=${encodeURIComponent(query)}`)
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.json();
        })
        .then(data => {
            suggestionsBox.innerHTML = '';  // Svuota i suggerimenti precedenti

            if (data.length === 0) {
                suggestionsBox.classList.remove('active'); // Nessun suggerimento
                return;
            }

            data.forEach(suggestion => {
                const suggestionItem = document.createElement('div');
                suggestionItem.className = 'suggestion-item';
                suggestionItem.innerHTML = `
                    
                    <span>${suggestion}</span>
                `;

                suggestionItem.addEventListener('click', function() {
                    document.getElementById('destinazione').value = suggestion;
                    suggestionsBox.classList.remove('active');
                });

                suggestionsBox.appendChild(suggestionItem);
            });

            suggestionsBox.classList.add('active');
        })
        .catch(error => {
            console.error('Errore nel recupero dei suggerimenti:', error);
        })
        .finally(() => {
            searchSpinner.classList.remove('active');
        });
}

// Event listener per il caricamento del DOM
document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();

    // Aggiungi event listener per il pulsante di logout
    const logoutButton = document.getElementById('logoutButton');
    if (logoutButton) {
        logoutButton.addEventListener('click', handleLogout);
    }

    // Event listener per il pulsante "Cerca"
    document.getElementById('cercaButton').addEventListener('click', function(event) {
        event.preventDefault();  // Previene il submit del form standard
        cercaViaggi();  // Chiama la funzione di ricerca
    });

    // Event listener per l'input del campo destinazione
    document.getElementById('destinazione').addEventListener('input', function(event) {
        const query = event.target.value;
        showSuggestions(query);  // Mostra i suggerimenti mentre l'utente digita
    });
	
	// Chiusura dei suggerimenti quando si clicca fuori
	document.addEventListener('click', function(e) {
        const suggestionsBox = document.getElementById('suggestions');
        const destinazioneInput = document.getElementById('destinazione');
        
        if (!destinazioneInput.contains(e.target) && !suggestionsBox.contains(e.target)) {
            suggestionsBox.classList.remove('active');
        }
    });

    // Gestione focus dell'input
    document.getElementById('destinazione').addEventListener('focus', function() {
        if (this.value.length >= 2) {
            document.getElementById('suggestions').classList.add('active');
        }
    });

    // Aggiungi debounce per la ricerca
    let debounceTimer;
    document.getElementById('destinazione').addEventListener('input', function(e) {
        clearTimeout(debounceTimer);
        debounceTimer = setTimeout(() => {
            showSuggestions(e.target.value);
        }, 300);
    });
});

// Funzione per la gestione dello slideshow
let slideIndex = 0;
showSlides();

function showSlides() {
    let slides = document.getElementsByClassName("slide");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }
    slides[slideIndex - 1].style.display = "block";
    setTimeout(showSlides, 5000); // Cambia immagine ogni 5 secondi
}
