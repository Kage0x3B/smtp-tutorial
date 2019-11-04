import React, {createRef} from 'react';
import styled from 'styled-components'
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import TextField from "@material-ui/core/TextField";
import Button from "@material-ui/core/Button";
import {ListItemText} from "@material-ui/core";

const NoDots = styled.div`
  hr {
    visibility: hidden;
  }
`;

const OutputText = styled.div`
  white-space: normal !important;
  word-break: break-all !important;
  overflow: auto;
  width: 100%;
  height: auto !important;er
`;

const InputPanel = styled.div`
  display: flex;
  align-items: center;
  padding: 10px;
  align-self: center;
  border-top: 1px solid #fafafa;
  width: 100%;
`;

const Scrollable = styled.div`
  height: 80vh;
  overflow-y: scroll;
  background-color: #eee;
`;

export default class SMTPConsole extends React.Component {
    constructor(props, context) {
        super(props, context);

        this.panelRef = null;

        this.client = props.client;

        this.state = {
            connected: false,
            consoleHistory: [],
            commandInput: ''
        };

        this.onInput = this.onInput.bind(this);
        this.onConnect = this.onConnect.bind(this);
        this.onSendMessage = this.onSendMessage.bind(this);
        this.onSMTPResponse = this.onSMTPResponse.bind(this);
        this.updateConsoleHistory = this.updateConsoleHistory.bind(this);
        this.scrollConsoleToBottom = this.scrollConsoleToBottom.bind(this);

        this.client.socket.on('smtp-response', this.onSMTPResponse);
    }

    componentDidMount() {
        this.scrollConsoleToBottom();
    }

    componentDidUpdate() {
        this.scrollConsoleToBottom();
    }

    onInput(e) {
        this.setState({
            commandInput: e.target.value
        })
    }

    onConnect() {
        if (this.state.connected) {
            return;
        }

        this.client.sendSMTPMessage("_CONNECT");

        this.setState({
            connected: true
        });

        /*this.props.onSendMessage(this.state.input, (err) => {
            if (err) {
                return console.error(err);
            }

            return this.setState({input: ''});
        });*/
    }

    onSendMessage() {
        if (!this.state.commandInput) {
            return;
        }

        this.updateConsoleHistory({
            statusCode: -1,
            message: "# " + this.state.commandInput
        });

        this.client.sendSMTPMessage(this.state.commandInput);

        this.setState({commandInput: ''});
    }

    onSMTPResponse(response) {
        console.log('onSMTPResponse:', response);

        this.updateConsoleHistory(response);
    }

    updateConsoleHistory(message) {
        this.setState({consoleHistory: this.state.consoleHistory.concat(message)})
    }

    scrollConsoleToBottom() {
        this.panelRef.current.scrollTo(0, this.panelRef.current.scrollHeight)
    }

    render() {
        this.panelRef = createRef();

        return (
            <div>
                <Scrollable ref={this.panelRef}>
                    <List>
                        {
                            this.state.consoleHistory.map((consoleLine, id) =>
                                <div key={id}>
                                    <NoDots>
                                        <ListItem>
                                            <ListItemText>
                                                <OutputText style={{fontFamily: '\'Source Code Pro\', monospace'}}>
                                                    {consoleLine.message}
                                                </OutputText>
                                            </ListItemText>
                                        </ListItem>
                                    </NoDots>
                                </div>)
                        }
                    </List>
                </Scrollable>
                <InputPanel>
                    <Button onClick={this.onConnect} variant={this.state.connected ? "outlined" : "contained"}
                            color={"primary"}
                            style={{marginRight: 20}} disabled={this.state.connected}>
                        {this.state.connected ? "Connected" : "Connect"}
                    </Button>
                    <TextField
                        onChange={this.onInput}
                        value={this.state.commandInput}
                        style={{width: '100%'}}
                        onKeyPress={e => (e.key === 'Enter' ? this.onSendMessage() : null)}
                    />
                    <Button onClick={this.onSendMessage} variant={"outlined"} color={"primary"}
                            style={{marginLeft: 20}}>
                        Send
                    </Button>
                </InputPanel>
            </div>
        )
    }
}