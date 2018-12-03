package package_four;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * @program: java-test-1
 * @description: netty搭建http服务器demo
 * @author: shydesky@gmail.com
 * @create: 2018-08-20
 **/

public class HttpServer {

    private static final int PORT = 8882;
    private ServerBootstrap serverBootstrap;

    private EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private EventLoopGroup workerGroup = new NioEventLoopGroup();

    public void open() throws InterruptedException {

        serverBootstrap = new ServerBootstrap();
        serverBootstrap.option(ChannelOption.SO_BACKLOG, 1024);
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new HttpInitializer());

        Channel ch = serverBootstrap.bind(PORT).sync().channel();

        System.err.printf("访问地址 http://127.0.0.1:%d/'", PORT);

        ch.closeFuture().sync();

    }

    public void close() {
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }

    public static void main(String args[]) {

        HttpServer server = new HttpServer();

        try {
            server.open();
        } catch (InterruptedException e) {
            System.out.println("出错了!");
        }
        server.close();
    }
}