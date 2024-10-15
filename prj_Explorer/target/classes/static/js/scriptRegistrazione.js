document.getElementById('utenteForm').addEventListener('submit', function(event) {
    event.preventDefault();

    let password = document.getElementById('password').value;
    let confirmPassword = document.getElementById('confirm_password').value;

    if (password !== confirmPassword) {
        alert('Le password non coincidono!');
        return;
    }
    
    let data = new Date(document.getElementById('date').value);

    let utente = {
        nome: document.getElementById('nome').value,
        cognome: document.getElementById('cognome').value,
        data_nascita: data,
        telefono: document.getElementById('phone').value,
        username: document.getElementById('username').value,
        password: password
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
