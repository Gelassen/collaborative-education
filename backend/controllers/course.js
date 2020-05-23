
var pool = require('../database');
var course = require('../model/course')

exports.all = async function(req, res) {
    let result = await course.getAll(req, res)
    res.send(result)
    res.end()    
}

exports.specific = async function(req, res) {
    let result = await course.getSpecific(req)
    res.send(result)
    res.end()    
}

exports.create = async function(req, res) {
    let result = await course.create(req)
    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await course.delete(req)
    res.send(result)
    res.end()    
}
