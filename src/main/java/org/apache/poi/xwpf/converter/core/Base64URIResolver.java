package org.apache.poi.xwpf.converter.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

public class Base64URIResolver implements IURIResolver {

    private String tempPath = "/upload_files";

    @Override
    public String resolve(String uri) {
        // 读取图片，转为base64编码
        String src = "";
        InputStream in = null;
        File imageFile = new File(FilenameUtils.normalize(tempPath + "/" + uri));
        if (imageFile.exists()) {
            try {
                in = new FileInputStream(imageFile);

                byte[] data = null;
                data = new byte[in.available()];
                in.read(data);

                if (data != null) {
                    src = "data:image/png;base64," + Base64.encodeBase64String(data);
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (in != null) {
                    IOUtils.closeQuietly(in);
                }
            }

            // 删除文件
            if (!imageFile.delete()) {
                System.gc();
                imageFile.delete();
            }
        }
        return src;
    }

}
