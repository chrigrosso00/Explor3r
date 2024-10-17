document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    let username = document.getElementById('username').value;
    let password = document.getElementById('password').value;

    console.log('Username:', username);
    console.log('Password:', password);

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
            window.location.href = '/';
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

document.getElementById('logoutButton').addEventListener('click', function() {
    document.cookie = 'token=; Path=/; Expires=Thu, 01 Jan 1970 00:00:01 GMT;';
    window.location.href = '/login';
});

function checkLoginStatus() {
    const token = document.cookie.split('; ').find(row => row.startsWith('token='));
    if (token) {
        document.getElementById('logoutButton').style.display = 'block';
    } else {
        document.getElementById('logoutButton').style.display = 'none';
    }
}

checkLoginStatus();