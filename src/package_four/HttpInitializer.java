package package_four;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        // 获取通道
        ChannelPipeline p = channel.pipeline();

        // 添加http加解码器
        p.addLast(new HttpResponseEncoder());

        p.addLast(new HttpRequestDecoder());
        p.addLast(new HttpHandler());
    }
}
