function caricaUtente() {
	
	let user = JSON.parse(localStorage.getItem('username'));
	
	let title = document.createElement('h1');
    title.textContent = 'Profilo ' + user;
    document.body.insertBefore(title, document.querySelector('main'));
	
	let container = document.getElementById('left-section');
    container.innerHTML = ''; 
			
	fetch(`api/utente/nominativo/${user}`)
       	.then(response => response.json())
        .then(userDetails => {
		
        let userDiv = document.createElement('div');
        userDiv.classList.add('user');
         
        let details = document.createElement('h2');
        details.textContent = "Dati personali: ";
        userDiv.appendChild(details);
         
        let name = document.createElement('p');
        name.textContent = "Nome: " + userDetails.nome;
        userDiv.appendChild(name);
                
        let surname = document.createElement('p');
        surname.textContent = "Cognome: " + userDetails.cognome;
    	userDiv.appendChild(surname);
    	
    	let birth = document.createElement('p');
        birth.textContent = "Data Nascita: " + userDetails.data_nascita;
    	userDiv.appendChild(birth);
    	
    	let phone = document.createElement('p');
        phone.textContent = "Numero di telefono: " + userDetails.telefono;
    	userDiv.appendChild(phone);
                
        container.appendChild(userDiv);
    })
    .catch(error => {
         console.error('Errore nel caricamento dei dettagli utente:', error);
    });
}

function creaViaggio(){
	let create = document.createElement('h2');
	create.textContent = 'Vuoi creare un viaggio?';
	
	let createVoyage = document.createElement('a');
	createVoyage.href = '/crea-viaggio';
    createVoyage.textContent = 'Crea il tuo viaggio ora';
    
    document.getElementById('right-section').append(create, createVoyage);
}

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

document.addEventListener('DOMContentLoaded', function() {
    checkLoginStatus();

    const logoutButton = document.getElementById('logoutButton');
    logoutButton.addEventListener('click', handleLogout);

    const cercaButton = document.getElementById('cercaButton');
    cercaButton.addEventListener('click', cercaViaggi);
    
    // Carica i viaggi in partenza quando la pagina è caricata
    caricaViaggiInPartenza();
});

function caricaPrenotazioni() {
    let user = JSON.parse(localStorage.getItem('username'));
    let load = document.createElement('h2');
    load.textContent = 'Le tue prenotazioni';
    let prenotationSection = document.getElementById('prenotation-section');
    prenotationSection.innerHTML = ''; 
    prenotationSection.appendChild(load);

    fetch(`api/prenotazioni`)
        .then(response => response.json())
        .then(prenotationDetails => {
            prenotationDetails.forEach((prenotation) => {
                if (prenotation.username === user) {
                    let dateElement = document.createElement("div");

                    let prenotationTitle = document.createElement("h4");
                    prenotationTitle.textContent = "Prenotazione";
                    dateElement.appendChild(prenotationTitle);
                    
                    let country = document.createElement("p");
                    country.textContent = "Paese: " + prenotation.paese;
                    dateElement.appendChild(country);

                    let price = document.createElement("p");
                    price.textContent = "Prezzo: €" + prenotation.prezzo;
                    dateElement.appendChild(price);

                    let departure = document.createElement("p");
                    departure.textContent = "Data Partenza: " + prenotation.data_Partenza;
                    dateElement.appendChild(departure);

                    let end = document.createElement("p");
                    end.textContent = "Data Ritorno: " + prenotation.data_Arrivo;
                    dateElement.appendChild(end);

                    let description = document.createElement("p");
                    description.textContent = "Descrizione: " + prenotation.descrizione;
                    description.classList.add("hidden-content");
                    dateElement.appendChild(description);

                    let itinerary = document.createElement("p");
                    itinerary.textContent = "Itinerario: " + prenotation.itinerario;
                    itinerary.classList.add("hidden-content");
                    dateElement.appendChild(itinerary);

                    let toggleButton = document.createElement("button");
                    toggleButton.textContent = "Leggi di più";
                    toggleButton.classList.add("toggle-button");
                    toggleButton.addEventListener("click", () => {
                        description.classList.toggle("hidden-content");
                        itinerary.classList.toggle("hidden-content");
                        toggleButton.textContent = description.classList.contains("hidden-content")
                            ? "Mostra di più"
                            : "Mostra meno";
                    });

                    dateElement.appendChild(toggleButton);
                    prenotationSection.appendChild(dateElement);
                }
            });
        })
        .catch(error => {
            console.error('Errore nel caricamento dei dettagli utente:', error);
        });
}


function caricaViaggi() {
    let user = JSON.parse(localStorage.getItem('username'));
    
    let load = document.createElement('h2');
    load.textContent = 'I tuoi viaggi creati';
    let travelSection = document.getElementById('travel-section');
    travelSection.innerHTML = ''; 
    travelSection.appendChild(load);

    fetch(`api/viaggi/nominativo/${user}`)
        .then(response => response.json())
        .then(travelDetails => {
            for(let i = 0; i < travelDetails.length; i++) {
                
                let dateElement = document.createElement("div");

                let travel = document.createElement("h4");
                travel.textContent = "Viaggio";
                dateElement.appendChild(travel);
                
                let country = document.createElement("p");
                country.textContent = "Paese: " + travelDetails[i].paese.stato;
                dateElement.appendChild(country);
                
                let price = document.createElement("p");
                price.textContent = "Prezzo: €" + travelDetails[i].prezzo;
                dateElement.appendChild(price);
                
                let departure = document.createElement("p");
                departure.textContent = "Data Partenza: " + travelDetails[i].data_Partenza;
                dateElement.appendChild(departure);

                let end = document.createElement("p");
                end.textContent = "Data Ritorno: " + travelDetails[i].data_Arrivo;
                dateElement.appendChild(end);

                // Creazione elementi Descrizione e Itinerario con classe "hidden"
                let description = document.createElement("p");
                description.textContent = "Descrizione: " + travelDetails[i].descrizione;
                description.classList.add("hidden");
                
                let itinerary = document.createElement("p");
                itinerary.textContent = "Itinerario: " + travelDetails[i].itinerario;
                itinerary.classList.add("hidden");
                
                // Bottone "Leggi di più"
                let toggleButton = document.createElement("button");
                toggleButton.textContent = "Leggi di più";
                toggleButton.onclick = function() {
                    description.classList.toggle("hidden");
                    itinerary.classList.toggle("hidden");
                    toggleButton.textContent = description.classList.contains("hidden") ? "Mostra di più" : "Mostra meno";
                };

                dateElement.appendChild(description);
                dateElement.appendChild(itinerary);
                dateElement.appendChild(toggleButton);

                let type = document.createElement("p");
                type.textContent = "Tipologia: " + travelDetails[i].tipologia;
                dateElement.appendChild(type);

                travelSection.appendChild(dateElement);
            }
        })
        .catch(error => {
            console.error('Errore nel caricamento dei dettagli utente:', error);
        });
}



document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
    caricaPrenotazioni();
    caricaViaggi();
});