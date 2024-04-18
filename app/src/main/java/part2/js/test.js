let globalCounter = 0;
const justVisitedLinks = [];

const options = {
    headers: {'User-Agent': 'Mozilla/5.0 (Macintosh; Intel Mac OS X 14_4_1) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.4.1 Safari/605.1.15'}
}




function countWords(word, url, deep) {


    fetch(url, options)
        .then(response => response.text()).catch((e) => {
            console.log(`text err ${e} \t ${url} `);
            resolve(0) ;})
        .then(content => {
            const words = content.split(" ");
            const count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
            globalCounter += count;

            if (deep > 0) {

                const links = content.match(/href="https:\/\/[^"]+"/g);
                console.log("Link to check ",links.length);
                if (links) {
                    links.forEach(link => {
                        const linkUrl = link.slice(6, -1);
                        if (justVisitedLinks.includes(linkUrl)) return;
                        countWords(word, linkUrl, deep - 1);
                        justVisitedLinks.push(linkUrl);
                    });
                }
            }

            console.log(`link: ${url} \t  appears ${count} \t  total: ${globalCounter}, links checked: ${justVisitedLinks.length}`);

        }).catch((e) => {
            console.log(`Not valid Link ${e} \t ${url} `);
            ;});
         
}

countWords("ingegneria", "https://www.unipg.it", 2)