function ItemModel(args) {
    var args = args || {};
    var mandatoryFieldNames = ["price", "name", "type", "description", "quantity"];

    var priceShouldBeANumber = function () {
        var FLOAT_PATTERN = /^[0-9.]+$/;
        return args.price.match(FLOAT_PATTERN);
    };

    var priceShouldBeLessThanThreshold = function () {
        return parseInt(args.price) <= 100000;
    };

    var mandatoryFieldsShouldBePresent = function () {
        return mandatoryFieldNames.reduce(function (previous, current) {
            return previous && args[current]
        }, true)
    };

    this.validate = function () {
        return mandatoryFieldsShouldBePresent() && priceShouldBeANumber() && priceShouldBeLessThanThreshold();
    };
}

var fields = ['#name', '#price', '#type', '#description', '#quantity'];

function validateForm() {

    var validate = true;
    fields.forEach(function (field) {
        if (field === "#price") {
            validate = validate && isPriceValid(field);
        } else {
            validate = validate && validator.isFieldEmpty(field);
        }
    });

    return validate;
}

function showItemErrorMessage() {
    fields.forEach(function (field) {
        if (field === "#price")
            showPriceErrorMessage();
        else
            validator.isFieldEmpty(field) ? validator.hideErrorMessage(errorSelector(field)) : validator.displayErrorMessage(errorSelector(field));
    });
}

function showPriceErrorMessage() {
    if (!validator.isFieldEmpty("#price")) {
        validator.displayErrorMessage("#not_empty");
    } else {
        validator.hideErrorMessage("#not_empty");
        isPriceValid("#price") ? validator.hideErrorMessage("#not_validate_number") : validator.displayErrorMessage("#not_validate_number");
    }
}

function isPriceValid(field) {
    return validator.isFieldEmpty(field) && $(field).val() <= 100000;
}

function errorSelector(field) {
    return field + '_field' + " .text-error";
}

