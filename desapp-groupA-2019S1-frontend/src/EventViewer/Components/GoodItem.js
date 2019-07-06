import React from "react";
// Bootstrap
import OverlayTrigger from "react-bootstrap/OverlayTrigger";
// Eventeando
import GoodItemDetail from "./GoodItemDetail";
// I18n Hook
import { withTranslation } from "react-i18next";

const GoodItem = (props) => (
    
        <OverlayTrigger placement="top"
                        delay={{ show: 250, hide: 400 }}
                        overlay={GoodItemDetail(props)}>
            <span>{props.good.name}</span>
        </OverlayTrigger>
)

export default withTranslation()(GoodItem);