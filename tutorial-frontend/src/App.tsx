import React from 'react';
import './App.css';
import Container from "@material-ui/core/Container";
import CssBaseline from "@material-ui/core/CssBaseline";
import {createStyles, Theme} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Paper from "@material-ui/core/Paper";
import makeStyles from "@material-ui/core/styles/makeStyles";

const useStyles = makeStyles((theme: Theme) =>
    createStyles({
        root: {
            flexGrow: 1,
        },
        section: {
            width: '100%',
            height: '100%',
        },
        image: {
            width: 128,
            height: 128,
        },
        img: {
            margin: 'auto',
            display: 'block',
            maxWidth: '100%',
            maxHeight: '100%',
        },
    }),
);

const App: React.FC = () => {
    const classes = useStyles();

    return (
        <>
            <CssBaseline/>
            <Container maxWidth={"xl"}>
                <Grid container spacing={2}>
                    <Grid item xs={4}>
                        <Grid container>
                            <Grid item xs={12}>
                                <Paper className={classes.section}>
                                    test1
                                </Paper>
                            </Grid>
                        </Grid>
                    </Grid>
                    <Grid item xs={8}>
                        <Grid container>
                            <Grid item xs={12}>
                                <Grid container spacing={2}>
                                    <Paper className={classes.section}>
                                        test2
                                    </Paper>
                                </Grid>
                            </Grid>
                            <Grid item xs={12}>
                                <Grid container>
                                    <Paper className={classes.section}>
                                        test3
                                    </Paper>
                                </Grid>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Container>
        </>
    );
};

export default App;
