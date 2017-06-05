package org.xyc.showsome.pecan.socket.sample;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOClient {

    //ͨ��������
    private Selector selector;

    /**
     * ���һ��Socketͨ�������Ը�ͨ����һЩ��ʼ���Ĺ���
     * @param ip ���ӵķ�������ip
     * @param port  ���ӵķ������Ķ˿ں�
     * @throws IOException
     */
    public void initClient(String ip,int port) throws IOException {
        // ���һ��Socketͨ��
        SocketChannel channel = SocketChannel.open();
        // ����ͨ��Ϊ������
        channel.configureBlocking(false);
        // ���һ��ͨ��������
        this.selector = Selector.open();

        // �ͻ������ӷ�����,��ʵ����ִ�в�û��ʵ�����ӣ���Ҫ��listen���������е�
        //��channel.finishConnect();�����������
        channel.connect(new InetSocketAddress(ip,port));
        //��ͨ���������͸�ͨ���󶨣���Ϊ��ͨ��ע��SelectionKey.OP_CONNECT�¼���
        channel.register(selector, SelectionKey.OP_CONNECT);
//        channel.register(selector, SelectionKey.OP_READ);
    }

    /**
     * ������ѯ�ķ�ʽ����selector���Ƿ�����Ҫ������¼�������У�����д���
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    public void listen() throws IOException {
        // ��ѯ����selector
        while (true) {
            NIOServer.println("�ͻ��˿�ʼ�ȴ�");
            selector.select();
            // ���selector��ѡ�е���ĵ�����
            Iterator ite = this.selector.selectedKeys().iterator();
            while (ite.hasNext()) {
                NIOServer.println("�ͻ��˽���ѭ��");
                SelectionKey key = (SelectionKey) ite.next();
                // ɾ����ѡ��key,�Է��ظ�����
                ite.remove();
                // �����¼�����
                if (key.isConnectable()) {
                    NIOServer.println("�ͻ��˿�ʼ����");
                    SocketChannel channel = (SocketChannel) key.channel();
                    // ����������ӣ����������
                    if(channel.isConnectionPending()){
                        channel.finishConnect();
                        NIOServer.println("�ͻ����������");
                    }
                    // ���óɷ�����
                    channel.configureBlocking(false);

                    //��������Ը�����˷�����ϢŶ
                    channel.write(ByteBuffer.wrap(new String("world").getBytes("UTF-8")));
                    NIOServer.println("�ͻ��˷��ͷ������Ϣ");
                    //�ںͷ�������ӳɹ�֮��Ϊ�˿��Խ��յ�����˵���Ϣ����Ҫ��ͨ�����ö���Ȩ�ޡ�
                    channel.register(this.selector, SelectionKey.OP_READ);

                    // ����˿ɶ����¼�
                } else if (key.isReadable()) {
                    read(key);
                }

            }

        }
    }
    /**
     * �����ȡ����˷�������Ϣ ���¼�
     * @param key
     * @throws IOException
     */
    public void read(SelectionKey key) throws IOException{
        SocketChannel channel = (SocketChannel) key.channel();
        // ������ȡ�Ļ�����
        ByteBuffer buffer = ByteBuffer.allocate(10);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data, "UTF-8").trim();
        System.out.println("�ͻ����յ���Ϣ��"+msg);
        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes("UTF-8"));
        try {
            Thread.sleep(1000 * 5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        channel.write(outBuffer);// ����Ϣ���͸������
    }


    /**
     * �����ͻ��˲���
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        NIOClient client = new NIOClient();
        client.initClient("localhost",8000);
        client.listen();
    }
}
