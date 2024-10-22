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
	let h2 = document.createElement('h2');
	h2.textContent = 'Vuoi creare un viaggio?';
	
	let creaViaggio = document.createElement('a');
	creaViaggio.href = '/crea-viaggio';
    creaViaggio.textContent = 'Crea il tuo viaggio ora';
    
    document.getElementById('right-section').append(h2, creaViaggio);
}

function caricaPrenotazioni() {
    let h2 = document.createElement('h2');
    h2.textContent = 'Le tue prenotazioni';
    
    let prenotazioniDiv = document.createElement('div');
    prenotazioniDiv.textContent = 'Qui verranno visualizzate le tue prenotazioni.';
    
    document.getElementById('container-prenotation').appendChild(prenotazioniDiv);
}

function caricaViaggi() {
    let h2 = document.createElement('h2');
    h2.textContent = 'I tuoi viaggi';
    
    let viaggiDiv = document.createElement('div');
    viaggiDiv.textContent = 'Qui verranno visualizzati i tuoi viaggi.';
    
    document.getElementById('container-travel').append(document.getElementById('container-travel'),h2, viaggiDiv);
}

document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
    caricaPrenotazioni();
    caricaViaggi();
});