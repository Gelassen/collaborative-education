
var category = require('../model/category')

exports.all = async function(req, res) {
    let result = await category.getAll(req, res)
    res.send(result)
    res.end()    
}

exports.specific = async function(req, res) {
    let result = await category.getSpecific(req)
    res.send(result)
    res.end()    
}

exports.create = async function(req, res) {
    let result = await category.create(req)
    res.send(result)
    res.end()    
}

exports.edit = async function(req, res) {
    let result = await category.edit(req)
    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await category.delete(req)
    res.send(result)
    res.end()    
}
