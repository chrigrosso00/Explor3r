// Funzione per verificare se il token JWT è presente nei cookie
    function checkLoginStatus() {
        const token = document.cookie.split('; ').find(row => row.startsWith('token='));
        const loginLink = document.getElementById('loginLink');
        const registerLink = document.getElementById('registerLink');
        const logoutButton = document.getElementById('logoutButton');

        if (token) {
            // Se il token è presente, nascondi i link di login/registrazione e mostra il bottone di logout
            loginLink.style.display = 'none';
            registerLink.style.display = 'none';
            logoutButton.style.display = 'block';

            let username = document.createElement('a');
            username.textContent = "Ciao, " + JSON.parse(localStorage.getItem('username'));
            username.style.marginRight = '10px';
            username.href = '/profilo';
            logoutButton.parentNode.insertBefore(username, logoutButton);
        } else {
            // Se il token non è presente, mostra i link di login/registrazione e nascondi il bottone di logout
            loginLink.style.display = 'block';
            registerLink.style.display = 'block';
            logoutButton.style.display = 'none';
        }
    }

    // Funzione per gestire il logout
    function handleLogout() {
        // Cancella il token JWT dal cookie
        document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        localStorage.removeItem("username");
        
        // Reindirizza alla pagina index.html
        window.location.href = '/';
    }

    // Event listener per il caricamento del DOM
    document.addEventListener('DOMContentLoaded', function() {
        checkLoginStatus();

        // Aggiungi event listener per il pulsante di logout
        const logoutButton = document.getElementById('logoutButton');
        if (logoutButton) {
            logoutButton.addEventListener('click', handleLogout);
        }
    });