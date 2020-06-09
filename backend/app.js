const Sentry = require('@sentry/node');
var express = require('express');
var bodyParser = require('body-parser');
var config = require('./config')
var pool = require('./database');
var app = express();

var tasks = require('./controllers/category')
var courses = require('./controllers/course')
var sources = require('./controllers/course-source')
var likes = require('./controllers/likes')
var offers = require('./controllers/offers')

const hostname = config.WEBSERVICE_HOST;
const port = config.WEBSERVICE_PORT;

Sentry.init({ 
    dsn: 'https://98190676bccf4e75be3f660fa2b19667@o400397.ingest.sentry.io/5258811',
    attachStacktrace: true,
    debug: true
});

app.use(Sentry.Handlers.requestHandler());
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.use(function(req, res, next) {
    console.log("[REQUEST] " + JSON.stringify(req.path) + " at " + new Date().toISOString());
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    next()
})

app.get('/v1/', function(req, res, next) {
    res.send('Hello to collaborative education server!')
})

app.get('/v1/category', function(req, res, next) {
    pool.status()
    tasks.all(req, res)
})

app.get('/v1/category/:id', function(req, res, next) {
    pool.status()
    tasks.specific(req, res)
})

app.post('/v1/category/create', function(req, res, next) {
    pool.status()
    tasks.create(req, res)
})

app.post('/v1/category/edit/:id', function(req, res, next) {
    tasks.edit(req, res)
})

app.post('/v1/category/delete/:id', function(req, res, next) {
    tasks.delete(req, res)
});

app.get('/v1/category/:id/course', function(req, res, next) {
    pool.status()
    courses.specific(req, res)
})

app.post('/v1/course/create', function(req, res, next) {
    pool.status()
    courses.create(req, res)
})

app.get('/v1/category/:id/course/:courseId/source', function(req, res, next) {
    pool.status()
    sources.specific(req, res) 
})

app.post('/v1/source/create', function(req, res, next) {
    pool.status()
    sources.create(req, res)
})

app.get('/v1/category/:id/course/:courseId/source/meta', function(req, res, next) {
    pool.status()
    sources.specificWithMeta(req, res) 
})

app.get('/v1/category/:id/course/:courseId/source/:sourceId/like', function(req, res, next) {
    pool.status()
    sources.specific(req, res) 
})

app.post('/v1/like/create', function(req, res, next) {
    console.log("hit /v1/like/create")
    likes.create(req, res)
})

app.get('/v1/offers', function(req, res, next) {
    pool.status()
    offers.all(req, res)
})

app.post('/v1/offers', function(req, res, next) {
    pool.status()
    offers.create(req, res)
})

// The error handler must be before any other error middleware and after all controllers
app.use(Sentry.Handlers.errorHandler());

app.listen(3000)

