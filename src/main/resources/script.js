"use strict";
let tokenController;
let button;

function clean() {
    if (button) {
        button.destroy();
        button = null;
    }

    if (tokenController && tokenController.destroy) {
        tokenController.destroy();
        tokenController = null;
    }
}

function createAISButton() {
    // clean up instances
    clean();

    // Client side Token object for creating the Token button, handling the popup, etc
    const token = new window.Token({
        env: 'sandbox',
    });

    // get button placeholder element
    const element = document.getElementById('tokenAccessBtn');

    // create the button
    button = token.createTokenButton(element, {
        label: 'Token Access',
    });

    // create TokenController to handle messages
    tokenController = token.createController();

    // bind the Token Button to the Token Controller when ready
    tokenController.bindButtonClick(
        button, // Token Button
        redirectAIS, // redirect token request function
        function(error) { // bindComplete callback
            if (error) throw error;
            // enable button after binding
            button.enable();
        },
    );
}

function createPISButton() {
    // clean up instances
    clean();

    // Client side Token object for creating the Token button, handling the popup, etc
    const token = new window.Token({
        env: 'sandbox',
    });

    // get button placeholder element
    const element = document.getElementById('tokenAccessBtn');

    // create the button
    button = token.createTokenButton(element, {
        label: 'Token Access',
    });

    // create TokenController to handle messages
    tokenController = token.createController();

    // bind the Token Button to the Token Controller when ready
    tokenController.bindButtonClick(
        button, // Token Button
        redirectPIS, // redirect token request function
        function(error) { // bindComplete callback
            if (error) throw error;
            // enable button after binding
            button.enable();
        },
    );
}

function redirectAIS() {
    document.location.assign("/ais");
}

function redirectPIS() {
    document.location.assign("/pis");
}

function setupButtonTypeSelector() {
    const selector = document.getElementsByName('buttonTypeSelector');
    let selected;
    for (let i = 0; i < selector.length; i++) {
        if (selector[i].checked) {
            selected = selector[i].value;
        }
        selector[i].addEventListener('click', function(e) {
            const value = e.target.value;
            if (value === selected) return;
            selected = value;
            if (value === 'PIS') {
                createPISButton();
            } else if (value === 'AIS') {
                createAISButton();
            }
        });
    }
    createAISButton();
}

setupButtonTypeSelector();
