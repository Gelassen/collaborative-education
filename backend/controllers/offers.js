
var pool = require('../database');
var model = require('../model/offers')

exports.all = async function(req, res) {
    let result = await model.getAll(req, res)
    res.send(result)
    res.end()    
}

exports.specific = async function(req, res) {
    let result = await model.getSpecific(req)
    res.send(result)
    res.end()    
}

exports.create = async function(req, res) {
    var result = await model.getSpecific(req)
        .then(function(response) {
            var response = JSON.parse(response)
            if (response.status.payload.length == 0) {
                return model.create(req)
            } else {
                return model.update(req)
            }
        })

    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await model.delete(req)
    res.send(result)
    res.end()    
}
