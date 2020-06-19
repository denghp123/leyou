package cn.itcast;

import org.csource.fastdfs.*;

/**
 * @Author dhp
 * @Date 2020/6/12 0:20
 * @Version 1.0
 */
public class FastDFS  {


    public static void main(String[] args) throws Exception {

        //        七步骤：
//        1、加载配置文件，配置文件中的内容就是 tracker 服务的地址。ClientGlobal加载
        ClientGlobal.init("D:\\xuexi\\workSpace\\leyou\\leyou-parent\\fastDFS-demo\\src\\main\\resources\\fastDFS_clent.conf");
        //        2、创建一个 TrackerClient 对象。直接 new 一个
        TrackerClient trackerClient = new TrackerClient();
        //        3、使用 TrackerClient 对象获取连接，获得一个 TrackerServer 对象
        TrackerServer trackerServer = trackerClient.getConnection();
        //        4、创建一个 StorageServer 的引用，值为 null
        StorageServer storageServer = null;
        //        5、创建一个 StorageClient 对象，需要两个参数 TrackerServer 对象、StorageServer 的引用
        StorageClient storageClient = new StorageClient(trackerServer,storageServer);
        //        6、使用 StorageClient 对象上传图片
//        storageClient.upload_file(本地文件路径,扩展名,null)
        String[] upload_files = storageClient.upload_file("D:\\xuexi\\t.png", "png", null);
        for (String upload_file : upload_files) {
            System.out.println(upload_file);
        }
//        group1
//        M00/00/00/wKgZhVxNMQmAeC4nAAFP0yQoHiA254.jpg
//        192.168.25.133/group1/M00/00/00/wKgZhVxNMQmAeC4nAAFP0yQoHiA254.jpg


    }



}
