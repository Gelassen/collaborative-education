// import * as Sentry from '@sentry/node';
const Sentry = require('@sentry/node');
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

Sentry.init({ 
    dsn: 'https://98190676bccf4e75be3f660fa2b19667@o400397.ingest.sentry.io/5258811',
    attachStacktrace: true,
    debug: true
});

app.use(Sentry.Handlers.requestHandler());
app.use(bodyParser.json())
app.use(bodyParser.urlencoded({extended: true}))

app.use(function(req, res, next) {
    console.log("[REQUEST] " + JSON.stringify(req.path) + " at " + new Date().toLocalString())
    res.setHeader('Access-Control-Allow-Origin', '*');
    res.set('Access-Control-Allow-Headers', 'Origin, X-Requested-With, Content-Type, Accept')
    next()
})

app.get('/v1/', function(req, res, next) {
    res.send('Hello to collaborative education server!')
    myUndefinedFunction();

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

app.get('/v1/category/:id/course/:courseId/source/:sourceId/like', function(req, res, next) {
    sources.specific(req, res) 
})

app.post('/v1/like/create', function(req, res, next) {
    console.log("hit /v1/like/create")
    likes.create(req, res)
})

// The error handler must be before any other error middleware and after all controllers
app.use(Sentry.Handlers.errorHandler());

app.listen(3000)

