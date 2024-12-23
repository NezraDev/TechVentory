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
