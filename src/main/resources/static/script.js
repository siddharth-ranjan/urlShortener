document.getElementById('shortenForm').addEventListener('submit', async function (e) {
    e.preventDefault();

    const originalUrl = document.getElementById('originalUrl').value;
    const resultElement = document.getElementById('result');

    try {
        const response = await fetch('/shorten', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({ originalUrl })
        });

        if (!response.ok) {
            throw new Error(`Server error: ${response.status}`);
        }

        const data = await response.json();
        const shortUrl = `${window.location.origin}/x/${data.shortUrl}`;

        resultElement.innerHTML = `Shortened URL: <a href="${shortUrl}" target="_blank">${shortUrl}</a>`;
    } catch (error) {
        resultElement.textContent = `Error: ${error.message}`;
    }
});
