function setFormMessage(formElement, type, message) {
    const messageElement = formElement.querySelector(".form__message");

    messageElement.textContent = message;
    messageElement.classList.remove("form_message--success", "form_message--error");
    messageElement.classList.add(`form_message--${type}`);
}
document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("login");
    const createAccountForm = document.getElementById("createAccount");
    const forgotPasswordForm = document.getElementById("forgotPassword");

    document.querySelector("#linkCreateAccount").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.add("form--hidden");
        createAccountForm.classList.remove("form--hidden");
        forgotPasswordForm.classList.add("form--hidden");
    });

    document.querySelector("#linkLogin").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.remove("form--hidden");
        createAccountForm.classList.add("form--hidden");
        forgotPasswordForm.classList.add("form--hidden");
    });

    document.querySelector("#linkLoginForgot").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.remove("form--hidden");
        createAccountForm.classList.add("form--hidden");
        forgotPasswordForm.classList.add("form--hidden");
    });

    document.querySelector("#linkForgotPassword").addEventListener("click", (e) => {
        e.preventDefault();
        loginForm.classList.add("form--hidden");
        createAccountForm.classList.add("form--hidden");
        forgotPasswordForm.classList.remove("form--hidden");
    });

    loginForm.addEventListener("submit", e => {
        e.preventDefault();

        // Perform your AJAX/Fetch login

        setFormMessage(loginForm, "error", "Invalid username/password");
        
    });

    document.querySelectorAll("input-control").forEach(inputElement => {
        inputElement.addEventListener("blur", e => {
            if (e.target.id === "username" && e.target.value.length > 0 && e.target.value.length < 10) {
                setInputError(inputElement, "Username must be at least 10 characters in length");
            }
        });

        inputElement.addEventListener("input", e => {
            clearInputError(inputElement);
        });
    });
 });

