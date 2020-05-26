
var pool = require('../database');
var model = require('../model/likes')

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
    var result;
    var likes = req.body.metadata.likes
    console.log("Likes uid: " + likes.likesUid)

    if (likes.likesUid === 'undefined' 
        || likes.likesUid == null
        || likes.likesUid == -1) {
        result = await model.create(req)
    } else {
        result = await model.update(req)
    }
    res.send(result)
    res.end()    
}

exports.delete = async function(req, res) {
    let result = await model.delete(req)
    res.send(result)
    res.end()    
}
