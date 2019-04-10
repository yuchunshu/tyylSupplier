package cn.com.chaochuang.doc.lucene.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.lucene.bean.LuceneData;
import cn.com.chaochuang.doc.lucene.bean.LuceneInfo;

/**
 *
 * @author LJX
 *
 */
@Service
public class LuceneServiceImpl implements LuceneService ,InitializingBean{

	/** 全文检索转换对象 */
    private static QueryParser parser = null;
    /** 全文检索分析对象 */
    private static Analyzer analyzer = null;
    /** 索引路径 */
    private static FSDirectory directory;

    /**根目录*/
    @Value(value = "${upload.rootpath}")
    private String rootPath;
	/** 索引存放路径 */
	@Value(value = "${lucene.indexPath}")
    private String indexPath;
    /** 索引文件 */
    private File indexFile;

    private Integer textMaxlen = 200;
    /** 最大记录数 */
    private Integer maxResultSize = 400;

    /** 高亮显示HTML标记前缀 */
    private static String prefixHTML = "<font color='red'>";
    /** 高亮显示HTML标记后缀 */
    private static String suffixHTML = "</font>";

    @Autowired
    private LogService    logService;
    
	@Override
	public void afterPropertiesSet() throws Exception {
		this.indexFile = new File(this.rootPath + "/" + this.indexPath);
		if(!this.indexFile.exists()){
			this.indexFile.mkdirs();
		}
		analyzer = new StandardAnalyzer(Version.LUCENE_36);
        directory = FSDirectory.open(this.indexFile);
	}


