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

async function fetchLogs() {
    const loadingIndicator = document.getElementById('loadingIndicator');
    const logContainer = document.getElementById('logContainer');
    const logList = document.getElementById('logList');

    loadingIndicator.classList.remove('hidden');
    logList.innerHTML = '';

    try {
        const response = await fetch('http://localhost:8080/translate/get');

        if (response.ok) {
            const logs = await response.json();
            logs.forEach(log => {
                const logItem = document.createElement('li');
                logItem.innerHTML = `
                    <strong>Source Text:</strong> ${log.sourceText}<br>
                    <strong>Translated Text:</strong> ${log.translatedText}
                `;
                logList.appendChild(logItem);
            });
            logContainer.classList.remove('hidden');
        } else {
            const errorText = await response.text();
            alert(`Error: ${errorText}`);
        }
    } catch (error) {
        alert(`Error: ${error.message}`);
    } finally {
        loadingIndicator.classList.add('hidden');
    }
}
