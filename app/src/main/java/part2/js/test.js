let globalCounter = 0;
const justVisitedLinks = [];

let options = {
    headers: {'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36'}
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