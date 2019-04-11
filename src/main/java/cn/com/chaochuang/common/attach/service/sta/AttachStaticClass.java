package cn.com.chaochuang.common.attach.service.sta;

import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.reference.IsImage;
import cn.com.chaochuang.common.upload.support.UploadFileItem;

/**
 * @author rongln
 * @date 2017年12月22日 下午11:15:12
 * 
 */

public abstract class AttachStaticClass {
	
	private static AttachStaticClass att;
	
	public static AttachStaticClass getInstance() {
        return att;
    }

	public static SysAttach saveUploadFileInfo(UploadFileItem fileItem) {
		SysAttach attach = new SysAttach();
        attach.setTrueName(fileItem.getTrueName());
        attach.setSaveName(fileItem.getSaveName());
        attach.setSavePath(fileItem.getSavePath());
        attach.setFileSize(fileItem.getFileSize());
        
        String attachType = fileItem.getTrueName().substring(fileItem.getTrueName().lastIndexOf(".")+1, fileItem.getTrueName().length()); 
        attach.setAttachType(attachType);
        if (fileItem.getSaveName().contains(".png") || fileItem.getSaveName().contains("jpg")
                        || fileItem.getSaveName().contains("jpeg") || fileItem.getSaveName().contains("gif")
                        || fileItem.getSaveName().contains("bmp")) {
            attach.setIsImage(IsImage.图文公告);
        } else {
            attach.setIsImage(IsImage.非图文公告);
        }
		return attach;
	}
	
	public static UploadFileItem getUploadFileInfo(String id,SimpleDomainRepository<SysAttach, String> repository) {
		if (null != id) {
            SysAttach attach = repository.findOne(id);
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
