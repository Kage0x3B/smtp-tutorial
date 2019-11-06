import React from 'react';
import styled from 'styled-components'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import Divider from "@material-ui/core/Divider";
import Typography from "@material-ui/core/Typography";
import {ListItemText} from "@material-ui/core";
import MailDialog from "./MailDialog";

const NoDots = styled.div`
  hr {
    visibility: hidden;
  }
`;

const EllipsesOverflow = styled.div`
  white-space: nowrap;
  overflow: hidden;
  text-overflow-mode: ellipsis;
`;

const Scrollable = styled.div`
  height: 50vh;
  overflow-y: scroll;
`;

export default class MessageList extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.client = props.client;

        this.state = {
            messageList: [],
            mailDialogOpen: false,
            openedMail: {}
        };

        this.onMessageReceived = this.onMessageReceived.bind(this);
        this.updateMessages = this.updateMessages.bind(this);
        this.showMail = this.showMail.bind(this);
        this.closeMailDialog = this.closeMailDialog.bind(this);

        this.client.socket.on('received-mail', this.onMessageReceived);
    }

    onMessageReceived(mail) {
        console.log('onMessageReceived:', mail);

        this.updateMessages(mail);
    }

    updateMessages(entry) {
        this.setState({messageList: this.state.messageList.concat(entry)})
    }

    showMail(mail) {
        this.setState({
            mailDialogOpen: true,
            openedMail: mail
        })
    }

    closeMailDialog() {
        this.setState({
            mailDialogOpen: false
        })
    }

    render() {
        return (
            <div>
                <Typography variant="h6" style={{padding: '10px'}}> Received mails</Typography>
                <Divider/>
                <Scrollable>
                    <List>
                        {
                            this.state.messageList.map((mail, id) => [
                                <div key={id}>
                                    <NoDots>
                                        <ListItem button onClick={() => this.showMail(mail)}>
                                            <ListItemText>
                                                {mail.senderAddress}
                                            </ListItemText>
                                            <ListItemText>
                                                <EllipsesOverflow>
                                                    {mail.subject}
                                                </EllipsesOverflow>
                                            </ListItemText>
                                        </ListItem>
                                    </NoDots>
                                    <Divider/>
                                </div>])
                        }
                    </List>
                </Scrollable>
                <MailDialog open={this.state.mailDialogOpen} mail={this.state.openedMail}
                            onClose={this.closeMailDialog}/>
            </div>
        )
    }
}