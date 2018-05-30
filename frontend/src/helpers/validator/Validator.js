import CoreValidators from './CoreValidators';

const Validator = {
    validate: function(rule, value) {
        if (!CoreValidators[rule.rule]) {
            return false;
        }

        return CoreValidators[rule.rule](value, rule);
    },
    validateAttribute: function(name, value, rules) {
        let attributeResult = {
            value: value,
            valid: true,
            error: undefined
        };
        if (!rules[name]) {
            return attributeResult;
        }
        let ruleSet = rules[name];
        for (let i = 0; i < ruleSet.length; i++) {
            let rule = ruleSet[i];
            if (!Validator.validate(rule, value)) {
                attributeResult.valid = false;
                attributeResult.error = rule.message || `Неправильное значение поля ${name}`;
            }
        }

        return attributeResult;
    },
    validateForm: function(attributes, rules) {
        let validatedAttributes = {},
            formValid = true;
        Object.keys(attributes).forEach((name) => {
            validatedAttributes[name] = this.validateAttribute(name, attributes[name].value, rules);
            if (!validatedAttributes[name].valid) {
                formValid = false;
            }
        });

        return {
            valid: formValid,
            attributes: validatedAttributes,
        };
    },
    getFormData: function(attributes, namesMapping) {
        let formData = {};
        Object.keys(attributes).forEach((attributeName) => {
            if (namesMapping && namesMapping[attributeName]) {
                formData[namesMapping[attributeName]] = attributes[attributeName].value;
            } else {
                formData[attributeName] = attributes[attributeName].value;
            }
        });

        return formData;
    }
};

export default Validator;
