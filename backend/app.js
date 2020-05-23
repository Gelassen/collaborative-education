var express = require('express');
var bodyParser = require('body-parser');
var config = require('./config')
var app = express();

var tasks = require('./controllers/category')
var courses = require('./controllers/course')
var sources = require('./controllers/course-source')
var likes = require('./controllers/likes')

const hostname = config.WEBSERVICE_HOST;
const port = config.WEBSERVICE_PORT;

var mysql = require('mysql')

var mysql      = require('mysql');
var connection = mysql.createConnection({
  host     : config.DATABASE_HOST,
  user     : config.USER, 
  password : config.PWD,
  database : config.DATABASE
});

connection.connect(function(err) {
  if (err) {
    console.error('error connecting: ' + err.stack);
    return;
  }

  console.log('connected as id ' + connection.threadId);

  connection.query('SELECT * FROM category', function(error, rows, fields) {
    console.log('Error: ' + JSON.stringify(error));
    console.log('Rows: ' + JSON.stringify(rows));
  })
});

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.use(function(req, res, next) {
    console.log("[REQUEST] " + JSON.stringify(req.path))
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    next()
})

app.get('/', function(req, res, next) {
    res.send('Hello to collaborative education server!')
})

app.get('/v1/category', function(req, res, next) {
    tasks.all(req, res)
})

app.get('/v1/category/:id', function(req, res, next) {
    tasks.specific(req, res)
})

app.post('/v1/category/create', function(req, res, next) {
    tasks.create(req, res)
})

app.post('/v1/category/edit/:id', function(req, res, next) {
    tasks.edit(req, res)
})

app.post('/v1/category/delete/:id', function(req, res, next) {
    tasks.delete(req, res)
});

app.get('/v1/category/:id/course', function(req, res, next) {
    courses.specific(req, res)
})

app.post('/v1/course/create', function(req, res, next) {
    courses.create(req, res)
})

app.get('/v1/category/:id/course/:courseId/source', function(req, res, next) {
    sources.specific(req, res) 
})

app.post('/v1/source/create', function(req, res, next) {
    sources.create(req, res)
})

app.get('/v1/category/:id/course/:courseId/source/meta', function(req, res, next) {
    sources.specificWithMeta(req, res) 
})

// app.post('/v1/source/create', function(req, res, next) {
    // sources.create(req, res)
// })

app.get('/v1/category/:id/course/:courseId/source/:sourceId/like', function(req, res, next) {
    sources.specific(req, res) 
})

app.post('/v1/like/create', function(req, res, next) {
    console.log("hit /v1/like/create")
    likes.create(req, res)
})

app.listen(3000)

