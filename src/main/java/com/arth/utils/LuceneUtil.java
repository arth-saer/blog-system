package com.arth.utils;

import com.arth.pojo.Blog;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.search.highlight.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LuceneUtil {

    private String lucenePath = "D://lucene";
    private Directory dir = null;

    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy年MM月");

    private IndexWriter getWriter() throws IOException {
        dir = FSDirectory.open(Paths.get(lucenePath, new String[0]));
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter writer = new IndexWriter(dir, config);
        return writer;
    }

    public void insertIndex(Blog blog) throws IOException {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));
        doc.add(new StringField("createDate", simpleDateFormat.format(new Date()), Field.Store.YES));
        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyword", blog.getKeyword(), Field.Store.YES));
        writer.addDocument(doc);
        writer.close();
    }

    public void updateIndex(Blog blog) throws IOException {
        IndexWriter writer = getWriter();
        Document doc = new Document();
        doc.add(new StringField("id", String.valueOf(blog.getId()), Field.Store.YES));
        doc.add(new TextField("title", blog.getTitle(), Field.Store.YES));

        doc.add(new TextField("content", blog.getContentNoTag(), Field.Store.YES));
        doc.add(new TextField("keyword", blog.getKeyword(), Field.Store.YES));
        writer.updateDocument(new Term("id", String.valueOf(blog.getId())), doc);
        writer.close();
    }

    public void deleteIndex(String id) throws IOException {
        IndexWriter writer = getWriter();
        writer.deleteDocuments(new Term[]{new Term("id", id)});
        writer.forceMergeDeletes();
        writer.commit();
        writer.close();
    }

    public List<Blog> search(String q) throws IOException, ParseException, InvalidTokenOffsetsException {
        List<Blog> blogs = new LinkedList<>();
        dir = FSDirectory.open(Paths.get(lucenePath, new String[0]));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        BooleanQuery.Builder builder = new BooleanQuery.Builder();
        SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

        QueryParser parser1 = new QueryParser("title", analyzer);
        Query query1 = parser1.parse(q);

        QueryParser parser2 = new QueryParser("content", analyzer);
        Query query2 = parser1.parse(q);

        QueryParser parser3 = new QueryParser("keyword", analyzer);
        Query query3 = parser1.parse(q);

        builder.add(query1, BooleanClause.Occur.SHOULD);
        builder.add(query2, BooleanClause.Occur.SHOULD);
        builder.add(query3, BooleanClause.Occur.SHOULD);

        TopDocs docs = searcher.search(builder.build(), 500);

        QueryScorer scorer = new QueryScorer(query1);
        Fragmenter fragmenter = new SimpleSpanFragmenter(scorer);
        SimpleHTMLFormatter formatter = new SimpleHTMLFormatter("<b><font color='green'>", "</font></b>");
        Highlighter highlighter = new Highlighter(formatter, scorer);
        highlighter.setTextFragmenter(fragmenter);

        for(ScoreDoc doc : docs.scoreDocs){
            Document document = searcher.doc(doc.doc);
            Blog blog = new Blog();
            blog.setId(Integer.parseInt(document.get("id")));
            blog.setCreateDateStr(document.get("createDate"));
            String title = document.get("title");
            String content = StringEscapeUtils.escapeHtml(document.get("content"));
            String keyword = document.get("keyword");

            if(title != null){
                TokenStream tokenStream = analyzer.tokenStream("title", new StringReader(title));
                String hTitle = highlighter.getBestFragment(tokenStream, title);
                if(hTitle != null && !hTitle.equals("")){
                    blog.setTitle(hTitle);
                }
                else {
                    blog.setTitle(title);
                }
            }
            if(content != null){
                TokenStream tokenStream = analyzer.tokenStream("content", new StringReader(content));
                String hContent = highlighter.getBestFragment(tokenStream, content);
                if(hContent != null && !hContent.equals("")){
                    blog.setContent(hContent);
                }
                else {
                    if(content.length() <= 200){
                        blog.setContent(content);
                    }
                    else{
                        blog.setContent(content.substring(0, 200));
                    }

                }
            }
            if(keyword != null){
                TokenStream tokenStream = analyzer.tokenStream("keyword", new StringReader(keyword));
                String hKeyword = highlighter.getBestFragment(tokenStream, keyword);
                if(hKeyword != null && !hKeyword.equals("")){
                    blog.setKeyword(hKeyword);
                }
                else {
                    blog.setKeyword(keyword);
                }
            }
            blogs.add(blog);
        }
        return blogs;

    }
}
