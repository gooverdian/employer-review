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

const Validator = {
    validate: function(rule, value) {
        if (!CoreValidators[rule]) {
            return false;
        }

        return CoreValidators[rule](value);
    }
};

export default Validator;