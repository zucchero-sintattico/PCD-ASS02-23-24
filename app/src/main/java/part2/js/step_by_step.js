word = "ingegneria";
url = "https://www.unipg.it";
deep = 1;

const options = {
    headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15'}
}



justVisited = [];
globalWordsCounter = 0;

function countWordsInOnePage(word, url) {
    return new Promise((resolve) => {
        fetch(url, options)
            .then(response => response.text())
            .then(content => {

                //give me content inside tags
                var words = content.split(" ");

                var count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
                
                globalWordsCounter += count;
                console.log(`The word "${word}" appears ${count} times in the page ${url}  \t  Total: ${globalWordsCounter}, Links checked: ${justVisited.length}`);
                resolve(count);
            })
            .catch(() => {
                resolve(0);
            });
    });
}


function getAllLinksInAPage(url) {
    let listOfLinks = [];
    return new Promise((resolve, reject) => {
        fetch(url)
            .then(response => response.text())
            .then(content => {
                var links = content.match(/href="https:\/\/[^"]+"/g);
                if (links) {
                    links.forEach(link => {
                        link = link.slice(6, -1);
                        if (justVisited.includes(link)) return;
                        justVisited.push(link);
                        listOfLinks.push(link);
                        
                    });
                }
                resolve(listOfLinks);
            })
            .catch(error => {
                
            });
    });
}



async function countWords(word, url, deep) {
    var listOfPromises = [];

    var count = await countWordsInOnePage(word, url);
    listOfPromises.push(count);

    if (deep > 0) {
        var links = await getAllLinksInAPage(url);
        links.forEach(link => {
            listOfPromises.push(countWords(word, link, deep - 1));
        });
    }

    return listOfPromises;
}




countWords(word, url, deep).then(listOfPromises => {
    Promise.all(listOfPromises).then(values => {
        console.log(values);
        var total = values.map(Number).reduce((acc, val) => acc + val, 0);
        console.log("Total: ", total);
        console.log("Total global var: ", globalWordsCounter);
    });

});