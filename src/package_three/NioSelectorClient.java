package package_three;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * @program: java-test-1
 * @description:
 * @author: shydesky@gmail.com
 * @create: 2018-08-20
 **/

public class NioSelectorClient {

    /**
     * @author lihzh
     * @throws IOException
     * @alia OneCoder
     */
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);
        channel.connect(new InetSocketAddress("127.0.0.1", 8000));
    }
}