'use strict'
var express = require('express');
var userRouter = express.Router();
var mongoose = require('mongoose');
var Users = require('../model/user');
var ObjectID = require('mongodb').ObjectID;

userRouter.get('/', function (req, res) {
    res.send('user router Test');
})

userRouter.get('/about', function (req, res) {
    res.send('This is a user router test');
})

userRouter.get('/add', function (req, res) {
    let testUser  = {
        _id: new ObjectID(),
        name: "ADDISON",
        phoneNumber: "91234567"
    };

    Users.create(testUser, function (err, user) {
        if (err) return console.error(err);
        console.log("USER " + user.name + " CREATED");
        res.send("COMPLETED YO");
    })
})

module.exports = userRouter;