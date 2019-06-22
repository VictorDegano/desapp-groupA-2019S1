import React, { Component } from "react";
import { connect }from "react-redux";
import PropTypes from "prop-types";

//Material UI
import Button from '@material-ui/core/Button';

import TextField from '@material-ui/core/TextField';
import { withStyles } from '@material-ui/styles';
//Actions
import * as UserActions from "../actions/UserActions.js";

const styles = theme => {
    return ({
        button: {
            margin: 'auto',
            //margin: 1,//theme.spacing(1),
            spacing: 1,
          },
        container: {
            margin: 'auto',
            display: 'flex',
            flexWrap: 'wrap',
        },
        textField: {
            margin: 'auto',
            marginLeft: 1,//theme.spacing(1),
            marginRight: 1,//theme.spacing(1),
            width: 200,
        },
        dense: {
            marginTop: 19,
        },
        menu: {
            width: 200,
        },
    })
};

class UserProfile extends Component {

    static propTypes ={
        loadUser: PropTypes.func.isRequired,
        user: PropTypes.object
    }

    constructor(props){
        super(props);

        this.state = {
            firstName: '',
            lastName: '',
            oldPassword: '',
            password: '',
            repeatPassword: '',
            bornDay: null
        }

        // store.subscribe(() => {
        //     // When state will be updated(in our case, when items will be fetched), 
        //     // we will update local component state and force component to rerender 
        //     // with new data.
        //     this.setState({
        //       user: store.getState().user,
        //     });
        //   });

        this.componentWillMount = this.componentWillMount.bind(this);
        this.render = this.render.bind(this);
        this.handleFirstNameChange = this.handleFirstNameChange.bind(this);
        this.handleLastNameChange = this.handleLastNameChange.bind(this);
        this.handleBornDayChange = this.handleBornDayChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleCancel = this.handleCancel.bind(this);
    }    

    //Ocurre antes de que el componente se monte(o complete de montarse)
    componentWillMount(){
        // this.setState({user: this.props.user});        
        // console.log('componentWilMount()');
        //  const userApi = new UserApi();

        // userApi.fetchUser("1")
        //         .then((user) => {
        //             // console.log(user);
        //             // console.log(moment(user.bornDay).format('DD-MM-YYYY'));
        //             this.props.loadUser(user);
        //             this.setState({
        //                 firstName: user.firstName,
        //                 lastName: user.lastName,
        //                 bornDay: moment(user.bornDay).format('YYYY-MM-DD')
        //             })
        //         });
    }

    handleFirstNameChange (event) {
        // console.log('handleFirstNameChange');
        this.setState({firstName: event.target.value});
    }
    
    handleLastNameChange (event) {
        // console.log('handleLastNameChange');
        this.setState({lastName: event.target.value});        
    }

    handleBornDayChange (event) {
        // console.log('handleBornDayChange');
        // console.log(event.target.value);
        this.setState({bornDay: event.target.value});
    }
 
    handleSubmit (event) {
        event.preventDefault();
        // console.log('handleSubmit');
    }

    handleCancel (event){
        // console.log('handleCancel');
    }

    render () {
        const { classes } = this.props;

        return <form className={classes.container}
                     noValidate 
                     autoComplete="off"
                     onSubmit={this.handleSubmit}>
                    <TextField id="standard-name"
                               label="Nombre"
                               className={classes.textField}
                               value={this.state.firstName}
                            //    value={this.props.user.firstName}
                               onChange={this.handleFirstNameChange}
                               margin="normal"/>
                    <TextField id="standard-name"
                               label="Apellido"
                               className={classes.textField}
                               value={this.state.lastName}
                               onChange={this.handleLastNameChange}
                               margin="normal"/>
                    <TextField  id="date"
                                label="Fecha De nacimiento"
                                type="date"
                                value={this.state.bornDay}
                                className={classes.textField}
                                onChange={this.handleBornDayChange}     
                                InputLabelProps={{
                                shrink: true,
                                }}
                            />
                    <Button variant="contained" 
                            size="small" 
                            className={classes.button}
                            onClick = {this.handleCancel}>
                        Cancelar
                    </Button>
                    <Button type="submit"
                            variant="contained" 
                            size="small" 
                            className={classes.button}>
                        Guardar
                    </Button>                    
                </form>;
    }



}    
 
function mapStateToProps (state){
    // console.log('mapStateToProps()'); 
    return {
        user: state.UserReducer.loggedUser
    };
}

//el mapStateToProps son los estados que se conectan a los props
//el mapDispatchToprops son las acciones, si se tiene sino se pasa null
//connect(mapStateToprops, mapDispatchToProps)(ComponentClass)
export default connect(
  mapStateToProps,
  UserActions
)(withStyles(styles)(UserProfile));
