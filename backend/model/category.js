var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise( (resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
               {sql:  'SELECT * FROM category', timeout: 60000}, 
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
                {sql: 'SELECT * FROM cageory WHERE uid = ?;', timeout: 60000}, 
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

exports.edit = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var body = req.body.params
            connection.query(
                {sql: 'UPDATE category SET title = ? WHERE uid = ?', timeout: 60000}, 
                [body.title, req.params.id], 
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getMessage(200, (rows.affectRows == 0) ? "No change applied" : "Data is changed")))
                    }
                    
                }
            )
        });
    })
}

exports.create = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            var param = req.body.title;
            connection.query(
                {sql: 'INSERT INTO category SET title = ?', timeout: 60000}, 
                [param], 
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
        });
    })
}

exports.delete = function(req) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            console.log("exports.delete: " + pool.escape(req.params.id))
            connection.query(
                {sql: 'DELETE FROM category WHERE uid = ?;', timeout: 60000}, 
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
