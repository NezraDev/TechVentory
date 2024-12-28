const submitButton = document.getElementById("submit");
const successModal = document.getElementById("successModal");
const closeModalButton = document.getElementById("closeModal");
const content = document.getElementById("content");

submitButton.addEventListener('click', (event) => {
    event.preventDefault(); // Prevent form from submitting the traditional way

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

  
    if (username === password) {
        alert("Username and password cannot be the same. Please choose different values.");
        return; 
    }

    const data = {
        username, password
    };

    fetch('/authentication/register', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    }).then(response => response.json())
        .then(data => {
            console.log('Success:', data);

            // Show the success modal with animation
            successModal.classList.remove("hidden");

            // Apply blur to the entire body
            document.body.classList.add('blur-sm');

            // Reset the form fields after successful registration
            document.getElementById("username").value = "";
            document.getElementById("password").value = "";
        })
        .catch((error) => {
            console.error('Error:', error);
        });
});

closeModalButton.addEventListener('click', () => {
    successModal.classList.add("hidden"); // Hide the modal when the close button is clicked

    // Remove blur from the body
    document.body.classList.remove('blur-sm');

    // Reset form fields again (if necessary)
    document.getElementById("username").value = "";
    document.getElementById("password").value = "";
});

document.getElementById('productForm').addEventListener('submit', function(event) {
    event.preventDefault();

    var form = document.getElementById('productForm');
    var formData = new FormData(form);

    fetch('/api/product', {
        method: 'POST',
        body: formData
    })
    .then(response => {
        if (response.ok) {
            alert('Product created successfully');
            window.location.href = '/products';
        } else {
            return response.json().then(data => {
                document.getElementById('nameError').innerText = data.errors.name || '';
                document.getElementById('categoryError').innerText = data.errors.category || '';
                document.getElementById('priceError').innerText = data.errors.price || '';
                document.getElementById('imageFileError').innerText = data.errors.imageFile || '';
            });
        }
    })
    .catch(error => console.error('Error:', error));
});
function previewImage(event) {
    var file = event.target.files[0];
    if (!file) return;
  
    var reader = new FileReader();
    reader.onload = function(e) {
      var previewContainer = document.getElementById('imagePreviewContainer');
      var imgElement = document.createElement('img');
      imgElement.src = e.target.result;
      imgElement.classList.add('max-w-xs', 'rounded-lg', 'border', 'border-gray-300');
      imgElement.style.width = '100px';  // Set default width
      imgElement.style.height = '100px'; // Set default height
      imgElement.style.objectFit = 'cover'; // Maintain aspect ratio and cover the area
      imgElement.style.display = 'block'; // Ensures the image is treated as a block element
      imgElement.style.margin = '0 auto'; // Centers the image horizontally
  
      // Replace the contents of the preview container with the new image
      previewContainer.innerHTML = '';
      previewContainer.appendChild(imgElement);
    }
    reader.readAsDataURL(file);
  }
  
