var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise( (resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                {sql: 'SELECT * FROM course', timeout: 60000}, 
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
            console.log("req.params.id: " + pool.escape(req.params.id))
            connection.query(
                {sql: 'SELECT * FROM course WHERE course_uid = ?;', timeout: 60000 }, 
                [req.params.id], 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        var response = util.getPayloadMessage(rows)
                        console.log("All courses: " + JSON.stringify(response)) 
                        resolve(response)
                    }
                    connection.release()
                }
            )
        });
    })
}

exports.create = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var body = req.body
            connection.query(
                {sql: 'INSERT INTO course SET title = ?, course_uid = ?, author = ?;', timeout: 60000}, 
                [body.title, body.course_uid, body.author], 
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var payload = []
                        payload.push({uid: -1, title: "", course_uid: body.course_uid, author: ""})
                        var response = util.getErrorMessage(payload)
                        resolve(response)
                    } else {
                        body.uid = (rows.affectRows == 0)  ? -1 : rows.insertId
                        var payload = []
                        payload.push(body)
                        var response = util.getPayloadMessage(payload) 
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
                {sql: 'DELETE FROM course WHERE course_uid = ?;', timeout: 60000 }, 
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