var pool = require('../database');
var util = require('../utils/network')

exports.getAll = function(req, res) {
    return new Promise((resolve) => {
        pool.getConnection(function(err, connection) {
            connection.query(
                { sql: 'SELECT * FROM proposals;', timeout: 60000 },
                function(error, rows, fields) {
                    if (error != null) {
                        resolve(JSON.stringify(util.getErrorMessage()))
                    } else {
                        resolve(JSON.stringify(util.getPayloadMessage(rows)))
                    }
                    connection.release()
                }
            )
        })
    })
}

exports.create = function(req) {
    return new Promise( (resolve) => {
        var proposal = req.body.metadata.proposal
        pool.getConnection(function(err, connection) {
            connection.query(
                { sql: 'INSERT INTO proposals SET name = ?, vote = ?, users = ?;', timeout: 60000 },
                [proposal.name, proposal.vote, JSON.stringify(proposal.users)],
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var payload = []
                        payload.push(proposal)
                        resolve(util.getErrorMessage(payload))
                    } else {
                        var response = []
                        proposal.uid = (rows.affectRows == 0) ? -1 : rows.insertId
                        response.push(proposal)
                        resolve(util.getPayloadMessage(response))
                    }
                    connection.release()
                }
            )
        })
    })
}

exports.update = function(req) {
    return new Promise( (resolve) => {
        var proposal = req.body.metadata.proposal
        pool.getConnection(function(err, connection) {
            connection.query(
                { sql: 'UPDATE proposals SET name =?, vote = ?, users = ?;', timeout: 60000 },
                [proposal.name, proposal.vote, JSON.stringify(proposal.users)],
                function(error, rows, fields) {
                    if (error != null) {
                        console.log(JSON.stringify(error))
                        var payload = []
                        payload.push(proposal)
                        resolve(util.getErrorMessage(payload))
                    } else {
                        var response = []
                        proposal.uid = (rows.affectRows == 0) ? -1 : rows.insertId
                        response.push(proposal)
                        resolve(util.getPayloadMessage(response))
                    }
                    connection.release()
                }
            )
        })
    })
}