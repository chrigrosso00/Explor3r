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
	let crea = document.createElement('h2');
	crea.textContent = 'Vuoi creare un viaggio?';
	
	let creaViaggio = document.createElement('a');
	creaViaggio.href = '/crea-viaggio';
    creaViaggio.textContent = 'Crea il tuo viaggio ora';
    
    document.getElementById('right-section').append(crea, creaViaggio);
}


function caricaPrenotazioni(){
	let user = JSON.parse(localStorage.getItem('username'));
	
	let carica = document.createElement('h2');
	carica.textContent = 'Le tue prenotazioni';
	let empty = document.createElement('p');
	
	fetch(`api/utente/nominativo/${user}`)
       	.then(response => response.json())
        .then(userDetails => {
		
		if(length==0){
			
        	empty.textContent = "Non ti sei prenotato per nessun viaggio";
	    }
		
        /*for(let i=0; i<userDetails.prenotazioni[i].length; i++){
			
			
		}*/
    	
    })
    .catch(error => {
         console.error('Errore nel caricamento dei dettagli utente:', error);
    });
	document.getElementById('prenotation-section').append(carica, empty);
}

function caricaPrenotazioni(){
	let user = JSON.parse(localStorage.getItem('username'));
	
	let carica = document.createElement('h2');
	carica.textContent = 'Le tue prenotazioni';
	let empty = document.createElement('p');
	
	fetch(`api/utente/nominativo/${user}`)
       	.then(response => response.json())
        .then(userDetails => {
		
		if(length==0){
			
        	empty.textContent = "Non ti sei prenotato per nessun viaggio";
	    }
		
        /*for(let i=0; i<userDetails.prenotazioni[i].length; i++){
			
			
		}*/
    	
    })
    .catch(error => {
         console.error('Errore nel caricamento dei dettagli utente:', error);
    });
	document.getElementById('prenotation-section').append(carica, empty);
}




document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
    caricaPrenotazioni()
});