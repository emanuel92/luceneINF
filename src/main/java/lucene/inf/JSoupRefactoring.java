package lucene.inf;

import org.apache.lucene.document.*;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.jsoup.Jsoup;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class JSoupRefactoring {

    public static void parseHtml(Path file, IndexWriter writer, Document doc) throws IOException {

        // generating a String out of Warc content
        //String htmlString = new String(Files.readAllBytes(file));
        //System.out.printf(htmlString);

        // creating JSoup Document from Lucene Document --> set structure of content for indexing -- Iteration einfügen?
        org.jsoup.nodes.Document soupDoc = Jsoup.parse(String.valueOf(doc));
        String soupTitle = soupDoc.title();
        String soupBody = soupDoc.body().text();

        //System.out.printf("Title: %s%n", soupTitle);
        //System.out.printf("Body: %s", soupBody);

        // creating Lucene-Document from JSoup Document
        Document doc1 = new Document();
        doc1.add(new StringField("title", soupTitle, Field.Store.YES));
        doc1.add(new TextField("body", soupBody, Field.Store.YES));

        //System.out.printf("Title: %s%n", soupTitle);
        //System.out.printf("Body: %s", soupBody);

        //Writing Lucene-Index-Files
        writer.updateDocument(new Term("contents", file.toString()), doc1);
    }
}


