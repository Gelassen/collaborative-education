var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise( (resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                'SELECT * FROM likes;', 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
            })
        });
    })
}

exports.getSpecific = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("req.params.id: " + pool.escape(req.params.id))
            connection.query(
                'SELECT * FROM likes WHERE course_uid = ?;', 
                [req.params.id], 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
                }
            )
        });
    })
}

exports.create = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var body = req.body
            var likes = body.metadata.likes
            console.log("On likes " + JSON.stringify(body));
            connection.query(
                'INSERT INTO likes SET counter = ?, course_uid = ?, users = ?;', 
                [likes.counter, likes.courseUid, JSON.stringify(likes.users)], 
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var payload = []
                        payload.push(body)
                        resolve(util.getErrorMessage(payload))
                    } else {
                        var response = []
                        response.push(body)
                        likes.likesUid = (rows.affectRows == 0) ? -1 : rows.insertId
                        likes.courseUid = response[0].source.uid
                        response[0].metadata.likes = likes
                        response[0].source.course_uid = response[0].source.courseUid
                        console.log("Response: " + JSON.stringify(response))
                        resolve(util.getPayloadMessage(response))
                    }
                }
            )
        });
    })
}

exports.update = function(req) {
    return new Promise((resolve) => {
        var body = req.body
        var likes = body.metadata.likes
        pool.getConnection(function(err, connection) {
            connection.query(
                'UPDATE likes SET counter = ?, course_uid = ?, users = ? WHERE likes_uid = ?;',
                [likes.counter, likes.courseUid, JSON.stringify(likes.users), likes.likesUid],
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var payload = []
                        payload.push(body)
                        resolve(util.getErrorMessage(payload))
                    } else {
                        var response = []
                        response.push(body)
                        likes.likesUid = (rows.affectRows == 0) ? -1 : rows.insertId
                        likes.courseUid = response[0].source.uid
                        response[0].metadata.likes = likes
                        response[0].source.course_uid = response[0].source.courseUid
                        console.log("Response: " + JSON.stringify(response))
                        resolve(util.getPayloadMessage(response))
                    }
                }
            )
        })
    })
}

exports.delete = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("exports.delete: " + pool.escape(req.params.id))
            connection.query(
                'DELETE FROM likes WHERE course_uid = ?;', 
                [req.params.id], 
                function(err, rows, fields) {
                    if (err != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage((rows.affectRows == 0) ? "Data hasn't been deleted" : "Data has been deleted")))
                    }
                }
            )
        });
    })
}