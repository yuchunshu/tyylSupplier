/*
 * FileName:    OaNoticeService.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年1月25日 (HM) 1.0 Create
 */

package cn.com.chaochuang.doc.archive.service;

import java.awt.image.BufferedImage;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;

import cn.com.chaochuang.common.bean.ReturnInfo;
import cn.com.chaochuang.common.data.service.CrudRestService;
import cn.com.chaochuang.doc.archive.bean.DoPigeEditBean;
import cn.com.chaochuang.doc.archive.bean.DocArchivesEditBean;
import cn.com.chaochuang.doc.archive.domain.DocArchives;

/**
 * @author HeYunTao
 *
 */
public interface DocArchivesService extends CrudRestService<DocArchives, Long> {

    /** 保存案卷 */
    Long saveDocArchives(DocArchivesEditBean bean);
    
    /** 迁移案卷*/
    Long saveSelectArch(DocArchivesEditBean bean);
    
    /** 归还案卷*/
    String saveReturnArch(String ids);

    /** 删除案卷 */
    public String deleteDocArchives(String ids, HttpServletRequest request);

    /** 文件归档 arcType：1、发文归档 2、收文归档 */
    public String saveFileToArch(DoPigeEditBean bean, HttpServletRequest request);

    /** 文件迁移 */
    public ReturnInfo saveChangeArch(DoPigeEditBean bean, HttpServletRequest request);

    /** 检查案卷号 */
    public boolean checkArchNo(String archNo, Long archId);

    /** 文件删除 */
    public String deleteFile(DoPigeEditBean bean, HttpServletRequest request);
    
    /** 生成二维码(QRCode)图片的公共方法*/
    BufferedImage QRCodeCommon(String content, String imgType, int size);
    
    /** 生成二维码(Zxing)图片的方法*/
    BufferedImage encode(String content, BarcodeFormat format, int size, Map<EncodeHintType, ?> hints);
    
    /** 生成二维码(QRCodeWriter)图片的方法*/
    BufferedImage createQrCode(String content, int size, String imgType);
}
