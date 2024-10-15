document.getElementById('utenteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    let utente = {
        nome: document.getElementById('nome').value,
        cognome: document.getElementById('cognome').value,
        username: document.getElementById('username').value,
        password: document.getElementById('password').value
    };

    fetch('http://localhost:8080/api/add/utente', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(utente)
    })
    .then(response => response.json())
    .then(data => {
        console.log('Success:', data);
        alert('Utente aggiunto con successo!');
    })
    .catch((error) => {
        console.error('Error:', error);
        alert('Errore nell\'aggiunta dell\'utente.');
    });
});