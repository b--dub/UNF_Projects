import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class Server {

    // All threads can access this variable to track how many are still open
    public static volatile int threadCount = 0;
    private static final String         // server diagnostic messages
            WAITING = "Waiting for a new connection...",
            ACCEPTING = "Accepting new connection: ";
    private static int connectionNumber = 0;    // Track number of connections


    public static void main(String[] args) throws Exception {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = null;
        for (int i=0; i<50; ++i) {
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(5000+i));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        }

	System.out.println(WAITING);	

        while (true) {
            selector.select();
            Iterator iterator = selector.selectedKeys().iterator();
            //Set selectorKeys = selector.selectedKeys();
            //Iterator iterator = selectorKeys.iterator();

            while (iterator.hasNext()) {
                SelectionKey selectionKey = (SelectionKey)iterator.next();
                iterator.remove();

                if (selectionKey.isAcceptable()) {
                    ServerSocketChannel serverSocketChannel1 = (ServerSocketChannel)selectionKey.channel();

                    SocketChannel socketChannel = serverSocketChannel1.accept();


                    if (socketChannel != null) {
			System.out.println(ACCEPTING);
                        Socket socket = socketChannel.socket();
                        new Thread(new ServerThread(++threadCount, ++connectionNumber, socket)).start();
                    }



                }

            }

        }

    }
}
