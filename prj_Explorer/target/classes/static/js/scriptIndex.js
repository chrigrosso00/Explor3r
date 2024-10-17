// Funzione per verificare se il token JWT è presente nei cookie
function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    if (token) {
        // Se il token è presente, nascondi i link di login/registrazione e mostra il bottone di logout
        document.getElementById('loginLink').style.display = 'none';
        document.getElementById('registerLink').style.display = 'none';
        document.getElementById('logoutButton').style.display = 'block';
    } else {
        // Se il token non è presente, mostra i link di login/registrazione e nascondi il bottone di logout
        document.getElementById('loginLink').style.display = 'block';
        document.getElementById('registerLink').style.display = 'block';
        document.getElementById('logoutButton').style.display = 'none';
    }
}

// Aggiungi l'evento di logout al bottone
document.getElementById('logoutButton').addEventListener('click', function() {
    // Cancella il token JWT dal cookie
    document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    
    // Reindirizza alla pagina index.html
    window.location.href = '/';
    
    // Una volta tornati sulla pagina index, esegui di nuovo il controllo dello stato di login
    checkLoginStatus();
});

// Controlla lo stato di login al caricamento della pagina
checkLoginStatus();

// Funzione per la gestione dello slideshow
let slideIndex = 0;
showSlides();

function showSlides() {
    let slides = document.getElementsByClassName("slide");
    for (let i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";  
    }
    slideIndex++;
    if (slideIndex > slides.length) {
        slideIndex = 1;
    }    
    slides[slideIndex-1].style.display = "block";  
    setTimeout(showSlides, 5000); // Cambia immagine ogni 5 secondi
}
