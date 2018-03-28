import React from 'react';
import {Col, Row} from 'react-flexbox-grid';
import './EmployerReview.css';
import FontAwesome from 'react-fontawesome';

class EmployerReview extends React.Component {
    render () {
        return (
            <Row className={"review" + (this.props.highlight ? ' review_highlighted' : '')}>
                <Col xs={2}>
                    <h1 className="review__header">
                        <FontAwesome className="review__star" name="star" /> {this.props.data.rating}
                    </h1>
                </Col>
                <Col xs={10} className="review__text">
                    {this.props.data.text}
                </Col>
            </Row>
        );
    }
}

export default EmployerReview;
