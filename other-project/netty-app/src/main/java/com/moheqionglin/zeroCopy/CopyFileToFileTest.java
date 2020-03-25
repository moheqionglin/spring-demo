package com.moheqionglin.zeroCopy;



import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * @author wanli.zhou
 * @description
 * @time 22/11/2018 10:13 PM
 */
public class CopyFileToFileTest {
    private static final String FILE_PATH = "/Users/wanli.zhou/Downloads/dump.o2hprof.index";

    private static final String DEST_FILE_DIRECTORY = "/Users/wanli.zhou/Desktop/aaaaa";

    public static void regularCopy() throws IOException {
        long start = System.currentTimeMillis();
        //Open
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);

        //Read
        byte[] buffer = new byte[fileInputStream.available()];
        fileInputStream.read(buffer);

        //Write
        FileOutputStream fileOutputStream = new FileOutputStream(DEST_FILE_DIRECTORY + "/" + "regularCopy.file");
        fileOutputStream.write(buffer);

        long end = System.currentTimeMillis() - start;
        fileOutputStream.close();
        fileInputStream.close();
        System.out.println("regularCopy cost " + end);
    }

    public static void regularCopyByShard() throws IOException {
        long start = System.currentTimeMillis();
        //Open
        FileInputStream fileInputStream = new FileInputStream(FILE_PATH);
        int size = fileInputStream.available();

        FileOutputStream fileOutputStream = new FileOutputStream(DEST_FILE_DIRECTORY + "/" + "regularCopy.file");
        //Read
        byte[] buffer = new byte[200000];
        for(int length = 0 ; (length =  fileInputStream.read(buffer)) > 0; ){
            //Write
            fileOutputStream.write(buffer);
        }

        long end = System.currentTimeMillis() - start;
        fileOutputStream.close();
        fileInputStream.close();
        System.out.println("regularCopyByShard cost " + end);
    }

    public static void regularCopyByShardNIO() throws IOException {

        long start = System.currentTimeMillis() ;
        FileChannel fileChannel = FileChannel.open(Paths.get(FILE_PATH), StandardOpenOption.READ);
        FileChannel destFileChannel = FileChannel.open(Paths.get(DEST_FILE_DIRECTORY).resolve("mapperMemoryCopy.file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        int size = (int) fileChannel.size();

        ByteBuffer byteBuffer = ByteBuffer.allocate(200000);
        for(int length = 0; (length = fileChannel.read(byteBuffer)) > 0;){
            byteBuffer.flip();
            destFileChannel.write(byteBuffer);
        }
        long end = System.currentTimeMillis() - start;
        fileChannel.close();
        destFileChannel.close();
        System.out.println("regularCopyByShardNIO NIO cost " + end);
    }

    public static void directMappedCopy() throws IOException {
        long start = System.currentTimeMillis();
        // Open
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));
        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect((int) fileInChannel.size());

        //direct memory
        fileInChannel.read(directByteBuffer);
        directByteBuffer.flip();
        //write
        FileChannel destFileChannel = FileChannel.open(Paths.get(DEST_FILE_DIRECTORY).resolve("mapperMemoryCopy.file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        destFileChannel.write(directByteBuffer);

        fileInChannel.close();
        destFileChannel.close();

        long end = System.currentTimeMillis() - start;

        System.out.println("directMappedCopy cost " + end);
    }

    public static void directMappedByShardCopy() throws IOException {
        long start = System.currentTimeMillis();
        // Open
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));
        FileChannel destFileChannel = FileChannel.open(Paths.get(DEST_FILE_DIRECTORY).resolve("mapperMemoryCopy.file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);

        ByteBuffer directByteBuffer = ByteBuffer.allocateDirect(200000);
        int size = (int) fileInChannel.size();
        for(int length = 0; (length = fileInChannel.read(directByteBuffer)) > 0;){
            //direct memory
            directByteBuffer.flip();
            //write
            destFileChannel.write(directByteBuffer);
        }

        fileInChannel.close();
        destFileChannel.close();

        long end = System.currentTimeMillis() - start;

        System.out.println("directMappedByShardCopy cost " + end);
    }
    public static void mapperMemoryCopy() throws IOException {
        long start = System.currentTimeMillis();

        // Open
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));

        //Mapped memory
        MappedByteBuffer mapedBuffer = fileInChannel.map(FileChannel.MapMode.READ_ONLY, 0, fileInChannel.size());

//        Path dstPath = Files.createFile(Paths.get(DEST_FILE_DIRECTORY).resolve("mapperMemoryCopy.file"));
        FileChannel destFileChannel = FileChannel.open(Paths.get(DEST_FILE_DIRECTORY).resolve("mapperMemoryCopy.file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        destFileChannel.write(mapedBuffer);

        fileInChannel.close();
        destFileChannel.close();

        long end = System.currentTimeMillis() - start;

        System.out.println("mapperMemoryCopy cost " + end);
    }

    public static void transferToCopy() throws IOException {
        long start = System.currentTimeMillis();
        FileChannel fileInChannel = FileChannel.open(Paths.get(FILE_PATH));
        FileChannel destFileChannel = FileChannel.open(Paths.get(DEST_FILE_DIRECTORY + "/" + "mapperMemoryCopy.file"), StandardOpenOption.CREATE, StandardOpenOption.WRITE);
        fileInChannel.transferTo(0, fileInChannel.size(), destFileChannel);
        long end = System.currentTimeMillis() - start;
        System.out.println("transferToCopy cost " + end);
    }

    public static void main(String[] args) throws IOException {
        for(int i =0; i < 10; i ++)
        //第一次1404ms  后面基本上 940左右
//        regularCopy();
        //392  336
//        regularCopyByShard();
        //26 0
        regularCopyByShardNIO();
//            directMappedCopy();
            //23 0
//            directMappedByShardCopy();
        //每次平均290左右
//        mapperMemoryCopy();
        //290左右 应为 transferTo的时候， JDK会判断是 transferTo到 网卡还是本地文件，如果是本地文件那么走内存映射（应为linux2.6以后的slice不是很好用，所以直接走内存映射），如果是网卡的话走 sendFile系统调用
//        transferToCopy();

    }
}