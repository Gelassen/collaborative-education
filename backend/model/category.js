var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise( (resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                'SELECT * FROM category', 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
            })
            connection.release()
        });
    })
}

exports.getSpecific = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("req.params.id: " + pool.escape(req.params.id))
            connection.query(
                'SELECT * FROM cageory WHERE uid = ?;', 
                [req.params.id], 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
                }
            )
            connection.release()
        });
    })
}

exports.edit = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var body = req.body.params
            connection.query(
                'UPDATE category SET title = ? WHERE uid = ?', 
                [pool.escape(body.name), pool.escape(body.status), req.params.id], 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getMessage(200, (rows.affectRows == 0) ? "No change applied" : "Data is changed")))
                    }
                    
                }
            )
            connection.release()
        });
    })
}

exports.create = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var param = req.body.title;
            connection.query(
                'INSERT INTO category SET title = ?', 
                [pool.escape(param)], 
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        req.body.uid = rows.affectRows == 0 ? -1 : rows.insertId
                        var payload = []
                        payload.push(req.body)
                        var response = util.getPayloadMessage(payload)
                        resolve(response)
                    }
                }
            )
            connection.release()
        });
    })
}

exports.delete = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("exports.delete: " + pool.escape(req.params.id))
            connection.query(
                'DELETE FROM category WHERE uid = ?;', 
                [req.params.id], 
                function(err, rows, fields) {
                    if (err != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage((rows.affectRows == 0) ? "Data hasn't been deleted" : "Data has been deleted")))
                    }
                }
            )
            connection.release() 
        });
    })
}