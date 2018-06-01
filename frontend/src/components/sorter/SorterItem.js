import React from 'react';
import { ListItem } from 'material-ui/List';

const minWidthPercentage = 20;

class SorterItem extends React.Component {
    getItemWidth() {
        const deviation = this.props.maxValue - this.props.minValue;
        const percentage = (
            ((this.props.value - this.props.minValue) / deviation * (100 - minWidthPercentage)) + minWidthPercentage
        ).toFixed(2);
        console.log(this.props.value, this.props.maxValue);
        return `${percentage}%`;
    }

    render() {
        return (
            <ListItem disableGutters className="sorter__item">
                <div className="sorter__filler-wrap">
                    <div className="sorter__filler" style={{width: this.getItemWidth()}} />
                </div>
                {this.props.children}
            </ListItem>
        );
    }
}

export default SorterItem;
