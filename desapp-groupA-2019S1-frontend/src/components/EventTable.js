import React from 'react';
import { makeStyles } from '@material-ui/core/styles';
import Table from '@material-ui/core/Table';
import TableBody from '@material-ui/core/TableBody';
import TableCell from '@material-ui/core/TableCell';
import TableHead from '@material-ui/core/TableHead';
import TableRow from '@material-ui/core/TableRow';
import Paper from '@material-ui/core/Paper';

const useStyles = makeStyles(theme => ({
    root: {
        width: '100%',
        marginTop: theme.spacing(3),
        overflowX: 'auto',
    },
    table: {
        minWidth: 650,
    },
}));

function createDataWithJson(jsonDeEvento) {
    return { name: jsonDeEvento.name,eventType: jsonDeEvento.type,organizer: jsonDeEvento.organizer, guestsAmount: jsonDeEvento.guestsAmount};
}
function parseArrayToFunction(rowsArray) {
    return rowsArray.flat(row=> createDataWithJson(row));
}

function EventTable(props) {
    const classes = useStyles();

    return (
        <Paper className={classes.root}>
            <Table className={classes.table}>
                <TableHead>
                    <TableRow>
                        <TableCell>Nombre</TableCell>
                        <TableCell align="right">Tipo de evento</TableCell>
                        <TableCell align="right">Organiza</TableCell>
                        <TableCell align="right">Cantidad de invitados</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {parseArrayToFunction(props.arrayDeEventos).map(row => (
                        <TableRow key={row.name}>
                            <TableCell component="th" scope="row">
                                {row.name}
                            </TableCell>
                            <TableCell align="right">{row.type}</TableCell>
                            <TableCell align="right">{row.organizer.firstName}</TableCell>
                            <TableCell align="right">{row.quantityOfGuest}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </Paper>
    );
}

export default EventTable;