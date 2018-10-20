'use strict'

var express = require('express');
var mongoose = require('mongoose');
var Users = require('../model/user');
var ObjectID = require('mongodb').ObjectID;


exports.addUser = function (name, phoneNumber, callback) {
    let newUser = {
        _id: new ObjectID(),
        name: name,
        phoneNumber: phoneNumber
    };

    var query = {
        $or:
            [
                { name: name },
                { phoneNumber: phoneNumber }
            ]
    }

    Users.findOne(query, function (err, user) {
        if (err) return callback(err);
        if (user) {
            const duplicateUserErr = new Error('Duplicate User');
            return callback(duplicateUserErr);
        }
        Users.create(newUser, function (err, user) {
            if (err) return callback(err);
            return callback(null, user);
        })
    })
}