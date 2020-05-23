
var pool = require('../database');
var courseSource = require('../model/course_source')

exports.all = async function(req, res) {
    let result = await courseSource.getAll(req, res)
    res.send(result)
    res.end()    
}

exports.specific = async function(req, res) {
    let result = await courseSource.getSpecific(req)
    res.send(result)
    res.end()    
}

exports.specificWithMeta = async function(req, res) {
    let result = await courseSource.getSpecificWithMeta(req)
    res.send(result)
    res.end()    
}

exports.create = async function(req, res) {
    let result = await courseSource.create(req)
    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await courseSource.delete(req)
    res.send(result)
    res.end()    
}
