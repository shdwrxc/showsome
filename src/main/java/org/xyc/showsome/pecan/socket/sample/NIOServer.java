package org.xyc.showsome.pecan.socket.sample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;

/**
 * DatagramChannel ��ͨ��UDP��д�����е����ݡ�
 * SocketChannel ��ͨ��TCP��д�����е����ݡ�
 * ServerSocketChannel���Լ����½�����TCP���ӣ���Web��������������ÿһ���½��������Ӷ��ᴴ��һ��SocketChannel��
 */

public class NIOServer {

    //ͨ��������
    private Selector selector;

    /**
     * ���һ��ServerSocketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
     *
     * @param port �󶨵Ķ˿ں�
     * @throws IOException
     */
    public void initServer(int port) throws IOException {
        // ���һ��ServerSocketͨ��
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        // ����ͨ��Ϊ������
        serverChannel.configureBlocking(false);
        // ����ͨ����Ӧ��ServerSocket�󶨵�port�˿�
        serverChannel.socket().bind(new InetSocketAddress(port));
        // ���һ��ͨ��������
        this.selector = Selector.open();
        //��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_ACCEPT�¼�,ע����¼���
        //�����¼�����ʱ��selector.select()�᷵�أ�������¼�û����selector.select()��һֱ������
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
//        serverChannel.register(selector, SelectionKey.OP_READ);
    }

    public void sss() throws Exception{
        ServerSocketChannel channel = ServerSocketChannel.open();
        channel.socket().bind(new InetSocketAddress(12));
        Selector selector1 = Selector.open();
        channel.register(selector1, SelectionKey.OP_ACCEPT);
    }

    /**
     * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
     *
     * @throws IOException
     */
//    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        // ��ѯ����selector
        while (true) {
            //��ע����¼�����ʱ���������أ�����,�÷�����һֱ����
            println("����ʼ�ȴ�");
            selector.select();
            // ���selector��ѡ�е���ĵ�������ѡ�е���Ϊע����¼�
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                println("�������ѭ��");
                SelectionKey key = (SelectionKey) ite.next();
                // ɾ����ѡ��key,�Է��ظ�����
                ite.remove();
                // �ͻ������������¼�
                if (key.isAcceptable()) {
                    println("����ʼ����");
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // ��úͿͻ������ӵ�ͨ��
                    SocketChannel channel = server.accept();
                    println("�������ӳɹ�");
                    // ���óɷ�����
                    channel.configureBlocking(false);

                    //��������Ը��ͻ��˷�����ϢŶ
                    channel.write(ByteBuffer.wrap(new String("hello").getBytes("UTF-8")));
                    println("�����͸��ͻ�����Ϣ");
                    //�ںͿͻ������ӳɹ�֮��Ϊ�˿��Խ��յ��ͻ��˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
                    channel.register(this.selector, SelectionKey.OP_READ);

                    // ����˿ɶ����¼�
                } else if (key.isReadable()) {
                    read(key);
                }

            }

        }
    }

    public static void println(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
        System.out.println(sdf.format(new Date()) + "," + str);
    }

    /**
     * �����ȡ�ͻ��˷�������Ϣ ���¼�
     *
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException {
        // �������ɶ�ȡ��Ϣ:�õ��¼�������Socketͨ��
        SocketChannel channel = (SocketChannel) key.channel();
        // ������ȡ�Ļ�����
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data, "UTF-8").trim();
        System.out.println("������յ���Ϣ��" + msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes("UTF-8"));
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.write(outBuffer);// ����Ϣ���͸��ͻ���
    }

    /**
     * ��������˲���
     *
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOServer server = new NIOServer();
        server.initServer(8000);
        server.listen();
    }
}
