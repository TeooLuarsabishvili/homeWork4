import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketClientExample {

    public void startClient()
            throws IOException, InterruptedException {

        InetSocketAddress hostAddress = new InetSocketAddress("localhost", 9898);
        SocketChannel client = SocketChannel.open(hostAddress);

        System.out.println("Client... started");

        String threadName = Thread.currentThread().getName();

        String[] messages = new String[]
                {"Thread "+threadName + ": says hello", "Thread "+threadName + ": says hello", "Thread "+threadName + ": says hello"};

        for (int i = 0; i < messages.length; i++) {
            byte[] message = messages[i].getBytes();
            ByteBuffer buffer = ByteBuffer.wrap(message);
            client.write(buffer);
            System.out.println(messages[i]);
            buffer.clear();
            Thread.sleep(5000);
        }
        client.close();
    }

    public static void main(String[] args) throws Exception {

        Runnable client = new Runnable() {
            @Override
            public void run() {
                try {
                    new SocketClientExample().startClient();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        };

        new Thread(client, "client-A").start();
        new Thread(client, "client-B").start();
    }

}