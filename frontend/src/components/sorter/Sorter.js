import React from 'react';
import List from 'material-ui/List';
import { ListItem, ListItemText } from 'material-ui/List';
import SorterItem from './SorterItem';
import Typography from 'material-ui/Typography';
import './Sorter.css';

class Sorter extends React.Component {
    getNormalizedItems(sortAscending) {
        const { items } = this.props;
        let sortModifier = sortAscending ? 1 : -1;
        let normalizedItems = [];
        for(let itemName in items) {
            if(!items.hasOwnProperty(itemName)) {
                continue;
            }
            normalizedItems.push({
                name: itemName,
                value: items[itemName]
            });
        }
        normalizedItems.sort(function (a, b) {
            if(a.value > b.value) {
                return sortModifier;
            }
            if(a.value < b.value) {
                return -1 * sortModifier;
            }

            return 0;
        });
        if (this.props.maxItemCount && normalizedItems.length >= this.props.maxItemCount) {
            normalizedItems.length = this.props.maxItemCount;
        }

        return normalizedItems;
    }

    render() {
        const { items } = this.props;
        if (!items) {
            return (
                <List>
                    <ListItem className="nothing-found">
                        <ListItemText primary="Данных нет" />
                    </ListItem>
                </List>
            );
        }
        const normalizedItems = this.getNormalizedItems(this.props.sortAscending);
        const firstValue = normalizedItems[0].value;
        const lastValue = normalizedItems[normalizedItems.length - 1].value;
        let minValue = this.props.minValue;
        if (isNaN(minValue)) {
            minValue = this.props.sortAscending ? firstValue : lastValue;
        }
        return (
            <List className="sorter">
                {normalizedItems.map((item, index) => (
                    <SorterItem
                        maxValue={this.props.sortAscending ? lastValue : firstValue}
                        minValue={minValue}
                        value={item.value}
                        key={index}
                    >
                        <ListItemText primary={item.name} />
                        <Typography className="sorter__value" variant="title">
                            {Math.round(item.value).toLocaleString()}{this.props.valueSuffix}
                        </Typography>
                    </SorterItem>
                ))}
            </List>
        );
    }
}

export default Sorter;
