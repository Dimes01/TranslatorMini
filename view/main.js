document.addEventListener('DOMContentLoaded', () => {
    const textArea = document.getElementById('text');
    textArea.addEventListener('input', autoResize, false);

    function autoResize() {
        this.style.height = 'auto';
        this.style.height = (this.scrollHeight) + 'px';
    }
});

async function translateText() {
    const text = document.getElementById('text').value;
    const sourceLang = document.getElementById('sourceLang').value;
    const targetLang = document.getElementById('targetLang').value;
    const loadingIndicator = document.getElementById('loadingIndicator');
    const resultDiv = document.getElementById('result');

    const data = {
        text: text,
        sourceLanguage: sourceLang,
        targetLanguage: targetLang
    };

    loadingIndicator.classList.remove('hidden');
    resultDiv.innerHTML = '';

    try {
        const response = await fetch('http://localhost:8080/translate', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(data)
        });

        if (response.ok) {
            const jsonResponse = await response.json();
            const translatedText = jsonResponse.translatedText;
            resultDiv.innerHTML = `<strong>Translated Text:</strong> ${translatedText}`;
            resultDiv.style.color = 'black';
        } else {
            const errorText = await response.text();
            resultDiv.innerHTML = `<strong>Error:</strong> ${errorText}`;
            resultDiv.style.color = 'red';
        }
    } catch (error) {
        resultDiv.innerHTML = `<strong>Error:</strong> ${error.message}`;
        resultDiv.style.color = 'red';
    } finally {
        loadingIndicator.classList.add('hidden');
    }
}
