'use strict'

var express = require('express');
var postingRouter = express.Router();

postingRouter.get('/', function (req, res) {
    res.send('Posting Test');
})

postingRouter.get('/about', function (req, res) {
    res.send('This is a posting test');
})

module.exports = postingRouter;