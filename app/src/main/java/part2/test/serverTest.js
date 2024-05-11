const express = require('express');
const app = express();
const http = require('http').Server(app);
const io = require('socket.io')(http);

app.get('/', (req, res) => {
    let word = req.query.word;
    let numberOfWords = req.query.numberOfWords;
    let numberOfLinks = req.query.numberOfLinks;
    let path = req.query.path;
    //response must be a string with the word repeated numberOfWords times
    let response = "";
    let i = 0;
    for (i = 0; i < numberOfWords; i++) {
        response += word + " ";
    }
    
    for (i = 0; i < numberOfLinks; i++) {
        response += `<a href="http://localhost:4000/?word=${word}&numberOfWords=${numberOfWords}&numberOfLinks=${numberOfLinks}&path=${path + i}">Go to... </a>`;
    }
    
    res.send(response);

});





http.listen(4000, () => {
    console.log('API server is running on port http://localhost:4000');
    
});