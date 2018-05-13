import CoreValidators from './CoreValidators';

const Validator = {
    validate: function(rule, value) {
        if (!CoreValidators[rule]) {
            return false;
        }

        return CoreValidators[rule](value);
    }
};

export default Validator;
