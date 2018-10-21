define({ "api": [
  {
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "optional": false,
            "field": "varname1",
            "description": "<p>No type.</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "varname2",
            "description": "<p>With type.</p>"
          }
        ]
      }
    },
    "type": "",
    "url": "",
    "version": "0.0.0",
    "filename": "./doc/main.js",
    "group": "C__Users_giam__Desktop_clutch_doc_main_js",
    "groupTitle": "C__Users_giam__Desktop_clutch_doc_main_js",
    "name": ""
  },
  {
    "type": "post",
    "url": "/posting/add-posting",
    "title": "Add New Posting",
    "name": "Add_Posting",
    "group": "Posting",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "title",
            "description": "<p>Title of Posting</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "description",
            "description": "<p>Description of Posting</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "position",
            "description": "<p>Position of User</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "position.latitude",
            "description": "<p>Latitude</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "position.longtitude",
            "description": "<p>Longtitude</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "startTime",
            "description": "<p>Start Time of Posting</p>"
          },
          {
            "group": "Parameter",
            "type": "Date",
            "optional": false,
            "field": "endTime",
            "description": "<p>End Time of Posting</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "priceAmount",
            "description": "<p>Price of Posting</p>"
          },
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "postedBy",
            "description": "<p>Object of User</p>"
          },
          {
            "group": "Parameter",
            "type": "ObjectId",
            "optional": false,
            "field": "_id",
            "description": "<p>User Id</p>"
          },
          {
            "group": "Parameter",
            "type": "String",
            "optional": false,
            "field": "name",
            "description": "<p>User Name</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Example Usage:",
        "content": "\n{\n    \"title\" : \"Wash The Dishes\",\n    \"description\" : \"Wash all my dishes for me\",\n    \"position\" : {\n        \"latitude\" : 10.0000000,\n        \"longtitude\" : 10.00000000\n    },\n    \"startTime\" : \"2018-10-20T00:00:00-0300\",\n    \"endTime\" : \"2018-10-20T00:00:00-0300\",\n    \"priceAmount\" : 5,\n    \"postedBy\" : {\n        \"_id\" : \"5bcb4db2e4ae271b049ba1a0\",\n        \"name\" : \"Addison\"\n    }\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Object",
            "optional": false,
            "field": "success",
            "description": "<p>Sucess Object</p>"
          },
          {
            "group": "Success 200",
            "type": "String",
            "optional": false,
            "field": "message",
            "description": "<p>Sucess Message</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n    {\n        \"success\": {\n            \"message\": \"Posting 'Wash The Dishes' has been successfully added\"\n        }\n    }",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./router/postingRouter.js",
    "groupTitle": "Posting"
  },
  {
    "type": "post",
    "url": "/posting/get-nearby-posting",
    "title": "Request User information",
    "name": "Get_Nearby_Posting",
    "group": "Posting",
    "parameter": {
      "fields": {
        "Parameter": [
          {
            "group": "Parameter",
            "type": "Object",
            "optional": false,
            "field": "position",
            "description": "<p>Position of User</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "position.latitude",
            "description": "<p>Latitude</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "position.longtitude",
            "description": "<p>Longtitude</p>"
          },
          {
            "group": "Parameter",
            "type": "Number",
            "optional": false,
            "field": "distance",
            "description": "<p>Search Radius in Meters</p>"
          }
        ]
      }
    },
    "examples": [
      {
        "title": "Example Usage:",
        "content": "\n{\n    \"position\" : {\n        \"latitude\" : 10.0000000,\n        \"longtitude\" : 10.00000000\n    },\n    \"distance\" : 1000\n}",
        "type": "json"
      }
    ],
    "success": {
      "fields": {
        "Success 200": [
          {
            "group": "Success 200",
            "type": "Array",
            "optional": false,
            "field": "postings",
            "description": "<p>All Nearby Postings.</p>"
          }
        ]
      },
      "examples": [
        {
          "title": "Success-Response:",
          "content": "HTTP/1.1 200 OK\n{\n  \"postings\" : [\n    {\n      \"postedBy\": {\n          \"_id\": \"5bcafa704b20803d44497931\"\n      },\n      \"_id\": \"5bcb4de1e4ae271b049ba1a2\",\n      \"title\": \"DO MY DISHES\",\n      \"description\": \"50kg of dishes to wash ASAP\",\n      \"location\": {\n          \"type\": \"Point\",\n          \"coordinates\": [\n              10,\n              10\n          ],\n          \"_id\": \"5bcb4de1419c361b04925748\"\n      },\n      \"startTime\": \"2018-10-20T03:00:00.000Z\",\n      \"endTime\": \"2018-10-20T05:30:00.000Z\",\n      \"priceAmount\": 5,\n      \"createdAt\": \"2018-10-20T15:46:41.810Z\",\n      \"updatedAt\": \"2018-10-20T15:46:41.810Z\",\n      \"__v\": 0\n  }]\n}",
          "type": "json"
        }
      ]
    },
    "version": "0.0.0",
    "filename": "./router/postingRouter.js",
    "groupTitle": "Posting"
  }
] });
