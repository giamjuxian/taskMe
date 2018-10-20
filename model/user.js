'use strict'

var mongoose = require('mongoose');
var Schema = mongoose.Schema;

var userSchema = new Schema({
    _id: {
        type: mongoose.Schema.Types.ObjectId,
        required: true
    },
    name: {
        type: String,
        required: true,
        ref: 'Name'
    },
    phoneNumber: {
        type: Number,
        ref: 'Phone Number'
    },
    ratings: {
        type: Array,
        items: {
            rating: {
                type: Number,
            },
            postedBy: {
                _id: {
                    type: mongoose.Schema.Types.ObjectId,
                    required: true
                },
                name: {
                    type: String
                }
            }
        }
    }
}, {
        timestamps: true
    });

var Users = mongoose.model('User', userSchema);
module.exports = Users;