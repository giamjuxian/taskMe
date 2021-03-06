'use strict'

var express = require('express');
var app = express();
var mongoose = require('mongoose');
var bodyParser = require('body-parser');
var postingRouter = require('./router/postingRouter');
var userRouter = require('./router/userRouter');
var MongoClient = require('mongodb').MongoClient;

var uri = "mongodb+srv://giamjuxian:giamjuxian@cluster0-lpkir.mongodb.net/test?retryWrites=true";

app.use(express.static('public'));
app.get('/', function (req, res) {
	res.send('This is my server!');
});

app.use(bodyParser.json()); // to support JSON-encoded bodies
app.use('/posting', postingRouter);
app.use('/user', userRouter);

// Connect MongoDB
mongoose.connect(uri);
var db = mongoose.connection;
db.on('error', console.error.bind(console, 'connection error:'));
db.once('open', function () {
	console.log("Connected to database");
});

var server = app.listen(3000, function () {
	console.log("");
	console.log("Listening on http://localhost:3000");
});



