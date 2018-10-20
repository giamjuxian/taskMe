'use strict'

var express = require('express');
var postingRouter = express.Router();

postingRouter.use(function timeLog (req, res, next) {
    console.log('Accessing Posting at   Time: ', Date.now());
    next();
})

postingRouter.get('/', function (req, res) {
    res.send('Posting Test');
})

postingRouter.get('/about', function (req, res) {
    res.send('This is a posting test');
})

module.exports = postingRouter;