	@Override
	public boolean writeLuceneIndex(LuceneInfo info) throws Exception {
		if(info != null && info.getFile() != null){
			OaDocFile file = info.getFile();
			//获取索引
			IndexWriter writer = openIndexWriter();
			try{
				Document document = new Document();
            	document.add(new Field("fileId", file.getId(), Field.Store.YES, Field.Index.ANALYZED));
        		document.add(new Field("title", file.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
        		if(file.getFileType() != null){
        			document.add(new Field("fileType", file.getFileType().getKey(), Field.Store.YES, Field.Index.NO));
        		}
        		document.add(new Field("status", file.getStatus().getKey(), Field.Store.YES, Field.Index.ANALYZED));
        		SysAttach attach = info.getAttach();
				if(attach != null){
					//文件路径
					String filePath = this.rootPath + "/" + attach.getSavePath() + attach.getSaveName();
					//文件内容
					String text = null;
					File indexedFile = new File(filePath);
					if(indexedFile.exists()){
						//获取文件文本
						if(filePath.indexOf(".doc") > 0 || filePath.indexOf(".docx") >0){
							text = this.WordFileTxt(filePath, filePath.indexOf(".docx")>0);
						}
						if(StringUtils.isNotBlank(text)){
							document.add(new Field("body", text, Field.Store.YES, Field.Index.ANALYZED,Field.TermVector.WITH_POSITIONS_OFFSETS));
						}
					}
				}
				writer.updateDocument(new Term("fileId",file.getId()), document);
	            writer.commit();
	            return true;
			}catch(Exception e){
				e.printStackTrace();
				return false;
			}finally {
				writer.close();
			}
		}

		return false;
	}

	private  IndexWriter openIndexWriter() throws IOException {
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36,
                analyzer);
        //索引 设置为追加或者覆盖
        indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
        IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
        return writer;
    }

	/**
	 * word获取文件文本
	 * @param fileName
	 * @param xdocFlag
	 * @return
	 * @throws Exception
	 */
	public  String WordFileTxt(String fileName, boolean xdocFlag) throws Exception {
        String bodyText = null;
        if (xdocFlag) {
            OPCPackage opcPackage = null;
            opcPackage = POIXMLDocument.openPackage(fileName);
            bodyText = new XWPFWordExtractor(opcPackage).getText();
        } else {
            FileInputStream is = new FileInputStream(fileName);
            bodyText = new WordExtractor(is).getText();
        }
        return bodyText;
    }


	@Override
	public boolean removeLuceneIndex(LuceneInfo info) throws IOException {
		if(info.getFile() != null){
			//获取索引
			IndexWriter writer = openIndexWriter();
			try {
	            Term term = new Term("fileId", info.getFile().getId());
	            writer.deleteDocuments(term);
	        } catch (IOException ex) {
	            ex.printStackTrace();
	            return false;
	        } finally {
	            try {
	                writer.close();
	            } catch (Exception ex) {
	            	ex.printStackTrace();
	            }
	        }
		}
		return false;
	}


	@Override
	public EasyUIPage searchLuceneIndex(LuceneInfo info, HttpServletRequest request) {
		EasyUIPage page = new EasyUIPage();
        List<LuceneData> datas = new ArrayList<LuceneData>();
		if(StringUtils.isBlank(info.getKeyword()) && StringUtils.isBlank(info.getStatus()) || info.getPage() == null || info.getRows() == null){
			page.setRows(datas);
			page.setTotal(Long.valueOf(0));
			return page;
		}
		IndexReader reader = null;
        IndexSearcher searcher = null;
        if (this.indexFile.list() == null || this.indexFile.list().length == 0) {
            return null;
        }
        try {
            reader = IndexReader.open(FSDirectory.open(this.indexFile));
            searcher = new IndexSearcher(reader);

            String[] queries = null;
            String[] fields = null;
         	//声明BooleanClause.Occur[]数组,它表示多个条件之间的关系 
            //BooleanClause.Occur.MUST表示and
            //BooleanClause.Occur.MUST_NOT表示not
            //BooleanClause.Occur.SHOULD表示or
            BooleanClause.Occur[] flags = null;
            if(StringUtils.isNotBlank(info.getKeyword()) && StringUtils.isBlank(info.getStatus())){
            	queries = new String[]{info.getKeyword(), info.getKeyword()};
                fields = new String[]{ "body", "title" };
                flags=new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD}; 
            }else if(StringUtils.isBlank(info.getKeyword()) && StringUtils.isNotBlank(info.getStatus())){
            	queries = new String[]{info.getStatus()};
                fields = new String[]{ "status"};
                flags=new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD}; 
                
            }else if(StringUtils.isNotBlank(info.getKeyword()) && StringUtils.isNotBlank(info.getStatus())){
            	queries = new String[]{info.getKeyword(), info.getKeyword(),info.getStatus()};
                fields = new String[]{ "body", "title","status" };
                flags=new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD,BooleanClause.Occur.MUST}; 
            }
          
            
            Query query = MultiFieldQueryParser.parse(Version.LUCENE_36, queries, fields,flags, analyzer);
            TopDocs topDocs = searcher.search(query, this.maxResultSize);
            ScoreDoc[] scoreDocs = topDocs.scoreDocs;
            //总数
            page.setTotal(Long.valueOf(scoreDocs.length));
            if(scoreDocs!= null && scoreDocs.length>0){
            	int start = (info.getPage()-1) * info.getRows();
            	int end = scoreDocs.length<info.getRows()*info.getPage()?scoreDocs.length:info.getRows()*info.getPage();
            	
                for(int i=start;i<end;i++){
                	Document doc = searcher.doc(scoreDocs[i].doc);
	                LuceneData data = new LuceneData();
	                //关键词加亮
	                SimpleHTMLFormatter simpleHTMLFormatter = new  SimpleHTMLFormatter(prefixHTML, suffixHTML);
	                Highlighter highlighter = new  Highlighter(simpleHTMLFormatter, new QueryScorer(query));
	                highlighter.setTextFragmenter(new SimpleFragmenter(this.textMaxlen));
	                if(StringUtils.isNotBlank(doc.get("body"))){
	                	data.setHitContent(highlighter.getBestFragment(analyzer, "body", doc.get("body")));
	                }
	                if(StringUtils.isNotBlank(doc.get("title"))){
	                	String title= highlighter.getBestFragment(analyzer, "title", doc.get("title"));
	                	if(StringUtils.isBlank(title)){
	                		title = doc.get("title");
	                	}
            			data.setFileName(title);
	                }
	                data.setFileId(doc.get("fileId"));
	                data.setFileType(doc.get("fileType"));
	                datas.add(data);
                }
            }
            page.setRows(datas);
        } catch (Exception ex) {
//            throw new RuntimeException(ex);
        	ex.printStackTrace();
			logService.saveLog(SjType.普通操作, "公文全文搜索-列表查询：失败！",LogStatus.失败, request);
        } finally {
            if(searcher != null) {
                try {
                    reader.close();
                    searcher.close();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
        }
		return page;
	}

}
