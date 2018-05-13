const CoreValidators = {
    required: function(value) {
        return !(
            value === undefined
            || value === ""
            || value === 0
            || value === "0"
            || value === null
            || value === false
            || (value.length && value.length === 0 )
        );
    }
};

export default CoreValidators;
