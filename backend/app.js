var express = require('express');
var bodyParser = require('body-parser');
var config = require('./config')
var app = express();

var tasks = require('./controllers/category')
var courses = require('./controllers/course')
var sources = require('./controllers/course-source')
var likes = require('./controllers/likes')

const hostname = config.HOST;
const port = config.WEBSERVICE_PORT;

var mysql = require('mysql')

app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.use(function(req, res, next) {
    console.log("[REQUEST] " + JSON.stringify(req.path))
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    next()
})

app.get('/', function(req, res, next) {
    res.send('Hello World!')
})

// app.get('/v1/test', function(req, res, next) {
    // tasks.test(req, res)
// })

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

