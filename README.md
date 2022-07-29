# search

## Features
- Segmented the name of the images and created indices for them using ElasticSearch, added some category flags into the images’ name to guarantee this step works.
- Processed over 160k lines of data to produce the inverted index for the images’ name using Hadoop and MapReduce. Stored the inverted index in the MongoDB database.
- Built a web interface to search the crawled images using both elasticsearch and a customized search strategy based on Java Programming Language and SpringBoot framework.

## Screenshoots
1. Home page
![Image](https://github.com/zwang99/search/blob/main/images/home.png)


2. Runtime using ElasticSearch
![Image](https://github.com/zwang99/search/blob/main/images/elastic.png)

3. Runtime using Hadoop generated inverted index
![Image](https://github.com/zwang99/search/blob/main/images/hadoop.png)

## Acknowledgements
Course project collaborate with teammate Chenhao Pan
