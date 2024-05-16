let originalImageUrl = null;

document.getElementById('fileInput').addEventListener('change', function(event) {
            const file = event.target.files[0];
            if (file) {
                const reader = new FileReader();
                reader.onload = function(e) {
                    originalImageUrl = e.target.result;
                    document.getElementById('outputImage').src = originalImageUrl;
                };
                reader.readAsDataURL(file);
            }
        });

function displayOriginalImage() {
            if (originalImageUrl) {
                document.getElementById('outputImage').src = originalImageUrl;
            } else {
                alert('Please upload an image first.');
            }
        }

async function convertToGrayScale() {
    const fileInput = document.getElementById('fileInput');
    if (fileInput.files.length === 0) {
        alert('Please select a file.');
        return;
    }

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch('/api/image/grayscale', {
        method: 'POST',
        body: formData,
    });

    if (response.ok) {
        const blob = await response.blob();
        const url = URL.createObjectURL(blob);
        document.getElementById('outputImage').src = url;
    } else {
        alert('Error converting to grayscale.');
    }
}

async function detectEdges() {
    const fileInput = document.getElementById('fileInput');
    if (fileInput.files.length === 0) {
        alert('Please select a file.');
        return;
    }

    const file = fileInput.files[0];
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch('/api/image/edge-detection', {
        method: 'POST',
        body: formData,
    });

    if (response.ok) {
        const blob = await response.blob();
        const url = URL.createObjectURL(blob);
        document.getElementById('outputImage').src = url;
    } else {
        alert('Error detecting edges.');
    }
}
