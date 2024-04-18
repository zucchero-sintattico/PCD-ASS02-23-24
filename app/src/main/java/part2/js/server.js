const express = require('express');
const app = express();
const http = require('http').Server(app);
const io = require('socket.io')(http);
const { startCounting } = require('./step_by_step.js');

let stopProcess = false;

app.get('/socket.io/socket.io.js', (req, res) => {
    res.sendFile(__dirname + '/node_modules/socket.io/client-dist/socket.io.js');
  });


io.on('connection', (socket) => {
    console.log('a user connected');
    socket.on('disconnect', () => {
      console.log('user disconnected');
    });
  });






app.get('/', (req, res) => {
    res.sendFile(__dirname + '/index.html');
});

app.get('/count-words', (req, res) => {
    
    function logger(url, count, globalWordsCounter, justVisitedLength) {
        console.log(`The word "${req.query.word}" appears ${count} times in the page ${url}  \t  Total: ${globalWordsCounter}, Links checked: ${justVisitedLength}`);
        io.emit('countRealTime', { url, count, globalWordsCounter, justVisitedLength });
    }

    function runInTheEnd(countedWords) {
        io.emit('endProcess');
    }

    startCounting(req.query.word, req.query.url, req.query.deep, runInTheEnd, logger);

    res.json({ message: 'Process started' });
    
});

app.get('/stop-process', (req, res) => {
    stopProcess = true;
    res.json({ message: 'Process stopped' });
});


  

http.listen(3000, () => {
    console.log('API server is running on port 3000');
    
});