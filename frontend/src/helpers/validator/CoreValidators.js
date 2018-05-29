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
    },
    integer: function(value, rule) {
        if (value === undefined || value === '') {
            return true;
        }
        console.log(value < rule.min);
        return !(
            isNaN(value)
            || Number(value) !== parseInt(value, 10)
            || (typeof rule.min !== "undefined" && (Number(value) < rule.min))
            || (typeof rule.max !== "undefined" && (Number(value) > rule.max))
        );
    },
};

export default CoreValidators;
