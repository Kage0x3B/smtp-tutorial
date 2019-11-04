import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";

export default class AlertDialog extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.client = props.client;

        this.state = {
            open: props.open
        };

        this.handleClose = this.handleClose.bind(this);
    }

    componentDidUpdate(prevProps, prevState, snapshot) {
        if (!prevProps.open) {
            this.setState({
                open: true
            });
        }
    }

    handleClose() {
        this.setState({
            open: false
        });

        if(this.props.onClose) {
            this.props.onClose();
        }
    }

    render() {
        return (
            <Dialog open={this.state.open}>
                <DialogTitle>{this.props.title}</DialogTitle>
                <DialogContent>
                    <DialogContentText>
                        {this.props.message}
                    </DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={this.handleClose} color="primary" autoFocus>
                        Close
                    </Button>
                </DialogActions>
            </Dialog>
        )
    }
}