
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

    var result = await model.getSpecific(req)
        .then(function(response) {
            console.log('Response: ' + response)
            var response = JSON.parse(response)
            if (response.status.payload.length == 0) {
                console.log("Likes create")
                return model.create(req)
            } else {
                console.log("Lieks update")
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
