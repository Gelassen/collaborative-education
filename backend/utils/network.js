exports.getMessage = function(code, message) {
    return {code: code, status: {message: message}};
}

exports.getPayloadMessage = function(payload) {
    return {code: "200", status: {message: "success", payload: payload}}
}

exports.getErrorMessage = function() {
    return {code: 500, status: {message: "unsuccess"}}
}

exports.getErrorMessage = function(payload) {
    return {code: 500, status: {message: "unsuccess", payload: payload}}
}