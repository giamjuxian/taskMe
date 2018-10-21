'use strict'

var express = require('express');
var userService = require('../services/userService');
var userRouter = express.Router();

userRouter.post('/add-user', function (req, res) {
    var name = req.body.name;
    var phoneNumber = req.body.phoneNumber;
    userService.addUser(name, phoneNumber, function (err, user) {
        if (err) return res.status(404).send({ error: err.message })
        console.log("====== USER '" + user.name + "' HAS BEEN SUCCESSFULLY ADDED");
        return res.status(200).json({
            success: {
                message: "User '" + user.name + "' has been sucessfully added"
            }
        })
    })
})

module.exports = userRouter;