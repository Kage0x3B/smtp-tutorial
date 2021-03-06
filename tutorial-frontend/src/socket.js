import io from 'socket.io-client';

export default function () {
    const socket = io.connect('https://smtp-tutorial.syscy.de', {secure: true});

    socket.on('connect', function (err) {
        console.log("Connected to tutorial backend");
    });

    socket.on('error', function (err) {
        console.log('received socket error:');
        console.log(err);
    });

    function createAccount(name) {
        socket.emit('create-account', {
            name: name
        });
    }

    function login(name, password) {
        socket.emit('login', {
            name: name,
            password: password
        });
    }

    function sendSMTPMessage(message) {
        socket.emit('smtp-message', {
            message: message
        });
    }

    function setErrorHandler(errorHandler) {
        socket.on('error-response', errorHandler);
    }

    return {
        socket,
        createAccount,
        login,
        sendSMTPMessage,
        setErrorHandler
    };
}
