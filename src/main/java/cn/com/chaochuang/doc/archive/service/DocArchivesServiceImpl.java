/*
 * FileName:    OaNoticeServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.service;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;
import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.beancopy.BeanCopyUtil;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.log.service.LogService;
import cn.com.chaochuang.common.security.util.UserTool;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.doc.archive.bean.DoPigeEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesBorrowEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesEditBean;
import cn.com.chaochuang.doc.archive.bean.LuceneEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBasic;
import cn.com.chaochuang.doc.archive.domain.DocArchivesBorrow;
import cn.com.chaochuang.doc.archive.reference.ArchStatus;
import cn.com.chaochuang.doc.archive.reference.BasicType;
import cn.com.chaochuang.doc.archive.reference.BorrowStatus;
import cn.com.chaochuang.doc.archive.reference.FileType;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBasicRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesBorrowRepository;
import cn.com.chaochuang.doc.archive.repository.DocArchivesRepository;
import cn.com.chaochuang.doc.event.domain.OaDocFile;
import cn.com.chaochuang.doc.event.reference.FileStatusFlag;
import cn.com.chaochuang.doc.event.repository.OaDocFileRepository;
import cn.com.chaochuang.doc.event.service.OaDocFileService;

/**
 * @author HeYunTao
 *
 */
@Service
@Transactional
public class DocArchivesServiceImpl extends SimpleLongIdCrudRestService<DocArchives> implements DocArchivesService {

	@Autowired
	private DocArchivesRepository 		repository;

	@Autowired
	private OaDocFileRepository 		docFileRepository;

	@Autowired
	private LogService 					logService;

	@Autowired
	private OaDocFileService 			docFileService;

	@Autowired
	private DocArchivesBasicRepository 	archivesBasicRepository;

	@Autowired
	private DocArchivesBorrowRepository archivesBorrowRepository;

	@Autowired
	private SysAttachRepository 		attachRepository;

	@Override
	public SimpleDomainRepository<DocArchives, Long> getRepository() {
		return this.repository;
	}

