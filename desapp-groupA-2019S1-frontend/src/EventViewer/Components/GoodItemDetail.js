import React from "react";
// Bootstrap
import Popover from "react-bootstrap/Popover";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";

function GoodItemDetail(props) {
  const t = props.t;
  const good = props.good;
  const eventType = props.eventType;
  return (
    <Popover>
      <Card>
        <Card.Header>
          <h4>{t("eventView->goodDetailTitle")}</h4>
        </Card.Header>
        <Card.Body>
          <Row>
            <span>
              {t("eventView->quantityForPerson")} {good.quantityForPerson}
            </span>
          </Row>
          {finalQuantity(eventType, good, t)}
          <Row>
            <span>
              {t("eventView->pricePerUnit")}
              {t("formatter->currency")}
              {good.pricePerUnit}
            </span>
          </Row>
        </Card.Body>
      </Card>
    </Popover>
  );
}

function finalQuantity(eventType, good, t) {
  if (eventType === "FIESTA") {
    return (
      <Row>
        <span>
          {t("eventView->finalQuantity")} {good.finalQuantity}
        </span>
      </Row>
    );
  } else {
    return <></>;
  }
}

export default GoodItemDetail;
