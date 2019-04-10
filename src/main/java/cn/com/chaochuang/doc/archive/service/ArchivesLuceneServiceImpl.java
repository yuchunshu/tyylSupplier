package cn.com.chaochuang.doc.archive.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
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
import org.apache.lucene.index.Term;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
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

import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy;
import com.itextpdf.text.pdf.parser.TextExtractionStrategy;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.bean.EasyUIPage;
import cn.com.chaochuang.common.log.reference.LogStatus;
import cn.com.chaochuang.common.log.reference.SjType;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;
import cn.com.chaochuang.doc.archive.bean.LuceneShowBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocPaperArchives;
import cn.com.chaochuang.doc.event.domain.OaDocFile;

/**
 * @author dengl 2017.12.07
 *
 */

@Service
public class ArchivesLuceneServiceImpl implements ArchivesLuceneService, InitializingBean {

	/** 全文检索分析对象 */
	private static Analyzer analyzer = null;
	/** 索引路径 */
	private static FSDirectory directory;

	/** 根目录 */
	@Value(value = "${upload.rootpath}")
	private String rootPath;

	/** 索引存放路径 */
	@Value(value = "${archLucene.indexPath}")
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
	private LogService logService;

	@Override
	public void afterPropertiesSet() throws Exception {
		this.indexFile = new File(this.rootPath + "/" + this.indexPath);
		if (!this.indexFile.exists()) {
			this.indexFile.mkdirs();
		}
		analyzer = new StandardAnalyzer(Version.LUCENE_36);
		directory = FSDirectory.open(this.indexFile);
	}
	
