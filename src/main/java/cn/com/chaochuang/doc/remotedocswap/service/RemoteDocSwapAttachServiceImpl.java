/*
 * FileName:    RemoteDocSwapAttachServiceImpl.java
 * Description:
 * Company:     南宁超创信息工程有限公司
 * Copyright:   ChaoChuang (c) 2016
 * History:     2016年7月21日 (yuandl) 1.0 Create
 */

package cn.com.chaochuang.doc.remotedocswap.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleLongIdCrudRestService;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.doc.remotedocswap.domain.RemoteDocSwapAttach;
import cn.com.chaochuang.doc.remotedocswap.reference.RemoteAttachType;
import cn.com.chaochuang.doc.remotedocswap.repository.RemoteDocSwapAttachRepository;

/**
 * @author yuandl
 *
 */
@Service
@Transactional
public class RemoteDocSwapAttachServiceImpl extends SimpleLongIdCrudRestService<RemoteDocSwapAttach> implements
                RemoteDocSwapAttachService, UploadFileInfoPersistence {

    @Value(value = "${upload.appid.remote.docswap.attach}")
    private String                        appid;

    @Value(value = "${upload.rootpath}")
    private String                        rootpPath;

    /** 封体处理完后的存放路径 */
    @Value(value = "${remote.docswap.envelopeDonePath}")
    private String                        envelopeDonePath;

    /** 封体发送的存放路径 */
    @Value(value = "${remote.docswap.envelopeSendPath}")
    private String                        envelopeSendPath;

    @Autowired
    private RemoteDocSwapAttachRepository repository;

    @Override
    public SimpleDomainRepository<RemoteDocSwapAttach, Long> getRepository() {
        return this.repository;
    }

    // @Override
    // public RemoteDocSwapAttach saveRemoteDocSwapAttach(MultipartFile multipartFile, String attachType) {
    // if (multipartFile == null) {
    // return null;
    // }
    // RemoteDocSwapAttach attach = new RemoteDocSwapAttach();
    // attach.setFileSize(new Long(multipartFile.getSize()));
    // if (StringUtils.isEmpty(attachType)) {
    // attach.setRemoteAttachType(RemoteDocSwapAttach.ATTACH_TYPE_OTHER);
    // } else {
    // attach.setRemoteAttachType(attachType);
    // }
    // attach.setSaveName(AttachUtils.getDocUniqueFileName("",
    // AttachUtils.getFileSuffix(multipartFile.getOriginalFilename())));
    // attach.setTrueName(multipartFile.getOriginalFilename());
    // String savePath = AttachHelper.appendYearMonthDayPath(StringUtils.trimToEmpty(this.envelopeSendPath));
    // savePath = FilenameUtils.normalize(savePath.trim() + "/", true);
    // attach.setSavePath(savePath);
    // // attach.setIsImage(Constants.NO);
    // try {
    // File filePath = new File(attach.getSavePath());
    // if (!filePath.exists()) {
    // filePath.mkdirs();
    // }
    // String fileName = attach.getPhysicalFile();
    // multipartFile.transferTo(new File(fileName));
    // } catch (IOException ex) {
    // ex.printStackTrace();
    // }
    // this.repository.save(attach);
    // return attach;
    // }

    @Override
    public void deleteRemoteDocSwapAttach(Long attachId) {
        RemoteDocSwapAttach attach = this.repository.findOne(attachId);
        if (attach == null) {
            return;
        }
        this.delete(attach);
        AttachUtils.removeFile(attach.getPhysicalFile());
    }

    @Override
    public Map<String, List<RemoteDocSwapAttach>> getAttachMap(Long remoteDocSwapContentId, RemoteAttachType attachType) {
        List<RemoteDocSwapAttach> attachs = this.repository.findByRemoteDocSwapContentIdAndRemoteAttachType(
                        remoteDocSwapContentId, attachType);
        if (attachs != null && attachs.size() > 0) {
            Map<String, List<RemoteDocSwapAttach>> result = new HashMap<String, List<RemoteDocSwapAttach>>();
            result.put(remoteDocSwapContentId.toString(), attachs);
            return result;
        }
        return null;
    }

    @Override
    public List<RemoteDocSwapAttach> findByContentIdAndAttachType(Long remoteDocSwapContentId,
                    RemoteAttachType attachType) {
        List<RemoteDocSwapAttach> attachs = this.repository.findByRemoteDocSwapContentIdAndRemoteAttachType(
                        remoteDocSwapContentId, attachType);
        return attachs;
    }

    @Override
    public String getAppId() {
        return this.appid;
    }

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {
        RemoteDocSwapAttach attach = new RemoteDocSwapAttach();
        attach.setTrueName(fileItem.getTrueName());
        attach.setSaveName(fileItem.getSaveName());
        attach.setSavePath(fileItem.getSavePath());
        attach.setFileSize(fileItem.getFileSize());
        // if (fileItem.getSaveName().contains(".png") || fileItem.getSaveName().contains(".jpg")
        // || fileItem.getSaveName().contains(".jpeg")) {
        // attach.setIsImage(IsImage.图文公告);
        // } else {
        // attach.setIsImage(IsImage.非图文公告);
        // }

        this.persist(attach);
        return attach.getId().toString();
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        if (null != id) {
            RemoteDocSwapAttach attach = this.findOne(Long.parseLong(id));
            if (null != attach) {
                UploadFileItem fileItem = new UploadFileItem();

                fileItem.setTrueName(attach.getTrueName());
                fileItem.setSaveName(attach.getSaveName());
                fileItem.setSavePath(attach.getSavePath());
                fileItem.setFileSize(attach.getFileSize());

                return fileItem;
            }
        }

        return null;
    }

}
