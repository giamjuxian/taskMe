'use strict'

var express = require('express');
var app = express();


app.get('/', function (req, res) {
    res.send('This is my server!');
});

app.get('/location', function (req, res) {
    res.send('Calling location api');
});


var server = app.listen(3000, function () { 
    console.log("Listening on http://localhost:3000");
});