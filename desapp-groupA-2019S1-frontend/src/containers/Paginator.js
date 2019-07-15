import React, { Component } from "react";
import Pagination from "react-bootstrap/Pagination";
import PageItem from "react-bootstrap/PageItem";

class Paginator extends Component {

    constructor(props){
        super(props);
        this.state = {
            currentPageNumber: this.props.currentPageNumber,
            totalItems: this.props.totalItems,
            itemsPerPage: this.props.itemsPerPage
        }

        this.componentWillReceiveProps = this.componentWillReceiveProps.bind(this);
        this.handleSelect = this.handleSelect.bind(this);
        this.getItems = this.getItems.bind(this);
    }

    componentWillReceiveProps(newProps) {
        this.setState({
            currentPageNumber: newProps.currentPageNumber,
            totalItems: newProps.totalItems,
            itemsPerPage: newProps.itemsPerPage
        });
    }

    handleSelect(eventKey) {
        this.props.pageChangeHandler(eventKey);
    }

    getItems(){
        let activePage = this.state.currentPageNumber;
        let totalPages = Math.ceil(this.state.totalItems / this.state.itemsPerPage);
        let pageItems = [];
    
        for (let number = 1; number <= totalPages; number++) {
            pageItems.push(
                <PageItem key={number} 
                          active={number === activePage}
                          onClick={()=>this.handleSelect(number)}>
                    {number}
                </PageItem>
            );
        }
        return pageItems;
    }

    render() {
        return (
            <Pagination size="sm">
                {this.getItems()}
            </Pagination>
        );
    }
}

export default Paginator;