import React from "react";
// Bootstrap
import Popover from "react-bootstrap/Popover";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";

function GoodItemDetail(props){
    const t= props.t;
    const good= props.good;
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
                    <Row>
                        <span>{t("eventView->finalQuantity")} {/*good.finalQuantity*/}</span> {/*TODO: una vez ivan habilite el campo descomentarlo*/ }
                    </Row>
                    <Row>
                        <span>
                            {t("eventView->pricePerUnit")} 
                            {t("formatter->currency")}
                            {good.pricePerUnit}
                        </span> {/*TODO: esto y el costo total deberia verlo solo el organizador */}
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

export default GoodItemDetail;