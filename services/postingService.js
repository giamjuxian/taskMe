'use strict'

var express = require('express');
var mongoose = require('mongoose');
var Postings = require('../model/posting');
var ObjectID = require('mongodb').ObjectID;

exports.addPosting = function(title, description, latitude, longtitude, 
    startTime, endTime, priceAmount, postedById, postedByName, callback) {

    startTime = new Date(startTime);
    endTime = new Date(endTime);

    let newPosting = {
        _id: new ObjectID(),
        title: title,
        description: description,
        location: {
            type: "Point",
            coordinates: [longtitude, latitude]
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

exports.getNearbyPosting = function (latitude, longtitude, distance, callback) {
    var center = [longtitude, latitude];
    var radius = distance / 6378.1 ;
    Postings.where('location.coordinates').within({ center: center, radius: radius, unique: true, spherical: true }).exec(
        function(err, postings) {
            if (err) return callback(err);
            console.log(postings);
            return callback(null, postings);
        }
    );
}