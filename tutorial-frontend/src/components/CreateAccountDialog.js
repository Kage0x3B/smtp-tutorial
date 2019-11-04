import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";
import TextField from "@material-ui/core/TextField";

export default class CreateAccountDialog extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.client = props.client;

        this.state = {
            open: props.open,
            name: ''
        };

        this.handleClose = this.handleClose.bind(this);
        this.handleNameInputChange = this.handleNameInputChange.bind(this);
    }

    handleNameInputChange(e) {
        this.setState({
            name: e.target.value
        });
    }

    handleClose() {
        this.setState({
            open: false
        });

        this.props.onClose(this.state.name);
    }

    render() {
        return (
            <Dialog open={this.state.open} onClose={this.handleClose} disableBackdropClick disableEscapeKeyDown>
                <DialogTitle>Create Mail Account</DialogTitle>
                <DialogContent>
                    <TextField
                        autoFocus
                        required
                        margin="dense"
                        id="mail-account-name"
                        label="Account name"
                        type="text"
                        fullWidth
                        value={this.state.name}
                        onChange={this.handleNameInputChange}
                    />
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary">
                        Create Account
                    </Button>
                </DialogActions>
            </Dialog>
        );
    }
}