	@Override
	public boolean writeLuceneIndex(LuceneEditBean bean) throws Exception {
		if (bean != null && bean.getFile() != null) {
			OaDocFile file = bean.getFile();
			// 获取索引
			IndexWriter writer = openIndexWriter();
			try {
				Document document = new Document();
				document.add(new Field("fileId", file.getId(), Field.Store.YES, Field.Index.ANALYZED));
				document.add(new Field("title", file.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
				DocArchives	archives = bean.getArchives();
				if(archives != null){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String str=sdf.format(archives.getCreateDate());
					document.add(new Field("date", str, Field.Store.YES, Field.Index.ANALYZED));
					document.add(new Field("id", String.valueOf(archives.getId()), Field.Store.YES, Field.Index.ANALYZED));
					document.add(new Field("fileType", archives.getFileType().getKey(), Field.Store.YES, Field.Index.ANALYZED));
				}
				SysAttach attach = bean.getAttach();
				if (attach != null) {
					// 文件路径
					String filePath = this.rootPath + "/" + attach.getSavePath() + attach.getSaveName();
					// 文件内容
					String text = null;
					File indexedFile = new File(filePath);
					if (indexedFile.exists()) {
						// 获取文件文本
						if (filePath.indexOf(".doc") > 0 || filePath.indexOf(".docx") > 0) {
							text = this.WordFileTxt(filePath, filePath.indexOf(".docx") > 0);
						}else if(filePath.indexOf(".pdf") > 0){
							text = this.PdfFileTxt(filePath);
						}else if(filePath.indexOf(".txt") > 0){
							text = this.TxtFile(filePath);
						}
						if (StringUtils.isNotBlank(text)) {
							document.add(new Field("body", text, Field.Store.YES, Field.Index.ANALYZED,
									Field.TermVector.WITH_POSITIONS_OFFSETS));
						}
					}
				}
				writer.updateDocument(new Term("fileId", file.getId()), document);
				writer.commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				writer.close();
			}
		}else{
			DocPaperArchives paperFile = bean.getPaperFile();
			// 获取索引
			IndexWriter writer = openIndexWriter();
			try {
				Document document = new Document();
				document.add(new Field("fileId", paperFile.getId(), Field.Store.YES, Field.Index.ANALYZED));
				document.add(new Field("title", paperFile.getTitle(), Field.Store.YES, Field.Index.ANALYZED));
				DocArchives	archives = bean.getArchives();
				if(archives != null){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String str=sdf.format(archives.getCreateDate());
					document.add(new Field("date", str, Field.Store.YES, Field.Index.ANALYZED));
					document.add(new Field("id", String.valueOf(archives.getId()), Field.Store.YES, Field.Index.ANALYZED));
					document.add(new Field("fileType", archives.getFileType().getKey(), Field.Store.YES, Field.Index.ANALYZED));
				}
				SysAttach attach = bean.getAttach();
				if (attach != null) {
					// 文件路径
					String filePath = this.rootPath + "/" + attach.getSavePath() + attach.getSaveName();
					// 文件内容
					String text = null;
					File indexedFile = new File(filePath);
					if (indexedFile.exists()) {
						// 获取文件文本
						if (filePath.indexOf(".doc") > 0 || filePath.indexOf(".docx") > 0) {
							text = this.WordFileTxt(filePath, filePath.indexOf(".docx") > 0);
						}else if(filePath.indexOf(".pdf") > 0){
							text = this.PdfFileTxt(filePath);
						}else if(filePath.indexOf(".txt") > 0){
							text = this.TxtFile(filePath);
						}
						if (StringUtils.isNotBlank(text)) {
							document.add(new Field("body", text, Field.Store.YES, Field.Index.ANALYZED,
									Field.TermVector.WITH_POSITIONS_OFFSETS));
						}
					}
				}
				writer.updateDocument(new Term("fileId", paperFile.getId()), document);
				writer.commit();
				return true;
			} catch (Exception e) {
				e.printStackTrace();
				return false;
			} finally {
				writer.close();
			}
		}
	}

	@Override
	public boolean removeLuceneIndex(LuceneEditBean bean) throws IOException {
		if(bean.getFile() != null){
			//获取索引
			IndexWriter writer = openIndexWriter();
			try {
	            Term term = new Term("fileId", bean.getFile().getId());
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
	public EasyUIPage archiveSearchLuceneIndex(LuceneEditBean bean, HttpServletRequest request) {
		EasyUIPage page = new EasyUIPage();
		List<LuceneShowBean> datas = new ArrayList<LuceneShowBean>();
		if (StringUtils.isBlank(bean.getKeyword()) || bean.getPage() == null || bean.getRows() == null) {
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
			BooleanClause.Occur[] flags = null;
			if (StringUtils.isNotBlank(bean.getKeyword())) {
				queries = new String[]{bean.getKeyword(), bean.getKeyword()};
                fields = new String[]{ "body", "title" };
                flags=new BooleanClause.Occur[]{BooleanClause.Occur.SHOULD,BooleanClause.Occur.SHOULD};
			}

			Query query = MultiFieldQueryParser.parse(Version.LUCENE_36, queries, fields, flags, analyzer);
			TopDocs topDocs = searcher.search(query, this.maxResultSize);
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			// 总数
			page.setTotal(Long.valueOf(scoreDocs.length));
			if (scoreDocs != null && scoreDocs.length > 0) {
				int start = (bean.getPage()-1) * bean.getRows();
            	int end = scoreDocs.length<bean.getRows()*bean.getPage()?scoreDocs.length:bean.getRows()*bean.getPage();
            	
                for(int i=start;i<end;i++){
					Document doc = searcher.doc(scoreDocs[i].doc);
					LuceneShowBean data = new LuceneShowBean();
					// 关键词加亮
					SimpleHTMLFormatter simpleHTMLFormatter = new SimpleHTMLFormatter(prefixHTML, suffixHTML);
					Highlighter highlighter = new Highlighter(simpleHTMLFormatter, new QueryScorer(query));
					highlighter.setTextFragmenter(new SimpleFragmenter(this.textMaxlen));
					if (StringUtils.isNotBlank(doc.get("body"))) {
						data.setHitContent(highlighter.getBestFragment(analyzer, "body", doc.get("body")));
					}
					if (StringUtils.isNotBlank(doc.get("title"))) {
						String title = highlighter.getBestFragment(analyzer, "title", doc.get("title"));
						if (StringUtils.isBlank(title)) {
							title = doc.get("title");
						}
						data.setFileName(title);
					}
					data.setFileId(doc.get("fileId"));
					data.setCreateDateShow(doc.get("date"));
					data.setId(doc.get("id"));
					data.setFileType(doc.get("fileType"));
					datas.add(data);
				}
			}
			page.setRows(datas);
		} catch (Exception ex) {
			ex.printStackTrace();
			logService.saveLog(SjType.普通操作, "公文全文搜索-列表查询：失败！", LogStatus.失败, request);
		} finally {
			if (searcher != null) {
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

	private IndexWriter openIndexWriter() throws IOException {
		IndexWriterConfig indexWriterConfig = new IndexWriterConfig(Version.LUCENE_36, analyzer);
		// 索引 设置为追加或者覆盖
		indexWriterConfig.setOpenMode(OpenMode.CREATE_OR_APPEND);
		IndexWriter writer = new IndexWriter(directory, indexWriterConfig);
		return writer;
	}

	/**
	 * pdf获取文件文本
	 * 
	 * @param fileName
	 * @param xdocFlag
	 * @return
	 * @throws Exception
	 */
	public String PdfFileTxt(String pdfPath) {
		PdfReader reader = null;
		StringBuffer buff = new StringBuffer();
		try {
			reader = new PdfReader(pdfPath);
			PdfReaderContentParser parser = new PdfReaderContentParser(reader);
			int num = reader.getNumberOfPages();// 获得页数
			TextExtractionStrategy strategy;
			for (int i = 1; i <= num; i++) {
				strategy = parser.processContent(i, new SimpleTextExtractionStrategy());
				buff.append(strategy.getResultantText());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buff.toString();
	}

	/**
	 * word获取文件文本
	 * 
	 * @param fileName
	 * @param xdocFlag
	 * @return
	 * @throws Exception
	 */
	public String WordFileTxt(String fileName, boolean xdocFlag) throws Exception {
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
	
	/**
	 * txt获取文件文本
	 * 
	 * @param fileName
	 * @param xdocFlag
	 * @return
	 * @throws Exception 
	 * @throws Exception
	 */
	public String TxtFile(String txtPath) throws Exception{
		StringBuilder result = new StringBuilder();
		File file = new File(txtPath);
		BufferedReader br = new BufferedReader(new FileReader(file));//构造一个BufferedReader类来读取文件
		String bodyText = null;
		while((bodyText = br.readLine())!=null){//使用readLine方法，一次读一行
             result.append(System.lineSeparator()+bodyText);
        }
        br.close(); 
		return result.toString();
	}
}
