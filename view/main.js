async function translateText() {
    const text = document.getElementById('text').value;
    const sourceLang = document.getElementById('sourceLang').value;
    const targetLang = document.getElementById('targetLang').value;

    const data = {
        text: text,
        sourceLanguage: sourceLang,
        targetLanguage: targetLang
    };

    try {
        const response = await fetch('http://localhost:8080/translate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        const resultDiv = document.getElementById('result');

        if (response.ok) {
            const translatedText = await response.text();
            resultDiv.innerHTML = `<strong>Translated Text:</strong> ${translatedText}`;
            resultDiv.style.color = 'black';
        } else {
            const errorText = await response.text();
            resultDiv.innerHTML = `<strong>Error:</strong> ${errorText}`;
            resultDiv.style.color = 'red';
        }
    } catch (error) {
        const resultDiv = document.getElementById('result');
        resultDiv.innerHTML = `<strong>Error:</strong> ${error.message}`;
        resultDiv.style.color = 'red';
    }
}