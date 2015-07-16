package udp.gcks.com.cn.udpsend;

import android.os.Bundle;
import android.os.Message;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @version 1.0
 * @说明 线程创建自己的UDP连接，端口动态，发送一组数据然后接收服务端返回
 */
class BroadCastUdp implements Runnable {
    public static void main(String[] args) {
        new Thread(new BroadCastUdp()).start();
    }

    public BroadCastUdp() {

    }

    public void run() {

        try {
            init();
            int i = 0;
            byte[] buffer = new byte[1024 * 64]; // 缓冲区
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd H:m:s");
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, InetAddress.getByName(MainActivity.ip), MainActivity.port);
            while (MainActivity.flag) {
                i++;
                // 发送随机的数据
                String content2 = i + ". android : This is point " + MainActivity.type + ";Time is " + format.format(new Date());
                String con=content2+">>>>>>>dsafasnfdkjansjnfdjnakjsnfknajkwnefjnawefjnajenfjnjanfjnjansfajsnfjknasjknfjknsajknvjnvjknjvnjsnjdnvjkansjfdnjksanjfnjasnjfnjsannafjkansdjnfjkansjknfjnasjknfjnasjnfnasknfkjnasjknfjnasjknkdsafasnfdkjansjnfdjnakjsnfknajkwnefjnawefjnajenfjnjanfjnjansfajsnfjknasjknfjknsajknvjnvjknjvnjsnjdnvjkansjfdnjksanjfnjasnjfnjsannafjkansdjnfjkansjknfjnasjknfjnasjnfnasknfkjnasjknfjnasjknkdsafasnfdkjansjnfdjnakjsnfknajkwnefjnawefjnajenfjnjanfjnjansfajsnfjknasjknfjknsajknvjnvjknjvnjsnjdnvjkansjfdnjksanjfnjasnjfnjsannafjkansdjnfjkansjknfjnasjknfjnasjnfnasknfkjnasjknfjnasjknkdsafasnfdkjansjnfdjnakjsnfknajkwnefjnawefjnajenfjnjanfjnjansfajsnfjknasjknfjknsajknvjnvjknjvnjsnjdnvjkansjfdnjksanjfnjasnjfnjsannafjkansdjnfjkansjknfjnasjknfjnasjnfnasknfkjnasjknfjnasjknkmkmkmkmkmklmkmkmmkmkmkmkmk";

                byte[] btSend = con.getBytes();
                packet.setData(btSend);
                //System.out.println(content2);
                Bundle bundle = new Bundle();
                Message m = new Message();
                bundle.putString("content", Thread.currentThread().getName() + "->" + content2);
                m.setData(bundle);

                try {
                    sendDate(packet);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                MainActivity.handler.sendMessage(m);
                Thread.sleep(9);
            }
        } catch (Exception e) {
            Bundle bundle = new Bundle();
            Message m = new Message();
            bundle.putString("content", "连接失败");
            m.setData(bundle);
            MainActivity.handler.sendMessage(m);
        }
    }

    /**
     * 接收数据包，该方法会造成线程阻塞
     *
     * @return
     * @throws IOException
     */
    public void receive(DatagramPacket packet) throws Exception {
        try {
            datagramSocket.receive(packet);
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * 发送数据包到指定地点
     *
     * @param packet
     * @throws IOException
     */
    public void sendDate(DatagramPacket packet) {
        try {
            datagramSocket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化客户端连接
     *
     * @throws SocketException
     */
    public void init() throws SocketException {
        try {
            datagramSocket = new DatagramSocket();
            datagramSocket.setSoTimeout(10 * 1000);
            System.out.println("客户端启动成功");
        } catch (Exception e) {
            datagramSocket = null;
            System.out.println("客户端启动失败");
            e.printStackTrace();
        }
    }

    private DatagramSocket datagramSocket = null; // 连接对象
}