'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
    id: {
        type: mongoose.Schema.Types.ObjectId,
        required: true
    },
    name : {
        type: String,
        required: true,
        ref: 'Name'
    }, 
    phoneNumber : {
        type: Number,
        ref: 'Phone Number'
    }, 
    rating: {
        type: Number,
        default: 5.0,
        required: true
    }
}, {
    timestamps: true
});

module.exports = User;