	@Override
	public Long saveDocArchives(DocArchivesEditBean bean) {
		DocArchives archives = null;
		OaDocFile file = null;
		if (bean != null && StringUtils.isNotEmpty(bean.getFileId())) {
			archives = new DocArchives();
			file = this.docFileService.findOne(bean.getFileId());
		}
		archives = BeanCopyUtil.copyBean(bean, DocArchives.class);
		if (archives.getDepId() == null) {
			archives.setDepId(Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
		}
		archives.setFileType(FileType.电子);
		archives.setArchStatus(ArchStatus.在库);
		if (StringUtils.isNotEmpty(archives.getCounterNo())) {// 获取柜位号名称
			List<DocArchivesBasic> counterNoName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
					archives.getCounterNo(), BasicType.档案柜,
					Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
			if (counterNoName.size() > 0 && counterNoName != null) {
				archives.setCounterNoName(counterNoName.get(0).getBasicName());
			}
		}
		if (StringUtils.isNotEmpty(archives.getArchType())) {// 获取案卷类型名称
			List<DocArchivesBasic> archTypeName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
					archives.getArchType(), BasicType.案卷类型,
					Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
			if (archTypeName.size() > 0 && archTypeName != null) {
				archives.setArchTypeName(archTypeName.get(0).getBasicName());
			}
		}
		if (StringUtils.isNotEmpty(archives.getMediumType())) {// 获取介质类型名称
			List<DocArchivesBasic> mediumTypeName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
					archives.getMediumType(), BasicType.介质类型,
					Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
			if (mediumTypeName.size() > 0 && mediumTypeName != null) {
				archives.setMediumTypeName(mediumTypeName.get(0).getBasicName());
			}
		}
		this.repository.save(archives);
		if (archives.getId() != null) {
			if (file.getStatus().equals(FileStatusFlag.待归档)) {
				file.setStatus(FileStatusFlag.归档);
				this.docFileRepository.save(file);
			}
			// 电子公文归档成功后，保存档案检索索引
			List<SysAttach> attachList = null;
			if (StringUtils.isNotEmpty(file.getId())) {
				attachList = this.attachRepository.findByOwnerId(file.getId());
				for (SysAttach attach : attachList) {
					LuceneEditBean info = new LuceneEditBean(attach, file, archives);
					try {
						ArchivesLuceneFactory.getInstance().writeIndex(info);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return archives.getId();

	}

	/**
	 * (non-Javadoc)
	 *
	 * @see cn.com.chaochuang.doc.archive.service.DocArchivesService#saveFileToArch(cn.com.chaochuang.doc.archive.bean.DoPigeEditBean)
	 */
	@Override
	public String saveFileToArch(DoPigeEditBean bean, HttpServletRequest request) {
		if (bean != null && (Tools.isEmptyString(bean.getFileIds()) || bean.getArchId() == null)) {
			return null;
		}
		if (bean != null && StringUtils.isNotBlank(bean.getFileIds())) {
			String[] ids = bean.getFileIds().split(",");
			DocArchives archive = new DocArchives();
			OaDocFile obj;
			if (bean.getArchId() != null) {
				archive = this.repository.findOne(bean.getArchId());
			}
			for (int i = 0; i < ids.length; i++) {
				obj = this.docFileRepository.findOne(ids[i]);
				obj.setStatus(FileStatusFlag.归档);
				obj.setArchives(archive);
				this.docFileRepository.save(obj);

				// 写日志
				logService.saveDefaultLog("公文(ID:" + obj.getId() + ",标题:" + obj.getTitle() + ")归档到案卷" + "(ID:"
						+ archive.getId() + ",案卷名:" + archive.getArchName() + ")", request);
			}
		}
		return null;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see cn.com.chaochuang.doc.archive.service.DocArchivesService#saveChangeArch(cn.com.chaochuang.doc.archive.bean.DoPigeEditBean)
	 */
	@Override
	public ReturnInfo saveChangeArch(DoPigeEditBean bean, HttpServletRequest request) {
		ReturnInfo returnInfo = new ReturnInfo(null, null, null);
		if (bean != null && (Tools.isEmptyString(bean.getFileIds()) || bean.getArchId() == null)) {
			return null;
		}
		if (bean != null && !Tools.isEmptyString(bean.getFileIds())) {
			String[] ids = bean.getFileIds().split(",");
			DocArchives archive = new DocArchives();
			OaDocFile obj;
			if (bean.getArchId() != null) {
				archive = this.repository.findOne(bean.getArchId());
			}
			for (int i = 0; i < ids.length; i++) {
				obj = this.docFileRepository.findOne(ids[i]);
				if (obj.getArchives().getId() == bean.getArchId()) {
					returnInfo.setError("该文件已存在于选中的案卷，不能迁移");
					return returnInfo;
				}
				obj.setArchives(archive);
				this.docFileRepository.save(obj);
				// 写日志
				logService.saveDefaultLog("公文(ID:" + obj.getId() + ",标题:" + obj.getTitle() + ")迁移到案卷" + "(ID:"
						+ archive.getId() + ",案卷名:" + archive.getArchName() + ")", request);
			}
		}

		returnInfo.setSuccess("迁移成功 ");
		return returnInfo;
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see cn.com.chaochuang.doc.archive.service.DocArchivesService#deleteFile(cn.com.chaochuang.doc.archive.bean.DoPigeEditBean,
	 *      javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public String deleteFile(DoPigeEditBean bean, HttpServletRequest request) {
		if (bean != null && (Tools.isEmptyString(bean.getFileIds()))) {
			return null;
		}
		if (bean != null && !Tools.isEmptyString(bean.getFileIds())) {
			String[] ids = bean.getFileIds().split(",");
			OaDocFile obj;
			for (int i = 0; i < ids.length; i++) {
				obj = this.docFileRepository.findOne(ids[i]);
				DocArchives archive = obj.getArchives();
				obj.setStatus(FileStatusFlag.删除);
				obj.setArchives(null);
				this.docFileRepository.save(obj);
				// 写日志
				logService.saveDefaultLog("删除案卷名为:" + archive.getArchName() + ",ID为" + archive.getId() + ",中标题为  "
						+ obj.getTitle() + "的归档文件" + "文件ID:" + obj.getId(), request);
			}

		}
		return "删除成功";
	}

	@Override
	public String deleteDocArchives(String ids, HttpServletRequest request) {
		if (ids != null) {
			String[] idArr = ids.split(",");
			for (String archId : idArr) {
				DocArchives archive = this.repository.findOne(Long.valueOf(archId));
				if (archive.getFileId().isEmpty()) {
					this.repository.delete(Long.valueOf(archId));
					logService.saveDefaultLog("删除案卷：案卷名为" + archive.getArchName() + ",id为'" + archId, request);
				} else {
					return "所要删除的案卷下存在归档文件，不能删除！";
				}
			}
		}
		return "删除成功";
	}

	/**
	 * (non-Javadoc)
	 *
	 * @see cn.com.chaochuang.doc.archive.service.DocArchivesService#checkArchNo(java.lang.String,
	 *      java.lang.Long)
	 */
	@Override
	public boolean checkArchNo(String archNo, Long archId) {
		if (Tools.isEmptyString(archNo)) {
			return true;
		}

		List<DocArchives> list = this.repository.findByArchNo(archNo);
		if (archId != null) {
			DocArchives archive = this.repository.findOne(Long.valueOf(archId));
			if (Tools.isNotEmptyString(archive.getArchNo().trim(), archNo.trim())) {
				return true;
			} else {
				if (Tools.isNotEmptyList(list)) {
					return false;
				} else {
					return true;
				}
			}
		} else {
			if (Tools.isNotEmptyList(list)) {
				return false;
			} else {
				return true;
			}
		}
	}

	@Override
	public Long saveSelectArch(DocArchivesEditBean bean) {
		DocArchives archives = null;
		if (StringUtils.isEmpty(bean.getIds()) && bean.getId() != null) {// 迁移单选
			archives = this.repository.findOne(bean.getId());
			bean.setFileType(archives.getFileType());
			archives = BeanCopyUtil.copyBean(bean, DocArchives.class);
			if (archives.getDepId() == null) {
				archives.setDepId(Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
			}
			archives.setArchStatus(ArchStatus.在库);
			if (StringUtils.isNotEmpty(archives.getCounterNo())) {// 获取柜位号名称
				List<DocArchivesBasic> counterNoName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
						archives.getCounterNo(), BasicType.档案柜,
						Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
				if (counterNoName.size() > 0 && counterNoName != null) {
					archives.setCounterNoName(counterNoName.get(0).getBasicName());
				}
			}
			if (StringUtils.isNotEmpty(archives.getArchType())) {// 获取案卷类型名称
				List<DocArchivesBasic> archTypeName = this.archivesBasicRepository.findByBasicCodeAndBasicTypeAndDepId(
						archives.getArchType(), BasicType.案卷类型,
						Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
				if (archTypeName.size() > 0 && archTypeName != null) {
					archives.setArchTypeName(archTypeName.get(0).getBasicName());
				}
			}
			if (StringUtils.isNotEmpty(archives.getMediumType())) {// 获取介质类型名称
				List<DocArchivesBasic> mediumTypeName = this.archivesBasicRepository
						.findByBasicCodeAndBasicTypeAndDepId(archives.getMediumType(), BasicType.介质类型,
								Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
				if (mediumTypeName.size() > 0 && mediumTypeName != null) {
					archives.setMediumTypeName(mediumTypeName.get(0).getBasicName());
				}
			}
			this.repository.save(archives);
			return archives.getId();
		} else {// 迁移多选
			String[] ids = bean.getIds().split(",");
			for (String id : ids) {
				archives = this.repository.findOne(Long.valueOf(id));
				bean.setFileType(archives.getFileType());
				if (StringUtils.isNotEmpty(bean.getCounterNo())) {
					archives.setCounterNo(bean.getCounterNo());
				}
				if (StringUtils.isNotEmpty(bean.getArchType())) {
					archives.setArchType(bean.getArchType());
				}
				if (StringUtils.isNotEmpty(bean.getMediumType())) {
					archives.setMediumType(bean.getMediumType());
				}
				if (StringUtils.isNotEmpty(bean.getArchYear())) {
					archives.setArchYear(bean.getArchYear());
				}
				if (bean.getRetentionDate() != null) {
					archives.setRetentionDate(bean.getRetentionDate());
				}
				if (bean.getCreateDate() != null) {
					archives.setCreateDate(bean.getCreateDate());
				}
				if (StringUtils.isNotEmpty(bean.getRemark())) {
					archives.setRemark(bean.getRemark());
				}
				archives.setArchStatus(ArchStatus.在库);
				if (StringUtils.isNotEmpty(archives.getCounterNo())) {// 获取柜位号名称
					List<DocArchivesBasic> counterNoName = this.archivesBasicRepository
							.findByBasicCodeAndBasicTypeAndDepId(archives.getCounterNo(), BasicType.档案柜,
									Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if (counterNoName.size() > 0 && counterNoName != null) {
						archives.setCounterNoName(counterNoName.get(0).getBasicName());
					}
				}
				if (StringUtils.isNotEmpty(archives.getArchType())) {// 获取案卷类型名称
					List<DocArchivesBasic> archTypeName = this.archivesBasicRepository
							.findByBasicCodeAndBasicTypeAndDepId(archives.getArchType(), BasicType.案卷类型,
									Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if (archTypeName.size() > 0 && archTypeName != null) {
						archives.setArchTypeName(archTypeName.get(0).getBasicName());
					}
				}
				if (StringUtils.isNotEmpty(archives.getMediumType())) {// 获取介质类型名称
					List<DocArchivesBasic> mediumTypeName = this.archivesBasicRepository
							.findByBasicCodeAndBasicTypeAndDepId(archives.getMediumType(), BasicType.介质类型,
									Long.valueOf(UserTool.getInstance().getCurrentUserDepartmentId()));
					if (mediumTypeName.size() > 0 && mediumTypeName != null) {
						archives.setMediumTypeName(mediumTypeName.get(0).getBasicName());
					}
				}
				this.repository.save(archives);
			}
			return archives.getId();
		}
	}

	@Override
	public String saveReturnArch(String ids) {
		String[] archids = ids.split(",");
		DocArchives archives = null;
		DocArchivesBorrow borrow = null;
		for (String archId : archids) {
			archives = this.repository.findOne(Long.valueOf(archId));// 获取归档后管理表记录
			borrow = this.archivesBorrowRepository.findByArchId(Long.valueOf(archId)).get(0);
			if (borrow != null) {
				DocArchivesBorrowEditBean bean = BeanCopyUtil.copyBean(borrow, DocArchivesBorrowEditBean.class);
				bean.setId(null);
				borrow = BeanCopyUtil.copyBean(bean, DocArchivesBorrow.class);
				borrow.setHandleUserId(Long.valueOf(UserTool.getInstance().getCurrentUserId()));
				borrow.setHandleUserName(UserTool.getInstance().getCurrentUserName());
				borrow.setBorrowDate(null);
				borrow.setReturnDate(new Date());
				borrow.setBorrowReason(null);
				borrow.setArchStatus(BorrowStatus.归还);
				borrow.setCreateTime(new Date());
				this.archivesBorrowRepository.save(borrow);
				if (StringUtils.isNotEmpty(borrow.getId())) {
					archives.setArchStatus(ArchStatus.在库);
					this.repository.save(archives);
				}
			}
		}
		return borrow.getId();
	}

	@Override
	public BufferedImage QRCodeCommon(String content, String imgType, int size) {
		BufferedImage bufImg = null;
//		try {
//			Qrcode qrcodeHandler = new Qrcode();
//			// 设置二维码排错率，可选L(7%)、M(15%)、Q(25%)、H(30%)，排错率越高可存储的信息越少，但对二维码清晰度的要求越小
//			qrcodeHandler.setQrcodeErrorCorrect('M');
//			qrcodeHandler.setQrcodeEncodeMode('B');
//			// 设置设置二维码尺寸，取值范围1-40，值越大尺寸越大，可存储的信息越大
//			qrcodeHandler.setQrcodeVersion(size);
//			// 获得内容的字节数组，设置编码格式
//			byte[] contentBytes = content.getBytes("utf-8");
//			// 图片尺寸
//			int imgSize = 67 + 12 * (size - 1);
//			bufImg = new BufferedImage(imgSize, imgSize, BufferedImage.TYPE_INT_RGB);
//			Graphics2D gs = bufImg.createGraphics();
//			// 设置背景颜色
//			gs.setBackground(Color.WHITE);
//			gs.clearRect(0, 0, imgSize, imgSize);
//
//			// 设定图像颜色> BLACK
//			gs.setColor(Color.BLACK);
//			// 设置偏移量，不设置可能导致解析出错
//			int pixoff = 2;
//			// 输出内容> 二维码
//			if (contentBytes.length > 0 && contentBytes.length < 800) {
//				boolean[][] codeOut = qrcodeHandler.calQrcode(contentBytes);
//				for (int i = 0; i < codeOut.length; i++) {
//					for (int j = 0; j < codeOut.length; j++) {
//						if (codeOut[j][i]) {
//							gs.fillRect(j * 3 + pixoff, i * 3 + pixoff, 3, 3);
//						}
//					}
//				}
//			} else {
//				throw new Exception("QRCode content bytes length = " + contentBytes.length + " not in [0, 800].");
//			}
//			gs.dispose();
//			bufImg.flush();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		return bufImg;
	}

	@Override
	public BufferedImage encode(String content, BarcodeFormat format, int size,
			Map<EncodeHintType, ?> hints) {
		BufferedImage image = null;
		try {
			content = new String(content.getBytes("UTF-8"), "ISO-8859-1");
			int imgSize = 67 + 12 * (size - 1);
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, format, imgSize, imgSize);
			image = new BufferedImage(bitMatrix.getWidth(), bitMatrix.getWidth(), BufferedImage.TYPE_INT_RGB);
			for (int x = 0; x < imgSize; x++) {
				for (int y = 0; y < imgSize; y++) {
					image.setRGB(x, y, bitMatrix.get(x, y) == true ? Color.BLACK.getRGB() : Color.WHITE.getRGB());
				}
			}
			image.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return image;
	}

	@Override
	public BufferedImage createQrCode(String content, int size, String imgType) {
		BufferedImage image = null;
		try {
			//设置二维码纠错级别MAP
			Hashtable<EncodeHintType,ErrorCorrectionLevel> hintMap = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();
			hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);//矫错级别
			QRCodeWriter qrCodeWriter = new QRCodeWriter();
			//创建比特矩阵（位矩阵）的QR码编码的字符串
			BitMatrix bitMatrix = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, size, size,hintMap);
			//使BufferedImage勾画QRcode
			int matrixWidth = bitMatrix.getWidth();
			image = new BufferedImage(matrixWidth-50, matrixWidth-50, BufferedImage.TYPE_INT_RGB);
			image.createGraphics();
			Graphics2D gs = (Graphics2D)image.getGraphics();
			gs.setColor(Color.WHITE);
			gs.fillRect(0, 0, matrixWidth, matrixWidth);
			//使用比特矩阵画并保存图像
			gs.setColor(Color.BLACK);
			for(int i = 0; i< matrixWidth;i++){
				for(int j = 0; j< matrixWidth; j++){
					if(bitMatrix.get(i, j)){
						gs.fillRect(i-25, j-25, 1, 1);
					}
				}
			}
			gs.dispose();
			image.flush();
		} catch (WriterException e) {
			e.printStackTrace();
		}
		return image;
	}
}
