import React from "react";
// Bootstrap
import Popover from "react-bootstrap/Popover";
import Card from "react-bootstrap/Card";
import Row from "react-bootstrap/Row";
import i18n from 'i18next';

function UserDetail(user) {

  return (
    <Popover>
      <Card>
        <Card.Header>
          <h4>{i18n.t("eventView->userDetail->userInfo")}</h4>
        </Card.Header>
        <Card.Body>
          <Row>
            <span>
              {i18n.t("eventView->userDetail->userName")} {`${user.firstName} ${user.lastName}`}
            </span>
          </Row>
          <Row>
            <span>
                {i18n.t("eventView->userDetail->userEmail")} {user.email}
            </span>
          </Row>
          <Row>
            <span>
                {i18n.t("eventView->userDetail->userBornDay")} {user.bornDay}
            </span>
          </Row>
        </Card.Body>
      </Card>
    </Popover>
  );
}

export default UserDetail;
