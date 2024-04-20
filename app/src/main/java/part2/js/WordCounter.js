class WordCounter {
    constructor() {


        this.justVisited = [];
        this.globalWordsCounter = 0;
        this.stop = false;
        this.listOfMediaExention = [
            ".png",
            ".jpg",
            ".jpeg",
            ".gif",
            ".pdf",
            ".mp4",
            ".mp3",
            ".avi",
            ".flv",
            ".mov",
            ".wmv",
            ".zip",
            ".dmg",
            ".exe"
        ];
    }

    countWordsInOnePage(word, url, logger) {
        return new Promise((resolve) => {
            fetch(url)
                .then(response => response.text())
                .then(content => {
                    const words = content.split(" ");
                    const count = words.filter(w => w.toLowerCase() === word.toLowerCase()).length;
                    this.globalWordsCounter += count;
                    logger(url, count, this.globalWordsCounter, this.justVisited.length);
                    resolve(count);
                })
                .catch(() => {
                    resolve(0);
                });
        });
    }

    getAllLinksInAPage(url) {
        let listOfLinks = [];
        return new Promise((resolve) => {
            fetch(url)
                .then(response => response.text())
                .then(content => {
                    let links = content.match(/href="https:\/\/[^"]+"/g);
                    if (links) {
                        links = links.filter(link => {
                            return !this.listOfMediaExention.some(ext => link.includes(ext));
                        });
                    }

                    if (links) {
                        links.forEach(link => {
                            link = link.slice(6, -1);
                            if (this.justVisited.includes(link)) return;
                            this.justVisited.push(link);
                            listOfLinks.push(link);
                        });
                    }
                    resolve(listOfLinks);
                }).catch(() => {
                    resolve(listOfLinks);
                });
        });
    }

    async countWords(word, url, deep, logger) {
        if (this.stop) return [];

        const count = await this.countWordsInOnePage(word, url, logger);
        const counts = [count];

        if (deep > 0) {
            const links = await this.getAllLinksInAPage(url);
            const linkCounts = await Promise.all(links.map(link => this.countWords(word, link, deep - 1, logger)));
            counts.push(...linkCounts);
        }

        return counts;
    }

    async startCounting(word, url, deep, runInTheEnd, logger) {
        const counts = await this.countWords(word, url, deep, logger);
        const total = counts.reduce((a, b) => a + b, 0);
        runInTheEnd(total);

        this.stop = false;
        this.globalWordsCounter = 0;
        this.justVisited = [];

        console.log("Process finished");
    }

    async stopCounting() {
        this.stop = true;
        console.log("Process stopping...");
    }
}

module.exports = WordCounter;
