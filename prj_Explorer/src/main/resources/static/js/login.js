

document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    username = document.getElementById('username').value;
	localStorage.setItem('username', JSON.stringify(username));
    let password = document.getElementById('password').value;

    let csrfToken = document.querySelector('meta[name="csrf-token"]').getAttribute('content');

    fetch('/api/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'X-CSRF-TOKEN': csrfToken
        },
        body: JSON.stringify({ username: username, password: password })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Invalid username or password');
        }
        return response.json();
    })
    .then(data => {
        if (data.token) {
            document.cookie = `token=${data.token}; path=/`;
            
            let token = document.cookie.split('; ').find(row => row.startsWith('token=')).split('=')[1];
			let decodedToken = JSON.parse(atob(token.split('.')[1]));	
    		let expirationTime = decodedToken.exp * 1000 - Date.now();
    		

		    setTimeout(() => {
		    	logoutUser();
		    }, expirationTime);
    
            window.location.href = '/';
            console.log('Expiration time in milliseconds:', expirationTime);
        }
    })
    .catch(error => {
        console.error('Errore:', error);
        alert('Errore nel login: ' + error.message);
    });
});

function fetchWithAuth(url, options = {}) {
    const token = sessionStorage.getItem('token');
    if (token) {
        options.headers = {
            ...options.headers,
            'Authorization': `Bearer ${token}`
        };
    }
    return fetch(url, options);
}

function logoutUser() {
    document.getElementById('logoutButton').addEventListener('click', function() {
        document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        window.location.href = '/login';
    });
}

function checkLoginStatus() {
    let token = document.cookie.split('; ').find(row => row.startsWith('token='));
    if (token) {
        document.getElementById('logoutButton').style.display = 'block';
    } else {
        document.getElementById('logoutButton').style.display = 'none';
    }
}

checkLoginStatus();