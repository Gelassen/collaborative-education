
var pool = require('../database');
var likes = require('../model/likes')

exports.all = async function(req, res) {
    let result = await likes.getAll(req, res)
    res.send(result)
    res.end()    
}

exports.specific = async function(req, res) {
    let result = await likes.getSpecific(req)
    res.send(result)
    res.end()    
}

exports.create = async function(req, res) {
    let result = await likes.create(req)
    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await likes.delete(req)
    res.send(result)
    res.end()    
}
