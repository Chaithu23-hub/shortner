function shortenUrl() {
    const url = document.getElementById("longUrl").value;

    fetch("/shorten", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ url: url })
    })
    .then(response => response.json())
    .then(data => {
        const shortLink = window.location.origin + "/" + data.shortCode;

        document.getElementById("result").innerHTML =
            `<p>Short URL:</p>
             <a href="${shortLink}" target="_blank">${shortLink}</a>`;
    })
    .catch(error => {
        document.getElementById("result").innerText = "Error creating short URL";
    });
}
