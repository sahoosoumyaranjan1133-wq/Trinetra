package com.novasearch.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.springframework.stereotype.Service;

@Service
public class SearchService {

    private final Directory directory;
    private final StandardAnalyzer analyzer;

    public SearchService() throws IOException {
        Path indexPath = Path.of("data", "novasearch-index");
        this.directory = FSDirectory.open(indexPath);
        this.analyzer = new StandardAnalyzer();
    }

    public void indexDocument(String title, String url, String content)
            throws IOException {

        IndexWriterConfig config = new IndexWriterConfig(analyzer);

        try (IndexWriter writer = new IndexWriter(directory, config)) {
            Document document = new Document();

            document.add(new StringField("url", url, Field.Store.YES));
            document.add(new TextField("title", title, Field.Store.YES));
            document.add(new TextField("content", content, Field.Store.YES));

            writer.updateDocument(new org.apache.lucene.index.Term("url", url), document);
        }
    }

    public List<String> search(String searchText) throws Exception {

        List<String> results = new ArrayList<>();

        if (searchText == null || searchText.isBlank()) {
            return results;
        }

        try (DirectoryReader reader = DirectoryReader.open(directory)) {

            IndexSearcher searcher = new IndexSearcher(reader);

            QueryParser parser = new QueryParser("content", analyzer);
            Query query = parser.parse(QueryParser.escape(searchText));

            TopDocs topDocs = searcher.search(query, 10);

            for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
                Document document = searcher.storedFields().document(scoreDoc.doc);

                results.add(
                    document.get("title") + " | " + document.get("url")
                );
            }
        }

        return results;
    }
}
