import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;

public class Test {
	public static void main(String[]args) throws IOException {
		
		RandomAccessFile stream = new RandomAccessFile("clientData.txt", "rw");
	    FileChannel channel = stream.getChannel();
	 
	    FileLock lock = null;
	    try {
	        lock = channel.tryLock();
	    } catch (final OverlappingFileLockException e) {
	        stream.close();
	        channel.close();
	    }
	    stream.writeBytes("test lock\r\n");
	    lock.release();
	 
	    stream.close();
	    channel.close();
		
		
		
	}
}
