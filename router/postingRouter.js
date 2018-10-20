'use strict'

var express = require('express');
var postingService = require('../services/postingService');
var postingRouter = express.Router();

postingRouter.get('/', function (req, res) {
    res.send('Posting Test');
})

postingRouter.post('/get-nearby-posting', function(req, res) {
    var currentLatitude = req.body.position.latitude;
    var currentLongtitude = req.body.position.longtitude;
    var distance = req.body.distance

    postingService.getNearbyPosting(currentLatitude, currentLongtitude, distance, function (err, postings) {
        if (err) {
            res.status(404).send({ error: err })
        }
        res.status(200).send({
            postings: postings
        })
    })
})

postingRouter.post('/add-posting', function (req, res) {
    var title = req.body.title;
    var description = req.body.description;
    var latitude = req.body.position.latitude;
    var longtitude = req.body.position.longtitude;
    var startTime = req.body.startTime;
    var endTime = req.body.endTime;
    var priceAmount = req.body.priceAmount;
    var postedById = req.body.postedBy._id;
    var postedByName = req.body.name;

    postingService.addPosting(title, description, latitude, longtitude, startTime, endTime
        , priceAmount, postedById, postedByName, function (err, posting) {
            if (err) {
                res.status(404).send({
                    error: {
                        message: "Error in adding posting."
                    }
                })
            }
            console.log("====== POSTING " + posting._id + " HAS BEEN SUCCESSFULLY ADDED");
            res.status(200).send({
                success: {
                    message: "Posting has been added successfully."
                }
            })
        })

})

module.exports = postingRouter;