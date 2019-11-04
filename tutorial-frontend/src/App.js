import React from 'react';
import './App.css';
import CssBaseline from "@material-ui/core/CssBaseline";
import Container from "@material-ui/core/Container";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import SMTPConsole from "./components/SMTPConsole";
import socket from "./socket";
import MessageList from "./components/MessageList";
import CreateAccountDialog from "./components/CreateAccountDialog";
import AlertDialog from "./components/AlertDialog";

export default class App extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.state = {
            client: socket(),
            user: null,
            accountDialogOpen: true,
            alertDialogOpen: false,
            alertTitle: "Alert",
            alertMessage: ""
        };

        this.onCreateAccount = this.onCreateAccount.bind(this);
        this.onError = this.onError.bind(this);
        this.onAlertClose = this.onAlertClose.bind(this);

        this.state.client.setErrorHandler(this.onError);
    }

    onCreateAccount(name) {
        this.state.client.createAccount(name);
    }

    onError(errorResponse) {
        console.log("Backend error:");
        console.log(errorResponse);

        this.setState({
            alertDialogOpen: true,
            alertTitle: "Error",
            alertMessage: errorResponse.message
        });
    }

    onAlertClose() {
        if(this.state.alertMessage.indexOf("Could not create account") !== -1) {
            window.location.reload();
        }
    }

    render() {
        return (
            <>
                <CssBaseline/>
                <Container maxWidth={"xl"}>
                    <Grid container spacing={2}>
                        <Grid item xs={12} md={4}>
                            <Paper id={"messageSection"}>
                                <MessageList client={this.state.client} />
                            </Paper>
                        </Grid>
                        <Grid item xs={12} md={8}>
                            <Paper id={"tutorialConsole"}>
                                <SMTPConsole client={this.state.client} />
                            </Paper>
                        </Grid>
                    </Grid>
                </Container>
                <CreateAccountDialog open={this.state.accountDialogOpen} onClose={this.onCreateAccount} />
                <AlertDialog open={this.state.alertDialogOpen} title={this.state.alertTitle} message={this.state.alertMessage} onClose={this.onAlertClose} />
            </>
        );
    }
}