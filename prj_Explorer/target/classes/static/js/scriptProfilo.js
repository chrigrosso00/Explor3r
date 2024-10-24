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

function caricaPrenotazioni(){
	let user = JSON.parse(localStorage.getItem('username'));
	
	let load = document.createElement('h2');
	load.textContent = 'Le tue prenotazioni';
	let empty = document.createElement('p');
	
	fetch(`api/prenotazioni`)
       	.then(response => response.json())
        .then(prenotationDetails => {
		
		if(prenotationDetails.prenotazioni.length<=0){
			empty.textContent = "Non ti sei prenotato per nessun viaggio";
	    }else{
		
		}
		
     
    	
    })
    .catch(error => {
         console.error('Errore nel caricamento dei dettagli utente:', error);
    });
	document.getElementById('prenotation-section').append(load, empty, state);
}




document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
    caricaPrenotazioni()
});