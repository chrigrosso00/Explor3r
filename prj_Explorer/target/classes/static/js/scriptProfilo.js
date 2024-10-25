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
            for(let i = 0; i < prenotationDetails.length; i++) {
                if(prenotationDetails[i].username === user) {
                    
                    let dateElement = document.createElement("div");
					
					let prenotation = document.createElement("h4");
                    prenotation.textContent = "Prenotazione";
                    dateElement.appendChild(prenotation);
                    
                    let country = document.createElement("p");
                    country.textContent = "Paese: " + prenotationDetails[i].paese;
                    dateElement.appendChild(country);
					
                    let departure = document.createElement("p");
                    departure.textContent = "Data Partenza: " + prenotationDetails[i].data_Partenza;
                    dateElement.appendChild(departure);

                    let end = document.createElement("p");
                    end.textContent = "Data Ritorno: " + prenotationDetails[i].data_Arrivo;
                    dateElement.appendChild(end);

                    let description = document.createElement("p");
                    description.textContent = "Descrizione: " + prenotationDetails[i].descrizione;
                    dateElement.appendChild(description);

                    let itinerary = document.createElement("p");
                    itinerary.textContent = "Itinerario: " + prenotationDetails[i].itinerario;
                    dateElement.appendChild(itinerary);

                    let type = document.createElement("p");
                    type.textContent = "Tipologia: " + prenotationDetails[i].tipologia;
                    dateElement.appendChild(type);

                    let price = document.createElement("p");
                    price.textContent = "Prezzo: " + prenotationDetails[i].prezzo;
                    dateElement.appendChild(price);

                    prenotationSection.appendChild(dateElement);
                }
            }
        })
        .catch(error => {
            console.error('Errore nel caricamento dei dettagli utente:', error);
        });
}

document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
    caricaPrenotazioni()
});