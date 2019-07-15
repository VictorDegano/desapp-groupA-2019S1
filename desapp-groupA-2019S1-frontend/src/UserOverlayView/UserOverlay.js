import React, { Component } from "react";
// Bootstrap
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
import Button from "react-bootstrap/Button";
// Eventeando
import UserDetail from "./UserDetail";
import UserApi from "../api/UserApi";

class UserOverlay extends Component {
    
    constructor(props){
        super(props);
        
        this.emptyUser = {
            bornDay: "",
            email: "",
            firstName: "",
            id: 0,
            lastName: ""
        };

        this.userApi = new UserApi();

        this.state = {
            guest: this.props.guest,
            user: this.emptyUser
          };

        this.handleOnEnterOverlay = this.handleOnEnterOverlay.bind(this);
        this.handleHideOverlay = this.handleHideOverlay.bind(this);
    }

    handleOnEnterOverlay(){
        this.userApi.getUser(this.state.guest.userId).then(
            response => {
                this.setState({
                    user: response.data
                })
            }
        );
    }

    handleHideOverlay(){
        this.setState({
            user: this.emptyUser
        })
    }

    render(){
        const guest = this.state.guest;
        const user =  this.state.user;

        return <OverlayTrigger placement="top"
                        trigger="click"
                        onEnter={()=>this.handleOnEnterOverlay()}
                        onHide={()=>this.handleHideOverlay()}
                        overlay={UserDetail(user)}>
                    <Button variant="link">{`${guest.firstName} ${guest.lastName}`}</Button>
                </OverlayTrigger>;
    }

}

export default UserOverlay;