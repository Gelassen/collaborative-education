var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise( (resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                { sql: 'SELECT * FROM course_source', timeout: 60000 }, 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
                    connection.release()
            })
        });
    })
}

exports.getSpecific = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                { sql: 'SELECT * FROM course_source WHERE course_uid = ?;', timeout: 60000 }, 
                [req.params.courseId], 
                function(error, rows, fields) {
                    if (error != null) {
                        var payload = []
                        payload.push({uid: -1, title: "", source: "", course_uid: req.params.courseId, author: ""})
                        var response = util.getErrorMessage(payload)
                        resolve(response)
                    } else {
                        var response = util.getPayloadMessage(rows)
                        resolve(response)
                    }
                    connection.release()
                }
            )
        });
    })
}

exports.mapper = function(rows) {
    var result = []
    for (var id = 0; id < rows.length; id++) {
        var item = rows[id];
        result.push(
            {
                source: { uid: item.uid, title: item.title, source: item.source, course_uid: item.course_uid, author: item.author },
                metadata: {
                    likes : { likesUid: item.likesUid, counter: item.counter, courseUid: item.uid, users: item.usersLiked }, 
                    comments : []
                }
            })
    }
    return result; 
}

exports.getSpecificWithMeta = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                {sql: 
                'SELECT uid, title, source, sources.course_uid, author, likes_uid as likesUid, counter, likes.course_uid as courseUid, likes.users as usersLiked ' + 
                'FROM course_source as sources ' + 
                'LEFT JOIN likes as likes on sources.uid = likes.course_uid ' + 
                'WHERE sources.course_uid = ?;',
                timeout: 60000 },
                [req.params.courseId],
                function(error, rows, fields) {
                    if (error != null) {
                        console.log("Failed to query sources with meta", error)
                        var response = util.getErrorMessage(module.exports.mapper(rows))
                        resolve(response)
                    } else {
                        var response = util.getPayloadMessage(module.exports.mapper(rows))
                        console.log("Course source Response: " + JSON.stringify(response))
                        resolve(response)
                    }
                    connection.release()
                }
            )
        })
    })
}

exports.create = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var body = req.body
            console.log(JSON.stringify(body))
            console.log(JSON.stringify(body.users))
            connection.query(
                {sql: 'INSERT INTO course_source SET title = ?, source = ?, course_uid = ?, author = ?;', timeout: 60000 }, 
                [body.title, body.source, body.courseUid, body.author], 
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var emptyResponse = []
                        emptyResponse.push({uid: -1, title: "", source: "", course_uid: body.courseUid, author: ""})
                        var response = util.getErrorMessage(module.exports.mapper(emptyResponse))
                        response.status.payload.metadata = { likes: { likesUid: null, counter: null, courseUid: null, users: null }, comments: [], pdata: null }
                        resolve(response)
                    } else {
                        body.course_uid = body.courseUid
                        body.uid = (rows.affectRows == 0)  ? -1 : rows.insertId
                        var payload = []
                        payload.push(body)
                        var response = util.getPayloadMessage(module.exports.mapper(payload))
                        console.log("Course source insert reponse: " + JSON.stringify(response)) 
                        resolve(response)
                    }
                    connection.release()
                }
            )
        });
    })
}

exports.delete = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("exports.delete: " + pool.escape(req.params.id))
            connection.query(
                {sql: 'DELETE FROM course_source WHERE course_uid = ?;', timeout: 60000 }, 
                [req.params.id], 
                function(err, rows, fields) {
                    if (err != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage((rows.affectRows == 0) ? "Data hasn't been deleted" : "Data has been deleted")))
                    }
                    connection.release()
                }
            )
        });
    })
}