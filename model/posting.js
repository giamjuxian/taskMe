'use strict'
var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var postingSchema = new Schema({
    id: {
        type: mongoose.Schema.Types.ObjectId,
        required: true
    },
    title : {
        type: String,
        required: true,
        ref: 'Title'
    }, 
    description : {
        type: String,
        ref: 'Description'
    }, 
    position: {
        latitude: {
            type: Number,
            required: true
        },
        longtitude: {
            type: Number,
            required: true
        },
    },
    startTime: {
        type: Date,
        required: true
    },
    endTime: {
        type: Date,
        required: true
    },
    priceAmount: {
        type: Number,
        required: true
    },
}, {
    timestamps: true
});

module.exports = Posting;