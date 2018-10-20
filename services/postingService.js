'use strict'

var express = require('express');
var mongoose = require('mongoose');
var Postings = require('../model/posting');
var ObjectID = require('mongodb').ObjectID;

exports.addPosting = function(title, description, latitude, longtitude, startTime, endTime
    , priceAmount, postedById, postedByName, callback) {
    
    startTime = new Date(startTime);
    endTime = new Date(endTime);

    let newPosting = {
        _id: new ObjectID(),
        title: title,
        description: description,
        position: {
            latitude: latitude,
            longtitude: longtitude
        },
        startTime: startTime,
        endTime: endTime,
        priceAmount: priceAmount,
        postedBy: {
            _id: postedById,
            name: postedByName
        }
    }

    Postings.create(newPosting, function (err, posting) {
        if (err) {
            return callback(err);
        }
        return callback(null, newPosting);
    })
}