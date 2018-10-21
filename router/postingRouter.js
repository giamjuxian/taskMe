'use strict'

var express = require('express');
var postingService = require('../services/postingService');
var postingRouter = express.Router();

postingRouter.get('/', function (req, res) {
    res.send('Posting Test');
})

/**
 * @api {post} /posting/get-nearby-posting Request User information
 * @apiName Get Nearby Posting
 * @apiGroup Posting
 *
 * @apiParam {Object} position Position of User
 * @apiParam {Number} position.latitude Latitude
 * @apiParam {Number} position.longtitude Longtitude
 * @apiParam {Number} distance Search Radius in Meters
 *
 * @apiExample Example Usage:
 * 
        {
            "position" : {
                "latitude" : 10.0000000,
                "longtitude" : 10.00000000
            },
            "distance" : 1000
        }
 *
 * @apiSuccess {Array} postings All Nearby Postings.
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
 *     {
 *       "postings" : [
 *         {
 *           "postedBy": {
 *               "_id": "5bcafa704b20803d44497931"
 *           },
 *           "_id": "5bcb4de1e4ae271b049ba1a2",
 *           "title": "DO MY DISHES",
 *           "description": "50kg of dishes to wash ASAP",
 *           "location": {
 *               "type": "Point",
 *               "coordinates": [
 *                   10,
 *                   10
 *               ],
 *               "_id": "5bcb4de1419c361b04925748"
 *           },
 *           "startTime": "2018-10-20T03:00:00.000Z",
 *           "endTime": "2018-10-20T05:30:00.000Z",
 *           "priceAmount": 5,
 *           "createdAt": "2018-10-20T15:46:41.810Z",
 *           "updatedAt": "2018-10-20T15:46:41.810Z",
 *           "__v": 0
 *       }]
 *     }
 */
postingRouter.post('/get-nearby-posting', function (req, res) {
    var currentLatitude = req.body.position.latitude;
    var currentLongtitude = req.body.position.longtitude;
    var distance = req.body.distance

    postingService.getNearbyPosting(currentLatitude, currentLongtitude, distance, function (err, postings) {
        if (err) {
            return res.status(404).send({ error: err.message })
        }
        return res.status(200).json({ postings: postings })
    })
})

/**
 * @api {post} /posting/add-posting Add New Posting
 * @apiName Add Posting
 * @apiGroup Posting
 *
 * @apiParam {String} title Title of Posting
 * @apiParam {String} description Description of Posting
 * @apiParam {Object} position Position of User
 * @apiParam {Number} position.latitude Latitude
 * @apiParam {Number} position.longtitude Longtitude
 * @apiParam {Date} startTime Start Time of Posting
 * @apiParam {Date} endTime End Time of Posting
 * @apiParam {Number} priceAmount Price of Posting
 * @apiParam {Object} postedBy Object of User
 * @apiParam {ObjectId} _id User Id
 * @apiParam {String} name User Name
 *
 * @apiExample Example Usage:
 * 
        {
            "title" : "Wash The Dishes",
            "description" : "Wash all my dishes for me",
            "position" : {
                "latitude" : 10.0000000,
                "longtitude" : 10.00000000
            },
            "startTime" : "2018-10-20T00:00:00-0300",
            "endTime" : "2018-10-20T00:00:00-0300",
            "priceAmount" : 5,
            "postedBy" : {
                "_id" : "5bcb4db2e4ae271b049ba1a0",
                "name" : "Addison"
            }
        }
 *
 * @apiSuccess {Object} success Sucess Object
 * @apiSuccess {String} message Sucess Message
 * @apiSuccessExample Success-Response:
 *     HTTP/1.1 200 OK
        {
            "success": {
                "message": "Posting 'Wash The Dishes' has been successfully added"
            }
        }
 */
postingRouter.post('/add-posting', function (req, res) {
    var title = req.body.title;
    var description = req.body.description;
    var latitude = req.body.position.latitude;
    var longtitude = req.body.position.longtitude;
    var startTime = req.body.startTime;
    var endTime = req.body.endTime;
    var priceAmount = req.body.priceAmount;
    var postedById = req.body.postedBy._id;
    var postedByName = req.body.postedBy.postedByName;

    postingService.addPosting(title, description, latitude, longtitude, startTime, endTime
        , priceAmount, postedById, postedByName, function (err, posting) {
            if (err) {
                return res.status(404).send({ error: err.message })
            }
            console.log("====== POSTING '" + posting.title + "' HAS BEEN SUCCESSFULLY ADDED");
            return res.status(200).json({
                success: {
                    message: "Posting '" + posting.title + "' has been successfully added"
                }
            })
        })

})

module.exports = postingRouter;