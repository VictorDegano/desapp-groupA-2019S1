import React from "react";
// Bootstrap
import Popover from "react-bootstrap/Popover";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";

function GoodItemDetail(props){
    const t= props.t;
    const good= props.good;
    const eventType= props.eventType;
    return (
        <Popover>
            <Card>
                <Card.Header>
                    <h4>{t("eventView->goodDetailTitle")}</h4>
                </Card.Header>
                <Card.Body>
                    <Card.Text>
                    <Row>
                        <span>{t("eventView->quantityForPerson")} {good.quantityForPerson}</span>
                    </Row>
                    {finalQuantity(eventType,good,t)}
                    <Row>
                        <span>
                            {t("eventView->pricePerUnit")} 
                            {t("formatter->currency")}
                            {good.pricePerUnit}
                        </span>
                    </Row>
                    {/*<Row>
                        <span>
                            {t("eventView->totalGoodCost")} 
                            {t("formatter->currency")}
                            {good.pricePerUnit * good.finalQuantity}
                        </span> 
                    </Row> TODO: esto y el costo total deberia verlo solo el organizador */}
                    </Card.Text>
                </Card.Body>
            </Card>
        </Popover>
    )
}

/*TODO: una vez ivan habilite el campo descomentarlo*/ 
function finalQuantity(eventType,good,t){
    if(eventType === "FIESTA"){
        return <Row>
                    <span>
                        {t("eventView->finalQuantity")} {/*good.finalQuantity*/}
                    </span>
                </Row>
    } else {
        return <></>;
    }
}

export default GoodItemDetail;