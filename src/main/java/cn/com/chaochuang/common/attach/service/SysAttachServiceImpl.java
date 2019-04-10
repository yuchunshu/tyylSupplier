/*
 * FileName:    SysAttachServiceImpl.java
 * Description:
 * Company:     
 * Copyright:    (c) 2016
 * History:     2016年1月26日 (HM) 1.0 Create
 */

package cn.com.chaochuang.common.attach.service;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import com.alibaba.fastjson.JSON;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import cn.com.chaochuang.common.data.repository.SimpleDomainRepository;
import cn.com.chaochuang.common.data.service.SimpleUuidCrudRestService;
import cn.com.chaochuang.common.reference.IsImage;
import cn.com.chaochuang.common.upload.support.UploadFileInfoPersistence;
import cn.com.chaochuang.common.upload.support.UploadFileItem;
import cn.com.chaochuang.common.util.AttachHelper;
import cn.com.chaochuang.common.util.AttachUtils;
import cn.com.chaochuang.common.util.Tools;
import cn.com.chaochuang.common.attach.bean.AttachBean;
import cn.com.chaochuang.common.attach.domain.SysAttach;
import cn.com.chaochuang.common.attach.repository.SysAttachRepository;

/**
 * @author HM
 *
 */
@Service
@Transactional
public class SysAttachServiceImpl extends SimpleUuidCrudRestService<SysAttach> implements SysAttachService,
                UploadFileInfoPersistence {

    @Value(value = "${upload.appid.sysattach}")
    private String              appid;

    @Value(value = "${upload.rootpath}")
    private String              rootPath;

    @Value(value = "upload.fileprefix")
    private String              filePrefix;

    @Autowired
    private SysAttachRepository repository;

    @Autowired
    private EntityManager       em;

    @Override
    public SimpleDomainRepository<SysAttach, String> getRepository() {
        return this.repository;
    }

    @Override
    public Map<String, List<SysAttach>> getAttachMap(String ownerId, String ownerType) {
        String ownerIdStr = ownerId;
        List<SysAttach> attachList = null;
        if (ownerIdStr != null) {
            attachList = this.repository.findByOwnerIdAndOwnerType(ownerIdStr, ownerType);
        }
        if (attachList != null && attachList.size() > 0) {
            Map<String, List<SysAttach>> map = new HashMap<String, List<SysAttach>>();
            map.put(ownerIdStr, attachList);
            return map;
        }
        return null;
    }

    @Override
    public Map<String, List<SysAttach>> getAttachMap(String ownerId, String[] ownerType) {
        String ownerIdStr = ownerId;
        List<SysAttach> attachList = null;
        if (ownerIdStr != null) {
            attachList = this.repository.findByOwnerIdAndOwnerTypeIn(ownerIdStr, ownerType);
        }
        if (attachList != null && attachList.size() > 0) {
            Map<String, List<SysAttach>> map = new HashMap<String, List<SysAttach>>();
            map.put(ownerIdStr, attachList);
            return map;
        }
        return null;
    }
    
    @Override
    public boolean deleteAttach(String attachId) {
        if (attachId != null) {
            SysAttach attach = this.repository.findOne(attachId);
            AttachUtils.removeFile(this.rootPath + File.separator + attach.getSavePath() + attach.getSaveName());
            this.repository.delete(attach);
            return true;
        }
        return false;
    }

    @Override
    public String getAppId() {
        return appid;
    }

    @Override
    public UploadFileItem getUploadFileInfo(String id) {
        if (null != id) {
            SysAttach attach = getRepository().findOne(id);
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

    @Override
    public String saveUploadFileInfo(UploadFileItem fileItem) {
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
        this.persist(attach);
        return attach.getId().toString();
    }


    @Override
    public List<SysAttach> findByOwnerIdAndOwnerType(String ownerId, String ownerType) {
        List<SysAttach> attachs = this.repository.findByOwnerIdAndOwnerType(ownerId, ownerType);
        return attachs;
    }

    /**
     * (non-Javadoc)
     *
     * @see cn.com.chaochuang.common.attach.service.SysAttachService#findByOwnerId(java.lang.String)
     */
    @Override
    public List<SysAttach> findByOwnerId(String ownerId) {

        return this.repository.findByOwnerId(ownerId);
    }

    @Override
    public void deleteOwnerAttach(String ownerId, String ownerType) {
        List<SysAttach> attachs = repository.findByOwnerIdAndOwnerType(ownerId, ownerType);
        if (attachs != null && attachs.size() > 0) {
            for (SysAttach att : attachs) {
                AttachUtils.removeFile(this.rootPath + File.separator + att.getSavePath() + att.getSaveName());
            }
            repository.delete(attachs);
        }
    }


    @Override
    public void deleteAttach(List<SysAttach> attachs) {
        if (Tools.isNotEmptyList(attachs)) {
            for (SysAttach a : attachs) {
                this.deleteAttach(a.getId());
            }
        }
    }

	@Override
	public SysAttach copyAttach(SysAttach  a) {
		if(null != a){
			String savePath = AttachHelper.appendYearMonthDayPath(StringUtils.trimToEmpty(this.appid));
	        savePath = FilenameUtils.normalize(savePath.trim() + "/", true);
	        String fileExt = FilenameUtils.getExtension(a.getTrueName());
	        String saveName = AttachHelper.getUniqueShortFileName(filePrefix, fileExt);
	        String filePath = FilenameUtils.normalize(this.rootPath + "/" + savePath + "/", true);
	        try {
				AttachUtils.copyFile(getAttachPath(a), filePath+saveName);
				SysAttach newAttach = new SysAttach();
				BeanUtils.copyProperties(a, newAttach,"id");
				newAttach.setSaveName(saveName);
				newAttach.setSavePath(savePath);
				this.persist(newAttach);
				return newAttach;
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
		return null;
	}

	@Override
	public String getAttachPath(SysAttach a) {
		if(null != a){
			return FilenameUtils.normalize(this.rootPath + "/" + a.getSavePath() + "/", true) + a.getSaveName();
		}
		return null;
	}

	@Override
	public String batchDownload(HttpServletResponse response, List<AttachBean> attachList, String downLoadAttachName) {
		if(Tools.isNotEmptyList(attachList)){
			String outFile = FilenameUtils.normalize(this.rootPath + "/" + AttachUtils.getUniqueFileName() + ".zip", true);
			File file = new File(outFile);
			if(!file.getParentFile().exists()){
				file.getParentFile().mkdirs();
			}
			byte[] b = new byte[1024];
			ZipOutputStream out = null;
			try {
				out = new ZipOutputStream(new FileOutputStream(file));
				for(AttachBean a : attachList){
					String aFilePath = FilenameUtils.normalize(this.rootPath + "/" + a.getSavePath() + "/", true) + a.getSaveName();
					FileInputStream in = new FileInputStream(new File(aFilePath));
					out.putNextEntry(new ZipEntry(a.getTrueName()));
					int len;
	                while ((len = in.read(b)) > 0) {
	                    out.write(b, 0, len);
	                }
	                out.closeEntry();
	                in.close();
				}
				out.flush();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
	            if (out != null) {
	                try {
	                    out.close();
	                } catch (Exception ex) {
                		ex.printStackTrace();
	                }
	            }
	        }
			AttachUtils.downloadAttach(response, downLoadAttachName+".zip", outFile, false);
			// 删除压缩包
	        if (file.exists()) {
	        	file.delete();
	        }
		}

		return null;
	}


    private File dataStreamToFile(DataHandler dataHandler, String filePath) {
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        try {
            File file = new File(filePath);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                if (file.createNewFile()) {
                    byte[] buff = new byte[1024 * 8];
                    int len;
                    inputStream = dataHandler.getInputStream();
                    fileOutputStream = new FileOutputStream(file);
                    while ((len = inputStream.read(buff)) > 0) {
                        fileOutputStream.write(buff, 0, len);
                    }
                    return file;
                } else {
                    return null;
                }
            } else {
                return file;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.flush();
                    fileOutputStream.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    

    }
    
    @Override
    public SysAttach linkAttachAndOwner(String attachId, String ownerId, String ownerType) {
        if (attachId != null) {
            SysAttach attach = this.findOne(attachId);
            if (attach != null) {
                attach.setOwnerId(ownerId);
                attach.setOwnerType(ownerType);
                this.repository.save(attach);
                return attach;
            }
        }
        return null;

    }

}
