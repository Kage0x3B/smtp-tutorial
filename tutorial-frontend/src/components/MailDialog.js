import React from 'react';
import Dialog from "@material-ui/core/Dialog";
import DialogTitle from "@material-ui/core/DialogTitle";
import DialogContent from "@material-ui/core/DialogContent";
import DialogContentText from "@material-ui/core/DialogContentText";
import DialogActions from "@material-ui/core/DialogActions";
import Button from "@material-ui/core/Button";

export default class MailDialog extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.handleClose = this.handleClose.bind(this);
    }

    handleClose() {
        this.props.onClose();
    }

    render() {
        return (
            <Dialog open={this.props.open}>
                <DialogTitle>{this.props.mail.senderAddress}</DialogTitle>
                <DialogContent>
                    <DialogContentText dangerouslySetInnerHTML={
                        this.props.mail.data ? {
                            __html: this.props.mail.data.replace(new RegExp("\n", 'g'), '<br>')
                        } : {__html: ""}}>
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