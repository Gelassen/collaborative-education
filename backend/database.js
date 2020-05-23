var mysql = require('mysql')
var config = require('./config')

var config = {
	host     : config.HOST,
	user     : config.USER,
	password : config.PWD,
    database : config.DATABASE,
    connectionLimit: '5'
};

var pool = mysql.createPool(config);

pool.on('connection', function(connection) {
    console.log('connected to database')

    connection.on('error', function(err) {
        console.log(new Date(), "MySQL error", err.code)
    });

    connection.on('close', function(connection) {
        console.log(new Date(), "MySQL close", err)
    })

});

module.exports = {
    getConnection: (callback) => {
        return pool.getConnection(callback)
    },

    escape: (param) => {
        return pool.escape(param)
    } 
}