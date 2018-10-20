'use strict'

var express = require('express');
var app = express();
var postingRouter = require('./router/postingRouter');

app.get('/', function (req, res) {
    res.send('This is my server!');
});

app.get('/location', function (req, res) {
    res.send('Calling location api');
});

app.use('/posting', postingRouter);

var server = app.listen(3000, function () { 
    console.log("Listening on http://localhost:3000");
});