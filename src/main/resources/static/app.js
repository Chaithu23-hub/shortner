function shortenUrl() {

    const url = document.getElementById("longUrl").value;
    const resultDiv = document.getElementById("result");

    if (!url) {
        resultDiv.innerText = "Please enter a URL";
        return;
    }

        fetch("http://localhost:8080/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ url: url })
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("API error");
        }
        return response.json();
    })
    .then(data => {
        const shortLink =
            "http://localhost:8080/" + data.shortCode;

        resultDiv.innerHTML = `
            Short URL:
            <br/>
            <a href="${shortLink}" target="_blank">
                ${shortLink}
            </a>
        `;
    })
    .catch(error => {
        resultDiv.innerText =
            "Failed to create short URL (check backend)";
    });
}
