

function caricaUtente() {
	
	let user = JSON.parse(localStorage.getItem('username'));
			
	fetch(`api/utente/nominativo/${user}`)
       	.then(response => response.json())
        .then(userDetails => {
		
		let container = document.getElementById('left-section');
        container.innerHTML = ''; 
        
        let userDiv = document.createElement('div');
        userDiv.classList.add('user');

        let username = document.createElement('h1');
        username.textContent = userDetails.username;
        userDiv.appendChild(username);
         
        let name = document.createElement('p');
        name.textContent = userDetails.nome;
        userDiv.appendChild(name);
                
        let surname = document.createElement('p');
        surname.textContent = userDetails.cognome;
        userDiv.appendChild(surname);
                
        container.appendChild(userDiv);
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

document.addEventListener('DOMContentLoaded', () => {
    caricaUtente();
    creaViaggio();
});