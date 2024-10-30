document.getElementById('utenteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    let password = document.getElementById('password').value;
    let confirmPassword = document.getElementById('confirm_password').value;

    // Verifica che le password coincidano
    if (password !== confirmPassword) {
        alert('Le password non coincidono!');
        return;
    }
    
    // Calcola l'età
    let data = new Date(document.getElementById('date').value);
    let today = new Date();
    let age = today.getFullYear() - data.getFullYear();
    let monthDifference = today.getMonth() - data.getMonth();

    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < data.getDate())) {
        age--;
    }

    // Verifica l'età minima
    if (age < 18) {
        alert('Devi avere almeno 18 anni per registrarti.');
        return;
    }

    // Crea l'oggetto utente
    let utente = {
        nome: document.getElementById('nome').value,
        cognome: document.getElementById('cognome').value,
        email: document.getElementById('email').value,
        data_nascita: data,
        telefono: document.getElementById('phone').value,
        username: document.getElementById('username').value,
        password: password
    };

    // Invia la richiesta al server
    fetch('http://localhost:8080/api/add/utente', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(utente)
    })
    .then(response => {			
        // Controlla se la risposta è 409 (email già esistente)
        if (response.status === 409) {
            throw new Error('L\'email è già registrata. Utilizza un\'altra email.');
        }
        if (!response.ok) {
            throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
    })
    .then(data => {
        console.log('Success:', data);
        alert('Utente aggiunto con successo!');
        window.location.href = '/login';
    })
    .catch(error => {
        console.error('Error:', error);
        if (error.message.includes("L'email è già registrata")) {
            alert(error.message); // Messaggio specifico per email duplicata
        } else {
            alert('Errore nell\'aggiunta dell\'utente.');
        }
    });
});
